package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class Usuario implements Serializable, UserDetails {
	private static long serialVersionUID = 7942729009777318706L;

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    @Id
    private String username;
    private String password;
    private boolean enabled;

    @Column(name = "fullname")
    private String nombreCompleto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idGrupo", referencedColumnName = "id")
    private Grupo grupo;

    @OneToMany(mappedBy="usuario", targetEntity = Authority.class)
    private Collection<GrantedAuthority> authorities;


    public String getNombreCompleto() {
            return nombreCompleto;
    }

    public Grupo getGrupo() {
            return this.grupo;
    }

    public void setGrupo(Grupo grupo) {
            this.grupo = grupo;
    }

    @Override
    public String getUsername() {
            return username;
    }

    public void setUsername(String userName) {
            username = userName;
    }

    @Override
    public String getPassword() {
            return password;
    }

    @Transient
    private Log logger = LogFactory.getLog(getClass());

    @Override
    public boolean isAccountNonExpired() {
            // TODO Auto-generated method stub
            return true;
    }

    @Override
    public boolean isAccountNonLocked() {
            // TODO Auto-generated method stub
            return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
            // TODO Auto-generated method stub
            return true;
    }

    @Override
    public boolean isEnabled() {
            return enabled;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
            return this.authorities;
    }

    public void getAuthorities(Collection<GrantedAuthority> authorities) {
            this.authorities = authorities;
    }

    /**
     * Obtiene el usuario actual a partir del contexto de seguridad de Spring.
     * 
     * @return Usuario actualmente autenticado en la aplicación.
     */
    public static Usuario obtenerActual() {
            return (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Obtiene el nombre de usuario actual previamente autenticado.
     * @return Login del usuario
     */
    public static String obtenerLoginActual() {
            return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * @param logger the logger to set
     */
    public void setLogger(Log logger) {
        this.logger = logger;
    }
}
