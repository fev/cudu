package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.domain.VerificacionCaptcha;

public interface CaptchaService {

    public VerificacionCaptcha verificar(String respuesta, String direccionIp);
}
