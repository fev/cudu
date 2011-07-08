<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglibs.jsp" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import ="org.scoutsfev.cudu.domain.Grupo"%>
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
<body  class="yui-skin-sam"> 
<div id="doc" class="yui-t7">
<div id="hd" style="height: 150px; padding-top: 10px"></div> 
<div id="bd">
    <c:out value="<script language='JavaScript'>" escapeXml="false"></c:out>
			var grupo=[];
                     <c:forEach var="grupo"                items="${grupos}" varStatus="status">
                        grupo.push({
                        key: "${grupo[0]}",
                        nombre:"${grupo[1]}"
                        });
                     </c:forEach>    
        <c:out value="</script>" escapeXml="false"></c:out>
    <div class="yui-g">
        <div class="yui-g first">
            <img src="<c:url value="/s/theme/img/db-logo.png" />" alt="cudu" />
            <div id="infoUsuario">
                <img src="<c:url value='${usuarioActual.grupo.imagen}' />" alt="fev" style="float: left; margin-right: 10px; margin-bottom:10px; margin-top:10px" />
                <div id="lblUsuario"><c:out value="${usuarioActual.nombreCompleto}" /></div>
                <div id="lblGrupo"><c:out value="${usuarioActual.grupo.nombre}" /></div>
                <div>
<!--<a href="preferencias"><fmt:message key="dashboard.preferencias" /></a> - -->
                    <a href="<c:url value="/?hl=es" />">Castellano</a>,
                    <a href="<c:url value="/?hl=ca" />">Valencià</a> -
                    <a href="<c:url value="/j_spring_security_logout"/>"><fmt:message key="dashboard.salir" /></a>
                </div>
            </div>
            <sec:authorize access="hasAnyRole('ROLE_PERMISO_C1','ROLE_PERMISO_C2','ROLE_PERMISO_C3')">
                <div style="width: 300px; background: WHITE; margin: 0; display: block;
                     border-bottom: 0px solid #EEE; height: auto;    -moz-border-radius: 8px; -webkit-border-radius: 8px; margin-top:20px">

                    
                        <label>  <fmt:message key="estadistica.grupos" /> : ${datosEstadistica.grupos}</label>
                        <label>  <fmt:message key="estadistica.voluntarios" /> : ${datosEstadistica.voluntarios}</label>
                        <label>  <fmt:message key="estadistica.asociados" /> :  ${datosEstadistica.asociados}</label>
                    


                    <div id="chartContainer" style="width: 300px; height: 400px; margin: 0 auto">


                    </div>


                    <label>  <fmt:message key="estadistica.grupo.e.menosNumeroso" /> </label>
                    <div>
                        <label for="j_username">   ${nombreGrupoMenosNumeroso}   ${valorGrupoMenosNumeroso}</label>
                    </div>

                        <label>  <fmt:message key="estadistica.grupo.menosActualizado" /> :</label>

                        <div >
                            <label>   ${nombreGrupoMenosActualizado}   ${fechaGrupoMenosActualizado}</label>
                        </div>      
                </div>
            </sec:authorize>
        </div>
        <div class="yui-g ht">
            
            <sec:authorize access="hasAnyRole('ROLE_PERMISO_B','ROLE_PERMISO_A')">
                <a href="<c:url value="" />">
                    <img src="<c:url value="/s/theme/img/db-asociado.png"/>" alt="ayuda" />
                    <span><fmt:message key="dashboard.nuevoparticipanteexterno" /></span>
                </a>
                <a href="<c:url value="" />">
                    <img src="<c:url value="/s/theme/img/db-sugerencias.png"/>" alt="ayuda" />
                    <span><fmt:message key="dashboard.administrarcursos" /></span>
                </a>
                <a href="<c:url value="" />">
                    <img src="<c:url value="/s/theme/img/db-sugerencias.png"/>" alt="ayuda" />
                    <span><fmt:message key="dashboard.editarcursos" /></span>
                </a>
                <a href="<c:url value="" />">
                    <img src="<c:url value="/s/theme/img/db-asociado.png"/>" alt="ayuda" />
                    <span><fmt:message key="dashboard.gestiondeFormadores" /></span>
                </a>
            </sec:authorize>

            
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
                
                <a href="<c:url value="sugerencias" />" class=" hidden">
                    <img src="<c:url value="/s/theme/img/db-sugerencias.png" />" alt="ayuda" />
                    <span><fmt:message key="dashboard.sugerencias" /></span>
                </a>
            </sec:authorize>
        
    
            <sec:authorize access="hasAnyRole('ROLE_PERMISO_C1','ROLE_PERMISO_C2','ROLE_PERMISO_C3')">
                <a id="lnkNuevoAsoc">
                    <img src="<c:url value="/s/theme/img/db-asociado.png" />" alt="nuevo" style="margin-top: 0" />
                    <span><fmt:message key="dashboard.nuevoasociado" /></span>
                </a>
                <a href="<c:url value="grupo/nuevo" />">
                    <img src="<c:url value="/s/theme/img/db-sugerencias.png"/>" alt="ayuda" />
                    <span><fmt:message key="dashboard.nuevogrupo" /></span>
                </a>                
                <a href="<c:url value="" />">
                    <img src="<c:url value="/s/theme/img/db-sugerencias.png"/>" alt="ayuda" />
                    <span><fmt:message key="dashboard.liquidaciones" /></span>
                </a>
            </sec:authorize>
        
            <sec:authorize access="hasAnyRole('ROLE_PERMISO_B','ROLE_PERMISO_A','ROLE_PERMISO_C1','ROLE_PERMISO_C2','ROLE_PERMISO_C3')">
                <a href="<c:url value="buscarAsociado/buscar" />">
                    <img src="<c:url value="/s/theme/img/db-buscar-asociado.png"/>" alt="ayuda" />
                    <span><fmt:message key="dashboard.buscarparticipante" /></span>
                </a>
                
                
                <a id="lnkListados">
                        <img src="<c:url value="/s/theme/img/db-listado.png" />" alt="listados" style="margin-top: 0" />
                        <span><fmt:message key="dashboard.listados" /></span>
                </a>
                
                <a href="<c:url value="" />">
                    <img src="<c:url value="/s/theme/img/db-sugerencias.png"/>" alt="ayuda" />
                    <span><fmt:message key="dashboard.administrarpermisos" /></span>
                </a>
            </sec:authorize>
            
            <sec:authorize access="hasAnyRole('ROLE_PERMISO_B','ROLE_PERMISO_A','ROLE_PERMISO_C1','ROLE_PERMISO_C2','ROLE_PERMISO_C3','ROLE_PERMISO_F','ROLE_PERMISO_H')">
                <a href="<c:url value="cambiarPassword/" />">
                    <img src="<c:url value="/s/theme/img/db-asociado.png"/>" alt="ayuda" />
                    <span><fmt:message key="dashboard.cambiarcontrasenya" /></span>
                </a>

              <%-- <a href="<c:url value="ayuda" />" class=" hidden">
                <img src="<c:url value="/s/theme/img/db-ayuda.png" />" alt="ayuda" />
                <span><fmt:message key="dashboard.ayuda" /></span>
              </a>--%>  
            </sec:authorize>
        </div>
    </div>
