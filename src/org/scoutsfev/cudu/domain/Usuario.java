package org.scoutsfev.cudu.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class Usuario implements Serializable, UserDetails {
	private static final long serialVersionUID = 7942729009777318706L;

	@Id
	private String username;
	private String password;
	private boolean enabled;

	private String nombreCompleto;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idGrupo", referencedColumnName = "id")
	private Grupo grupo;

	@OneToMany(mappedBy="usuario", targetEntity = Authority.class)
	private Collection<GrantedAuthority> authorities;
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	
	public void setNombreCompleto(String fullname) {
		nombreCompleto = fullname;
	}

	public void setAccountNonExpired(boolean expired){}
	
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public void setAccountNonLocked(boolean expired){}
	
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	public void setCredentialsNonExpired(boolean expired){}
	
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		return enabled;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
