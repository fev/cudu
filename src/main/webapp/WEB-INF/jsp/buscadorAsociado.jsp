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
    
    #myAutoComplete {
    width:20em; /* set width here or else widget will expand to fit its container */
    padding-bottom:20em; /* allow enough real estate for the container */
}
.yui-skin-sam .yui-ac-content { /* set scrolling */
    max-height:18em;overflow:auto;overflow-x:hidden; /* set scrolling */
    _height:18em; /* ie6 */
}


       /* Class for marked rows */
.yui-skin-sam .yui-dt tr.markC,
.yui-skin-sam .yui-dt tr.markC td.yui-dt-asc,
.yui-skin-sam .yui-dt tr.markC td.yui-dt-desc,
.yui-skin-sam .yui-dt tr.markC td.yui-dt-asc,
.yui-skin-sam .yui-dt tr.markC td.yui-dt-desc {
    background-color: #F5D487;
    color: #000;
}

.yui-skin-sam .yui-dt tr.markM,
.yui-skin-sam .yui-dt tr.markM td.yui-dt-asc,
.yui-skin-sam .yui-dt tr.markM td.yui-dt-desc,
.yui-skin-sam .yui-dt tr.markM td.yui-dt-asc,
.yui-skin-sam .yui-dt tr.markM td.yui-dt-desc {
    background-color: #EAF67A;
    color: #000;
}


.yui-skin-sam .yui-dt tr.markE,
.yui-skin-sam .yui-dt tr.markE td.yui-dt-asc,
.yui-skin-sam .yui-dt tr.markE td.yui-dt-desc,
.yui-skin-sam .yui-dt tr.markE td.yui-dt-asc,
.yui-skin-sam .yui-dt tr.markE td.yui-dt-desc {
    background-color:#B0B2F9;
    color: #000;
}

        /* Class for marked rows */
.yui-skin-sam .yui-dt tr.markP,
.yui-skin-sam .yui-dt tr.markP td.yui-dt-asc,
.yui-skin-sam .yui-dt tr.markP td.yui-dt-desc,
.yui-skin-sam .yui-dt tr.markP td.yui-dt-asc,
.yui-skin-sam .yui-dt tr.markP td.yui-dt-desc {
    background-color: #F9B0B0;
    color: #000;
}

.yui-skin-sam .yui-dt tr.markR,
.yui-skin-sam .yui-dt tr.markR td.yui-dt-asc,
.yui-skin-sam .yui-dt tr.markR td.yui-dt-desc,
.yui-skin-sam .yui-dt tr.markR td.yui-dt-asc,
.yui-skin-sam .yui-dt tr.markR td.yui-dt-desc {
    background-color: #A2F5A7;
    color: #000d;
}

