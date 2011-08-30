<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglibs.jsp" %>
<%@page import="java.io.FileInputStream"%>
<%@page import ="java.util.List"%>
<%@page import ="org.scoutsfev.cudu.domain.MisCursos"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><fmt:message key="app.title" /></title>
<link rel="icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link rel="shortcut icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/reset-fonts-grids/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/base/base-min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/theme/cudu.css" />" />
<%-- Desde la cache --%>

<link rel="stylesheet" type="text/css" href="<c:url value="/s/yasui/base/base-min.css" />" />

<%-- Normal --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/container/assets/skins/sam/container.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/paginator/assets/skins/sam/paginator.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/datatable/assets/skins/sam/datatable.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/theme/cudu.css" />" />


<!-- Combo-handled YUI CSS files: -->
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/assets/skins/sam/autocomplete.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/button/assets/skins/sam/button.css"/>" />
<!-- Combo-handled YUI JS files: -->
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.9.0/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.9.0/build/animation/animation-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.9.0/build/datasource/datasource-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.9.0/build/autocomplete/autocomplete-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.9.0/build/element/element-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.9.0/build/button/button-min.js"></script>
<style type="text/css">
div#bd { padding-left: 20px; }
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

<%-- a#btnDlg01Eliminar:hover { background-color: #500; } --%>
</style>
</head>
<body>
<div id="doc" class="yui-t7">
<jsp:include page="header.jsp"></jsp:include>
<div id="bd">  
  <div class="yui-g">
  <div class="yui-g first"><h1 id="hform">
  	<fmt:message key="dashboard.miscursos" />
  </h1></div>
      <c:out value="<script language='JavaScript'>" escapeXml="false"></c:out>
    var dataCursosRealizados=[],dataCursosActuales=[];
    
    <c:forEach var="cursoRealizado" items="${misCursosRealizados}" varStatus="status">        
         var calificacion;
        if('${cursoRealizado.calificacion}'=='N')
        {
            calificacion="<fmt:message key="miscursos.calificacionmonografico.noapto" />";
        }else if('${cursoRealizado.calificacion}'=='A')
        {
            calificacion="<fmt:message key="miscursos.calificacionmonografico.apto" />";
        }else
        {
            calificacion="<fmt:message key="miscursos.calificacionmonografico.pendiente" />";
        }
        
        dataCursosRealizados.push({
                   Ronda: "${cursoRealizado.ronda}",
               Formacion: "${cursoRealizado.formacion}",
                   Curso: "${cursoRealizado.curso}",
            Calificacion: calificacion

        });                                        
     </c:forEach>    
        
        
    <c:forEach var="cursoActual" items="${misCursosActuales}" varStatus="status">        
        var trabajo;
        if('${cursoActual.trabajo}'=='N')
        {
            trabajo="<fmt:message key="miscursos.calificaciontrabajo.noapto" />";
        }else if('${cursoActual.trabajo}'=='A')
        {
            trabajo="<fmt:message key="miscursos.calificaciontrabajo.apto" />";
        }else
        {
            trabajo="<fmt:message key="miscursos.calificaciontrabajo.pendiente" />";
        }
        
         var calificacion;
        if('${cursoActual.calificacion}'=='N')
        {
            calificacion="<fmt:message key="miscursos.calificacionmonografico.noapto"/>";
        }else if("${cursoActual.calificacion}"=='A')
        {
            calificacion="<fmt:message key="miscursos.calificacionmonografico.apto"/>";
        }else
        {
            calificacion="<fmt:message key="miscursos.calificacionmonografico.pendiente"/>";
        }
        dataCursosActuales.push({
                   Ronda: "${cursoActual.ronda}",
               Formacion: "${cursoActual.formacion}",
                   Curso: "${cursoActual.curso}",
            Calificacion: calificacion,
                  Faltas: "${cursoActual.faltas}",
                      id: "${cursoActual.misCursosPK.idMonografico}",
                 Trabajo: trabajo

        });                                        
     </c:forEach>    
        <c:out value="</script>" escapeXml="false"></c:out>
      
      
  <div class="yui-g" style="text-align:right; padding-top: 1px; margin-right: 13px">
    <img src="<c:url value="/s/theme/img/tango/document-save.png" />" />

    <a id="btnSave" href="<%=request.getContextPath()%>/s/filesClient/listado.pdf ">
      <img src="<c:url value="/s/theme/img/tango/save_24.png" />" />
      <span><fmt:message key="mensaje de prueba" /></span>
    </a>

    <a href="javascript:dbgcopy()"><img src="<c:url value="/s/theme/img/tango/edit-paste.png" />" /></a>
  </div>
  </div>
  
  <c:set var="erroresValidacion" value="false" />
  <spring:hasBindErrors name="asociado">
  	<cudu:message id="mp01" key="frm.errores">
  		<c:set var="erroresValidacion" value="true" />
  		<errors id="mpbderr" path="*" />
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
            <c:out value="${asociado.nombre}" />
      </div>
      <div class="field required">
        <label for="txtApellido1"><fmt:message key="asociado.f.primerapellido" /></label>
            <c:out value="${asociado.primerapellido}" />
      </div>
      <div class="field">
        <label for="txtApellido2"><fmt:message key="asociado.f.segundoapellido" /></label>
        <c:out value="${asociado.segundoapellido}" />
      </div>
  
    </div>
    <div class="yui-g">
      <div class="field required">
        <label for="txtFechaNac"><fmt:message key="asociado.f.fechaNac" /></label>
        <c:out value="${asociado.fechanacimiento}" />
      </div>
      <div class="field">
        <label for="txtDNI"><fmt:message key="asociado.f.dni" /></label>
        <c:out value="${asociado.dni}" />
      </div>
    </div>
  </div>

      
      
        <c:choose>
            <c:when test="${misCursosActuales}.length>0"> 
                  
              <div id="tc-loading" class="yui-g">
                    <div id="cursosActuales"></div> 
              </div>
        </c:when> 

        <c:otherwise>
            <div class="yui-g first" id="secondary">
                <div id=cursosActuales>
                    <fmt:message key="miscursos.noexistencursosactuales"/> 
                </div>
            </div>
        </c:otherwise>
    </c:choose>

      <c:choose>
        <c:when test="${misCursosRealizados}.length>0"> 
          <div id="tc-loading" class="yui-g">
            <div id="cursosRealizados"></div>   
          </div>
        </c:when> 

        <c:otherwise>
            <div class="yui-g first" id="secondary">
                <div id=cursosRealizados>
                <fmt:message key="miscursos.noexistencursosanteriores"/> 
            </div>
        </div>
        </c:otherwise>
    </c:choose>
    
  
  
  <div class="yui-g form-action">
    
  <div class="yui-g">
      <input type="button" value="<fmt:message key="btn.volver" />" class="button back" onclick="javascript:window.history.back();" />
      <input type="submit" value="<fmt:message key="btn.guardar" />" class="button save" />
      <%-- <input type="button" value="<fmt:message key="btn.imprimir" />" class="button print" /> --%>
