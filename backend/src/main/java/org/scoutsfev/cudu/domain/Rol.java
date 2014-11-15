package org.scoutsfev.cudu.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Rol implements GrantedAuthority {

    @Column(name = "authority")
    private String rol;

    protected Rol() { }

    public Rol(String rol) {
        this.rol = rol;
    }

    @Override
    public String getAuthority() {
        return rol;
    }
}