/* selection */
    .yui-skin-sam th.yui-dt-selected,
    .yui-skin-sam th.yui-dt-selected a { 
        background-color:#446CD7; /* bright blue selected cell */
    }

        .yui-skin-sam .yui-dt table { border-right: 0; }
        /* .yui-skin-sam .yui-dt td.yui-dt-last, .yui-skin-sam .yui-dt th.yui-dt-last { border-right: 0; } */
        .yui-dt-hidden { display: none; }

        a#radioCualquierRama { background: transparent url('<c:url value="/s/theme/img/aramas.png" />') no-repeat 0px 0px;
                width: 90px; height: 16px; margin-top: 6px; margin-left: 9px; text-decoration: none;  }
        a#radioCualquierRama.selected { background-position: 0px -16px !important }

        div.field a.chkTipo { border: 1px solid #D4D2CC; padding: 3px 5px; margin-top: 1px; text-decoration: none; color: #333 }
        div.field a.chkTipo:hover { border-color: #0C4A64; color: #0C4A64; }
        div.field a.chkTipo.selected { border-color: #4899ce; background-color: #FFF; color: #4899ce; }
        div.field a.chkTipo.selected:hover { border-color: #ce4848; background-color: #ffeeee; color: #ce4848; }

        #overlaySelectorColumnas { width: 450px; background: url('<c:url value="/s/theme/img/onepixb.png" />');
                -moz-border-radius: 0 0 5px 5px; -webkit-border-radius: 0 0 8px 8px; }
        #overlaySelectorColumnas div.yui-g { border: 1px solid #D4D4D4; border-top: 0; background: #FBFBF7; padding: 4px 8px 4px 0; margin: 10px }
        #overlaySelectorColumnas a { display: block; float:left; margin: 4px 0px 4px 8px; padding: 4px; text-decoration: none;
                cursor: pointer; min-width: 120px; border: 1px solid #FBFBF7; color: #333 }
        #overlaySelectorColumnas a.sel, #overlaySelectorColumnas a:hover {
                border: 1px solid #206CFF; background-color: #E0ECFF; color: #206CFF; }
        #overlaySelectorColumnas a:hover { border-style: dashed; background-color: transparent }
        #overlaySelectorColumnas a.sel:hover { background-color:#FFE3E3; border-color: #900; color:#900; }
        #overlaySelectorColumnas div.ft a { margin: 0 10px 10px 0; text-decoration: none; color: #FFF;
                border: 0; min-width: 70px; float:right; background: url('<c:url value="/s/theme/img/onepixb.png" />') }
        #overlaySelectorColumnas div.ft a#btnSCCerrar:hover { background: #668e35; }




        #overlaySelectorFilas { width: 450px; background: url('<c:url value="/s/theme/img/onepixb.png" />');
                -moz-border-radius: 0 0 5px 5px; -webkit-border-radius: 0 0 8px 8px; }
        #overlaySelectorFilas div.yui-g { border: 1px solid #D4D4D4; border-top: 0; background: #FBFBF7; padding: 4px 8px 4px 0; margin: 10px }
        #overlaySelectorFilas a { display: block; float:left; margin: 4px 0px 4px 8px; padding: 4px; text-decoration: none;
                cursor: pointer; min-width: 120px; border: 1px solid #FBFBF7; color: #333 }
        #overlaySelectorFilas a.sel, #overlaySelectorFilas a:hover {
                border: 1px solid #206CFF; background-color: #E0ECFF; color: #206CFF; }
        #overlaySelectorFilas a:hover { border-style: dashed; background-color: transparent }
        #overlaySelectorFilas a.sel:hover { background-color:#FFE3E3; border-color: #900; color:#900; }
        #overlaySelectorColFilas div.ft a { margin: 0 10px 10px 0; text-decoration: none; color: #FFF;
                border: 0; min-width: 70px; float:right; background: url('<c:url value="/s/theme/img/onepixb.png" />') }
        #overlaySelectorCoFilas div.ft a#btnSCCerrar:hover { background: #668e35; }




        /* Expansión de formulario */
        div#doc3 div#bd { background: #FFF; -webkit-border-radius: 6px; -moz-border-radius: 6px }
        div#doc3 div#ft, div#doc3 div#hd { background: transparent; }




/* custom styles for inline instances */
.yui-skin-sam .yui-ac-input { position:static;width:20em; vertical-align:middle;}
.yui-skin-sam .yui-ac-container { width:20em;left:0px;}

/* needed for stacked instances for ie & sf z-index bug of absolute inside relative els */
#bAutoComplete { z-index:9001; } 
#lAutoComplete { z-index:9000; }

