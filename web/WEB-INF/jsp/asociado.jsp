<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><fmt:message key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/reset-fonts-grids/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/base/base-min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/theme/cudu.css" />" />
<style type="text/css">
div#bd { padding-left: 20px; }
a.dropramas { background: transparent url('<c:url value="/s/theme/img/sramas.png" />') no-repeat 0px 0px;
	width: 20px; height: 20px; margin-top: 3px; text-decoration: none;  }
a#radioColonia { background-position: 0px -20px !important }
a#radioColonia.selected { background-position: 0px 0px !important }
a#radioManada { background-position: -21px -20px !important }
a#radioManada.selected { background-position: -21px 0px !important }
a#radioExploradores { background-position: -43px -20px !important }
a#radioExploradores.selected { background-position: -43px 0px !important }
a#radioPioneros { background-position: -65px -20px !important }
a#radioPioneros.selected { background-position: -65px 0px !important }
a#radioRutas { background-position: -87px -20px !important }
a#radioRutas.selected { background-position: -87px 0px !important }
</style>
</head>
<body>
<div id="doc" class="yui-t7">
<jsp:include page="header.jsp"></jsp:include>
<form:form modelAttribute="asociado" method="POST">
<div id="bd">  
  <div class="yui-g">
  <div class="yui-g first"><h1 id="hform"><spring:message code="asociado.titulo.${asociado.tipo}" /></h1></div>
  <div class="yui-g" style="text-align:right; padding-top: 1px; margin-right: 13px">
    <img src="<c:url value="/s/theme/img/tango/document-print.png" />" />
    <img src="<c:url value="/s/theme/img/tango/edit-copy.png" />" />
    <a href="javascript:dbgcopy()"><img src="<c:url value="/s/theme/img/tango/edit-paste.png" />" /></a>
  </div>
  </div>
  
  <c:set var="erroresValidacion" value="false" />
  <spring:hasBindErrors name="asociado">
  	<cudu:message id="mp01" key="frm.errores">
  		<c:set var="erroresValidacion" value="true" />
  		<form:errors id="mpbderr" path="*" />
  	</cudu:message>
  </spring:hasBindErrors>
  
  <c:if test="${param.ok != null && erroresValidacion == false}">
  	<cudu:message id="mp01" key="frm.ok" single="true" />
  </c:if>
  
  <div class="yui-g legend"><h2><fmt:message key="asociado.h.info" /></h2></div>
  <div class="yui-g">
    <div class="yui-g first">    
      <div class="field required">
        <label for="txtNombre"><fmt:message key="asociado.f.nombre" /></label>
        <form:input id="txtNombre" path="nombre" cssClass="textbox w3u" cssErrorClass="textbox w3u error" />
      </div>
      <div class="field required">
        <label for="txtApellido1"><fmt:message key="asociado.f.primerapellido" /></label>
        <form:input id="txtApellido1" path="primerapellido" cssClass="textbox w3u" cssErrorClass="textbox w3u error" />
      </div>
      <div class="field">
        <label for="txtApellido2"><fmt:message key="asociado.f.segundoapellido" /></label>
        <form:input id="txtApellido2" path="segundoapellido" cssClass="textbox w3u" cssErrorClass="textbox w3u error" />
      </div>
      <div class="field required">
        <label><fmt:message key="asociado.f.sexo" /></label>
        <form:radiobutton id="radioGeneroMasculino" cssClass="radio" path="sexo" value="M" title="Masculino" />
        <label for="radioGeneroMasculino" class="radio"><fmt:message key="asociado.f.sexoM" /></label>
        <form:radiobutton id="radioGeneroFemenino" cssClass="radio" path="sexo" value="F" title="Femenino" />
        <label for="radioGeneroFemenino" class="radio"><fmt:message key="asociado.f.sexoF" /></label>
      </div>
    </div>
    <div class="yui-g">
      <div class="field required">
        <label for="txtFechaNac"><fmt:message key="asociado.f.fechaNac" /></label>
        <form:input id="txtFechaNac" path="fechanacimiento" cssClass="textbox w1u" cssErrorClass="textbox w1u error" />
        <img src="<c:url value="/s/theme/img/calendar.png" />" alt="Elegir fecha" />
        <span id="lblFechaNac" class="literal">8 años, 9 meses</span>
      </div>
      <div class="field required">
        <label for="dropUnidad"><fmt:message key="asociado.f.rama" /></label>        
        <a id="radioColonia" href="#" class="dropramas" >&nbsp;</a>
        <a id="radioManada" href="#" class="dropramas">&nbsp;</a>
        <a id="radioExploradores" href="#" class="dropramas">&nbsp;</a>
        <a id="radioPioneros" href="#" class="dropramas">&nbsp;</a>
        <a id="radioRutas" name="rama.rutas" href="#" class="dropramas">&nbsp;</a>
        <form:checkbox id="chkRamasColonia" path="rama.colonia" cssClass="hidden" />
        <form:checkbox id="chkRamasManada" path="rama.manada" cssClass="hidden" />
        <form:checkbox id="chkRamasExploradores" path="rama.exploradores" cssClass="hidden" />
        <form:checkbox id="chkRamasPioneros" path="rama.pioneros" cssClass="hidden" />
        <form:checkbox id="chkRamasRutas" path="rama.rutas" cssClass="hidden" />
      </div>
      <div class="field">
        <label for="txtDNI"><fmt:message key="asociado.f.dni" /></label>
        <form:input id="txtDNI" path="dni" cssClass="textbox w1u" maxlength="9" />
      </div>
      <div class="field">
        <label for="txtNSS"><fmt:message key="asociado.f.ss" /></label>
        <form:input id="txtNSS" path="seguridadsocial" cssClass="textbox w1u" cssStyle="width: 133px" />
        <form:checkbox id="chkSSPrivado" path="tieneseguroprivado" cssClass="checkbox" />
        <label for="chkSSPrivado" class="checkbox"><fmt:message key="asociado.f.sprivado" /></label>
        <!--  <input id="txtNSSProv" type="text" class="textbox w0u" maxlength="2" />
        <input id="txtNSSNum" type="text" class="textbox w1u" maxlength="8" />
        <input id="txtNSSDC" type="text" class="textbox dc" maxlength="2" readonly="readonly" /> -->
      </div>
    </div>
  </div>
  
  <div class="yui-g legend"><h2><fmt:message key="asociado.h.contacto" /></h2></div>
  <div class="yui-g ">
    <div class="yui-g first">
      <div class="field required">
        <label for="txtCalle" class="w2u"><fmt:message key="asociado.f.calle" /></label>
        <form:input id="txtCalle" path="calle" cssClass="textbox w3u" cssErrorClass="textbox w3u error" />
      </div>
      <div class="field required">
        <label for="txtNumero" class="w2u"><fmt:message key="asociado.f.numero" /></label>
        <form:input id="txtNumero" path="numero" cssClass="textbox w0u" cssErrorClass="textbox w0u error" maxlength="3" />
        <label for="txtEscalera" class="w1u"><fmt:message key="asociado.f.escalera" /></label>
        <!-- HACK bgcolor, mover a css -->
        <form:input id="txtEscalera" path="escalera" cssClass="textbox w0u" maxlength="3" cssStyle="background-color: inherit;" />
        <label for="txtPuerta" class="w1u"><fmt:message key="asociado.f.puerta" /></label>
        <form:input id="txtPuerta" path="puerta" cssClass="textbox w0u" maxlength="3" cssStyle="background-color: inherit;" />
      </div>
      <div class="field required">
        <label for="txtCodigoPostal" class="w2u"><fmt:message key="asociado.f.codigopostal" /></label>
        <form:input id="txtCodigoPostal" path="codigopostal" cssClass="textbox w1u" cssErrorClass="textbox w1u error" />
      </div>
      <div class="field required">
        <label for="txtProvincia" class="w2u"><fmt:message key="asociado.f.provincia" /></label>
        <form:input id="txtProvincia" path="provincia" cssClass="textbox w3u" cssErrorClass="textbox w3u error" />
        <%-- <img id="imgProvincia" src="<c:url value="/s/theme/img/magnifier.png" />" alt="Buscar provincia." />
        <span id="lblProvincia" class="literal">Valencia</span> --%>
      </div>
      <div class="field required">
        <label for="txtMunicipio" class="w2u"><fmt:message key="asociado.f.municipio" /></label>
        <form:input id="txtMunicipio" path="municipio" cssClass="textbox w3u" cssErrorClass="textbox w3u error" />
        <%-- <img id="imgMunicipio" src="<c:url value="/s/theme/img/magnifier.png" />" alt="Buscar municipio." />
        <span id="lblMunicipio" class="literal">Alborache</span> --%>
      </div>
    </div>
    <div class="yui-g">
      <div class="field">
        <label for="txtTelefono"><fmt:message key="asociado.f.telefonocasa" /></label>
        <form:input id="txtTelefono" path="telefonocasa" cssClass="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtMovil"><fmt:message key="asociado.f.telefonomovil" /></label>
        <form:input id="txtMovil" path="telefonomovil" cssClass="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtMail"><fmt:message key="asociado.f.email" /></label>
        <form:input id="txtMail" path="email" cssClass="textbox w3u" />
      </div>
    </div>    
  </div>
  
  <c:if test="${asociado.tipo != 'J'}"> <%-- KRAAL o COMITÉ tienen cargos --%>
  <div class="yui-g legend"><h2><fmt:message key="asociado.h.cargos" /></h2></div>
  <div class="yui-g">
    <p>TODO: pantalla muy chachi donde editar esto...</p>
  </div>
  </c:if>

  <c:if test="${asociado.tipo == 'J'}"> <%-- JOVEN Responsables del menor, copias de documentos --%>
  <div class="yui-g legend"><h2><fmt:message key="asociado.h.responsables" /></h2></div>
  <div class="yui-g">
    <div class="yui-g first">
      <h3><fmt:message key="asociado.h.entornofamiliar" /></h3>
      <div class="field required">
        <form:checkbox id="checkTutorLegal" path="tienetutorlegal" cssClass="checkbox" title="Tutor legal" />
        <label for="checkTutorLegal" class="checkbox"><fmt:message key="asociado.f.tutorlegal" /></label>
        <form:checkbox id="checkPadresSeparados" path="tienetutorlegal" cssClass="checkbox" title="Padres separados"  />
        <label for="checkPadresSeparados" class="checkbox"><fmt:message key="asociado.f.padresseparados" /></label>
      </div>
    </div>
  </div>  
  <div class="yui-g">
    <div class="yui-g first">
      <h3><fmt:message key="asociado.h.datospadre" /></h3>
      <div class="field">
        <label for="txtPadreNombre"><fmt:message key="asociado.f.datospadres.nombre" /></label>
        <form:input id="txtPadreNombre" path="padreNombre" cssClass="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtPadreTel"><fmt:message key="asociado.f.datospadres.telefono" /></label>
        <form:input id="txtPadreTel" path="padreTelefono" cssClass="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtPadreMail"><fmt:message key="asociado.f.datospadres.email" /></label>
        <form:input id="txtPadreMail" path="padreEmail" cssClass="textbox w3u" />
      </div>
    </div>
    <div class="yui-g">
      <h3><fmt:message key="asociado.h.datosmadre" /></h3>
      <div class="field">
        <label for="txtMadreNombre"><fmt:message key="asociado.f.datospadres.nombre" /></label>
        <form:input id="txtMadreNombre" path="madreNombre" cssClass="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtMadreTel"><fmt:message key="asociado.f.datospadres.telefono" /></label>
        <form:input id="txtMadreTel" path="madreTelefono" cssClass="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtMadreMail"><fmt:message key="asociado.f.datospadres.email" /></label>
        <form:input id="txtMadreMail" path="madreEmail" cssClass="textbox w3u" />
      </div>
    </div>
  </div>

  <div class="yui-g legend"><h2><fmt:message key="asociado.h.copiasdoc" /></h2></div>
  <div class="yui-g">
    <p><fmt:message key="asociado.f.copiasdoc.ayuda" /></p>
    <div class="yui-g first">
      <div class="field">
        <input id="chkCopiaDNI" type="checkbox" class="checkbox" />
        <label for="chkCopiaDNI" class="checkbox"><fmt:message key="asociado.f.copiasdoc.dni" /></label>
      </div>
      <div class="field">
        <input id="chkCopiaNSS" type="checkbox" class="checkbox" />
        <label for="chkCopiaNSS" class="checkbox"><fmt:message key="asociado.f.copiasdoc.ss" /></label>
      </div>
    </div>
    <div class="yui-g">
      <div class="field">
        <input id="chkCopiaFichaMedica" type="checkbox" class="checkbox" />
        <label for="chkCopiaFichaMedica" class="checkbox"><fmt:message key="asociado.f.copiasdoc.fmed" /></label>
      </div>
      <div class="field">
        <input id="chkCopiaVacunas" type="checkbox" class="checkbox" />
        <label for="chkCopiaVacunas" class="checkbox"><fmt:message key="asociado.f.copiasdoc.vac" /></label>
      </div>
    </div>
  </div>
  </c:if>

  <div class="yui-g form-action">
    <div class="yui-g first">
   	<c:if test="${asociado.id > 0}">
      <input type="submit" value="<fmt:message key="btn.eliminar" />" class="button delete" />
    </c:if>
    </div>
    <div class="yui-g">
      <input type="button" value="<fmt:message key="btn.volver" />" class="button back" onclick="javascript:cudu.back()" />
      <input type="submit" value="<fmt:message key="btn.guardar" />" class="button save" />
      <%-- <input type="button" value="<fmt:message key="btn.imprimir" />" class="button print" /> --%>
    </div>
  </div>
