package org.scoutsfev.cudu.domain;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Entity
public class Usuario implements UserDetails {

    @Id
    @Column(name = "usuario", length = 64)
    private String usuario;

    @NotNull
    @Column(length = 255)
    private String password;

    @NotNull
    private boolean habilitado;

    @NotNull
    @Column(length = 128)
    private String nombreCompleto;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "grupo_id", referencedColumnName = "id")
    private Grupo grupo;

    @ElementCollection(targetClass = Rol.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "usuario_id"))
    protected Collection<GrantedAuthority> roles;

    protected Usuario() { }

    public Usuario(String usuario, String password, String nombreCompleto, boolean habilitado, String[] roles) {
        this.usuario = usuario;
        this.password = password;
        this.habilitado = habilitado;
        this.nombreCompleto = nombreCompleto;

        if (roles == null)
            this.roles = new ArrayList<>();
        else {
            this.roles = Collections2.transform(Arrays.asList(roles), new Function<String, GrantedAuthority>() {
                @Override
                public Rol apply(String rol) {
                    return new Rol(rol);
                }
            });
        }
    }

    @Override
    public String getUsername() {
        return usuario;
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
        return habilitado;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return usuario;
    }
}
