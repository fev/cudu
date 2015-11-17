package org.scoutsfev.cudu.domain;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

@Entity
public class Token {

    @Id
    @Size(min = 26, max = 26)
    private String token;

    @NotNull
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @Column(insertable = true, updatable = false)
    private Timestamp creado;

    @Transient
    private String password;

    @NotNull
    @Min(0)
    private long duracionEnSegundos = 0;

    @Transient
    private Short score = 0;

    protected Token() { }

    public Token(String email, String token, Instant now, Duration duracion) {
        this.email = email;
        this.token = token;
        this.duracionEnSegundos = duracion.getSeconds();
        this.creado = Timestamp.from(now);
    }

    public boolean expirado(Instant now) {
        Duration duracion = Duration.ofSeconds(duracionEnSegundos);
        Instant limite = creado.toInstant().plus(duracion);
        return now.isAfter(limite);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getCreado() {
        return creado;
    }

    public void setCreado(Timestamp creado) {
        this.creado = creado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getDuracionEnSegundos() {
        return duracionEnSegundos;
    }

    public void setDuracionEnSegundos(long duracionEnSegundos) {
        this.duracionEnSegundos = duracionEnSegundos;
    }

    public Short getScore() {
        return score;
    }

    public void setScore(Short score) {
        this.score = score;
    }
}
