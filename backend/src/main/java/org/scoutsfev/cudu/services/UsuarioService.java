package org.scoutsfev.cudu.services;

import com.google.common.base.Strings;
import org.scoutsfev.cudu.domain.Token;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.storage.TokenRepository;
import org.scoutsfev.cudu.storage.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;

@Service
public class UsuarioService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final SecureRandom secureRandom;

    //    private final EmailService emailService;
    //    private final ApplicationEventPublisher applicationEventPublisher;

    @Value("${cudu.reset.duracionTokenEnSegundos}")
    private final int duracionTokenEnSegundos = 600;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, TokenRepository tokenRepository) throws NoSuchAlgorithmException {
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.secureRandom = SecureRandom.getInstanceStrong();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (logger.isDebugEnabled())
            logger.debug("Cargando datos para usuario '{}'.", username);
        if (!Strings.isNullOrEmpty(username)) {
            Usuario usuario = usuarioRepository.findByEmail(username);
            if (usuario != null)
                return usuario;
        }
        throw new UsernameNotFoundException("El usuario especificado no existe.");
    }

    public void resetPassword(String email) throws MessagingException {
        String oneTimeCode = new BigInteger(130, secureRandom).toString(32);
        Duration duracionToken = Duration.ofSeconds(duracionTokenEnSegundos);
        Token token = new Token(email, oneTimeCode, Instant.now(), duracionToken);
        tokenRepository.save(token);
        // TODO AuditEvent
        // TODO emailService.SendEmail("Luis Belloch", "luisbelloch@gmail.com", Locale.ENGLISH);
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

        usuarioRepository.activar(usuario.getId(), token.getPassword());
        tokenRepository.delete(original);

        // TODO AuditApplicationEvent event = new AuditApplicationEvent(token.getEmail(), EventosAuditoria.CambioPassword, token.getToken());
        // TODO emailService.SendEmail("Luis Belloch", "luisbelloch@gmail.com", Locale.ENGLISH);
        return null;
    }

    // TODO establecerRestricciones + audit event + email
    // TODO cambiarIdioma(idAsociado, codigo)

    private String logError(String mensaje, Token token) {
        String codigoError = "E" + Strings.padStart(new BigInteger(16, secureRandom).toString(16).toUpperCase(), 4, '0');
        if (token != null)
            logger.error("[{0}] {1}. Token {2}, email: {3}, creado: {4}", codigoError, mensaje, token.getToken(), token.getEmail(), token.getCreado());
        else
            logger.error("[{0}] {1}. Token NULL", codigoError, mensaje);
        return codigoError;
    }
}
