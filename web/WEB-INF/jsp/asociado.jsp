<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Cudú</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/yui/reset-fonts-grids/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/yui/base/base-min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/theme/cudu.css" />" />
<style type="text/css">
div#bd { padding-left: 20px;  }
.rounded { margin-top: 10px } 
.rounded a { display:block; height: 110px; text-decoration: none; border: 1px solid #FFF; }
.rounded a:hover { border: 1px solid #CCC; -moz-border-radius: 8px; -webkit-border-radius: 8px; }
.rounded a img, .rounded a span { display: block; margin: 8px auto; }
.rounded a span { text-align: center; font-size: 138.5%; }
.uiJ { display: none; }
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
    <img src="<c:url value="/theme/img/tango/document-print.png" />" alt="cudu" />
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
        <input id="txtApellido1" type="text" class="textbox w3u" />
      </div>
      <div class="field required">
        <label for="txtApellido2">2o Apellido</label>
        <input id="txtApellido2" type="text" class="textbox w3u" />
      </div>
      <div class="field required">
        <label for="radioGeneroMasculino">Sexo</label><%-- Toogle al pulsar sobre el label, selecci�n alternativa --%>
        <input id="radioGeneroMasculino" type="radio" class="radio" name="radioGenero" title="Masculino" value="M" />
        <label for="radioGeneroMasculino" class="radio">Masculino</label>
        <input id="radioGeneroFemenino" type="radio" class="radio" name="radioGenero" title="Masculino" value="F" />
        <label for="radioGeneroFemenino" class="radio">Femenino</label>
      </div>
    </div>
    <div class="yui-g">
      <div class="field required">
        <label for="txtFechaNac">Fecha Nac.</label>
        <input id="txtFechaNac" type="text" class="textbox w1u" />
        <img src="<c:url value="/theme/img/calendar.png" />" alt="Elegir fecha" />
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
        <input id="txtNSS" type="text" class="textbox w1u" style="width: 133px" />
        <input id="chkSSPrivado" type="checkbox" class="checkbox"/>
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
        <input id="txtCalle" type="text" class="textbox w3u" />
      </div>
      <div class="field required">
        <label for="txtPatio" class="w2u">Patio</label>
        <input id="txtPatio" type="text" class="textbox w0u" />
        <label for="txtEscalera" class="w1u">Escalera</label>
        <!-- HACK bgcolor, mover a css -->
        <input id="txtEscalera" type="text" class="textbox w0u" style="background-color: inherit;" />
        <label for="txtPuerta" class="w1u">Puerta</label>
        <input id="txtPuerta" type="text" class="textbox w0u" style="background-color: inherit;" />
      </div>
      <div class="field required">
        <label for="txtCodigoPostal" class="w2u">C.P.</label>
        <input id="txtCodigoPostal" type="text" class="textbox w1u" />
      </div>
      <div class="field required">
        <label for="txtProvincia" class="w2u">Provincia</label>
        <input id="txtProvincia" type="text" class="textbox w0u" />
        <img id="imgProvincia" src="<c:url value="/theme/img/magnifier.png" />" alt="Buscar provincia." />
        <span id="lblProvincia" class="literal">Valencia</span>
      </div>
      <div class="field required">
        <label for="txtMunicipio" class="w2u">Municipio</label>
        <input id="txtMunicipio" type="text" class="textbox w0u" />
        <img id="imgMunicipio" src="<c:url value="/theme/img/magnifier.png" />" alt="Buscar municipio." />
        <span id="lblMunicipio" class="literal">Alborache</span>
      </div>
    </div>
    <div class="yui-g">
      <div class="field">
        <label for="txtTelefono">Tel. Casa</label>
        <input id="txtTelefono" type="text" class="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtMovil">Móvil</label>
        <input id="txtMovil" type="text" class="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtMail">E-Mail</label>
        <input id="txtMail" type="text" class="textbox w3u" />
      </div>
    </div>    
  </div>
  
  <div class="yui-g legend uiK"><h2>Cargos o comisiones</h2></div>
  <div class="yui-g">
    <p>TODO: pantalla muy chachi donde editar esto...</p>
  </div>

  <div class="yui-g legend uiJ"><h2>Responsables del menor</h2></div>
  <div class="yui-g">
    <div class="yui-g first">
      <h3>Entorno familiar</h3>
      <div class="field required">
        <input id="checkTutorLegal" type="checkbox" class="checkbox" title="Tutor legal" />
        <label for="checkTutorLegal" class="checkbox">Tiene tutor legal</label>
        <input id="checkPadresSeparados" type="checkbox" class="checkbox" title="Padres separados"  />
        <label for="checkPadresSeparados" class="checkbox">Padres separados</label>
      </div>
    </div>
  </div>  
  <div class="yui-g">
    <div class="yui-g first">
      <h3>Datos del Padre</h3>
      <div class="field">
        <label for="txtPadreNombre">Nombre</label>
        <input id="txtPadreNombre" type="text" class="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtPadreTel">Teléfono</label>
        <input id="txtPadreTel" type="text" class="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtPadreMail">E-Mail</label>
        <input id="txtPadreMail" type="text" class="textbox w3u" />
      </div>
    </div>
    <div class="yui-g">
      <h3>Datos de la Madre</h3>
      <div class="field">
        <label for="txtMadreNombre">Nombre</label>
        <input id="txtMadreNombre" type="text" class="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtMadreTel">Teléfono</label>
        <input id="txtMadreTel" type="text" class="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtMadreMail">E-Mail</label>
        <input id="txtMadreMail" type="text" class="textbox w3u" />
      </div>
    </div>
  </div>

  <div class="yui-g legend uiJ"><h2>Copias de documentos</h2></div>
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
    document.location = '<c:url value="dashboard.mvc" />'; 
}
</script>
</body>
</html>