</div>
</form:form>
<div id="ft"><fmt:message key="app.copyright" /></div>
</div>

<script src="<c:url value="/s/jquery/jquery.js" />"></script>
<script type="text/javascript">
cudu = {
	back: function() {
		document.location = '<c:url value="/dashboard" />'; 
	},

	ui: {
		dropramas: $(".dropramas"),
		ramas: {
			"radioColonia": document.getElementById('chkRamasColonia'),
			"radioManada": document.getElementById('chkRamasManada'),
			"radioExploradores": document.getElementById('chkRamasExploradores'),
			"radioPioneros": document.getElementById('chkRamasPioneros'),
			"radioRutas": document.getElementById('chkRamasRutas')
		}
	}
};

$(document).ready(function() {

	// Seleccionar las ramas del asociado
	if (cudu.ui.ramas["radioColonia"].checked)
		$('#radioColonia').toggleClass("selected");
	if (cudu.ui.ramas["radioManada"].checked)
		$('#radioManada').toggleClass("selected");
	if (cudu.ui.ramas["radioExploradores"].checked)
		$('#radioExploradores').toggleClass("selected");
	if (cudu.ui.ramas["radioPioneros"].checked)
		$('#radioPioneros').toggleClass("selected");
	if (cudu.ui.ramas["radioRutas"].checked)
		$('#radioRutas').toggleClass("selected");

	// Atender al click sobre el selector de rama
	cudu.ui.dropramas.click(function(e) {
		<c:if test="${asociado.tipo == 'J'}">
		cudu.ui.dropramas.removeClass("selected"); // comentar para permitir selección múltiple
		</c:if>
		$(this).toggleClass("selected");

		cudu.ui.dropramas.each(function() {
			var el = cudu.ui.ramas[this.id];
			if ($(this).hasClass('selected')) {
				el.checked = true;
			} else {
				el.checked = false;
			}
		});
	});
});

function dbgcopy() {
	$('#txtNombre')[0].value = "Jack";
	$('#txtApellido1')[0].value = "Sparrow";
	$('#txtApellido2')[0].value = "Pearl";
	$('#txtFechaNac')[0].value = '21/10/2001';
	$('#radioGeneroMasculino')[0].checked = true;
	$('#txtCalle')[0].value = "Amarradero de la perla negra";
	$('#txtNumero')[0].value = "13";
	$('#txtCodigoPostal')[0].value = '46015';
	$('#txtProvincia')[0].value = "Valencia";
	$('#txtMunicipio')[0].value = "Valencia";
		
}
</script>
</body>
</html>