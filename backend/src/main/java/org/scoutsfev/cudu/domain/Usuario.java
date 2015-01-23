package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import org.hibernate.annotations.Immutable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "asociado")
public class Usuario extends AsociadoAbstracto implements UserDetails {

    @Column(length = 255, nullable = true, insertable = false, updatable = false)
    @JsonIgnore
    private String password;

    @Embedded
    private Restricciones restricciones = new Restricciones();

    @Column(length = 3)
    private String lenguaje;

    @Column(nullable = false, columnDefinition = "boolean default false")
    @JsonIgnore
    private boolean requiereCaptcha = false;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "grupo_id", referencedColumnName = "id")
    protected Grupo grupo;

    protected Usuario() { }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return usuarioActivo && !Strings.isNullOrEmpty(password);
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    public Restricciones getRestricciones() {
        return restricciones;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public boolean isRequiereCaptcha() {
        return requiereCaptcha;
    }

    public Grupo getGrupo() {
        return grupo;
    }
}
