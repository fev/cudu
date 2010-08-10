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

.yui-skin-sam .yui-dt td.yui-dt-last, .yui-skin-sam .yui-dt th.yui-dt-last { border-right: 0; }

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

<c:if test="${param.dbg != null}">
	<div class="yui-log yui-log-container" id="yuilogct" style="position: absolute; right: 0; font-size: 12px"></div>
</c:if>

<div id="doc" class="yui-t7">
<jsp:include page="header.jsp"></jsp:include>
<div id="bd">
  <div class="yui-g tc-tb">
	<a href="<c:url value="/asociado/nuevo" />" class="save">
		<img src="<c:url value="/s/theme/img/tango/document-new.png" />" />
		<span><fmt:message key="listados.tb.nuevo" /></span>
	</a>
	<a href="javascript:toogleFilter()">
		<img src="<c:url value="/s/theme/img/tango/edit-find.png" />" />
		<span><fmt:message key="listados.tb.filtrar" /></span>
	</a>
    <a href="#">
      <img src="<c:url value="/s/theme/img/tango/select-column.png" />" />
      <span><fmt:message key="listados.tb.columnas" /></span>
    </a>
    <a href="#">
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
    <div class="yui-g first">
      <div class="field">
        <label for="txtNombre" class="w2u">Nombre</label>
        <input id="txtNombre" class="textbox w3u" />
      </div>
      <div class="field">
        <label for="txtApellidos" class="w2u">Apellidos</label>
        <input id="txtApellidos" class="textbox w3u" />
      </div>
    </div>
    <div class="yui-g">
      <div id="tc-filter-ramas" class="field">
      	<label for="" class="w1u">Ramas</label>
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
<script type="text/javascript">
cudu = {};

cudu.i8n = {
	ramas: {
		'C': '<fmt:message key="rama.unos.C" />',
		'M': '<fmt:message key="rama.unos.M" />',
		'E': '<fmt:message key="rama.unos.E" />',
		'P': '<fmt:message key="rama.unos.P" />',
		'R': '<fmt:message key="rama.unos.R" />'
	},
	tipos: {
		'J': '<fmt:message key="asociado.tipo.joven" />',
		'K': '<fmt:message key="asociado.tipo.kraal" />',
		'C': '<fmt:message key="asociado.tipo.comite" />'
	}
};

<c:if test="${param.dbg != null}">
cudu.logger = new YAHOO.widget.LogReader('yuilogct', {draggable: true});
</c:if>

