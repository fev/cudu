<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglibs.jsp" %>
<%@page import="java.io.FileInputStream"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><fmt:message key="app.title" /></title>
<link rel="icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link rel="shortcut icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/reset-fonts-grids/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/base/base-min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/theme/cudu.css" />" />
<style type="text/css">
div#bd { padding-left: 20px; }
<%-- a#btnDlg01Eliminar:hover { background-color: #500; } --%>
</style>
</head>
<body>
<div id="doc" class="yui-t7">
<jsp:include page="header.jsp"></jsp:include>
<form:form modelAttribute="usuario" method="POST">
<div id="bd">  
  <div class="yui-g">
  <div class="yui-g first"><h1 id="hform">
  	<spring:message code="asociado.titulo.editar" />
  </h1></div>
  
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
          <form:input id="email" path="email" cssClass="textbox w3u" cssErrorClass="textbox w3u error"/>
      </div>      
    </div>
    <div class="yui-g">
      
    </div>
  </div>
  
  <div class="yui-g legend"><h2><fmt:message key="asociado.h.contacto" /></h2></div>
  <div class="yui-g ">
    <div class="yui-g first">
    </div>
    <div class="yui-g">
      
    </div>
  </div>
  
  <c:if test="${asociado.tipo != 'J'}"> <%-- KRAAL o COMITÉ tienen cargos --%>
  <div class="yui-g legend hidden"><h2><fmt:message key="asociado.h.cargos" /></h2></div>
  <div class="yui-g hidden">
    <p>TODO: pantalla muy chachi donde editar esto...</p>
  </div>
  </c:if>

  
  <div class="yui-g form-action">
    <div class="yui-g first">
    </div>
    <div class="yui-g">
      <input type="button" value="<fmt:message key="btn.volver" />" class="button back" onclick="javascript:window.history.back();" />
      <input type="submit" value="<fmt:message key="btn.guardar" />" class="button save" />
      <%-- <input type="button" value="<fmt:message key="btn.imprimir" />" class="button print" /> --%>
    </div>
  </div>
</div>
</form:form>
<div id="ft"><fmt:message key="app.copyright" /></div>
</div>
<script src="<c:url value="/s/jquery/jquery-1.4.2.js" />" type="text/javascript"></script>
<script type="text/javascript">
jQuery.fn.center = function() {
	return this.each(function() {
		var e = jQuery(this);
		var offset = e.offset();
		var y = (offset.top + $(window).height() / 2) - (e.height() / 2);
		e.attr("style", "top: " + y + "px");
	});
};

cudu = {
	back: function() {
		document.location = '<c:url value="/dashboard" />'; 
	},

	remove: function() {		
		$("#stddlg").center().fadeIn();
	}
};



function dbgcopy() {
	$('#txtNombre')[0].value = "Jack";
	$('#txtApellido1')[0].value = "Sparrow";
	$('#txtApellido2')[0].value = "Pearl";
	$('#txtFechaNac')[0].value = '21/10/2001';
}
</script>
</body>
</html>