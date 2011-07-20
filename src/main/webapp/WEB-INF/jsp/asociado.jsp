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
<form:form modelAttribute="asociado" method="POST">
<div id="bd">  
  <div class="yui-g">
  <div class="yui-g first"><h1 id="hform">
  	<c:choose>
  	  <c:when test="${asociado.id > 0}"><spring:message code="asociado.titulo.editar" /></c:when> 
  	  <c:otherwise><spring:message code="asociado.titulo.nuevo.${asociado.tipo}" /></c:otherwise>
  	</c:choose>
  </h1></div>
      
       <c:out value="<script language='JavaScript'>" escapeXml="false"></c:out>
			var data=[];
                     <c:forEach var="linea"                items="${recorrido}" varStatus="status">
                         var rama,tipo;
                         if("${linea[2]}"=="C"){rama = '<fmt:message key="rama.uno.C" />';}
                         else if("${linea[2]}"=="M"){rama = '<fmt:message key="rama.uno.M" />';}
                         else if("${linea[2]}"=="E"){rama = '<fmt:message key="rama.uno.E" />';}
                         else if("${linea[2]}"=="P"){rama = '<fmt:message key="rama.uno.P" />';}
                         else{rama = '<fmt:message key="rama.uno.R" />';}
                         
                         if("${linea[2]}"=="J"){tipo= '<fmt:message key="asociado.tipo.joven" />';}
                         else if("${linea[2]}"=="K"){tipo = '<fmt:message key="asociado.tipo.kraal" />';}
                         else{tipo = '<fmt:message key="asociado.tipo.comite" />';}
                         
                            data.push({
                                Tipo:  tipo,
                                Ramas: rama,
                                Grupo: "${linea[3]}",
                                Ronda: "${linea[4]}"
                                
                            });                        
                     </c:forEach>    
                            
                    var grupo=[];
                     <c:forEach var="grupo" items="${grupos}" varStatus="status">
                        grupo.push("${grupo[0]}");
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
  		<form:errors id="mpbderr" path="*" />
  	</cudu:message>
  </spring:hasBindErrors>
  
  <c:if test="${param.ok != null && erroresValidacion == false}">
  	<cudu:message id="mp01" key="frm.ok" single="true" />
  </c:if>

    <sec:authorize access="hasAnyRole('ROLE_PERMISO_B','ROLE_PERMISO_A','ROLE_PERMISO_C1','ROLE_PERMISO_C2','ROLE_PERMISO_C3')">
      <div class="yui-g legend"><h2><fmt:message key="asociado.h.tipo" /></h2></div>
      <p align="center">
          <c:if test="${asociado.tipo == 'J' || asociado.tipo == 'C' }"> <%-- KRAAL o COMITÃ‰ --%>
              <form:radiobutton id="radioKraal" cssClass="radio" path="tipo" value="K" title="Kraal" />
              <label><fmt:message key="asociado.f.cambiar_a_kraal"/></label>
          </c:if>
          <c:if test="${asociado.tipo == 'J' || asociado.tipo == 'K' }"> <%-- Joven--%>
              <form:radiobutton id="radioComite" cssClass="radio" path="tipo" value="C" title="Comite" />
              <label><fmt:message key="asociado.f.cambiar_a_comite" /></label>
          </c:if>
          <c:if test="${asociado.tipo == 'J'}">
              <form:radiobutton id="radioJoven" cssClass="radio" path="tipo" value="J" title="Joven" />
              <label><fmt:message key="asociado.tipo.joven"/></label>
          </c:if>
          <c:if test="${asociado.tipo == 'K'}">
              <form:radiobutton id="radioKraal" cssClass="radio" path="tipo" value="K" title="Kraal" />
             <label><fmt:message key="asociado.tipo.kraal"/></label>
          </c:if>
          <c:if test="${asociado.tipo == 'C'}">
              <form:radiobutton id="radioJoven" cssClass="radio" path="tipo" value="C" title="Comite" />
              <label><fmt:message key="asociado.tipo.comite"/></label>
          </c:if>
      </p>
    </sec:authorize>


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
        <span id="lblFechaNac" class="literal"><%-- 8 años, 9 meses --%></span>
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
        <form:input id="txtDNI" path="dni" cssClass="textbox w1u" maxlength="10" />
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
        <form:input id="txtNumero" path="numero" cssClass="textbox w0u" cssErrorClass="textbox w0u error" maxlength="5" />
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
        <form:input id="txtTelefono" path="telefonocasa" cssClass="textbox w3u" maxlength="15" />
      </div>
      <div class="field">
        <label for="txtMovil"><fmt:message key="asociado.f.telefonomovil" /></label>
        <form:input id="txtMovil" path="telefonomovil" cssClass="textbox w3u" maxlength="15" />
      </div>
      <div class="field required">
        <label for="txtMail"><fmt:message key="asociado.f.email" /></label>
        <form:input id="txtMail" path="email" cssClass="textbox w3u" />
      </div>
      <div class="field required">
          
        <label for="txtGrupo" class="w2u"><fmt:message key="asociado.f.grupo" /></label>
        <form:label id="txtGrupo" path="grupo.nombre" cssClass="textbox w3u">${asociado.grupo.nombre}</form:label>
        
      </div>
      
        <div id="diviCargos">
            <label  for="cxbCargos"><fmt:message key="asociado.f.cargo" /></label>
                <form:checkbox id="chkPresidencia" path="cargo.presidencia" cssClass="checkbox" />
              <label for="chkPresidencia" class="checkbox"><fmt:message key="asociado.cargo.presidencia"/></label>              
                </div>

              <label for="chkSecretaria" class="checkbox"><fmt:message key="asociado.cargo.secretaria"/></label>
              <form:checkbox id="chkSecretaria" path="cargo.secretaria" cssClass="checkbox" />

              <label for="chkTesoreria" class="checkbox"><fmt:message key="asociado.cargo.tesoreria"/></label>
              <form:checkbox id="chkTesoreria" path="cargo.tesoreria" cssClass="checkbox" />

              <label for="chkIntendencia" class="checkbox"><fmt:message key="asociado.cargo.intendencia"/></label>
              <form:checkbox id="chkIntendencia" path="cargo.intendencia" cssClass="checkbox" />

              <label for="chkCocina" class="checkbox"><fmt:message key="asociado.cargo.cocina"/></label>
              <form:checkbox id="chkCocina" path="cargo.cocina" cssClass="checkbox" />

              <label for="chkConsiliario" class="checkbox"><fmt:message key="asociado.cargo.consiliario"/></label>            
              <form:checkbox id="chkConsiliario" path="cargo.consiliario" cssClass="checkbox" />
            
              <label for="chkVocal" class="checkbox"><fmt:message key="asociado.cargo.vocal"/></label>
              <form:checkbox id="chkVocal" path="cargo.vocal" cssClass="checkbox" />/>

              <label for="chkOtro" class="checkbox"><fmt:message key="asociado.cargo.otro"/></label>
              <form:checkbox id="chkOtro" path="cargo.otro" cssClass="checkbox"/>
              
              
              
        </div> 
    </div>      
  </div>
  
  <c:if test="${asociado.tipo != 'J'}"> <%-- KRAAL o COMITÉ tienen cargos --%>
  <div class="yui-g legend hidden"><h2><fmt:message key="asociado.h.cargos" /></h2></div>
  <div class="yui-g hidden">
    <p>TODO: pantalla muy chachi donde editar esto...</p>
  </div>
  </c:if>

  <c:if test="${asociado.tipo == 'J'}"> <%-- JOVEN Responsables del menor, copias de documentos --%>
      <c:if test="${edadAsociado<18}"> 
          <div class="yui-g legend"><h2><fmt:message key="asociado.h.responsables" /></h2></div>
          <div class="yui-g">
            <div class="yui-g first">
              <h3><fmt:message key="asociado.h.entornofamiliar" /></h3>
              <div class="field required">
                <form:checkbox id="checkTutorLegal" path="tienetutorlegal" cssClass="checkbox" title="Tutor legal" />
                <label for="checkTutorLegal" class="checkbox"><fmt:message key="asociado.f.tutorlegal" /></label>
                <form:checkbox id="checkPadresSeparados" path="padresdivorciados" cssClass="checkbox" title="Padres separados"  />
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
                <form:input id="txtPadreTel" path="padreTelefono" cssClass="textbox w3u" maxlength="15" />
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
                <form:input id="txtMadreTel" path="madreTelefono" cssClass="textbox w3u" maxlength="15" />
              </div>
              <div class="field">
                <label for="txtMadreMail"><fmt:message key="asociado.f.datospadres.email" /></label>
                <form:input id="txtMadreMail" path="madreEmail" cssClass="textbox w3u" />
              </div>
            </div>
          </div>
      </c:if>

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

  <div id="tc-loading" class="yui-g">
  	<div id="recorrido"></div> 
  </div>
  
  <div id="tc-filter" class="yui-g tc-flt" style="padding: 0px 5px">
    <div class="yui-g">
      <div class="field">
        <label for="txtBusqueda" class="w2u">Búsqueda</label>
        <input id="txtBusqueda" class="textbox w3u" />
      </div>
      <div class="field">
      	<label class="w2u">Eliminados</label>
      	<input id="radioVerEliminados" name="radioEliminados" disabled="disabled" class="radio" value="M" type="radio" />
        <label for="radioVerEliminados" class="radio">Mostrar</label>
        <input id="radioOcultarEliminados" name="radioEliminados" class="radio"  value="O" type="radio" checked="checked" />
        <label for="radioOcultarEliminados" class="radio">Ocultar</label>
      </div>
    </div>
    
  </div>
  <div class="yui-g tc-table yui-skin-sam">
    <div id="listado"></div>
    <div id="paginador"></div>
  </div>
  
  
  
  <div class="yui-g form-action">
    <div class="yui-g first">
   	<c:if test="${asociado.id > 0}">
            <sec:authorize access="hasAnyRole('ROLE_PERMISO_B','ROLE_PERMISO_A','ROLE_PERMISO_C1','ROLE_PERMISO_C2','ROLE_PERMISO_C3')">
                <input type="button" value="<fmt:message key="btn.eliminar" />" class="button delete" onclick="javascript:cudu.remove()" />
            </sec:authorize>
            <c:if test="${asociado.id > 0}">
                <input type="submit" value="<fmt:message key="btn.eliminardefinitivamente" />" class="button delete" onclick="javascript:cudu.removeForEver()" />
            </c:if>            
        </c:if>
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