/* buttons */
.yui-ac .yui-button {vertical-align:middle;}
div#bd { padding-left: 20px; }
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
      <fmt:message key="asociado.titulo.buscar" />
  </h1></div>
  <div class="yui-g" style="text-align:right; padding-top: 1px; margin-right: 13px">
    <img src="<c:url value="/s/theme/img/tango/document-save.png" />" />

    <a id="btnSave" href="<%=request.getContextPath()%>/s/filesClient/listado.pdf ">
      <img src="<c:url value="/s/theme/img/tango/save_24.png" />" />
      <span>descargar</span>
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

  <div class="yui-g legend"><h2><fmt:message key="asociado.h.info" /></h2></div>
  <div class="yui-g">
    <div class="yui-g first">    
      <div class="field">
        <label for="txtNombre"><fmt:message key="asociado.f.nombre" /></label>
        <form:input id="txtNombre" path="nombre" cssClass="textbox w3u" cssErrorClass="textbox w3u error" />
      </div>
      <div class="field">
        <label for="txtApellido1"><fmt:message key="asociado.f.primerapellido" /></label>
        <form:input id="txtApellido1" path="primerapellido" cssClass="textbox w3u" cssErrorClass="textbox w3u error" />
      </div>
      <div class="field">
        <label for="txtApellido2"><fmt:message key="asociado.f.segundoapellido" /></label>
        <form:input id="txtApellido2" path="segundoapellido" cssClass="textbox w3u" cssErrorClass="textbox w3u error" />
      </div>
        
      <div class="field">
          <label for="radioTipo"><fmt:message key="asociado.f.tipo" /></label>          

          <form:radiobutton id="radioKraal" cssClass="radio" path="tipo" value="K" title="Kraal" />
          <label><fmt:message key="asociado.tipo.kraal"/></label>

          <form:radiobutton id="radioComite" cssClass="radio" path="tipo" value="C" title="Comite" />
          <label><fmt:message key="asociado.tipo.comite" /></label>

          <form:radiobutton id="radioJoven" cssClass="radio" path="tipo" value="J" title="Joven" />
          <label><fmt:message key="asociado.tipo.joven"/></label>
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
        <label for="txtDNI"><fmt:message key="asociado.f.dni" /></label>
        <form:input id="txtDNI" path="dni" cssClass="textbox w1u" maxlength="10" />
      </div>
      

        
    <div class="field" id="bAutoComplete">
        <label for="bInput">Cargo</label>
        <input id="bInput" type="text"> <span id="toggleB"></span>
            <div id="bContainer"></div>
    </div>

    
    <div class="field"  id="lAutoComplete">
        <label for="lInput">Grupo</label>
            <input id="lInput" type="text"> <span id="toggleL"></span>
            <div id="lContainer"></div>
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
      <div class="field">
        <label for="txtMail"><fmt:message key="asociado.f.email" /></label>
        <form:input id="txtMail" path="email" cssClass="textbox w3u" />
      </div>
    </div>    
  </div>
  
  <div class="yui-g legend hidden"><h2><fmt:message key="asociado.h.cargos" /></h2></div>
  <div class="yui-g hidden">
    <p>TODO: pantalla muy chachi donde editar esto...</p>
  </div>


  <div class="yui-g form-action">
    <div class="yui-g first">
   	<c:if test="${asociado.id > 0}">
      <input type="button" value="<fmt:message key="btn.eliminar" />" class="button delete" onclick="javascript:cudu.remove()" />
    </c:if>
    </div>
    <div class="yui-g">
      <input type="button" value="<fmt:message key="btn.volver" />" class="button back" onclick="javascript:window.history.back();" />
      <input type="button" value="<fmt:message key="btn.limpiar" />" class="button save " />
      <input type="submit" value="<fmt:message key="btn.buscar" />" class="button save" />
      <%-- <input type="button" value="<fmt:message key="btn.imprimir" />" class="button print" /> --%>
    </div>
  </div>
</div>
</form:form>
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
      	<form:form id="frmEliminar" method="delete">
        <a id="btnDlg01Eliminar" href="javascript:$('#frmEliminar').submit()">
          <span><fmt:message key="btn.eliminar" /></span>
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

