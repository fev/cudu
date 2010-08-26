package org.scoutsfev.cudu.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 1224102169403270693L;
	
	@Id
	private String authority;
	
	//@Id
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "username")//, referencedColumnName = "username")
	private Usuario usuario;

	@Override
	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