</div>
<div class="ft" style="height: 500px">
</div>
</div>

</div>
        
<div id="popListados" class="popupdlg">
   <div id ="lnkCerrarPoPup" class="yui-t7">
       <a id='lnkCerrarPoPupListados' style="background-color:#FFE3E3; border-color: #900; color:#900; float:right">
                <fmt:message key="boton.cerrar"/>
            </a>
        <div class="yui-t7">
            <div  class="bd">
               <div class="yui-g legend">
                  <h1 style="text-align:center; color:#FFF;"><fmt:message key="asociado.tipo.pregunta" /></h1>
               </div>
               <div class="yui-gb content">
                  <div class="yui-u first rounded">
                    <a href="<c:url value="listados" />">
                    <img src="<c:url value="/s/theme/img/joven64.png" />" alt="ayuda" />
                    <span><fmt:message key="dashboard.listados" /></span>
                    </a>
                  </div>
                  <div class="yui-u rounded">
                    <a href="<c:url value="listados_grupos" />">
                        <img src="<c:url value="/s/theme/img/db-grupo.png" />" alt="ayuda" />
                        <span><fmt:message key="estadistica.grupos" /></span>
                    </a>
                  </div>
                </div>
             </div>
        </div>
    </div>
    </div>

                    
<div id="poptaseg" class="popupdlg">
        <div class="yui-t7">
            <a id='lnkCerrarPoPupNuevoAsociados' style="background-color:#FFE3E3; border-color: #900; color:#900; float:right">
                <fmt:message key="boton.cerrar"/>
            </a>
        <div class="yui-t7">
            <div  class="bd">                                            
               <div class="yui-g legend">
                  <h1 style="text-align:center; color:#FFF;"><fmt:message key="asociado.tipo.pregunta" /></h1>
               </div>
               
               <label style="color:#FFF;"  for="dInput"><fmt:message key="asociado.f.grupo" /></label>
                 <fieldset id="menubuttonsfromjavascript"/> 
               <div class="yui-gb content">
                  <div class="yui-u first rounded">
                      <a id="step1-a-joven" href="javascript:void(0)" onclick="JavaScript:seleccionNuevo('joven')"> 
                      <img src="<c:url value="/s/theme/img/joven64.png" />" />
                      <span><fmt:message key="asociado.tipo.joven" /></span>
                    </a>
                  </div>
                  <div class="yui-u rounded">       
                      <a id="step1-a-kraal" href="javascript:void(0)" onclick="JavaScript:seleccionNuevo('kraal')"/>
                      <img src="<c:url value="/s/theme/img/kraal64.png" />" />
                      <span><fmt:message key="asociado.tipo.kraal" /></span>
                    </a>
                  </div>
                  <div class="yui-u rounded">
                      <a id="step1-a-comite" href="javascript:void(0)" onclick="JavaScript:seleccionNuevo('comite')">
                      <img src="<c:url value="/s/theme/img/comite64.png" />" />
                      <span><fmt:message key="asociado.tipo.comite" /></span>
                    </a>
                  </div>
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
<script type="text/javascript" src="<c:url value="/s/jquery/jquery-1.4.2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/scripts/estadistica/highcharts.js"/>"></script>
<script src="<c:url value="/s/jquery/jquery-1.4.2.min.js" />" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
    
        $("#lnkCerrarPoPupNuevoAsociados").click(function (){
            $("#poptaseg").fadeOut();
        });
        
         $("#lnkCerrarPoPupListados").click(function (){
            $("#popListados").fadeOut();
        });
        
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
        /*
        
        $("#lnkCerrarPoPup").click(function () {		                
                $("#popcurs").fadeOut();
                $("#popListados").fadeOut();
                $("#poptaseg").fadeOut();
        });*/
        $("#lnkListados").click(function () {
                $("#popListados").fadeIn();
        });
        

	var chart;
            chart = new Highcharts.Chart({
                chart: {
                    renderTo: 'chartContainer',
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '<fmt:message key="estadistica.asociados" />'
                },
                tooltip: {
                    formatter: function() {
                            var percent = parseInt(this.y/${datosEstadistica.asociados} *100);
                            return '<b>'+ this.point.name +'</b>: '+ this.y + '  ('+percent +'%)';
                    }
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                                enabled: false
                        },
                        showInLegend: true
                    }
                },
                series: [{
                        type: 'pie',
                        name: 'Browser share',
                        data: [
                            {name: '<fmt:message key="rama.uno.C" />',   y: ${datosEstadistica.castores}, color: '#FF9900'},
                            {name: '<fmt:message key="rama.uno.E" />',   y: ${datosEstadistica.exploradores},    color: '#0081CC'},
                            {name: '<fmt:message key="rama.uno.M" />',   y: ${datosEstadistica.lobatos},    color: '#FFFF00'},
                            {name: '<fmt:message key="rama.uno.P" />',   y: ${datosEstadistica.pioneros},    color: '#CC3333'},
                            {name: '<fmt:message key="rama.uno.R" />',   y: ${datosEstadistica.companys},    color: '#009933'},

                            {name: '<fmt:message key="estadistica.asociado.tipo.kraal" />', y:${datosEstadistica.scouters}, color: '#6600CC'},
                            {name: '<fmt:message key="asociado.tipo.comite" />', y:${datosEstadistica.comite}, color: '#663333'}
                        ]
                    }]
                });
        });