<div id="stddlg" class="popupdlg">
<div class="yui-t7">
<div class="bd">
    
   <div class="yui-g legend">
      <h1><fmt:message key="asociado.d.eliminar" /></h1>
      <%-- <p>Recuerda que puedes recuperarlo posteriormente desde los listados.</p> --%>
   </div>
   
   <div class="yui-g content">
      <div class="yui-u first rounded">
          <sec:authorize access="hasAnyRole('ROLE_PERMISO_B','ROLE_PERMISO_A','ROLE_PERMISO_C1','ROLE_PERMISO_C2','ROLE_PERMISO_C3')">
      	<form:form id="frmEliminar" method="delete">
        <a id="btnDlg01Eliminar" href="javascript:$('#frmEliminar').submit()">
          <span><fmt:message key="btn.eliminar" /></span>
        </a>
        </form:form>
          </sec:authorize>
          
          <form:form id="frmEliminar" method="deleteFromDB">
        <a id="btnDlg01Eliminar" href="javascript:$('#frmEliminar').submit()">
          <span><fmt:message key="btn.eliminardefinitivamente" /></span>
        </a>
        </form:form>
          
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

cargos = {
	'P': 	        "<fmt:message key="asociado.cargo.presidencia"/>", 
	'S':        "<fmt:message key="asociado.cargo.secretaria"/>", 
	'T':        "<fmt:message key="asociado.cargo.tesoreria"/>", 
        'I':        "<fmt:message key="asociado.cargo.intendencia"/>", 
        'C':        "<fmt:message key="asociado.cargo.cocina"/>", 
        'N':        "<fmt:message key="asociado.cargo.consiliario"/>", 
        'V':        "<fmt:message key="asociado.cargo.vocal"/>", 
        'O':        "<fmt:message key="asociado.cargo.otro"/>"
	
	
};