<div id="doc" class="yui-t7">
<jsp:include page="header.jsp"></jsp:include>
<div id="bd">
  <div class="yui-g tc-tb">
	<%-- <a href="<c:url value="/asociado/nuevo" />" class="save">
		<img src="<c:url value="/s/theme/img/tango/document-new.png" />" />
		<span><fmt:message key="listados.tb.nuevo" /></span>
	</a> --%>
	<a id="btnFiltro" href="javascript:cudu.ui.toogleFilter()">
		<img src="<c:url value="/s/theme/img/tango/edit-find.png" />" />
		<span><fmt:message key="listados.tb.filtrar" /></span>
	</a>
    <a id="btnSelectorColumnas" href="javascript:cudu.noop()">
      <img src="<c:url value="/s/theme/img/tango/select-column.png" />" />
      <span><fmt:message key="listados.tb.columnas" /></span>
    </a>

    <a id="btnList" href="javascript:cudu.ui.listFilter()">
      <img src="<c:url value="/s/theme/img/tango/document-list.png" />" />
      <span><fmt:message key="listados.tb.list" /></span>
    </a>
    <a id="btnImprimir" href="#" target="_blank">
      <img src="<c:url value="/s/theme/img/tango/document-print.png" />" />
      <span><fmt:message key="listados.tb.imprimir" /></span>
    </a>
    <a id="btnPdf" href="#" target="_blank">
      <img src="<c:url value="/s/theme/img/tango/document-pdf.png" />" />
      <span><fmt:message key="listados.tb.pdf" /></span>
    </a>
    

    <a href="<c:url value="/" />" class="">
		<img src="<c:url value="/s/theme/img/tango/edit-undo.png" />" />
		<span><fmt:message key="listados.tb.volver" /></span>
	</a>
    <%--<a href="#">
		<img src="<c:url value="/s/theme/img/tango/document-save.png" />" />
		<span>Guardar</span>
	</a>
	<a href="#" class="delete">
		<img src="<c:url value="/s/theme/img/tango/edit-delete-row.png" />" />
		<span>Eliminar</span>
	</a>--%>
	<a id="btnExpandir" href="javascript:cudu.ui.expandirUI()" class="right single">
		<img id="imgBtnExpandir" src="<c:url value="/s/theme/img/tango/tc-expandir.png" />" />
		<span id="lblBtnExpandir"><fmt:message key="listados.tb.expandir" /></span>
	</a>
  </div>
  <div id="tc-loading" class="yui-g">
  	<div style="margin: 10px 5px"><fmt:message key="listados.tabla.cargando" /></div>
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
    <div class="yui-g first">
      <div id="tc-filter-ramas" class="field">
      	<label for="" class="w1u">Rama</label>
      	<a id="radioColonia" href="javascript:" class="dropramas" >&nbsp;</a>
        <a id="radioManada" href="javascript:" class="dropramas">&nbsp;</a>
        <a id="radioExploradores" href="javascript:" class="dropramas">&nbsp;</a>
        <a id="radioPioneros" href="javascript:" class="dropramas">&nbsp;</a>
        <a id="radioRutas" href="javascript:" class="dropramas">&nbsp;</a>
        <a id="radioCualquierRama" href="javascript:" class="dropramas selected">&nbsp;</a>
      </div>
      <div id="tc-filter-tipo" class="field">
      	<label for="" class="w1u">Tipo</label>
      	<a id="chkTipoJ" href="javascript:" class="chkTipo"><fmt:message key="asociado.tipo.joven" /></a>
      	<a id="chkTipoK" href="javascript:" class="chkTipo"><fmt:message key="asociado.tipo.kraal" /></a>
      	<a id="chkTipoC" href="javascript:" class="chkTipo"><fmt:message key="asociado.tipo.comite" /></a>
      	<a id="chkTipoT" href="javascript:" class="chkTipo selected"><fmt:message key="listados.f.cualquiera" /></a>
      </div>
    </div>
  </div>
  <div class="yui-g tc-table yui-skin-sam">
    <div id="listado"></div>
    <div id="paginador"></div>
  </div>
</div>
<div id="ft"><fmt:message key="app.copyright" />
</div>
</div>


<script type="text/javascript" src="<c:url value="/s/cdn/yui-lst.js" />"></script>

<%-- <script type="text/javascript" src="<c:url value="/s/yui/yahoo-dom-event/yahoo-dom-event.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/yui/selector/selector-min.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/yui/event-delegate/event-delegate-min.js" />"></script>--%>
<%-- <script type="text/javascript" src="<c:url value="/s/yui/logger/logger.js" />"></script> --%>
<%-- <script type="text/javascript" src="<c:url value="/s/yui/connection/connection-min.js" />"></script> 
<script type="text/javascript" src="<c:url value="/s/yui/json/json-min.js" />"></script>  
<script type="text/javascript" src="<c:url value="/s/yui/dragdrop/dragdrop-min.js" />"></script> 
<script type="text/javascript" src="<c:url value="/s/yui/element/element-min.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/yui/container/container-min.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/yui/paginator/paginator-debug.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/yui/animation/animation-min.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/yui/datasource/datasource-debug.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/yui/datatable/datatable-debug.js" />"></script> --%>
<script type="text/javascript" src="<c:url value="/s/scripts/listados.js" />"></script>
<script type="text/javascript">
cudu.i8n.ramas = {
	'C': '<fmt:message key="rama.unos.C" />',
	'M': '<fmt:message key="rama.unos.M" />',
	'E': '<fmt:message key="rama.unos.E" />',
	'P': '<fmt:message key="rama.unos.P" />',
	'R': '<fmt:message key="rama.unos.R" />'
};

