package org.scoutsfev.cudu.services;

import java.io.Serializable;

public class EmailErrorEvent implements Serializable {
    public final String email;
    public final String correlationId;

    public EmailErrorEvent(String email, String correlationId) {
        this.email = email;
        this.correlationId = correlationId;
    }
}
