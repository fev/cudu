package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.domain.VerificacionCaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class NullCaptchaServiceImpl implements CaptchaService {

    private static final Logger logger = LoggerFactory.getLogger(NullCaptchaServiceImpl.class);

    @Override
    public VerificacionCaptcha verificar(String respuesta, String direccionIp) {
        logger.info("Verificación de captcha positiva para IP {}", direccionIp);
        return VerificacionCaptcha.Positiva();
    }
}
