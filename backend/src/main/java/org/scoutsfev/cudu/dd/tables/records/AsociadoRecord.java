/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.dd.tables.records;


import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
import org.scoutsfev.cudu.dd.tables.Asociado;


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
public class AsociadoRecord extends UpdatableRecordImpl<AsociadoRecord> {

	private static final long serialVersionUID = 2015445869;

	/**
	 * Setter for <code>public.asociado.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.asociado.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.asociado.tipo</code>.
	 */
	public void setTipo(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.asociado.tipo</code>.
	 */
	public String getTipo() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>public.asociado.grupo_id</code>.
	 */
	public void setGrupoId(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.asociado.grupo_id</code>.
	 */
	public String getGrupoId() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>public.asociado.nombre</code>.
	 */
	public void setNombre(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.asociado.nombre</code>.
	 */
	public String getNombre() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>public.asociado.apellidos</code>.
	 */
	public void setApellidos(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>public.asociado.apellidos</code>.
	 */
	public String getApellidos() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>public.asociado.sexo</code>.
	 */
	public void setSexo(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>public.asociado.sexo</code>.
	 */
	public String getSexo() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>public.asociado.fecha_nacimiento</code>.
	 */
	public void setFechaNacimiento(Date value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>public.asociado.fecha_nacimiento</code>.
	 */
	public Date getFechaNacimiento() {
		return (Date) getValue(6);
	}

	/**
	 * Setter for <code>public.asociado.dni</code>.
	 */
	public void setDni(String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>public.asociado.dni</code>.
	 */
	public String getDni() {
		return (String) getValue(7);
	}

	/**
	 * Setter for <code>public.asociado.seguridad_social</code>.
	 */
	public void setSeguridadSocial(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>public.asociado.seguridad_social</code>.
	 */
	public String getSeguridadSocial() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>public.asociado.tiene_seguro_privado</code>.
	 */
	public void setTieneSeguroPrivado(Boolean value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>public.asociado.tiene_seguro_privado</code>.
	 */
	public Boolean getTieneSeguroPrivado() {
		return (Boolean) getValue(9);
	}

	/**
	 * Setter for <code>public.asociado.direccion</code>.
	 */
	public void setDireccion(String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>public.asociado.direccion</code>.
	 */
	public String getDireccion() {
		return (String) getValue(10);
	}

	/**
	 * Setter for <code>public.asociado.codigo_postal</code>.
	 */
	public void setCodigoPostal(Integer value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>public.asociado.codigo_postal</code>.
	 */
	public Integer getCodigoPostal() {
		return (Integer) getValue(11);
	}

	/**
	 * Setter for <code>public.asociado.telefono_casa</code>.
	 */
	public void setTelefonoCasa(String value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>public.asociado.telefono_casa</code>.
	 */
	public String getTelefonoCasa() {
		return (String) getValue(12);
	}

	/**
	 * Setter for <code>public.asociado.telefono_movil</code>.
	 */
	public void setTelefonoMovil(String value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>public.asociado.telefono_movil</code>.
	 */
	public String getTelefonoMovil() {
		return (String) getValue(13);
	}

	/**
	 * Setter for <code>public.asociado.email</code>.
	 */
	public void setEmail(String value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>public.asociado.email</code>.
	 */
	public String getEmail() {
		return (String) getValue(14);
	}

	/**
	 * Setter for <code>public.asociado.municipio</code>.
	 */
	public void setMunicipio(String value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>public.asociado.municipio</code>.
	 */
	public String getMunicipio() {
		return (String) getValue(15);
	}

	/**
	 * Setter for <code>public.asociado.tiene_tutor_legal</code>.
	 */
	public void setTieneTutorLegal(Boolean value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>public.asociado.tiene_tutor_legal</code>.
	 */
	public Boolean getTieneTutorLegal() {
		return (Boolean) getValue(16);
	}

	/**
	 * Setter for <code>public.asociado.padres_divorciados</code>.
	 */
	public void setPadresDivorciados(Boolean value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>public.asociado.padres_divorciados</code>.
	 */
	public Boolean getPadresDivorciados() {
		return (Boolean) getValue(17);
	}

	/**
	 * Setter for <code>public.asociado.padre_nombre</code>.
	 */
	public void setPadreNombre(String value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>public.asociado.padre_nombre</code>.
	 */
	public String getPadreNombre() {
		return (String) getValue(18);
	}

	/**
	 * Setter for <code>public.asociado.padre_telefono</code>.
	 */
	public void setPadreTelefono(String value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>public.asociado.padre_telefono</code>.
	 */
	public String getPadreTelefono() {
		return (String) getValue(19);
	}

	/**
	 * Setter for <code>public.asociado.padre_email</code>.
	 */
	public void setPadreEmail(String value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>public.asociado.padre_email</code>.
	 */
	public String getPadreEmail() {
		return (String) getValue(20);
	}

	/**
	 * Setter for <code>public.asociado.madre_nombre</code>.
	 */
	public void setMadreNombre(String value) {
		setValue(21, value);
	}

	/**
	 * Getter for <code>public.asociado.madre_nombre</code>.
	 */
	public String getMadreNombre() {
		return (String) getValue(21);
	}

	/**
	 * Setter for <code>public.asociado.madre_telefono</code>.
	 */
	public void setMadreTelefono(String value) {
		setValue(22, value);
	}

	/**
	 * Getter for <code>public.asociado.madre_telefono</code>.
	 */
	public String getMadreTelefono() {
		return (String) getValue(22);
	}

	/**
	 * Setter for <code>public.asociado.madre_email</code>.
	 */
	public void setMadreEmail(String value) {
		setValue(23, value);
	}

	/**
	 * Getter for <code>public.asociado.madre_email</code>.
	 */
	public String getMadreEmail() {
		return (String) getValue(23);
	}

	/**
	 * Setter for <code>public.asociado.fecha_alta</code>.
	 */
	public void setFechaAlta(Timestamp value) {
		setValue(24, value);
	}

	/**
	 * Getter for <code>public.asociado.fecha_alta</code>.
	 */
	public Timestamp getFechaAlta() {
		return (Timestamp) getValue(24);
	}

	/**
	 * Setter for <code>public.asociado.fecha_baja</code>.
	 */
	public void setFechaBaja(Timestamp value) {
		setValue(25, value);
	}

	/**
	 * Getter for <code>public.asociado.fecha_baja</code>.
	 */
	public Timestamp getFechaBaja() {
		return (Timestamp) getValue(25);
	}

	/**
	 * Setter for <code>public.asociado.fecha_actualizacion</code>.
	 */
	public void setFechaActualizacion(Timestamp value) {
		setValue(26, value);
	}

	/**
	 * Getter for <code>public.asociado.fecha_actualizacion</code>.
	 */
	public Timestamp getFechaActualizacion() {
		return (Timestamp) getValue(26);
	}

	/**
	 * Setter for <code>public.asociado.rama_colonia</code>.
	 */
	public void setRamaColonia(Boolean value) {
		setValue(27, value);
	}

	/**
	 * Getter for <code>public.asociado.rama_colonia</code>.
	 */
	public Boolean getRamaColonia() {
		return (Boolean) getValue(27);
	}

	/**
	 * Setter for <code>public.asociado.rama_manada</code>.
	 */
	public void setRamaManada(Boolean value) {
		setValue(28, value);
	}

	/**
	 * Getter for <code>public.asociado.rama_manada</code>.
	 */
	public Boolean getRamaManada() {
		return (Boolean) getValue(28);
	}

	/**
	 * Setter for <code>public.asociado.rama_exploradores</code>.
	 */
	public void setRamaExploradores(Boolean value) {
		setValue(29, value);
	}

	/**
	 * Getter for <code>public.asociado.rama_exploradores</code>.
	 */
	public Boolean getRamaExploradores() {
		return (Boolean) getValue(29);
	}

	/**
	 * Setter for <code>public.asociado.rama_expedicion</code>.
	 */
	public void setRamaExpedicion(Boolean value) {
		setValue(30, value);
	}

	/**
	 * Getter for <code>public.asociado.rama_expedicion</code>.
	 */
	public Boolean getRamaExpedicion() {
		return (Boolean) getValue(30);
	}

	/**
	 * Setter for <code>public.asociado.rama_ruta</code>.
	 */
	public void setRamaRuta(Boolean value) {
		setValue(31, value);
	}

	/**
	 * Getter for <code>public.asociado.rama_ruta</code>.
	 */
	public Boolean getRamaRuta() {
		return (Boolean) getValue(31);
	}

	/**
	 * Setter for <code>public.asociado.activo</code>.
	 */
	public void setActivo(Boolean value) {
		setValue(32, value);
	}

	/**
	 * Getter for <code>public.asociado.activo</code>.
	 */
	public Boolean getActivo() {
		return (Boolean) getValue(32);
	}

	/**
	 * Setter for <code>public.asociado.usuario_activo</code>.
	 */
	public void setUsuarioActivo(Boolean value) {
		setValue(33, value);
	}

	/**
	 * Getter for <code>public.asociado.usuario_activo</code>.
	 */
	public Boolean getUsuarioActivo() {
		return (Boolean) getValue(33);
	}

	/**
	 * Setter for <code>public.asociado.password</code>.
	 */
	public void setPassword(String value) {
		setValue(34, value);
	}

	/**
	 * Getter for <code>public.asociado.password</code>.
	 */
	public String getPassword() {
		return (String) getValue(34);
	}

	/**
	 * Setter for <code>public.asociado.requiere_captcha</code>.
	 */
	public void setRequiereCaptcha(Boolean value) {
		setValue(35, value);
	}

	/**
	 * Getter for <code>public.asociado.requiere_captcha</code>.
	 */
	public Boolean getRequiereCaptcha() {
		return (Boolean) getValue(35);
	}

	/**
	 * Setter for <code>public.asociado.lenguaje</code>.
	 */
	public void setLenguaje(String value) {
		setValue(36, value);
	}

	/**
	 * Getter for <code>public.asociado.lenguaje</code>.
	 */
	public String getLenguaje() {
		return (String) getValue(36);
	}

	/**
	 * Setter for <code>public.asociado.ambito_edicion</code>.
	 */
	public void setAmbitoEdicion(String value) {
		setValue(37, value);
	}

	/**
	 * Getter for <code>public.asociado.ambito_edicion</code>.
	 */
	public String getAmbitoEdicion() {
		return (String) getValue(37);
	}

	/**
	 * Setter for <code>public.asociado.restriccion_asociacion</code>.
	 */
	public void setRestriccionAsociacion(Integer value) {
		setValue(38, value);
	}

	/**
	 * Getter for <code>public.asociado.restriccion_asociacion</code>.
	 */
	public Integer getRestriccionAsociacion() {
		return (Integer) getValue(38);
	}

	/**
	 * Setter for <code>public.asociado.no_puede_editar_datos_del_grupo</code>.
	 */
	public void setNoPuedeEditarDatosDelGrupo(Boolean value) {
		setValue(39, value);
	}

	/**
	 * Getter for <code>public.asociado.no_puede_editar_datos_del_grupo</code>.
	 */
	public Boolean getNoPuedeEditarDatosDelGrupo() {
		return (Boolean) getValue(39);
	}

	/**
	 * Setter for <code>public.asociado.no_puede_editar_otras_ramas</code>.
	 */
	public void setNoPuedeEditarOtrasRamas(Boolean value) {
		setValue(40, value);
	}

	/**
	 * Getter for <code>public.asociado.no_puede_editar_otras_ramas</code>.
	 */
	public Boolean getNoPuedeEditarOtrasRamas() {
		return (Boolean) getValue(40);
	}

	/**
	 * Setter for <code>public.asociado.solo_lectura</code>.
	 */
	public void setSoloLectura(Boolean value) {
		setValue(41, value);
	}

	/**
	 * Getter for <code>public.asociado.solo_lectura</code>.
	 */
	public Boolean getSoloLectura() {
		return (Boolean) getValue(41);
	}

	/**
	 * Setter for <code>public.asociado.estudios</code>.
	 */
	public void setEstudios(String value) {
		setValue(42, value);
	}

	/**
	 * Getter for <code>public.asociado.estudios</code>.
	 */
	public String getEstudios() {
		return (String) getValue(42);
	}

	/**
	 * Setter for <code>public.asociado.profesion</code>.
	 */
	public void setProfesion(String value) {
		setValue(43, value);
	}

	/**
	 * Getter for <code>public.asociado.profesion</code>.
	 */
	public String getProfesion() {
		return (String) getValue(43);
	}

	/**
	 * Setter for <code>public.asociado.hermanos_en_el_grupo</code>.
	 */
	public void setHermanosEnElGrupo(Boolean value) {
		setValue(44, value);
	}

	/**
	 * Getter for <code>public.asociado.hermanos_en_el_grupo</code>.
	 */
	public Boolean getHermanosEnElGrupo() {
		return (Boolean) getValue(44);
	}

	/**
	 * Setter for <code>public.asociado.fecha_usuario_creado</code>.
	 */
	public void setFechaUsuarioCreado(Timestamp value) {
		setValue(45, value);
	}

	/**
	 * Getter for <code>public.asociado.fecha_usuario_creado</code>.
	 */
	public Timestamp getFechaUsuarioCreado() {
		return (Timestamp) getValue(45);
	}

	/**
	 * Setter for <code>public.asociado.fecha_usuario_visto</code>.
	 */
	public void setFechaUsuarioVisto(Timestamp value) {
		setValue(46, value);
	}

	/**
	 * Getter for <code>public.asociado.fecha_usuario_visto</code>.
	 */
	public Timestamp getFechaUsuarioVisto() {
		return (Timestamp) getValue(46);
	}

	/**
	 * Setter for <code>public.asociado.usuario_creado_por_id</code>.
	 */
	public void setUsuarioCreadoPorId(Integer value) {
		setValue(47, value);
	}

	/**
	 * Getter for <code>public.asociado.usuario_creado_por_id</code>.
	 */
	public Integer getUsuarioCreadoPorId() {
		return (Integer) getValue(47);
	}

	/**
	 * Setter for <code>public.asociado.usuario_creado_por_nombre</code>.
	 */
	public void setUsuarioCreadoPorNombre(String value) {
		setValue(48, value);
	}

	/**
	 * Getter for <code>public.asociado.usuario_creado_por_nombre</code>.
	 */
	public String getUsuarioCreadoPorNombre() {
		return (String) getValue(48);
	}

	/**
	 * Setter for <code>public.asociado.notas</code>.
	 */
	public void setNotas(String value) {
		setValue(49, value);
	}

	/**
	 * Getter for <code>public.asociado.notas</code>.
	 */
	public String getNotas() {
		return (String) getValue(49);
	}

	/**
	 * Setter for <code>public.asociado.email_contacto</code>.
	 */
	public void setEmailContacto(String value) {
		setValue(50, value);
	}

	/**
	 * Getter for <code>public.asociado.email_contacto</code>.
	 */
	public String getEmailContacto() {
		return (String) getValue(50);
	}

	/**
	 * Setter for <code>public.asociado.calidad_password</code>.
	 */
	public void setCalidadPassword(Short value) {
		setValue(51, value);
	}

	/**
	 * Getter for <code>public.asociado.calidad_password</code>.
	 */
	public Short getCalidadPassword() {
		return (Short) getValue(51);
	}

	/**
	 * Setter for <code>public.asociado.certificado_delitos_sexuales</code>.
	 */
	public void setCertificadoDelitosSexuales(Boolean value) {
		setValue(52, value);
	}

	/**
	 * Getter for <code>public.asociado.certificado_delitos_sexuales</code>.
	 */
	public Boolean getCertificadoDelitosSexuales() {
		return (Boolean) getValue(52);
	}

	/**
	 * Setter for <code>public.asociado.curso_covid</code>.
	 */
	public void setCursoCovid(Boolean value) {
		setValue(53, value);
	}

	/**
	 * Getter for <code>public.asociado.curso_covid</code>.
	 */
	public Boolean getCursoCovid() {
		return (Boolean) getValue(53);
	}

	/**
	 * Setter for <code>public.asociado.certificado_voluntariado</code>.
	 */
	public void setCertificadoVoluntariado(Boolean value) {
		setValue(54, value);
	}

	/**
	 * Getter for <code>public.asociado.certificado_voluntariado</code>.
	 */
	public Boolean getCertificadoVoluntariado() {
		return (Boolean) getValue(54);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached AsociadoRecord
	 */
	public AsociadoRecord() {
		super(Asociado.ASOCIADO);
	}

	/**
	 * Create a detached, initialised AsociadoRecord
	 */
	public AsociadoRecord(Integer id, String tipo, String grupoId, String nombre, String apellidos, String sexo, Date fechaNacimiento, String dni, String seguridadSocial, Boolean tieneSeguroPrivado, String direccion, Integer codigoPostal, String telefonoCasa, String telefonoMovil, String email, String municipio, Boolean tieneTutorLegal, Boolean padresDivorciados, String padreNombre, String padreTelefono, String padreEmail, String madreNombre, String madreTelefono, String madreEmail, Timestamp fechaAlta, Timestamp fechaBaja, Timestamp fechaActualizacion, Boolean ramaColonia, Boolean ramaManada, Boolean ramaExploradores, Boolean ramaExpedicion, Boolean ramaRuta, Boolean activo, Boolean usuarioActivo, String password, Boolean requiereCaptcha, String lenguaje, String ambitoEdicion, Integer restriccionAsociacion, Boolean noPuedeEditarDatosDelGrupo, Boolean noPuedeEditarOtrasRamas, Boolean soloLectura, String estudios, String profesion, Boolean hermanosEnElGrupo, Timestamp fechaUsuarioCreado, Timestamp fechaUsuarioVisto, Integer usuarioCreadoPorId, String usuarioCreadoPorNombre, String notas, String emailContacto, Short calidadPassword, Boolean certificadoDelitosSexuales, Boolean cursoCovid, Boolean certificadoVoluntariado) {
		super(Asociado.ASOCIADO);

		setValue(0, id);
		setValue(1, tipo);
		setValue(2, grupoId);
		setValue(3, nombre);
		setValue(4, apellidos);
		setValue(5, sexo);
		setValue(6, fechaNacimiento);
		setValue(7, dni);
		setValue(8, seguridadSocial);
		setValue(9, tieneSeguroPrivado);
		setValue(10, direccion);
		setValue(11, codigoPostal);
		setValue(12, telefonoCasa);
		setValue(13, telefonoMovil);
		setValue(14, email);
		setValue(15, municipio);
		setValue(16, tieneTutorLegal);
		setValue(17, padresDivorciados);
		setValue(18, padreNombre);
		setValue(19, padreTelefono);
		setValue(20, padreEmail);
		setValue(21, madreNombre);
		setValue(22, madreTelefono);
		setValue(23, madreEmail);
		setValue(24, fechaAlta);
		setValue(25, fechaBaja);
		setValue(26, fechaActualizacion);
		setValue(27, ramaColonia);
		setValue(28, ramaManada);
		setValue(29, ramaExploradores);
		setValue(30, ramaExpedicion);
		setValue(31, ramaRuta);
		setValue(32, activo);
		setValue(33, usuarioActivo);
		setValue(34, password);
		setValue(35, requiereCaptcha);
		setValue(36, lenguaje);
		setValue(37, ambitoEdicion);
		setValue(38, restriccionAsociacion);
		setValue(39, noPuedeEditarDatosDelGrupo);
		setValue(40, noPuedeEditarOtrasRamas);
		setValue(41, soloLectura);
		setValue(42, estudios);
		setValue(43, profesion);
		setValue(44, hermanosEnElGrupo);
		setValue(45, fechaUsuarioCreado);
		setValue(46, fechaUsuarioVisto);
		setValue(47, usuarioCreadoPorId);
		setValue(48, usuarioCreadoPorNombre);
		setValue(49, notas);
		setValue(50, emailContacto);
		setValue(51, calidadPassword);
		setValue(52, certificadoDelitosSexuales);
		setValue(53, cursoCovid);
		setValue(54, certificadoVoluntariado);
	}
}
