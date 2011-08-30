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

<!-- Combo-handled YUI CSS files: -->
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/assets/skins/sam/autocomplete.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/button/assets/skins/sam/button.css"/>" />
<!-- Combo-handled YUI JS files: -->
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.9.0/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.9.0/build/animation/animation-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.9.0/build/datasource/datasource-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.9.0/build/autocomplete/autocomplete-min.js"></script>

<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.9.0/build/fonts/fonts-min.css" /> 
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.9.0/build/menu/assets/skins/sam/menu.css" /> 
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.9.0/build/button/assets/skins/sam/button.css" /> 
<script type="text/javascript" src="http://yui.yahooapis.com/2.9.0/build/yahoo-dom-event/yahoo-dom-event.js"></script> 
<script type="text/javascript" src="http://yui.yahooapis.com/2.9.0/build/container/container_core-min.js"></script> 
<script type="text/javascript" src="http://yui.yahooapis.com/2.9.0/build/menu/menu-min.js"></script> 
<script type="text/javascript" src="http://yui.yahooapis.com/2.9.0/build/element/element-min.js"></script> 
<script type="text/javascript" src="http://yui.yahooapis.com/2.9.0/build/button/button-min.js"></script> 
 
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
#lnkNuevoCurso  { cursor:pointer; }

#lnkListados{ cursor:pointer; }
#lnkCerrarPoPupNuevoAsociados{ cursor:pointer; }
#lnkCerrarPoPupListados{ cursor:pointer; }
#lnkCerrarPoPupCursos{ cursor:pointer; }
                        /* custom styles for inline instances */
                        .yui-skin-sam .yui-ac-input { position:static;width:20em; vertical-align:middle;}
                        .yui-skin-sam .yui-ac-container { width:20em;left:0px;}

                        /* needed for stacked instances for ie & sf z-index bug of absolute inside relative els */
                        #bAutoComplete { z-index:9001; } 
                        #lAutoComplete { z-index:9000; }

                        /* buttons */
                        .yui-ac .yui-button {vertical-align:middle;}
                        .yui-ac .yui-button button       { color:#FFF; background: "<c:url value="/s/theme/img/ac-arrow-rt.png" />" center center no-repeat}
                        .yui-ac .open .yui-button button { color:#FFF; background: "<c:url value="/s/theme/img/ac-arrow-dn.png" />" center center no-repeat}

              
                        
                          /*
        Set the "zoom" property to "normal" since it is set to "1" by the 
        ".example-container .bd" rule in yui.css and this causes a Menu
        instance's width to expand to 100% of the browser viewport.
    */
    
    div.yuimenu .bd {
    
        zoom: normal;
    
    }
 
    #button-example-form fieldset {
 
        border: 2px groove #ccc;
        margin: .5em;
        padding: .5em;
 
    }
 
    #menubutton3menu,
    #menubutton4menu {
    
        position: absolute;
        visibility: hidden;
        border: solid 1px #000;
        padding: .5em;
        background-color: #ccc;
    
    }
 
    #button-example-form-postdata {
    
        border: dashed 1px #666;
        background-color: #ccc;
        padding: 1em;
    
    }
 
    #button-example-form-postdata h2 {
    
        margin: 0 0 .5em 0;
        padding: 0;
        border: none;
    
    }
    
    
    /*margin and padding on body element
  can introduce errors in determining
  element position and are not recommended;
  we turn them off as a foundation for YUI
  CSS treatments. */
