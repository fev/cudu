package org.scoutsfev.cudu.domain;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TokenTests {

    @Test
    public void un_token_esta_expirado_cuando_excede_la_duracion() throws Exception {
        Instant now = LocalDateTime.of(2014, 12, 1, 12, 0, 0).toInstant(ZoneOffset.ofHours(0));
        Token token = new Token("email@example.com", "AABBCC", now, Duration.ofMinutes(10));
        assertTrue(token.expirado(now.plus(11, ChronoUnit.MINUTES)));
    }

    @Test
    public void un_token_esta_activo_cuando_no_excede_la_duracion() throws Exception {
        Instant now = LocalDateTime.of(2014, 12, 1, 12, 0, 0).toInstant(ZoneOffset.ofHours(0));
        Token token = new Token("email@example.com", "AABBCC", now, Duration.ofMinutes(10));
        assertFalse(token.expirado(now.plus(5, ChronoUnit.MINUTES)));
    }
}