cudu.tabla = function() {
	var cfg = {
		filasPorPagina : 15
	};
	
	var columnas = [
		{ key: "tipo", label: "Tipo", sortable: true, formatter: "tipo" },
		{ key: "ramas", label: '<fmt:message key="listados.c.ramas" />', sortable: true, formatter: "rama" },
		{ key: "nombreCompleto", label: '<fmt:message key="listados.c.nombre" />', sortable: true },
		{ key: "fechanacimiento", label: '<fmt:message key="listados.c.fechanacimiento" />', sortable: true, parser: "date", formatter: "date" },
		{ key: "telefonocasa", label: '<fmt:message key="listados.c.telefonocasa" />', sortable: true, formatter: "telefono" },
		{ key: "telefonomovil", label: '<fmt:message key="listados.c.telefonomovil" />', sortable: true, formatter: "telefono"  },
		{ key: "id", label: '<fmt:message key="listados.c.id" />', sortable: true, hidden: true },
		/* 
		{ key: "email", label: '<fmt:message key="listados.c.email" />', sortable: true, hidden: true },
		{ key: "dni", label: '<fmt:message key="listados.c.dni" />', sortable: true, hidden: true },
		{ key: "provincia", label: '<fmt:message key="listados.c.provincia" />', sortable: true, hidden: true },
		{ key: "municipio", label: '<fmt:message key="listados.c.municipio" />', sortable: true, hidden: false },
		{ key: "dni", label: '<fmt:message key="listados.c.dni" />', sortable: true, hidden: false },
		{ key: "idGrupo", label: '<fmt:message key="listados.c.grupo" />', sortable: true, hidden: false },*/
	];

	var phoneFormatter = function(elLiner, oRecord, oColumn, oData) {
		if (oData == null) return;
		
		var sb = '';
		for (var i = 0; i < oData.length; i++) {
			if ((i % 3) == 0) {
				sb += ' ';
			}
			sb += oData[i];
		}
		elLiner.innerHTML = sb;
    };
    YAHOO.widget.DataTable.Formatter.telefono = phoneFormatter;

	var translatedValueFormatterCtor = function(translationData) {
		var fnc = function(elLiner, oRecord, oColumn, oData) {
			if ((typeof oData === 'undefined') || (oData == null))
				return;
	
			var vArray = [];
			var splittedStr = oData.split(',');
			for(var i = 0; i < splittedStr.length; i++) {
				if (typeof splittedStr[i] !== 'undefined') {
					vArray.push(translationData[splittedStr[i]]);
				}
			}
	
			elLiner.innerHTML = vArray.join(', ');
		};
		fnc.translationData = translationData;
		return fnc;
	};
	
	YAHOO.widget.DataTable.Formatter.rama = translatedValueFormatterCtor(cudu.i8n.ramas);
	YAHOO.widget.DataTable.Formatter.tipo = translatedValueFormatterCtor(cudu.i8n.tipos);

	this.buildColumnQuery = function(columnas) {
		var sb = [];
		for (var i = 0; i < columnas.length; i++) {
			// if (!columnas[i].hidden)
			sb.push(columnas[i].key);
		}
		return 'c=' + sb.join(',');
	};
	var queryColumnas = this.buildColumnQuery(columnas);

	this.dataSource = new YAHOO.util.DataSource("/cudu/listados/asociados.json?");
    this.dataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
    this.dataSource.responseSchema = {
    	resultsList: "result.data", 
        fields: columnas,
        metaFields: { totalRecords: "result.totalRecords" }
    };

    this.paginador = new YAHOO.widget.Paginator({
        containers: ['paginador'],
        pageLinks: 10,
        rowsPerPage: cfg.filasPorPagina,
        template: "{CurrentPageReport} {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink}",
        pageReportTemplate: "pág. {currentPage} de {totalPages}",
        previousPageLinkLabel: 'ant',
        nextPageLinkLabel: 'sig',
        firstPageLinkLabel: 'inicio',
        lastPageLinkLabel: 'final'
    });

    this.filtros = {
		tipo: '',
		rama: ''
    };
	
	this.buildQuery = function(queryColumnas, campoOrden, orden, inicio, resultados, filtros) {
		return queryColumnas
			+ '&s=' + campoOrden
			+ '&d=' + orden
			+ '&i=' + inicio
			+ '&r=' + resultados 
			+ '&f_tipo=' + filtros.tipo
			+ '&f_rama=' + filtros.rama
	};

    this.requestBuilder = function(oState, oSelf) {
        return cudu.ui.tabla.buildQuery(queryColumnas, oState.sortedBy.key, 
        		((oState.sortedBy.dir == YAHOO.widget.DataTable.CLASS_ASC) ? "asc" : "desc"),
        		oState.pagination.recordOffset,
        		oState.pagination.rowsPerPage,
        		cudu.ui.tabla.filtros);
		/*
        return queryColumnas + 
        		"&s=" + oState.sortedBy.key +
                "&d=" + ((oState.sortedBy.dir == YAHOO.widget.DataTable.CLASS_ASC) ? "asc" : "desc") +
                "&i=" + oState.pagination.recordOffset +
                "&r=" + oState.pagination.rowsPerPage;
				// filtro
		*/
    };

    var tablecfg = {
        initialRequest: this.buildQuery(queryColumnas, columnas[0].key, 'desc', 0, cfg.filasPorPagina, this.filtros),
        generateRequest: this.requestBuilder,
        dynamicData: true,
        selectionMode: "standard",
        sortedBy: { key: columnas[0].key, dir: YAHOO.widget.DataTable.CLASS_DESC },
        paginator: this.paginador,
        draggableColumns: true,
        MSG_EMPTY: 'No existen documentos.',
        MSG_LOADING: 'Cargando...',
        MSG_SORTASC: 'Pulse para ordenar de menor a mayor.',
        MSG_SORTDESC: 'Pulse para ordenar de mayor a menor.',
        dateOptions: {format:"%d/%m/%Y", locale:"es"}
    };

    this.tabla = new YAHOO.widget.DataTable("listado", columnas, this.dataSource, tablecfg);

    this.tabla.subscribe("rowMouseoverEvent", this.tabla.onEventHighlightRow);
    this.tabla.subscribe("rowMouseoutEvent", this.tabla.onEventUnhighlightRow);
    this.tabla.subscribe("rowClickEvent", this.tabla.onEventSelectRow);
    this.tabla.subscribe("rowSelectEvent", function(e) {
        window.location = 'asociado/' + e.record.getData().id; 
    });

    /*
    this.tabla.subscribe("postRenderEvent", serviceStatus.endProgress);
    this.tabla.__showTableMessage = this.tabla.showTableMessage;
    this.tabla.showTableMessage = function(sHTML, sClassName) {
        if (sClassName == YAHOO.widget.DataTable.CLASS_LOADING) {
            serviceStatus.startProgress();
        } else {
            this.__showTableMessage(sHTML, sClassName);
            serviceStatus.endProgress();
        }
    }; */

    // Manejar el campo del número total de registros para que funcione
    // correctamente la paginación.
    this.tabla.handleDataReturnPayload = function(oRequest, oResponse, oPayload) {
        oPayload.totalRecords = oResponse.meta.totalRecords;
        return oPayload;
    };

    this.reload = function() {
        var endRequestCallback = function(sRequest, oResponse, oPayload) {
            // serviceStatus.endProgress();
            this.onDataReturnInitializeTable(sRequest, oResponse, oPayload);
        };
        
        var oCallback = {
            success: endRequestCallback,
            failure: endRequestCallback,
            scope: this.tabla,
            argument: this.tabla.getState()
        };

        /* + "&fichero=" + (filtro.fichero || '')
        + "&estadoPoliza=" + (filtro.estadoPoliza.join(',')) */
        this.dataSource.sendRequest(
        	cudu.ui.tabla.buildQuery(queryColumnas, columnas[0].key, 'desc', 0, cfg.filasPorPagina, cudu.ui.tabla.filtros), 
        	oCallback);
    };
};

