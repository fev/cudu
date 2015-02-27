package org.scoutsfev.cudu.services;

import com.google.common.base.Strings;
import org.scoutsfev.cudu.domain.VerificacionCaptcha;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Profile("production")
public class CaptchaServiceImpl implements CaptchaService {

    @Value("${cudu.captcha.url}")
    private final String captchaUrl = "https://www.google.com/recaptcha/api/siteverify?secret={secret}&response={response}&remoteip={remoteip}";

    @Value("${cudu.captcha.secret}")
    private final String captchaSecret = null;

    private final RestTemplate restTemplate;

    public CaptchaServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public VerificacionCaptcha verificar(String respuesta, String direccionIp) {
        if (Strings.isNullOrEmpty(respuesta)) {
            return VerificacionCaptcha.Negativa("No es posible verificar el captcha.");
        }
        return restTemplate.getForObject(captchaUrl, VerificacionCaptcha.class, captchaSecret, respuesta, direccionIp);
    }
}