cudu.i8n.tipos = {
	'J': '<fmt:message key="asociado.tipo.joven" />',
	'K': '<fmt:message key="asociado.tipo.kraal" />',
	'C': '<fmt:message key="asociado.tipo.comite" />'
};

cudu.i8n.tabla = {
	SinDatos: '<fmt:message key="listados.tabla.sindatos" />',
	Cargando: '<fmt:message key="listados.tabla.cargando" />',
	OrdenarAscendente: '<fmt:message key="listados.tabla.ordenascendente" />',
	OrdenarDescendente: '<fmt:message key="listados.tabla.ordendescendente" />',
	PagActual: '<fmt:message key="listados.tabla.pagactual" />',
	PagAnterior: '<fmt:message key="listados.tabla.paganterior" />',
	PagSiguiente: '<fmt:message key="listados.tabla.pagsiguiente" />',
	PagInicio: '<fmt:message key="listados.tabla.paginicio" />',
	PagFin: '<fmt:message key="listados.tabla.pagfin" />'
};

cudu.i8n.btnExpandir = {
	Expandir: '<fmt:message key="listados.tb.expandir" />',
	Contraer: '<fmt:message key="listados.tb.contraer" />',
	imgExpandir: '<c:url value="/s/theme/img/tango/tc-expandir.png" />',
	imgContraer: '<c:url value="/s/theme/img/tango/tc-contraer.png" />'
};

cudu.dom.tcFilter = document.getElementById('tc-filter');
cudu.dom.chkTipoJ = document.getElementById('chkTipoJ');
cudu.dom.chkTipoK = document.getElementById('chkTipoK');
cudu.dom.chkTipoC = document.getElementById('chkTipoC');

cudu.dom.btnPdf = document.getElementById('btnPdf');
cudu.dom.btnImprimir = document.getElementById('btnImprimir');
cudu.dom.lblBtnExpandir = document.getElementById('lblBtnExpandir');
cudu.dom.imgBtnExpandir = document.getElementById('imgBtnExpandir');

cudu.dom.tcLoading = document.getElementById('tc-loading');

cudu.dom.codigosRamas = {
	'radioColonia': 'C',
	'radioManada': 'M',
	'radioExploradores': 'E',
	'radioPioneros': 'P',
	'radioRutas': 'R'
};

