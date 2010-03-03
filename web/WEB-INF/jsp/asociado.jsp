<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Cudú</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/reset-fonts-grids/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/base/base-min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/theme/cudu.css" />" />
<style type="text/css">
div#bd { padding-left: 20px; }
</style>
</head>
<body>
<div id="doc" class="yui-t7">
<jsp:include page="header.jsp"></jsp:include>
<form:form modelAttribute="asociado" method="POST">
<div id="bd">  
  <div class="yui-g">
  <div class="yui-g first"><h1 id="hform">Nuevo asociada/o</h1></div>
  <div class="yui-g" style="text-align:right; padding-top: 1px; margin-right: 13px">
    <img src="<c:url value="/s/theme/img/tango/document-print.png" />" alt="cudu" />
  </div>
  </div>

  <div class="yui-g legend"><h2><fmt:message key="grupo.h.info" /></h2></div>
  <div class="yui-g">
    <div class="yui-g first">    
      <div class="field required">
        <label for="txtNombre">Nombre</label>
        <form:input id="txtNombre" path="nombre" cssClass="textbox w3u" />
      </div>
      <div class="field required">
        <label for="txtApellido1">1er Apellido</label>
        <form:input id="txtApellido1" path="primerapellido" cssClass="textbox w3u" />
      </div>
      <div class="field required">
        <label for="txtApellido2">2o Apellido</label>
        <form:input id="txtApellido2" path="segundoapellido" cssClass="textbox w3u" />
      </div>
      <div class="field required">
        <label for="radioGeneroMasculino">Sexo</label><%-- Toogle al pulsar sobre el label, selección alternativa --%>
        <input id="radioGeneroMasculino" type="radio" class="radio" name="radioGenero" title="Masculino" value="M" />
        <label for="radioGeneroMasculino" class="radio">Masculino</label>
        <input id="radioGeneroFemenino" type="radio" class="radio" name="radioGenero" title="Masculino" value="F" />
        <label for="radioGeneroFemenino" class="radio">Femenino</label>
      </div>
    </div>
    <div class="yui-g">
      <div class="field required">
        <label for="txtFechaNac">Fecha Nac.</label>
        <form:input id="txtFechaNac" path="fechanacimiento" cssClass="textbox w1u" />
        <img src="<c:url value="/s/theme/img/calendar.png" />" alt="Elegir fecha" />
      </div>
      <div class="field required">
        <label for="dropUnidad"><fmt:message key="asociado.f.rama" /></label>
        <div class="combo w1u">Lobatos</div>
      </div>
      <div class="field">
        <label for="txtDNI">Nº DNI</label>
        <input id="txtDNI" type="text" class="textbox w1u" maxlength="8" />
        <input id="txtDNIDC" type="text" class="textbox dc" readonly="readonly" />
      </div>
      <div class="field">
        <label for="txtNSS">Seg. Social</label>
        <form:input id="txtNSS" path="seguridadsocial" cssClass="textbox w1u" cssStyle="width: 133px" />
        <form:checkbox id="chkSSPrivado" path="tieneseguroprivado" cssClass="checkbox" />
        <label for="chkSSPrivado" class="checkbox">Seg. Privado</label>
        <!--  <input id="txtNSSProv" type="text" class="textbox w0u" maxlength="2" />
        <input id="txtNSSNum" type="text" class="textbox w1u" maxlength="8" />
        <input id="txtNSSDC" type="text" class="textbox dc" maxlength="2" readonly="readonly" /> -->
      </div>
    </div>
  </div>
  
  <div class="yui-g legend"><h2>Datos de contacto</h2></div>
  <div class="yui-g ">
    <div class="yui-g first">
      <div class="field required">
        <label for="txtCalle" class="w2u">Calle</label>
        <form:input id="txtCalle" path="calle" cssClass="textbox w3u" />
      </div>
      <div class="field required">
        <label for="txtPatio" class="w2u">Patio</label>
        <form:input id="txtPatio" path="patio" cssClass="textbox w0u" />
        <label for="txtEscalera" class="w1u">Escalera</label>
        <!-- HACK bgcolor, mover a css -->
        <form:input id="txtEscalera" path="escalera" cssClass="textbox w0u" cssStyle="background-color: inherit;" />
        <label for="txtPuerta" class="w1u">Puerta</label>
        <form:input id="txtPuerta" path="puerta" cssClass="textbox w0u" cssStyle="background-color: inherit;" />
      </div>
      <div class="field required">
        <label for="txtCodigoPostal" class="w2u">C.P.</label>
        <form:input id="txtCodigoPostal" path="codigopostal" cssClass="textbox w1u" />
      </div>
      <div class="field required">
        <label for="txtProvincia" class="w2u">Provincia</label>
        <input id="txtProvincia" type="text" class="textbox w0u" />
        <img id="imgProvincia" src="<c:url value="/s/theme/img/magnifier.png" />" alt="Buscar provincia." />
        <span id="lblProvincia" class="literal">Valencia</span>
      </div>
      <div class="field required">
        <label for="txtMunicipio" class="w2u">Municipio</label>
        <input id="txtMunicipio" type="text" class="textbox w0u" />
        <img id="imgMunicipio" src="<c:url value="/s/theme/img/magnifier.png" />" alt="Buscar municipio." />
        <span id="lblMunicipio" class="literal">Alborache</span>
      </div>
    </div>
    <div class="yui-g">
      <div class="field">
        <label for="txtTelefono">Tel. Casa</label>
        <form:input id="txtTelefono" path="telefonocasa" cssClass="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtMovil">Móvil</label>
        <form:input id="txtMovil" path="telefonomovil" cssClass="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtMail">E-Mail</label>
        <form:input id="txtMail" path="email" cssClass="textbox w3u" />
      </div>
    </div>    
  </div>
  
  <div class="yui-g legend"><h2>Cargos o comisiones</h2></div>
  <div class="yui-g">
    <p>TODO: pantalla muy chachi donde editar esto...</p>
  </div>

  <div class="yui-g legend"><h2>Responsables del menor</h2></div>
  <div class="yui-g">
    <div class="yui-g first">
      <h3>Entorno familiar</h3>
      <div class="field required">
        <form:checkbox id="checkTutorLegal" path="tienetutorlegal" cssClass="checkbox" title="Tutor legal" />
        <label for="checkTutorLegal" class="checkbox">Tiene tutor legal</label>
        <form:checkbox id="checkPadresSeparados" path="tienetutorlegal" cssClass="checkbox" title="Padres separados"  />
        <label for="checkPadresSeparados" class="checkbox">Padres separados</label>
      </div>
    </div>
  </div>  
  <div class="yui-g">
    <div class="yui-g first">
      <h3>Datos del Padre</h3>
      <div class="field">
        <label for="txtPadreNombre">Nombre</label>
        <form:input id="txtPadreNombre" path="padreNombre" cssClass="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtPadreTel">Teléfono</label>
        <form:input id="txtPadreTel" path="padreTelefono" cssClass="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtPadreMail">E-Mail</label>
        <form:input id="txtPadreMail" path="padreEmail" cssClass="textbox w3u" />
      </div>
    </div>
    <div class="yui-g">
      <h3>Datos de la Madre</h3>
      <div class="field">
        <label for="txtMadreNombre">Nombre</label>
        <form:input id="txtMadreNombre" path="madreNombre" cssClass="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtMadreTel">Teléfono</label>
        <form:input id="txtMadreTel" path="madreTelefono" cssClass="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtMadreMail">E-Mail</label>
        <form:input id="txtMadreMail" path="madreEmail" cssClass="textbox w3u" />
      </div>
    </div>
  </div>

  <div class="yui-g legend"><h2>Copias de documentos</h2></div>
  <div class="yui-g">
    <p>Cudú puede ayudarte a recordar de qué documentos dispones copia:</p>
    <div class="yui-g first">
      <div class="field">
        <input id="chkCopiaDNI" type="checkbox" class="checkbox" />
        <label for="chkCopiaDNI" class="checkbox">Documento Nacional de Identidad (DNI)</label>
      </div>
      <div class="field">
        <input id="chkCopiaNSS" type="checkbox" class="checkbox" />
        <label for="chkCopiaNSS" class="checkbox">Cartilla SS \ Tarjeta SIP</label>
      </div>
    </div>
    <div class="yui-g">
      <div class="field">
        <input id="chkCopiaFichaMedica" type="checkbox" class="checkbox" />
        <label for="chkCopiaFichaMedica" class="checkbox">Ficha médica</label>
      </div>
      <div class="field">
        <input id="chkCopiaVacunas" type="checkbox" class="checkbox" />
        <label for="chkCopiaVacunas" class="checkbox">Cartilla Vacunaciones</label>
      </div>
    </div>
  </div>

  <div class="yui-g form-action">
    <div class="yui-g first">
      <input type="submit" value="Eliminar" class="button delete" />
    </div>
    <div class="yui-g">
      <input type="button" value="Volver" class="button back" onclick="javascript:back()" />
      <input type="submit" value="Guardar" class="button save" />
      <!-- <input type="button" value="Imprimir" class="button print" /> -->
    </div>
  </div>