cudu = {
	back: function() {
		document.location = '<c:url value="/dashboard" />'; 
	},

	remove: function() {		
		$("#stddlg").center().fadeIn();
	},
        removeForEver: function() {
                $("#stddlg").center().fadeIn();  
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

        //  TABLA DE RECORRIDO DEL ASOCIADO
	  if(data.length>0){
	  YAHOO.util.Event.addListener(window, "load", function() {
	  YAHOO.example.Basic = function() {
	      var myColumnDefs = [
    
		  {key: "Tipo", label: '<fmt:message key="recorrido.asociado.tipo" />', sortable:true, resizeable:true},
		  {key: "Ramas", label: '<fmt:message key="recorrido.asociado.ramas" />',  sortable:true, resizeable:true},
		  {key: "Grupo", label: '<fmt:message key="recorrido.asociado.grupo" />',  sortable:true, resizeable:true},
		  {key: "Ronda", label: '<fmt:message key="recorrido.asociado.ronda" />',  sortable:true, resizeable:true}
		  
	      ];
	      
	      var myDataSource = new YAHOO.util.DataSource(data);
	      myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
	      myDataSource.responseSchema = {
		  fields: ["Tipo","Ramas","Grupo","Ronda"]
	      };



	      var myDataTable = new YAHOO.widget.DataTable("recorrido",
		      myColumnDefs, myDataSource, {caption:"<fmt:message key="recorrido.asociado.tituloTabla" />"});

	      return {
		  oDS: myDataSource,
		  oDT: myDataTable
	      };
	  }();
      });
    }
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

//////////////////////////////////////////////////FIN DE ESTADISTICA, INICIO DE COMBOBOX
/*
        combobox = function() {
                //CARGOS
                var cargos=[
                    "<fmt:message key="asociado.cargo.presidencia"/>", 
                    "<fmt:message key="asociado.cargo.secretaria"/>", 
                    "<fmt:message key="asociado.cargo.tesoreria"/>", 
                    "<fmt:message key="asociado.cargo.intendencia"/>", 
                    "<fmt:message key="asociado.cargo.cocina"/>", 
                    "<fmt:message key="asociado.cargo.consiliario"/>", 
                    "<fmt:message key="asociado.cargo.vocal"/>", 
                    "<fmt:message key="asociado.cargo.otro"/>"
                ];
                 var dDcargos = new YAHOO.util.LocalDataSource(cargos);

                // instanciar autocomplete
                var oConfigs = {
                    prehighlightClassName: "yui-ac-prehighlight",
                    useShadow: true,
                    queryDelay: 0,
                    minQueryLength: 0,
                    animVert: .01
                }
                var dACcargos = new YAHOO.widget.AutoComplete("dCargos", "dContainerCargos", dDcargos, oConfigs);

                // Grupo combobox
                var dToggler = YAHOO.util.Dom.get("toggleDCargo");
                var oPushButtonDcargo= new YAHOO.widget.Button({container:dToggler});
                var toggleDCargo = function(e) {
                    //YAHOO.util.Event.stopEvent(e);
                    if(!YAHOO.util.Dom.hasClass(dToggler, "open")) {
                        YAHOO.util.Dom.addClass(dToggler, "open")
                    }

                    // Is open
                    if(dACcargos.isContainerOpen()) {
                        dACcargos.collapseContainer();
                    }
                    // Is closed
                    else {
                        dACcargos.getInputEl().focus(); // Needed to keep widget active
                        setTimeout(function() { // For IE
                            dACcargos.sendQuery("");
                        },0);
                    }
                }
                oPushButtonDcargo.on("click", toggleDCargo);
                dACcargos.containerCollapseEvent.subscribe(function(){YAHOO.util.Dom.removeClass(dToggler, "open")});

                return {

                    dACcargos: dACcargos,
                    dDcargos: dDcargos
                };
            }();
            */
</script>
</body>
</html>