cudu.ui = {
 	tcFilter: document.getElementById('tc-filter'),
 	chkTipoJ: document.getElementById('chkTipoJ'),
 	chkTipoK: document.getElementById('chkTipoK'),
 	chkTipoC: document.getElementById('chkTipoC')
};

cudu.filtrarPor = {
	rama: function() {
		cudu.ui.tabla.filtros.rama = 'M,C';
				
		cudu.ui.tabla.reload();
	},

	tipo: function() {
		if (YAHOO.util.Dom.hasClass(cudu.ui.ctrlTipoCualquiera, 'selected')) {
			cudu.ui.tabla.filtros.tipo = '';
		} else {
			var tipos = [];
			if (YAHOO.util.Dom.hasClass(cudu.ui.chkTipoJ, 'selected')) tipos.push('J');
			if (YAHOO.util.Dom.hasClass(cudu.ui.chkTipoK, 'selected')) tipos.push('K');
			if (YAHOO.util.Dom.hasClass(cudu.ui.chkTipoC, 'selected')) tipos.push('C');
			cudu.ui.tabla.filtros.tipo = tipos.join(',');
		}
		cudu.ui.tabla.reload();
	}
};

YAHOO.util.Event.addListener(window, "load", function() {
	cudu.ui.tabla = new cudu.tabla();

	// Filtro por ramas
	cudu.ui.ctrlRamas = YAHOO.util.Dom.getElementsByClassName('dropramas', 'a', 'tc-filter-ramas');	
	cudu.ui.ctrlRamasCualquiera = YAHOO.util.Dom.get('radioCualquierRama');
	YAHOO.util.Event.addListener(cudu.ui.ctrlRamas, "click", function(e) {
		if (e.target.id == 'radioCualquierRama') {
			YAHOO.util.Dom.removeClass(cudu.ui.ctrlRamas, 'selected');
		} else {
			YAHOO.util.Dom.removeClass(cudu.ui.ctrlRamasCualquiera, 'selected');
		}

		if (YAHOO.util.Dom.hasClass(e.target, 'selected')) {
			YAHOO.util.Dom.removeClass(e.target, 'selected');
		} else {
			YAHOO.util.Dom.addClass(e.target, 'selected');
		} 

		cudu.filtrarPor.rama();
	});

	// Filtro por tipo
	cudu.ui.ctrlTipo = YAHOO.util.Dom.getElementsByClassName('chkTipo', 'a', 'tc-filter-tipo');
	cudu.ui.ctrlTipoCualquiera = YAHOO.util.Dom.get('chkTipoT');
	YAHOO.util.Event.addListener(cudu.ui.ctrlTipo, "click", function(e) {
		if (e.target.id == 'chkTipoT') {
			YAHOO.util.Dom.removeClass(cudu.ui.ctrlTipo, 'selected');
		} else {
			YAHOO.util.Dom.removeClass(cudu.ui.ctrlTipoCualquiera, 'selected');
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

/*
$(document).ready(function() {
	cudu.ui.dropramas.toggleClass('selected');

	cudu.ui.dropramas.click(function(e) {
		$(this).toggleClass("selected");

		cudu.ui.dropramas.each(function() {
			// var el = cudu.ui.ramas[this.id];
			var filter = [];
			if ($(this).hasClass('selected'))
				filter.append('
			} else {
				el.checked = false;
			}
		});
	});
});
*/

function toogleFilter() {
	var tcFilter = cudu.ui.tcFilter;
    if (!tcFilter.isOpen) {
      (new YAHOO.util.Anim(tcFilter, {height: { to: 100 }}, 0.9, YAHOO.util.Easing.bounceOut)).animate();
      tcFilter.isOpen = true;
    } else {
      (new YAHOO.util.Anim(tcFilter, {height: { to: 0 }}, 0.7, YAHOO.util.Easing.backIn)).animate();
      tcFilter.isOpen = false;
    }
}

/*
var marcarPastelitos = false;
var myRowFormatter = function(elTr, oRecord) {
    if (marcarPastelitos && (oRecord.getData('amount') == 0)) {
        YAHOO.util.Dom.addClass(elTr, 'mark');
    }
    return true;
};

function resaltarVentas() {
    if (dom.chkPasteles.checked) {
      marcarPastelitos = true;
    } else {
      marcarPastelitos = false;
    }   
    YAHOO.example.Basic.oDT.refreshView();
} */

</script>
</body>
</html>