</div>
</form:form>
<div id="ft">(c) 2009 Federació d'Escoltisme Valencià</div>
</div>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
cudu = {
	ui: {
	   hform: document.getElementById('hform')
    },
	res: {
	   hjoven: '<fmt:message key="asociado.h.joven" />',
	   hkraal: '<fmt:message key="asociado.h.kraal" />',
	   hcomite: '<fmt:message key="asociado.h.comite" />'
    }
};

function ui_step2(selector) {
	$(".step1").slideUp();
    $(selector).fadeIn("slow");
}

function oldDocReady(){
    $("#step1-a-joven").click(function () {
	    ui_step2(".step2,.step2-joven");
	    cudu.ui.hform.innerHTML = cudu.res.hjoven;
	    $('#txtNombre').focus();
	    $('#hdTipoAsociado').value = 'J';
	});
    $("#step1-a-kraal").click(function () {
    	ui_step2(".step2,.step2-kraal");
    	cudu.ui.hform.innerHTML = cudu.res.hkraal;
        $('#txtNombre').focus();
        $('#hdTipoAsociado').value = 'K';
    });
    $("#step1-a-comite").click(function () {
    	ui_step2(".step2,.step2-kraal");
    	cudu.ui.hform.innerHTML = cudu.res.hcomite;
        $('#txtNombre').focus();
        $('#hdTipoAsociado').value = 'C';
    });
}

function back() {
	// cambiar por redirect
    document.location = '<c:url value="dashboard" />'; 
}
</script>
</body>
</html>