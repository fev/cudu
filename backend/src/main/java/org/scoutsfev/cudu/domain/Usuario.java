package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import org.hibernate.annotations.Immutable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "asociado")
public class Usuario extends AsociadoAbstracto implements UserDetails {

    @Column(length = 255, nullable = true)
    @JsonIgnore
    private String password;

    @ElementCollection(targetClass = Rol.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "usuario_id"))
    protected Collection<GrantedAuthority> roles;

    protected Usuario() { }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !Strings.isNullOrEmpty(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }
}