//////////////////////////////////////////////////FIN DE ESTADISTICA, INICIO DE COMBOBOX
   //	"contentready" event handler for the "menubuttonsfrommarkup" <fieldset>
         var sText;
	//	Search for an element to place the Menu Button into via the 
	//	Event utility's "onContentReady" method
	YAHOO.util.Event.onContentReady("menubuttonsfromjavascript", function () {
		//	"click" event handler for each item in the Button's menu
		var onMenuItemClick = function (p_sType, p_aArgs, p_oItem) {
			
			sText = p_oItem.cfg.getProperty("text");
                        sTextKey = p_oItem.value;
			
			YAHOO.log("[MenuItem Properties] text: " + sText + ", value: " + 
					p_oItem.value);
			
    		oMenuButtonGrupo.set("label", sText);			
 
		};
 
 
		//	Create an array of YAHOO.widget.MenuItem configuration properties
 
		var i, aMenuButtonGrupo = [];
                    for(i = grupo.length-1; i >0;i--)
                    {
                        aMenuButtonGrupo.push({
                            text:grupo[i].nombre,
                            value:grupo[i].key,
                            onclick: { fn: onMenuItemClick } 
                        });
                    }

 
 
		//	Instantiate a Menu Button using the array of YAHOO.widget.MenuItem 
		//	configuration properties as the value for the "menu"  
		//	configuration attribute.
 
		var oMenuButtonGrupo = new YAHOO.widget.Button({
                    type: "menu", 
                    label: grupo[grupo.length-1].nombre, 
                    name: "comboGrupo", 
                    menu: aMenuButtonGrupo, 
                    container: this });
	});
  
	function seleccionNuevo(arg){
          
           window.location.href = "asociado/nuevo/"+arg+"/"+sTextKey;
        }
</script>
</body>
</html>     