YAHOO.util.Event.addListener(window, "load", function() {
	// TODO grupo.nombre no se interpreta bien por YUI
	var listaColumnas = [
                {key:"checked",label:"", width:"30", formatter:YAHOO.widget.DataTable.formatCheckbox},

                { key: "id", label: '<fmt:message key="listados.c.id" />', sortable: true, hidden: false, pk: true, formatter:YAHOO.widget.DataTable.formatCheckbox },
   		{ key: "tipo", label: "Tipo", sortable: true, formatter: "tipo" },
   		{ key: "ramas", label: '<fmt:message key="listados.c.ramas" />', sortable: true, formatter: "rama" },
   		//{ key: "nombreCompleto", label: '<fmt:message key="listados.c.nombre" />', sortable: true },
                { key: "nombre", label: '<fmt:message key="listados.c.nombre" />', sortable: true },
                { key: "primerapellido", label: '<fmt:message key="listados.c.primerapellido" />', sortable: true },
                { key: "segundoapellido", label: '<fmt:message key="listados.c.segundoapellido" />', sortable: true },

   		{ key: "fechanacimiento", label: '<fmt:message key="listados.c.fechanacimiento" />', sortable: true, parser: "date", formatter: "date" },
   		{ key: "telefonocasa", label: '<fmt:message key="listados.c.telefonocasa" />', sortable: true, formatter: "telefono" },
   		{ key: "telefonomovil", label: '<fmt:message key="listados.c.telefonomovil" />', sortable: true, formatter: "telefono"  },
		<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'SdA','SdC','MEV')">
   		{ key: 'idGrupo', label: '<fmt:message key="listados.c.grupo" />', sortable: true },
   		{ key: 'asociacion', label: '<fmt:message key="listados.c.asociacion" />', sortable: true, formatter: "asociacion" },
   		</sec:authorize>
   		{ key: "calle", label: '<fmt:message key="listados.c.direccion" />', sortable: true, hidden: true },
                { key: "numero", label: '<fmt:message key="listados.c.numero" />', sortable: true, hidden: true },
                { key: "escalera", label: '<fmt:message key="listados.c.escalera" />', sortable: true, hidden: true },
   		{ key: "email", label: '<fmt:message key="listados.c.email" />', sortable: true, hidden: true },
   		{ key: "dni", label: '<fmt:message key="listados.c.dni" />', sortable: true, hidden: true },
   		{ key: "provincia", label: '<fmt:message key="listados.c.provincia" />', sortable: true, hidden: true },
   		{ key: "municipio", label: '<fmt:message key="listados.c.municipio" />', sortable: true, hidden: true },
                { key: "puerta", label: '<fmt:message key="listados.c.puerta" />', sortable: true, hidden: true },

               
                
                { key: "fechaActualizacion", label: '<fmt:message key="listados.c.ultimaModificacion" />', sortable: true, hidden: true, parser: "date", formatter: "date" }


   	];

        var listaFilas = [
   		{ key: "10", label: "10", sortable: true },
                { key: "20", label: "20", sortable: true },
                { key: "50", label: "50", sortable: true },
                { key: "9999999", label: "Todos", sortable: true }
        ];

        var asociadosPorPagina=10;
        

	cudu.dom.seleccionadoFilas = 
            new cudu.ui.datatable.panelSeleccionFilas
        ({
		columnas: listaColumnas.slice(1),
                filas: listaFilas,
                filasPorPagina: asociadosPorPagina,
		dataSourceUrl: '<c:url value="listados/asociados.json" />'
	});
	cudu.dom.tabla = new cudu.ui.datatable.table({ 
            columnas: listaColumnas.slice(1),
                filas: listaFilas,
                    filasPorPagina: asociadosPorPagina,
		dataSourceUrl: '<c:url value="listados/asociados.json" />'
	});
	
	YAHOO.util.Dom.addClass(cudu.dom.tcLoading, 'hidden');

	// Filtro por ramas
	cudu.dom.ctrlRamas = YAHOO.util.Dom.getElementsByClassName('dropramas', 'a', 'tc-filter-ramas');	
	cudu.dom.ctrlRamasCualquiera = YAHOO.util.Dom.get('radioCualquierRama');
	YAHOO.util.Event.addListener(cudu.dom.ctrlRamas, "click", function(e) {
		if (e.target.id == 'radioCualquierRama') {
			YAHOO.util.Dom.removeClass(cudu.dom.ctrlRamas, 'selected');
		} else {
			YAHOO.util.Dom.removeClass(cudu.dom.ctrlRamasCualquiera, 'selected');
		}

		if (YAHOO.util.Dom.hasClass(e.target, 'selected')) {
			YAHOO.util.Dom.removeClass(e.target, 'selected');
		} else {
			YAHOO.util.Dom.addClass(e.target, 'selected');
		} 

		cudu.filtrarPor.rama(cudu.dom.codigosRamas[e.target.id]);
	});

	// Filtro por tipo
	cudu.dom.ctrlTipo = YAHOO.util.Dom.getElementsByClassName('chkTipo', 'a', 'tc-filter-tipo');
	cudu.dom.ctrlTipoCualquiera = YAHOO.util.Dom.get('chkTipoT');
	YAHOO.util.Event.addListener(cudu.dom.ctrlTipo, "click", function(e) {
		if (e.target.id == 'chkTipoT') {
			YAHOO.util.Dom.removeClass(cudu.dom.ctrlTipo, 'selected');
		} else {
			YAHOO.util.Dom.removeClass(cudu.dom.ctrlTipoCualquiera, 'selected');
		}

		if (YAHOO.util.Dom.hasClass(e.target, 'selected')) {
			YAHOO.util.Dom.removeClass(e.target, 'selected');
		} else {
			YAHOO.util.Dom.addClass(e.target, 'selected');
		}

		cudu.filtrarPor.tipo();
	});

	// DBG
	// toogleFilter();


        //desplegable
        YAHOO.util.Event.onContentReady("menu_vertical", function () {
          var elMenu = new YAHOO.widget.Menu("menu_vertical", { width: '150px' });
          elMenu.render();
          elMenu.show();
        });
});

</script>
</body>
</html>