<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglibs.jsp" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Cudú</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/yui/reset-fonts-grids/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/yui/base/base-min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/theme/cudu.css" />" />
<style type="text/css">
div.ht a { display: block; text-decoration: none; border-bottom: 0px solid #EEE; height: 70px;
    -moz-border-radius: 8px; -webkit-border-radius: 8px; }
div.ht a:hover { background-color: #d3eef7; color: #009ace; 
/* probar en IE primero */ background: transparent url('<c:url value="/theme/img/onepixb.png" />') repeat 0 0; }
div.ht img { float: left; margin: 4px 20px 0 10px }
div.ht span { position:relative; color:#FFF; font-size: 197%; font-weight: bold; top: 17px }
div.ht a:hover span { /* color: #009ace; */ }
div.yui-g.first img { display:block; margin-top: 20px; }
h2 { font-weight: bold; font-size: 159%; }
body { background: #9bdbf7 url('<c:url value="/theme/img/bg.jpg" />') repeat-x 0 0; }
div#hd, div#bd, div#ft { background: transparent; }
div#infoUsuario { margin: 30px 0px 0px 10px; color: #FFF }
div#infoUsuario a { color: #FFF; }
div#lblUsuario { font-size: 182% }
div#lblGrupo { font-size: 146.5%; margin-bottom: 15px }

#lnkNuevoAsoc {cursor:pointer;}
#poptaseg { display:none; position: absolute; margin-left: -300px;
	top: 150px; left: 50%; width: 600px; height: 200px;  
	background: transparent url('<c:url value="/theme/img/onepixb.png" />') repeat 0 0;
	-moz-border-radius: 10px; -webkit-border-radius: 10px; }
#poptaseg div.yui-t7 {width:35.38em;*width:34.53em;min-width:460px;margin:auto;text-align:left; } 
.rounded { margin-top: 10px } 
.rounded a { display:block; height: 110px; text-decoration: none; border: 1px solid transparent; color: #FFF }
.rounded a:hover { border: 1px solid #CCC; -moz-border-radius: 8px; -webkit-border-radius: 8px; }
.rounded a img, .rounded a span { display: block; margin: 8px auto; }
.rounded a span { text-align: center; font-size: 138.5% }
</style>
</head>
<body>
<div id="doc" class="yui-t7">
<div id="hd" style="height: 150px; padding-top: 10px"></div> 
<div id="bd">
  <div class="yui-g">
    <div class="yui-g first">
        <img src="<c:url value="/theme/img/db-logo.png" />" alt="cudu" />
        <div id="infoUsuario">
          <div id="lblUsuario"><c:out value="${usuarioActual.nombreCompleto}" /></div>
          <div id="lblGrupo"><c:out value="${usuarioActual.grupo.nombre}" /></div>
          <div>
            <a href="preferencias.mvc"><fmt:message key="dashboard.preferencias" /></a> -
            <a href="<c:url value="/j_spring_security_logout"/>">Salir</a>
          </div>
        </div>
    </div>
    <div class="yui-g ht">
      <a id="lnkNuevoAsoc">
        <img src="<c:url value="/theme/img/db-asociado.png" />" alt="nuevo" style="margin-top: 0" />
        <span>Nuevo asociado</span>
      </a>
      <a href="<c:url value="listados.mvc" />">
        <img src="<c:url value="/theme/img/db-listado.png" />" alt="ayuda" />
        <span>Listados</span>
      </a>
      <a href="<c:url value="grupo.mvc?id=AK" />">
        <img src="<c:url value="/theme/img/db-grupo.png" />" alt="ayuda" />
        <span>Mi grupo</span>
      </a>
      <a href="<c:url value="ayuda.mvc" />">
        <img src="<c:url value="/theme/img/db-ayuda.png" />" alt="ayuda" />
        <span>Ayuda</span>
      </a>
    </div>
  </div>
</div>
<div class="ft" style="height: 500px">
</div>
</div>

<div id="poptaseg">
<div class="yui-t7">
<div class="bd">
   <div class="yui-g legend" style="margin-top: 20px; margin-bottom: 8px">
      <h1 style="text-align:center; color:#FFF;"><fmt:message key="asociado.tipo.pregunta" /></h1>
   </div>
   <div class="yui-gb" style="margin-bottom: 10px">
      <div class="yui-u first rounded">
        <a id="step1-a-joven" href="<c:url value="asociado.mvc?t=j" />">
          <img src="<c:url value="/theme/img/joven64.png" />" />
          <span><fmt:message key="asociado.tipo.joven" /></span>
        </a>
      </div>
      <div class="yui-u rounded">
        <a id="step1-a-kraal" href="asociado.mvc?t=k">
          <img src="<c:url value="/theme/img/kraal64.png" />" />
          <span><fmt:message key="asociado.tipo.kraal" /></span>
        </a>
      </div>
      <div class="yui-u rounded">
        <a id="step1-a-comite" href="asociado.mvc?t=c">
          <img src="<c:url value="/theme/img/comite64.png" />" />
          <span><fmt:message key="asociado.tipo.comite" /></span>
        </a>
      </div>
    </div>
 </div>
</div>
</div>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#lnkNuevoAsoc").click(function () {
		$("#poptaseg").fadeIn();	
	});
});
</script>
</body>
</html>