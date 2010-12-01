<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglibs.jsp" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
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
div.ht a { display: block; text-decoration: none; border-bottom: 0px solid #EEE; height: 70px;
    -moz-border-radius: 8px; -webkit-border-radius: 8px; }
div.ht a:hover { background-color: #d3eef7; color: #009ace; 
/* probar en IE primero */ background: transparent url('<c:url value="/s/theme/img/onepixb.png" />') repeat 0 0; }
div.ht img { float: left; margin: 4px 20px 0 10px }
div.ht span { position:relative; color:#FFF; font-size: 197%; font-weight: bold; top: 17px }
div.ht a:hover span { /* color: #009ace; */ }
div.yui-g.first img { display:block; margin-top: 20px; }
h2 { font-weight: bold; font-size: 159%; }
body { background: #9bdbf7 url('<c:url value="/s/theme/img/bg.jpg" />') repeat-x 0 0; }
div#hd, div#bd, div#ft { background: transparent; }
div#infoUsuario { margin: 30px 0px 0px 10px; color: #FFF }
div#infoUsuario a { color: #FFF; }
div#lblUsuario { font-size: 182% }
div#lblGrupo { font-size: 146.5%; margin-bottom: 15px }

#lnkNuevoAsoc { cursor:pointer; }
</style>
</head>
<body>
<div id="doc" class="yui-t7">
<div id="hd" style="height: 150px; padding-top: 10px"></div> 
<div id="bd">
  <div class="yui-g">
    <div class="yui-g first">
        <img src="<c:url value="/s/theme/img/db-logo.png" />" alt="cudu" />
        <div id="infoUsuario">
          <div id="lblUsuario"><c:out value="${usuarioActual.nombreCompleto}" /></div>
          <div id="lblGrupo"><c:out value="${usuarioActual.grupo.nombre}" /></div>
          <div>
            <!--<a href="preferencias"><fmt:message key="dashboard.preferencias" /></a> - -->
            <a href="<c:url value="/?hl=es" />">Castellano</a>,
            <a href="<c:url value="/?hl=ca" />">Valencià</a> -
            <a href="<c:url value="/j_spring_security_logout"/>"><fmt:message key="dashboard.salir" /></a>
          </div>
        </div>
    </div>
    <div class="yui-g ht">
      <a id="lnkNuevoAsoc">
        <img src="<c:url value="/s/theme/img/db-asociado.png" />" alt="nuevo" style="margin-top: 0" />
        <span><fmt:message key="dashboard.nuevoasociado" /></span>
      </a>
      <a href="<c:url value="listados" />">
        <img src="<c:url value="/s/theme/img/db-listado.png" />" alt="ayuda" />
        <span><fmt:message key="dashboard.listados" /></span>
      </a>
      <sec:authorize access="!hasAnyRole('ROLE_ADMIN', 'SdA', 'SdC', 'MEV')">
      <a href="<c:url value="grupo" />">
        <img src="<c:url value="/s/theme/img/db-grupo.png" />" alt="ayuda" />
        <span><fmt:message key="dashboard.migrupo" /></span>
      </a>
      </sec:authorize>
      <a href="<c:url value="sugerencias" />" class=" hidden">
        <img src="<c:url value="/s/theme/img/db-sugerencias.png" />" alt="ayuda" />
        <span><fmt:message key="dashboard.sugerencias" /></span>
      </a>
      <%-- <a href="<c:url value="ayuda" />" class=" hidden">
        <img src="<c:url value="/s/theme/img/db-ayuda.png" />" alt="ayuda" />
        <span><fmt:message key="dashboard.ayuda" /></span>
      </a>--%>
    </div>
  </div>
</div>
<div class="ft" style="height: 500px">
</div>
</div>

<div id="poptaseg" class="popupdlg">
<div class="yui-t7">
<div class="bd">
   <div class="yui-g legend">
      <h1 style="text-align:center; color:#FFF;"><fmt:message key="asociado.tipo.pregunta" /></h1>
   </div>
   <div class="yui-gb content">
      <div class="yui-u first rounded">
        <a id="step1-a-joven" href="<c:url value="asociado/nuevo/joven" />">
          <img src="<c:url value="/s/theme/img/joven64.png" />" />
          <span><fmt:message key="asociado.tipo.joven" /></span>
        </a>
      </div>
      <div class="yui-u rounded">
        <a id="step1-a-kraal" href="asociado/nuevo/kraal">
          <img src="<c:url value="/s/theme/img/kraal64.png" />" />
          <span><fmt:message key="asociado.tipo.kraal" /></span>
        </a>
      </div>
      <div class="yui-u rounded">
        <a id="step1-a-comite" href="asociado/nuevo/comite">
          <img src="<c:url value="/s/theme/img/comite64.png" />" />
          <span><fmt:message key="asociado.tipo.comite" /></span>
        </a>
      </div>
    </div>
 </div>
</div>
</div>

<script src="<c:url value="/s/jquery/jquery-1.4.2.js" />" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#lnkNuevoAsoc").click(function () {
		/* var popup = $("#poptaseg");
		var y = ($(window).height() / 2) - (popup.height() / 2);
		popup.attr("style", "top: " + y + "px").fadeIn(); */
		$("#poptaseg").fadeIn();
	});
});
</script>
</body>
</html>