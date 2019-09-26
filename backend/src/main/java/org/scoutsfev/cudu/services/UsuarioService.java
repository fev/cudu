package org.scoutsfev.cudu.services;

import com.google.common.base.Strings;
import org.scoutsfev.cudu.domain.*;
import org.scoutsfev.cudu.storage.AsociadoRepository;
import org.scoutsfev.cudu.storage.TokenRepository;
import org.scoutsfev.cudu.storage.UsuarioRepository;
import org.scoutsfev.cudu.storage.UsuarioStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;

@Service
public class UsuarioService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final SecureRandom secureRandom;
    private final AsociadoRepository asociadoRepository;
    private final EmailService emailService;
    private final CaptchaService captchaService;

    // TODO Mover @Value a clase separada con @ConfigurationProperties
    // http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-typesafe-configuration-properties

    @Value("${cudu.reset.duracionTokenEnSegundos}")
    private final int duracionTokenEnSegundos = 3600;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, TokenRepository tokenRepository, AsociadoRepository asociadoRepository,
                          EmailService emailService, CaptchaService captchaService, ApplicationEventPublisher eventPublisher)
            throws NoSuchAlgorithmException {
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.eventPublisher = eventPublisher;
        this.asociadoRepository = asociadoRepository;
        this.emailService = emailService;
        this.captchaService = captchaService;
        //this.secureRandom = SecureRandom.getInstanceStrong();
        this.secureRandom = SecureRandom.getInstance("SHA1PRNG");
        int intValue = 232323;
        byte[] byteValue = new byte[] {
            (byte)(intValue >>> 24),
            (byte)(intValue >>> 16),
            (byte)(intValue >>> 8),
            (byte)intValue};
        secureRandom.nextBytes(byteValue);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (logger.isDebugEnabled())
            logger.debug("Cargando datos para usuario '{}'.", username);
        if (!Strings.isNullOrEmpty(username)) {
            Usuario usuario = usuarioRepository.findByEmail(username);
            if (usuario != null) {
                if (!usuario.isUsuarioActivo()) {
                    throw new DisabledException("El usuario está desactivado.");
                }
                return usuario;
            }
        }
        throw new UsernameNotFoundException("El usuario especificado no existe.");
    }

    public void nuevaApikey(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null)
            throw new UsernameNotFoundException("Imposible encontrar al usuario: " + email);

        if (!usuario.isActivo() ) {
            throw new AccountExpiredException("El asociado " + email + " está desactivado.");
        }
        secureRandom.generateSeed(25);
        String oneTimeCode = new BigInteger(130, secureRandom).toString(32);
        Duration duracionToken = Duration.ofDays(3650);
        Token token = new Token(usuario.getEmail(), oneTimeCode, Instant.now(), duracionToken);
        tokenRepository.save(token);
        eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.NuevaApikey));
        Locale locale;
        if (usuario.getLenguaje() == null)
            locale = Locale.forLanguageTag("es");
        else
            locale = Locale.forLanguageTag(usuario.getLenguaje());
        emailService.enviarMailNuevaApikey(usuario.getNombre(), usuario.getEmail(), token.getToken(), locale);
    }

    public void resetPassword(String email, boolean comprobarQueElUsuarioEstaActivo) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null)
            throw new UsernameNotFoundException("Imposible encontrar al usuario: " + email);

        if (comprobarQueElUsuarioEstaActivo && (!usuario.isActivo() || !usuario.isUsuarioActivo())) {
            throw new AccountExpiredException("El usuario " + email + " está desactivado.");
        }
        secureRandom.generateSeed(23);
        String oneTimeCode = new BigInteger(130, secureRandom).toString(32);
        Duration duracionToken = Duration.ofSeconds(duracionTokenEnSegundos);
        Token token = new Token(usuario.getEmail(), oneTimeCode, Instant.now(), duracionToken);
        tokenRepository.save(token);
        eventPublisher.publishEvent(new AuditApplicationEvent(usuario.getEmail(), EventosAuditoria.ResetPassword));
        Locale locale;
        if (usuario.getLenguaje() == null)
            locale = Locale.forLanguageTag("es");
        else
            locale = Locale.forLanguageTag(usuario.getLenguaje());
        emailService.enviarMailCambioContraseña(usuario.getNombre(), usuario.getEmail(), token.getToken(), locale);
    }

    public void desactivarUsuario(int asociadoId, boolean desactivarAsociado) {
        usuarioRepository.desactivar(asociadoId);
        tokenRepository.eliminarTodos(asociadoId);
        if (desactivarAsociado)
            asociadoRepository.activar(asociadoId, false);
    }

    public boolean existeActivacionEnCurso(String email) {
        Token token = tokenRepository.findByEmail(email);
        if (token == null)
            return false;
        if (token.expirado(Instant.now())) {
            tokenRepository.delete(token);
            return false;
        }
        return true;
    }

    public String cambiarPassword(Token token) {
        if (token == null || Strings.isNullOrEmpty(token.getToken()))
            return logError("El token es nulo o vacio al cambiar el password.", token);

        Token original = tokenRepository.findOne(token.getToken());
        if (original == null || original.expirado(Instant.now()))
            return logError("El token está expirado al cambiar el password.", token);

        if (!original.getEmail().equals(token.getEmail()))
            return logError("El email del token no coincide con el original '" + original.getEmail() + "'.", token);

        Usuario usuario = usuarioRepository.findByEmail(original.getEmail());
        if (usuario == null)
            return logError("El token existe en base de datos, pero no existe ningún usuario con ese email.", token);

        usuarioRepository.activar(usuario.getId(), token.getPassword(), token.getScore());
        tokenRepository.delete(original);

        eventPublisher.publishEvent(new AuditApplicationEvent(token.getEmail(), EventosAuditoria.CambioPassword, token.getToken()));
        // TODO emailService.SendEmail("Luis Belloch", "luisbelloch@gmail.com", Locale.ENGLISH);
        return null;
    }

    public String nuevaApikeyValidacion(Token token) {
        if (token == null || Strings.isNullOrEmpty(token.getToken()))
            return logError("El token es nulo o vacio al cambiar el password.", token);

        Token original = tokenRepository.findOne(token.getToken());
        if (original == null || original.expirado(Instant.now()))
            return logError("El token está expirado al cambiar el password.", token);

        if (!original.getEmail().equals(token.getEmail()))
            return logError("El email del token no coincide con el original '" + original.getEmail() + "'.", token);

        Usuario usuario = usuarioRepository.findByEmail(original.getEmail());
        if (usuario == null)
            return logError("El token existe en base de datos, pero no existe ningún usuario con ese email.", token);

        eventPublisher.publishEvent(new AuditApplicationEvent(token.getEmail(), EventosAuditoria.NuevaApikey, token.getToken()));
        // TODO emailService.SendEmail("Luis Belloch", "luisbelloch@gmail.com", Locale.ENGLISH);
        return null;
    }

    public void marcarEntrada(String email) {
        usuarioRepository.marcarEntrada(email, Timestamp.valueOf(LocalDateTime.now()));
    }

    public void marcarCaptcha(String email, boolean positivo) {
        usuarioRepository.marcarCaptcha(email, positivo);
    }

    public void comprobarCaptcha(Credenciales credenciales, HttpServletRequest request) throws InvalidCaptchaException {
        String direccionIp = obtenerDireccionIp(request);
        if (!credenciales.isForzarComprobacion() && !usuarioRepository.requiereCaptcha(credenciales.getEmail()).orElse(false))
            return;

        if (Strings.isNullOrEmpty(credenciales.getCaptcha())) {
            eventPublisher.publishEvent(new AuditApplicationEvent(credenciales.getEmail(), EventosAuditoria.CaptchaVacio));
            throw new InvalidCaptchaException("No se ha podido verificar el código captcha proveniente del cliente porque era nulo o vacio.");
        }

        VerificacionCaptcha verificacion = captchaService.verificar(credenciales.getCaptcha(), direccionIp);
        if (!verificacion.isPositiva()) {
            HashMap<String, Object> datosAddicionales = new HashMap<>();
            datosAddicionales.put("direccionIp", direccionIp);
            datosAddicionales.put("codigosDeError", verificacion.getCodigosDeError());
            eventPublisher.publishEvent(new AuditApplicationEvent(credenciales.getEmail(), EventosAuditoria.CaptchaInvalido, datosAddicionales));
            throw new InvalidCaptchaException("Ha fallado la verificación de captcha.");
        }
        eventPublisher.publishEvent(new AuditApplicationEvent(credenciales.getEmail(), EventosAuditoria.CaptchaVerificado, direccionIp));
    }

    private String obtenerDireccionIp(HttpServletRequest request) {
        String direccionIp = request.getHeader("X-FORWARDED-FOR");
        if (direccionIp == null)
            direccionIp = request.getRemoteAddr();
        return direccionIp;
    }

    // TODO establecerRestricciones + audit event + email
    // TODO cambiarIdioma(idAsociado, codigo)
    // TODO Test: solo se marca como que requiere captcha cuando se lanza BadCredentialsException, el resto se delegan a spring

    private String logError(String mensaje, Token token) {
        secureRandom.generateSeed(24);
        String codigoError = "E" + Strings.padStart(new BigInteger(16, secureRandom).toString(16).toUpperCase(), 4, '0');
        if (token != null)
            logger.error("[{0}] {1}. Token {2}, email: {3}, creado: {4}", codigoError, mensaje, token.getToken(), token.getEmail(), token.getCreado());
        else
            logger.error("[{0}] {1}. Token NULL", codigoError, mensaje);
        return codigoError;
    }

    public boolean cambiarIdioma(Usuario usuario, String codigo) {
        if (Strings.isNullOrEmpty(codigo) || (!codigo.equalsIgnoreCase("es") && !codigo.equalsIgnoreCase("ca")))
            return false;
        usuarioRepository.cambiarIdioma(usuario.getId(), codigo);
        // Al recargar la página, la ruta /actual no toca BBDD, saca el usuario del contexto actual
        // por lo que no contiene los cambios realizados. Rellenamos la propiedad con el nuevo codigo.
        usuario.setLenguaje(codigo);
        return true;
    }

    public Boolean existeOtroUsuario(Integer asociadoId, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        return usuario != null && !usuario.getId().equals(asociadoId);
    }
}
