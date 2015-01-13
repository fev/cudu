package org.scoutsfev.cudu.services;

import org.springframework.security.authentication.AccountStatusException;

public class InvalidCaptchaException extends AccountStatusException {

    public InvalidCaptchaException(String msg) {
        super(msg);
    }

    public InvalidCaptchaException(String msg, Throwable t) {
        super(msg, t);
    }
}