body {
	margin:0;
	padding:0;
}
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
            <sec:authorize access="hasAnyRole('ROLE_PERMISO_F','ROLE_PERMISO_H')">
                <a href="<c:url value="asociado/${idAsociado}"/>">
                    <img src="<c:url value="/s/theme/img/db-mis-datos.png" />" alt="ayuda" />
                    <span><fmt:message key="dashboard.misdatos" /></span>
                </a>  
                <a href="<c:url value="grupo/${grupoAsociado}" />">
                    <img src="<c:url value="/s/theme/img/db-grupo.png" />" alt="ayuda" />
                    <span><fmt:message key="dashboard.migrupo" /></span>
                </a>        
                <c:if test="${anyosAsociado>18}">
                    <a id="lnkNuevoCurso">
                        <img src="<c:url value="/s/theme/img/db-cursos.png" />" alt="cursos" style="margin-top: 0" />
                        <span><fmt:message key="dashboard.nuevocurso" /></span>
                    </a>

		      <a href="<c:url value="mis_cursos/${idAsociado}" />">
                        <img src="<c:url value="/s/theme/img/db-cursos.png" />" alt="cursos" style="margin-top: 0" />
                        <span><fmt:message key="dashboard.miscursos" /></span>
                    </a>
                </c:if> 
      </sec:authorize>

        <sec:authorize access="hasAnyRole('ROLE_PERMISO_C1','ROLE_PERMISO_C2','ROLE_PERMISO_C3')">

      <a id="lnkNuevoAsoc">
        <img src="<c:url value="/s/theme/img/db-asociado.png" />" alt="nuevo" style="margin-top: 0" />
        <span><fmt:message key="dashboard.nuevoasociado" /></span>
      </a>
      
      <a href="<c:url value="listados" />">
        <img src="<c:url value="/s/theme/img/db-listado.png" />" alt="ayuda" />
        <span><fmt:message key="dashboard.listados" /></span>
      </a>
      </sec:authorize>
        
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

<div id="popcurs" class="popupdlg">
<div class="yui-t7">
    <a id='lnkCerrarPoPupCursos' style="background-color:#FFE3E3; border-color: #900; color:#900; float:right">
                <fmt:message key="boton.cerrar"/>
            </a>
<div   class="bd">
   <div class="yui-g legend">
      <h1 style="text-align:center; color:#FFF;"><fmt:message key="asociado.tipo.pregunta" /></h1>
   </div>
   <div class="yui-gb content">
      <div class="yui-u first rounded">
        <a id="step1-a-AJ" href="<c:url value="curso/${idAsociado}/AJ" />">
          <img src="<c:url value="/s/theme/img/certAJ.png" />" />
          <span><fmt:message key="asociado.curso.AJ" /></span>
        </a>
      </div>
      <div class="yui-u rounded">
        <a href="<c:url value="curso/${idAsociado}/FC" />">
          <img src="<c:url value="/s/theme/img/certFC.png" />" />
          <span><fmt:message key="asociado.curso.FC" /></span>
        </a>
      </div>
      <div class="yui-u rounded">
        <a id="step1-a-comite" href="curso/${idAsociado}/MTL">
          <img src="<c:url value="/s/theme/img/certMTL.png" />" />
          <span><fmt:message key="asociado.curso.MTL" /></span>
        </a>
      </div>
        <div class="yui-u first rounded">
        <a id="step1-a-joven" href="<c:url value="curso/${idAsociado}/MA" />">
          <img src="<c:url value="/s/theme/img/certMALI.png" />" />
          <span><fmt:message key="asociado.curso.MALI" /></span>
        </a>
      </div>
      <sec:authorize access="!hasAnyRole('ROLE_ADMIN', 'SdA', 'SdC', 'MEV','ROLE_PERMISO_F')">
      <div class="yui-u rounded">
        <a id="step1-a-kraal" href="<c:url value="curso/${idAsociado}/FA" />">
          <img src="<c:url value="/s/theme/img/certFA.png" />" />
          <span><fmt:message key="asociado.curso.FA" /></span>
        </a>
      </div>
      </sec:authorize>
    </div>
 </div>
</div>
</div>
<script src="<c:url value="/s/jquery/jquery-1.4.2.min.js" />" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
         $("#lnkCerrarPoPupCursos").click(function (){
            $("#popcurs").fadeOut();
        });
	$("#lnkNuevoAsoc").click(function () {
		/* var popup = $("#poptaseg");
		var y = ($(window).height() / 2) - (popup.height() / 2);
		popup.attr("style", "top: " + y + "px").fadeIn(); */
		$("#poptaseg").fadeIn();
	});

        $("#lnkNuevoCurso").click(function () {
                $("#popcurs").fadeIn();
        });
});
</script>
</body>
</html>