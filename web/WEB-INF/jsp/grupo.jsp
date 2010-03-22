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
.postmsg { border: 1px solid #CCC; padding: 4px; cursor: pointer }
.postmsg.ok { background-color: #E3FFE3; color: #64992C; border-color: #64992C; }
</style>
</head>
<body>
<div id="doc" class="yui-t7">
<jsp:include page="header.jsp"></jsp:include>
<div id="bd">
  <div class="yui-g">
    <div class="yui-g first">
      <h1><fmt:message key="grupo.h.titulo" /> <c:out value="${grupo.nombre}" /></h1>
    </div>
    <!--<div class="yui-g">
    </div>-->
  </div>
  <form:form modelAttribute="grupo" method="POST">

  <spring:hasBindErrors name="grupo">
  	<cudu:message id="prueba" key="frm.errores">
  		<form:errors id="mpbderr" path="*" />
  	</cudu:message>
  </spring:hasBindErrors>
  
  <c:if test="${grupo.uiStatedSaved}">
  	<cudu:message id="mp01" key="frm.ok" single="true" />
  </c:if>
 
  <div class="yui-g legend"><h2><fmt:message key="grupo.h.info" /></h2></div> 
  <div class="yui-g">
    <div class="yui-g first">
      <div class="field">
        <label for="lblId" class="w2u">Id</label>
        <span id="lblId" class="literal">
            <abbr title="<fmt:message key="grupo.ayuda.id" />"><c:out value="${grupo.id}" /></abbr>
        </span>
      </div>
      <div class="field required">
        <label for="txtNombre" class="w2u"><fmt:message key="grupo.nombre" /></label>
        <form:input id="txtNombre" path="nombre" cssClass="textbox w3u" />
      </div>
    </div>
    <div class="yui-g">
      <div class="field">
        <label for="txtAniversario" class="w4u">Aniversario</label>
        <form:input id="txtAniversario" path="aniversario" cssClass="textbox w1u" />
        <img src="<c:url value="/s/theme/img/calendar.png" />" alt="Elegir fecha" />
      </div>
      <div class="field">
        <label for="txtEntPatr" class="w4u">Ent. Patroc.</label>
        <form:input id="txtEntPatr" path="entidadPatrocinadora" cssClass="textbox w3u" />
      </div>
    </div>
  </div>

  <div class="yui-g legend"><h2><fmt:message key="grupo.h.contacto" /></h2></div>
  <div class="yui-g">
    <div class="yui-g first">
      <div class="field required">
        <label for="txtCalle" class="w2u">Calle</label>
        <form:input path="calle" cssClass="textbox w3u" />
      </div>
      <div class="field required">
        <label for="txtNumero" class="w2u">Número</label>
        <form:input id="txtNumero" path="numero" cssClass="textbox w0u" />
        <label for="txtEscalera" class="w1u">Escalera</label>
        <!-- HACK bgcolor, mover a css -->
        <form:input id="txtEscalera" path="escalera" cssClass="textbox w0u" cssStyle="background-color: inherit;" />
        <label for="txtPuerta" class="w1u">Puerta</label>
        <form:input id="txtPuerta" path="puerta" cssClass="textbox w0u" />
      </div>
      <div class="field required">
        <label for="txtCodigoPostal" class="w2u">C.P.</label>
        <form:input id="txtCodigoPostal" path="codigoPostal" cssClass="textbox w1u" />
      </div>
      <div class="field required">
        <label for="txtProvincia" class="w2u">Provincia</label>
        <form:input id="txtProvincia" path="idProvincia" cssClass="textbox w0u" />
        <img id="imgProvincia" src="<c:url value="/s/theme/img/magnifier.png" />" alt="Buscar provincia." />
        <span id="lblProvincia" class="literal">Valencia</span>
      </div>
      <div class="field required">
        <label for="txtMunicipio" class="w2u">Municipio</label>
        <form:input id="txtMunicipio" path="idMunicipio" cssClass="textbox w0u" />
<!--        <form:select path="idProvincia">-->
<!--          <form:option value="12">Anywhere</form:option>-->
<!--          <form:option value="46">Valencia</form:option>-->
<!--          <form:option value="76">Oceans</form:option>-->
<!--        </form:select>-->
        <img id="imgMunicipio" src="<c:url value="/s/theme/img/magnifier.png" />" alt="Buscar municipio." />
        <span id="lblMunicipio" class="literal">Alborache</span>
      </div>
    </div>
    <div class="yui-g">
      <div class="field required">
        <label for="txtTelefono">Teléfono 1</label>
        <form:input id="txtTelefono1" path="telefono1" cssClass="textbox w3u" maxlength="15" />
      </div>
      <div class="field ">
        <label for="txtTelefono">Teléfono 2</label>
        <form:input id="txtTelefono2" path="telefono2" cssClass="textbox w3u" maxlength="15" />
      </div>
      <div class="field required">
        <label for="txtMail">Mail</label>
        <form:input id="txtMail" path="mail" cssClass="textbox w3u" maxlength="100" />
      </div>
      <div class="field">
        <label for="txtWeb">Pág. Web</label>
        <form:input id="txtWeb" path="web" cssClass="textbox w3u"  maxlength="300" />
      </div>
    </div>
  </div>

  <div class="yui-g form-action">
  	<div class="yui-g first">
<!--  <input type="submit" value="Eliminar" class="button delete" />-->
  	</div>
  	<div class="yui-g">
  	  <input type="button" value="Volver" class="button back" onclick="javascript:back()" />
  	  <input type="submit" value="Guardar" class="button save" />
<!--  <input type="button" value="Imprimir" class="button print" />-->
  	</div>
  </div>
  </form:form>
</div>
<div id="ft">© 2009 Federació d'Escoltisme Valencià</div>
</div>
<script type="text/javascript">
function back() {
  document.location = '<c:url value="dashboard.mvc" />'; 
}
</script>
</body>
</html>
