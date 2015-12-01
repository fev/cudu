package org.scoutsfev.cudu.services;

import org.springframework.context.ApplicationEvent;

public class EmailErrorApplicationEvent extends ApplicationEvent {

    private final EmailErrorEvent event;

    public EmailErrorApplicationEvent(String email, String correlationId) {
        this(new EmailErrorEvent(email, correlationId));
    }

    public EmailErrorApplicationEvent(EmailErrorEvent event) {
        super(event);
        this.event = event;
    }

    public EmailErrorEvent getEvent() {
        return event;
    }
}

