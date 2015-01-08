package org.scoutsfev.cudu.services;

import org.springframework.security.authentication.AccountStatusException;

public class InvalidCaptchaException extends AccountStatusException {

    public static final String VERIFIED_CAPTCHA_EVENT = "VERIFIED_CAPTCHA";
    public static final String INVALID_CAPTCHA_EVENT = "INVALID_CAPTCHA";
    public static final String MISSING_CAPTCHA_EVENT = "MISSING_CAPTCHA";

    public InvalidCaptchaException(String msg) {
        super(msg);
    }

    public InvalidCaptchaException(String msg, Throwable t) {
        super(msg, t);
    }
}
