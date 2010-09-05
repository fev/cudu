<%@include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="app.title" /></title>
<link rel="icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link rel="shortcut icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/reset-fonts-grids/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/base/base-min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/paginator/assets/skins/sam/paginator.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/datatable/assets/skins/sam/datatable.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/logger/assets/logger.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/theme/cudu.css" />" />
<style type="text/css">
.yui-skin-sam .yui-dt tr.mark, 
.yui-skin-sam .yui-dt tr.mark td.yui-dt-asc, 
.yui-skin-sam .yui-dt tr.mark td.yui-dt-desc, 
.yui-skin-sam .yui-dt tr.mark td.yui-dt-asc, 
.yui-skin-sam .yui-dt tr.mark td.yui-dt-desc { 
    background-color: #a33; 
    color: #fff;
}

.yui-skin-sam .yui-dt table { border-right: 0; }
.yui-skin-sam .yui-dt td.yui-dt-last, .yui-skin-sam .yui-dt th.yui-dt-last { border-right: 0; }
.yui-dt-hidden { display: none; }

a#radioCualquierRama { background: transparent url('<c:url value="/s/theme/img/aramas.png" />') no-repeat 0px 0px;
	width: 90px; height: 16px; margin-top: 6px; margin-left: 9px; text-decoration: none;  }
a#radioCualquierRama.selected { background-position: 0px -16px !important }

div.field a.chkTipo { border: 1px solid #D4D2CC; padding: 3px 5px; margin-top: 1px; text-decoration: none; color: #333 }
div.field a.chkTipo:hover { border-color: #0C4A64; color: #0C4A64; }
div.field a.chkTipo.selected { border-color: #4899ce; background-color: #FFF; color: #4899ce; }
div.field a.chkTipo.selected:hover { border-color: #ce4848; background-color: #ffeeee; color: #ce4848; }
</style>
</head>
<body>

<div id="doc" class="yui-t7">
<jsp:include page="header.jsp"></jsp:include>
<div id="bd">
  <div class="yui-g tc-tb">
	<%-- <a href="<c:url value="/asociado/nuevo" />" class="save">
		<img src="<c:url value="/s/theme/img/tango/document-new.png" />" />
		<span><fmt:message key="listados.tb.nuevo" /></span>
	</a> --%>
	<a href="javascript:cudu.ui.toogleFilter()">
		<img src="<c:url value="/s/theme/img/tango/edit-find.png" />" />
		<span><fmt:message key="listados.tb.filtrar" /></span>
	</a>
    <%--<a href="#">
      <img src="<c:url value="/s/theme/img/tango/select-column.png" />" />
      <span><fmt:message key="listados.tb.columnas" /></span>
    </a> --%>
    <a id="btnImprimir" href="#" target="_blank">
      <img src="<c:url value="/s/theme/img/tango/document-print.png" />" />
      <span><fmt:message key="listados.tb.imprimir" /></span>
    </a>
    <%--<a href="javascript:dbreload()">
      <img src="<c:url value="/s/theme/img/tango/emblem-favorite.png" />" />
      <span>Reload!</span>
    </a>
	<a href="#">
		<img src="<c:url value="/s/theme/img/tango/document-save.png" />" />
		<span>Guardar</span>
	</a>
	<a href="#" class="delete">
		<img src="<c:url value="/s/theme/img/tango/edit-delete-row.png" />" />
		<span>Eliminar</span>
	</a>--%>
	<a href="<c:url value="/" />">
		<img src="<c:url value="/s/theme/img/tango/edit-undo.png" />" />
		<span><fmt:message key="listados.tb.volver" /></span>
	</a>
  </div>
  <div id="tc-filter" class="yui-g tc-flt" style="padding: 0px 5px">
    <div class="yui-g">
      <div class="field">
        <label for="txtBusqueda" class="w2u">Búsqueda</label>
        <input id="txtBusqueda" class="textbox w3u" />
      </div>
		<%--  <div class="field">
        <label for="txtApellidos" class="w2u">Apellidos</label>
        <input id="txtApellidos" class="textbox w3u" />
      </div> --%>
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
<div id="ft"><fmt:message key="app.copyright" /></div>
</div>

<script type="text/javascript" src="<c:url value="/s/yui/yahoo-dom-event/yahoo-dom-event.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/yui/logger/logger.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/yui/connection/connection-min.js" />"></script> 
<script type="text/javascript" src="<c:url value="/s/yui/json/json-debug.js" />"></script>  
<script type="text/javascript" src="<c:url value="/s/yui/dragdrop/dragdrop-min.js" />"></script> 
<script type="text/javascript" src="<c:url value="/s/yui/element/element-min.js" />"></script> 
<script type="text/javascript" src="<c:url value="/s/yui/paginator/paginator-debug.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/yui/animation/animation-min.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/yui/datasource/datasource-debug.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/yui/datatable/datatable-debug.js" />"></script> 
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
}

cudu.dom.tcFilter = document.getElementById('tc-filter');
cudu.dom.chkTipoJ = document.getElementById('chkTipoJ');
cudu.dom.chkTipoK = document.getElementById('chkTipoK');
cudu.dom.chkTipoC = document.getElementById('chkTipoC');

cudu.dom.btnImprimir = document.getElementById('btnImprimir');

cudu.dom.codigosRamas = {
	'radioColonia': 'C',
	'radioManada': 'M',
	'radioExploradores': 'E',
	'radioPioneros': 'P',
	'radioRutas': 'R'
};

YAHOO.util.Event.addListener(window, "load", function() {
	var columnas = [
   		{ key: "tipo", label: "Tipo", sortable: true, formatter: "tipo" },
   		{ key: "ramas", label: '<fmt:message key="listados.c.ramas" />', sortable: true, formatter: "rama" },
   		{ key: "nombreCompleto", label: '<fmt:message key="listados.c.nombre" />', sortable: true },
   		{ key: "fechanacimiento", label: '<fmt:message key="listados.c.fechanacimiento" />', sortable: true, parser: "date", formatter: "date" },
   		{ key: "telefonocasa", label: '<fmt:message key="listados.c.telefonocasa" />', sortable: true, formatter: "telefono" },
   		{ key: "telefonomovil", label: '<fmt:message key="listados.c.telefonomovil" />', sortable: true, formatter: "telefono"  },
		<sec:authorize access="hasRole('ROLE_ADMIN')">
   		{ key: 'idGrupo', label: 'Grupo', sortable: true },
   		</sec:authorize>
   		{ key: "id", label: '<fmt:message key="listados.c.id" />', sortable: true, hidden: true }
   		/* 
   		grupo.nombre no se interpreta bien por YUI
   		{ key: "email", label: '<fmt:message key="listados.c.email" />', sortable: true, hidden: true },
   		{ key: "dni", label: '<fmt:message key="listados.c.dni" />', sortable: true, hidden: true },
   		{ key: "provincia", label: '<fmt:message key="listados.c.provincia" />', sortable: true, hidden: true },
   		{ key: "municipio", label: '<fmt:message key="listados.c.municipio" />', sortable: true, hidden: false },
   		{ key: "dni", label: '<fmt:message key="listados.c.dni" />', sortable: true, hidden: false },
   		{ key: "idGrupo", label: '<fmt:message key="listados.c.grupo" />', sortable: true, hidden: false },*/
   	];
	
	cudu.dom.tabla = new cudu.ui.datatable.table(columnas);

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
});
</script>
</body>
</html>