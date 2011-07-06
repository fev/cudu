<%@include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="app.title" /></title>
<link rel="icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link rel="shortcut icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />

<%-- Combo-handled YUI CSS files:
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/combo?2.8.2r1/build/reset-fonts-grids/reset-fonts-grids.css&2.8.2r1/build/base/base-min.css&2.8.2r1/build/assets/skins/sam/skin.css">
--%>

<%-- Desde la cache --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/reset-fonts-grids/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/base/base-min.css" />" />

<%-- Normal --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/container/assets/skins/sam/container.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/paginator/assets/skins/sam/paginator.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/datatable/assets/skins/sam/datatable.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/theme/cudu.css" />" />
<style type="text/css">

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



        div.field a.chkAsociacion{ border: 1px solid #D4D2CC; padding: 3px 5px; margin-top: 1px; text-decoration: none; color: #333 }
        div.field a.chkAsociacion:hover { border-color: #0C4A64; color: #0C4A64; }
        div.field a.chkAsociacion.selected { border-color: #4899ce; background-color: #FFF; color: #4899ce; }
        div.field a.chkAsociacion.selected:hover { border-color: #ce4848; background-color: #ffeeee; color: #ce4848; }
        
        

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
</style>
</head>
<body>

<div id="doc" class="yui-t7">
<jsp:include page="header.jsp"></jsp:include>
<div id="bd">
  <div class="yui-g tc-tb">

    <a id="btnFiltro" href="javascript:cudu.ui.toogleFilter()">
            <img src="<c:url value="/s/theme/img/tango/edit-find.png" />" />
            <span><fmt:message key="listados.tb.filtrar" /></span>
    </a>
    <a id="btnSelectorColumnas" href="javascript:cudu.noop()">
      <img src="<c:url value="/s/theme/img/tango/select-column.png" />" />
      <span><fmt:message key="listados.tb.columnas" /></span>
    </a>

    <a id="btnList" href="javascript:cudu.noop()">
      <img src="<c:url value="/s/theme/img/tango/document-list.png" />" />
      <span><fmt:message key="listados.tb.list" /></span>
    </a>
    <a id="btnImprimir" href="#" target="_blank">
      <img src="<c:url value="/s/theme/img/tango/document-print.png" />" />
      <span><fmt:message key="listados.tb.imprimir" /></span>
    </a>
   
    <a href="<c:url value="/dashboard" />" class="">
        <img src="<c:url value="/s/theme/img/tango/edit-undo.png" />" />
        <span><fmt:message key="listados.tb.volver" /></span>
    </a>
   
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
      <div id="tc-filter-Asociacion" class="field">
      	<label for="" class="w1u">Tipo</label>
      	<a id="chkAsociacionsdA" href="javascript:" class="chkAsociacion"><fmt:message key="asociacion.tipo.sdA" /></a>
      	<a id="chkAsociacionsdC" href="javascript:" class="chkAsociacion"><fmt:message key="asociacion.tipo.sdC" /></a>
      	<a id="chkAsociacionMEV" href="javascript:" class="chkAsociacion"><fmt:message key="asociacion.tipo.MEV" /></a>
      	<a id="chkAsociacionT" href="javascript:" class="chkAsociacion selected"><fmt:message key="listados.f.cualquiera" /></a>
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



<!-- Combo-handled YUI JS files:
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.8.2r1/build/utilities/utilities.js&2.8.2r1/build/container/container-min.js&2.8.2r1/build/datasource/datasource-min.js&2.8.2r1/build/paginator/paginator-min.js&2.8.2r1/build/datatable/datatable-min.js&2.8.2r1/build/selector/selector-min.js&2.8.2r1/build/event-delegate/event-delegate-min.js&2.8.2r1/build/json/json-min.js"></script>
-->
<script type="text/javascript" src="<c:url value="/s/cdn/yui-lst.js" />"></script>

<script type="text/javascript" src="<c:url value="/s/scripts/listados_grupos.js" />"></script>
<script type="text/javascript">
cudu.i8n.asociaciones= {
	'MEV': 'MEV',
	'sdA': 'sdA',
	'sdC': 'sdAC'
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
cudu.dom.chkAsociacionMEV= document.getElementById('chkAsociacionMEV');
cudu.dom.chkAsociacionsdA= document.getElementById('chkAsociacionsdA');
cudu.dom.chkAsociacionsdC= document.getElementById('chkAsociacionsdC');

cudu.dom.btnPdf = document.getElementById('btnPdf');
cudu.dom.btnImprimir = document.getElementById('btnImprimir');
cudu.dom.lblBtnExpandir = document.getElementById('lblBtnExpandir');
cudu.dom.imgBtnExpandir = document.getElementById('imgBtnExpandir');

cudu.dom.tcLoading = document.getElementById('tc-loading');

YAHOO.util.Event.addListener(window, "load", function() {
	// TODO grupo.nombre no se interpreta bien por YUI
	var listaColumnas = [
                { key: "id", label: '<fmt:message key="grupo.f.id" />', sortable: true },
                { key: 'asociacion', label: '<fmt:message key="listados.c.asociacion" />', sortable: true, formatter: "asociacion" },
                { key: "nombre", label: '<fmt:message key="grupo.f.nombre" />', sortable: true },
                { key: "aniversario", label: '<fmt:message key="grupo.f.aniversario" />', sortable: true, hidden: true, parser: "date", formatter: "date" },
                { key: "telefono1", label: '<fmt:message key="grupo.f.telefono1" />', sortable: true, formatter: "telefono" },
   		{ key: "telefono2", label: '<fmt:message key="grupo.f.telefono2" />', sortable: true, formatter: "telefono" },
                { key: "direccionCompleta", label: '<fmt:message key="grupo.f.direccion" />', sortable: true }
   	];

        var listaFilas = [
   		{ key: "10", label: "10", sortable: true },
                { key: "20", label: "20", sortable: true },
                { key: "50", label: "50", sortable: true },
                { key: "9999999", label: "Todos", sortable: true }
        ];

        var gruposPorPagina=10;
        

	cudu.dom.seleccionadoFilas = 
            new cudu.ui.datatable.panelSeleccionFilas
        ({
		columnas: listaColumnas,
                filas: listaFilas,
                filasPorPagina: gruposPorPagina,
		dataSourceUrl: '<c:url value="listados_grupos/grupos.json" />'
	});
	cudu.dom.tabla = new cudu.ui.datatable.table({ 
            columnas: listaColumnas,
                filas: listaFilas,
                filasPorPagina: gruposPorPagina,
		dataSourceUrl: '<c:url value="listados_grupos/grupos.json" />'
	});
	
	YAHOO.util.Dom.addClass(cudu.dom.tcLoading, 'hidden');

	// Filtro por ASOCIACION
	cudu.dom.ctrlAsociacion = YAHOO.util.Dom.getElementsByClassName('chkAsociacion', 'a', 'tc-filter-Asociacion');
	cudu.dom.ctrlAsociacionCualquiera = YAHOO.util.Dom.get('chkAsociacionT');
	YAHOO.util.Event.addListener(cudu.dom.ctrlAsociacion, "click", function(e) {
		if (e.target.id == 'chkAsociacionT') {
			YAHOO.util.Dom.removeClass(cudu.dom.ctrlAsociacion, 'selected');
		} else {
			YAHOO.util.Dom.removeClass(cudu.dom.ctrlAsociacionCualquiera, 'selected');
		}

		if (YAHOO.util.Dom.hasClass(e.target, 'selected')) {
			YAHOO.util.Dom.removeClass(e.target, 'selected');
		} else {
                        //YAHOO.util.Dom.removeClass(cudu.dom.ctrlAsociacion, 'selected');
                        YAHOO.util.Dom.removeClass(cudu.dom.ctrlAsociacionCualquiera, 'selected');
			YAHOO.util.Dom.addClass(e.target, 'selected');
		}
		cudu.filtrarPor.asociacion();
	});
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
    