</div>
  </div>
</div>
<div id="ft"><fmt:message key="app.copyright" /></div>
</div>

<div id="stddlg" class="popupdlg">
<div class="yui-t7">
<div class="bd">
   <div class="yui-g legend">
      <h1><fmt:message key="asociado.d.eliminar" /></h1>
      <%-- <p>Recuerda que puedes recuperarlo posteriormente desde los listados.</p> --%>
   </div>
   <div class="yui-g content">
      <div class="yui-u first rounded">
      	<form id="frmEliminar" method="delete">
        <a id="btnDlg01Eliminar" href="javascript:$('#frmEliminar').submit()">
          <span><fmt:message key="btn.eliminar" /></span>
        </a>
		</form>
      </div>
      <div class="yui-u rounded">
        <a id="btnDlg01Cancelar" href="javascript:$('#stddlg').fadeOut(200)">
          <span><fmt:message key="btn.cancelar" /></span>
        </a>
      </div>
    </div>
 </div>
</div>
</div>

      
<script src="<c:url value="/s/jquery/jquery-1.4.2.js" />" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/s/cdn/yui-lst.js" />"></script>
<script type="text/javascript">       
jQuery.fn.center = function() {
	return this.each(function() {
		var e = jQuery(this);
		var offset = e.offset();
		var y = (offset.top + $(window).height() / 2) - (e.height() / 2);
		e.attr("style", "top: " + y + "px");
	});
};


$(document).ready(function() {
    
  
    //Mis cursos dataCursosRealizados
    if(dataCursosRealizados.length>0){
          YAHOO.util.Event.addListener(window, "load", function() {
          YAHOO.example.Basic = function() {
          var myColumnDefs = [

              {key: "Ronda", label: '<fmt:message key="miscursos.ronda" />', sortable:true, resizeable:true},
              {key: "Formacion", label: '<fmt:message key="miscursos.formacion" />',  sortable:true, resizeable:true},
              {key: "Curso", label: '<fmt:message key="miscursos.curso" />',  sortable:true, resizeable:true},
              {key: "Calificacion", label: '<fmt:message key="miscursos.calificacion" />',  sortable:true, resizeable:true}

          ];

          var myDataSource = new YAHOO.util.DataSource(dataCursosRealizados);
          myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
          myDataSource.responseSchema = {
              fields: ["Ronda","Formacion","Curso","Calificacion"]
          };



          var myDataTable = new YAHOO.widget.DataTable("cursosRealizados",
                  myColumnDefs, myDataSource, {caption:"<fmt:message key="miscursos.cursosanteriores" />"});

          return {
              oDS: myDataSource,
              oDT: myDataTable
          };
	  }();
      });
  } 
  
  
  
    //Mis cursos dataCursosActuales
    if(dataCursosActuales.length>0){
          YAHOO.util.Event.addListener(window, "load", function() {
          YAHOO.example.Basic = function() {
          var myColumnDefs = [

              {key: "id", label: '<fmt:message key="miscursos.dardebaja" />', sortable: true, hidden: false, formatter:YAHOO.widget.DataTable.formatCheckbox },
              {key: "Ronda", label: '<fmt:message key="miscursos.ronda" />', sortable:true, resizeable:true},
              {key: "Formacion", label: '<fmt:message key="miscursos.formacion" />',  sortable:true, resizeable:true},
              {key: "Curso", label: '<fmt:message key="miscursos.curso" />',  sortable:true, resizeable:true},
              {key: "Calificacion", label: '<fmt:message key="miscursos.calificacion" />',  sortable:true, resizeable:true},
              {key: "Trabajo", label: '<fmt:message key="miscursos.trabajo" />',  sortable:true, resizeable:true},
              {key: "Faltas", label: '<fmt:message key="miscursos.faltas" />',  sortable:true, resizeable:true}
              
              

          ];

          var myDataSource = new YAHOO.util.DataSource(dataCursosActuales);
          myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
          myDataSource.responseSchema = {
              fields: ["Ronda","Formacion","Curso","Calificacion","Trabajo","Faltas"]
          };



          var myDataTable = new YAHOO.widget.DataTable("cursosActuales",
          myColumnDefs, myDataSource, {caption:'<fmt:message key="miscursos.cursosactuales" />'});

          return {
              oDS: myDataSource,
              oDT: myDataTable
          };
	  }();
      });
  }
  
  
});
</script>
</body>
</html> 
