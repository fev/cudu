/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.dd.tables.pojos;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Asociado implements Serializable {

	private static final long serialVersionUID = -36792612;

	private final Integer   id;
	private final String    tipo;
	private final String    grupoId;
	private final String    nombre;
	private final String    apellidos;
	private final String    genero;
	private final Date      fechaNacimiento;
	private final String    dni;
	private final String    seguridadSocial;
	private final Boolean   tieneSeguroPrivado;
	private final String    direccion;
	private final Integer   codigoPostal;
	private final String    telefonoCasa;
	private final String    telefonoMovil;
	private final String    email;
	private final String    municipio;
	private final Boolean   tieneTutorLegal;
	private final Boolean   padresDivorciados;
	private final String    padreNombre;
	private final String    padreTelefono;
	private final String    padreEmail;
	private final String    madreNombre;
	private final String    madreTelefono;
	private final String    madreEmail;
	private final Timestamp fechaAlta;
	private final Timestamp fechaBaja;
	private final Timestamp fechaActualizacion;
	private final Boolean   ramaColonia;
	private final Boolean   ramaManada;
	private final Boolean   ramaExploradores;
	private final Boolean   ramaExpedicion;
	private final Boolean   ramaRuta;
	private final Boolean   activo;
	private final Boolean   usuarioActivo;
	private final String    password;
	private final Boolean   requiereCaptcha;
	private final String    lenguaje;
	private final String    ambitoEdicion;
	private final Integer   restriccionAsociacion;
	private final Boolean   noPuedeEditarDatosDelGrupo;
	private final Boolean   noPuedeEditarOtrasRamas;
	private final Boolean   soloLectura;
	private final String    estudios;
	private final String    profesion;
	private final Boolean   hermanosEnElGrupo;
	private final Timestamp fechaUsuarioCreado;
	private final Timestamp fechaUsuarioVisto;
	private final Integer   usuarioCreadoPorId;
	private final String    usuarioCreadoPorNombre;
	private final String    notas;
	private final String    emailContacto;
	private final Short     calidadPassword;
	private final Boolean   certificadoDelitosSexuales;
	private final Boolean   cursoCovid;
	private final Boolean   certificadoVoluntariado;

	public Asociado(Asociado value) {
		this.id = value.id;
		this.tipo = value.tipo;
		this.grupoId = value.grupoId;
		this.nombre = value.nombre;
		this.apellidos = value.apellidos;
		this.genero = value.genero;
		this.fechaNacimiento = value.fechaNacimiento;
		this.dni = value.dni;
		this.seguridadSocial = value.seguridadSocial;
		this.tieneSeguroPrivado = value.tieneSeguroPrivado;
		this.direccion = value.direccion;
		this.codigoPostal = value.codigoPostal;
		this.telefonoCasa = value.telefonoCasa;
		this.telefonoMovil = value.telefonoMovil;
		this.email = value.email;
		this.municipio = value.municipio;
		this.tieneTutorLegal = value.tieneTutorLegal;
		this.padresDivorciados = value.padresDivorciados;
		this.padreNombre = value.padreNombre;
		this.padreTelefono = value.padreTelefono;
		this.padreEmail = value.padreEmail;
		this.madreNombre = value.madreNombre;
		this.madreTelefono = value.madreTelefono;
		this.madreEmail = value.madreEmail;
		this.fechaAlta = value.fechaAlta;
		this.fechaBaja = value.fechaBaja;
		this.fechaActualizacion = value.fechaActualizacion;
		this.ramaColonia = value.ramaColonia;
		this.ramaManada = value.ramaManada;
		this.ramaExploradores = value.ramaExploradores;
		this.ramaExpedicion = value.ramaExpedicion;
		this.ramaRuta = value.ramaRuta;
		this.activo = value.activo;
		this.usuarioActivo = value.usuarioActivo;
		this.password = value.password;
		this.requiereCaptcha = value.requiereCaptcha;
		this.lenguaje = value.lenguaje;
		this.ambitoEdicion = value.ambitoEdicion;
		this.restriccionAsociacion = value.restriccionAsociacion;
		this.noPuedeEditarDatosDelGrupo = value.noPuedeEditarDatosDelGrupo;
		this.noPuedeEditarOtrasRamas = value.noPuedeEditarOtrasRamas;
		this.soloLectura = value.soloLectura;
		this.estudios = value.estudios;
		this.profesion = value.profesion;
		this.hermanosEnElGrupo = value.hermanosEnElGrupo;
		this.fechaUsuarioCreado = value.fechaUsuarioCreado;
		this.fechaUsuarioVisto = value.fechaUsuarioVisto;
		this.usuarioCreadoPorId = value.usuarioCreadoPorId;
		this.usuarioCreadoPorNombre = value.usuarioCreadoPorNombre;
		this.notas = value.notas;
		this.emailContacto = value.emailContacto;
		this.calidadPassword = value.calidadPassword;
		this.certificadoDelitosSexuales = value.certificadoDelitosSexuales;
		this.cursoCovid = value.cursoCovid;
		this.certificadoVoluntariado = value.certificadoVoluntariado;
	}

	public Asociado(
		Integer   id,
		String    tipo,
		String    grupoId,
		String    nombre,
		String    apellidos,
		String    genero,
		Date      fechaNacimiento,
		String    dni,
		String    seguridadSocial,
		Boolean   tieneSeguroPrivado,
		String    direccion,
		Integer   codigoPostal,
		String    telefonoCasa,
		String    telefonoMovil,
		String    email,
		String    municipio,
		Boolean   tieneTutorLegal,
		Boolean   padresDivorciados,
		String    padreNombre,
		String    padreTelefono,
		String    padreEmail,
		String    madreNombre,
		String    madreTelefono,
		String    madreEmail,
		Timestamp fechaAlta,
		Timestamp fechaBaja,
		Timestamp fechaActualizacion,
		Boolean   ramaColonia,
		Boolean   ramaManada,
		Boolean   ramaExploradores,
		Boolean   ramaExpedicion,
		Boolean   ramaRuta,
		Boolean   activo,
		Boolean   usuarioActivo,
		String    password,
		Boolean   requiereCaptcha,
		String    lenguaje,
		String    ambitoEdicion,
		Integer   restriccionAsociacion,
		Boolean   noPuedeEditarDatosDelGrupo,
		Boolean   noPuedeEditarOtrasRamas,
		Boolean   soloLectura,
		String    estudios,
		String    profesion,
		Boolean   hermanosEnElGrupo,
		Timestamp fechaUsuarioCreado,
		Timestamp fechaUsuarioVisto,
		Integer   usuarioCreadoPorId,
		String    usuarioCreadoPorNombre,
		String    notas,
		String    emailContacto,
		Short     calidadPassword,
		Boolean   certificadoDelitosSexuales,
		Boolean   cursoCovid,
		Boolean   certificadoVoluntariado
	) {
		this.id = id;
		this.tipo = tipo;
		this.grupoId = grupoId;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.genero = genero;
		this.fechaNacimiento = fechaNacimiento;
		this.dni = dni;
		this.seguridadSocial = seguridadSocial;
		this.tieneSeguroPrivado = tieneSeguroPrivado;
		this.direccion = direccion;
		this.codigoPostal = codigoPostal;
		this.telefonoCasa = telefonoCasa;
		this.telefonoMovil = telefonoMovil;
		this.email = email;
		this.municipio = municipio;
		this.tieneTutorLegal = tieneTutorLegal;
		this.padresDivorciados = padresDivorciados;
		this.padreNombre = padreNombre;
		this.padreTelefono = padreTelefono;
		this.padreEmail = padreEmail;
		this.madreNombre = madreNombre;
		this.madreTelefono = madreTelefono;
		this.madreEmail = madreEmail;
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
		this.fechaActualizacion = fechaActualizacion;
		this.ramaColonia = ramaColonia;
		this.ramaManada = ramaManada;
		this.ramaExploradores = ramaExploradores;
		this.ramaExpedicion = ramaExpedicion;
		this.ramaRuta = ramaRuta;
		this.activo = activo;
		this.usuarioActivo = usuarioActivo;
		this.password = password;
		this.requiereCaptcha = requiereCaptcha;
		this.lenguaje = lenguaje;
		this.ambitoEdicion = ambitoEdicion;
		this.restriccionAsociacion = restriccionAsociacion;
		this.noPuedeEditarDatosDelGrupo = noPuedeEditarDatosDelGrupo;
		this.noPuedeEditarOtrasRamas = noPuedeEditarOtrasRamas;
		this.soloLectura = soloLectura;
		this.estudios = estudios;
		this.profesion = profesion;
		this.hermanosEnElGrupo = hermanosEnElGrupo;
		this.fechaUsuarioCreado = fechaUsuarioCreado;
		this.fechaUsuarioVisto = fechaUsuarioVisto;
		this.usuarioCreadoPorId = usuarioCreadoPorId;
		this.usuarioCreadoPorNombre = usuarioCreadoPorNombre;
		this.notas = notas;
		this.emailContacto = emailContacto;
		this.calidadPassword = calidadPassword;
		this.certificadoDelitosSexuales = certificadoDelitosSexuales;
		this.cursoCovid = cursoCovid;
		this.certificadoVoluntariado = certificadoVoluntariado;
	}

	public Integer getId() {
		return this.id;
	}

	public String getTipo() {
		return this.tipo;
	}

	public String getGrupoId() {
		return this.grupoId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public String getGenero() {
		return this.genero;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public String getDni() {
		return this.dni;
	}

	public String getSeguridadSocial() {
		return this.seguridadSocial;
	}

	public Boolean getTieneSeguroPrivado() {
		return this.tieneSeguroPrivado;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public Integer getCodigoPostal() {
		return this.codigoPostal;
	}

	public String getTelefonoCasa() {
		return this.telefonoCasa;
	}

	public String getTelefonoMovil() {
		return this.telefonoMovil;
	}

	public String getEmail() {
		return this.email;
	}

	public String getMunicipio() {
		return this.municipio;
	}

	public Boolean getTieneTutorLegal() {
		return this.tieneTutorLegal;
	}

	public Boolean getPadresDivorciados() {
		return this.padresDivorciados;
	}

	public String getPadreNombre() {
		return this.padreNombre;
	}

	public String getPadreTelefono() {
		return this.padreTelefono;
	}

	public String getPadreEmail() {
		return this.padreEmail;
	}

	public String getMadreNombre() {
		return this.madreNombre;
	}

	public String getMadreTelefono() {
		return this.madreTelefono;
	}

	public String getMadreEmail() {
		return this.madreEmail;
	}

	public Timestamp getFechaAlta() {
		return this.fechaAlta;
	}

	public Timestamp getFechaBaja() {
		return this.fechaBaja;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public Boolean getRamaColonia() {
		return this.ramaColonia;
	}

	public Boolean getRamaManada() {
		return this.ramaManada;
	}

	public Boolean getRamaExploradores() {
		return this.ramaExploradores;
	}

	public Boolean getRamaExpedicion() {
		return this.ramaExpedicion;
	}

	public Boolean getRamaRuta() {
		return this.ramaRuta;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public Boolean getUsuarioActivo() {
		return this.usuarioActivo;
	}

	public String getPassword() {
		return this.password;
	}

	public Boolean getRequiereCaptcha() {
		return this.requiereCaptcha;
	}

	public String getLenguaje() {
		return this.lenguaje;
	}

	public String getAmbitoEdicion() {
		return this.ambitoEdicion;
	}

	public Integer getRestriccionAsociacion() {
		return this.restriccionAsociacion;
	}

	public Boolean getNoPuedeEditarDatosDelGrupo() {
		return this.noPuedeEditarDatosDelGrupo;
	}

	public Boolean getNoPuedeEditarOtrasRamas() {
		return this.noPuedeEditarOtrasRamas;
	}

	public Boolean getSoloLectura() {
		return this.soloLectura;
	}

	public String getEstudios() {
		return this.estudios;
	}

	public String getProfesion() {
		return this.profesion;
	}

	public Boolean getHermanosEnElGrupo() {
		return this.hermanosEnElGrupo;
	}

	public Timestamp getFechaUsuarioCreado() {
		return this.fechaUsuarioCreado;
	}

	public Timestamp getFechaUsuarioVisto() {
		return this.fechaUsuarioVisto;
	}

	public Integer getUsuarioCreadoPorId() {
		return this.usuarioCreadoPorId;
	}

	public String getUsuarioCreadoPorNombre() {
		return this.usuarioCreadoPorNombre;
	}

	public String getNotas() {
		return this.notas;
	}

	public String getEmailContacto() {
		return this.emailContacto;
	}

	public Short getCalidadPassword() {
		return this.calidadPassword;
	}

	public Boolean getCertificadoDelitosSexuales() {
		return this.certificadoDelitosSexuales;
	}

	public Boolean getCursoCovid() {
		return this.cursoCovid;
	}

	public Boolean getCertificadoVoluntariado() {
		return this.certificadoVoluntariado;
	}
}
