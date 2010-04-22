<%@include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="app.title" /></title>
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
    <a href="javascript:dbreload()">
      <img src="<c:url value="/s/theme/img/tango/emblem-favorite.png" />" />
      <span>Reload!</span>
    </a>
	<%-- <a href="#">
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
      <div class="field">
        <input type="checkbox" class="radio" id='chkPasteles' onclick="resaltarVentas()" />
        <label for="chkPasteles" class="radio">Resaltar quién no ha vendido nada</label>
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

<%-- <script src="<c:url value="/s/jquery/jquery.js" />"></script> --%>

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
		'E': '<fmt:message key="rama.unos.T" />',
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
		{ key: "email", label: '<fmt:message key="listados.c.email" />', sortable: true, hidden: true },
		{ key: "id", label: "Id", sortable: true, hidden: true }
		/*
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
		
    /*
    var ramaFormatter = function(elLiner, oRecord, oColumn, oData) {
		if (typeof oData === 'undefined') {
			elLiner.innerHTML = '¿?';
			return;
		}

		var txtRama = [];
		var sRama = oData.split(',');
		console.log(sRama);
		for(var i = 0; i < sRama.length; i++) {
			if (typeof sRama[i] !== 'undefined') {
				txtRama.push(cudu.i8n.ramas[sRama[i]]);
			}
		}

		elLiner.innerHTML = txtRama.join(', ');
    };
    YAHOO.widget.DataTable.Formatter.rama = ramaFormatter;
    */

	this.buildColumnQuery = function(columnas) {
		var sb = [];
		for (var i = 0; i < columnas.length; i++) {
			// if (!columnas[i].hidden)
			sb.push(columnas[i].key);
		}
		return 'c=' + sb.join(',');
	};
	var queryColumnas = this.buildColumnQuery(columnas);

	this.buildQuery = function(queryColumnas, campoOrden, orden, inicio, resultados, filtro) {
		return queryColumnas
			+ '&s=' + campoOrden
			+ '&d=' + orden
			+ '&i=' + inicio
			+ '&r=' + resultados;
			// filtro
			// + '&d=' + orden
	};
	
	this.dataSource = new YAHOO.util.DataSource("/cudu/listados/asociados.json?");
    this.dataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
    this.dataSource.responseSchema = {
    	resultsList: "result.data", 
        fields: columnas,
        metaFields: { totalRecords: "result.totalRecords" }
    };

    this.requestBuilder = function(oState, oSelf) {
        // return
        return queryColumnas + 
        		"&s=" + oState.sortedBy.key +
                "&d=" + ((oState.sortedBy.dir == YAHOO.widget.DataTable.CLASS_ASC) ? "asc" : "desc") +
                "&i=" + oState.pagination.recordOffset +
                "&r=" + oState.pagination.rowsPerPage;
				// filtro
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

    var tablecfg = {
        initialRequest: this.buildQuery(queryColumnas, columnas[0].key, 'desc', 0, cfg.filasPorPagina, null),
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

    /* this.tabla.subscribe("postRenderEvent", serviceStatus.endProgress);
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

    /*
    this.reload = function() {
        var endRequestCallback = function(sRequest, oResponse, oPayload) {
            serviceStatus.endProgress();
            this.onDataReturnInitializeTable(sRequest, oResponse, oPayload);
        };
        
        var oCallback = {
            success: endRequestCallback,
            failure: endRequestCallback,
            scope: this.tabla,
            argument: this.tabla.getState()
        };

        this.dataSource.sendRequest("sort=id&dir=desc&startIndex=0&results=10"
                + "&estado=" + filtro.estado
                + "&plan=" + (filtro.plan || '')
                + "&colaborador=" + (filtro.colaborador || '')
                + "&colectivo=" + (filtro.colectivo || '')
                + "&fechaInicio=" + (filtro.fechaInicio || '')
                + "&fechaFin=" + (filtro.fechaFin || '')
                + "&fichero=" + (filtro.fichero || '')
                + "&estadoPoliza=" + (filtro.estadoPoliza.join(','))
            , oCallback);
    }; */	
};

YAHOO.util.Event.addListener(window, "load", dbgreload);

function dbgreload() {
	var tabla = new cudu.tabla();

	/*
	YAHOO.example.Basic = function() {
    	var myColumnDefs = [ 
          {key:"name", label:'Nombre', sortable: true},
          {key:"apellidos", label:'Apellidos', sortable: true},
          {key:"address", label:'Dirección', sortable: true}, 
	      {key:"city", label:'Ciudad', sortable: true},
          {key:"fecha_nac", label:'Fecha nac.', sortable: true, formatter:YAHOO.widget.DataTable.formatDate},
          {key:"amount", label:'Pastelitos', sortable: true},
          {key:"unidad", label:'Unidad', sortable: true},
        ]; 
 
        var myDataSource = new YAHOO.util.DataSource(bookorders);
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
        myDataSource.responseSchema = {
            fields: ["name","apellidos","address","city","fecha_nac","unidad","amount"]
        };
 
        var myDataTable = new YAHOO.widget.DataTable("listado", myColumnDefs, 
                myDataSource, {draggableColumns:true, formatRow: myRowFormatter});
                
        return {
            oDS: myDataSource,
            oDT: myDataTable
        };
    }();*/
}

dom = {
  tcFilter: document.getElementById('tc-filter'),
  chkPasteles: document.getElementById('chkPasteles')
};

function toogleFilter() {
    if (!dom.tcFilter.isOpen) {
      (new YAHOO.util.Anim(dom.tcFilter, {height: { to: 100 }}, 0.9, YAHOO.util.Easing.bounceOut)).animate();
      dom.tcFilter.isOpen = true;
    } else {
      (new YAHOO.util.Anim(dom.tcFilter, {height: { to: 0 }}, 0.7, YAHOO.util.Easing.backIn)).animate();
      dom.tcFilter.isOpen = false;
    }
}

bookorders = [
    {name:"Mike", apellidos: 'Wazowski', address:"1236 Some Street", city:"San Francisco", unidad:'Manada', amount:5, active:"yes", colors:["red"], fecha_nac:"4/19/2007"},
    {name:"Joan B.", apellidos:'Jones', address:"3271 Another Ave", city:"New York", unidad:'Pioneros', amount:3, active:"no", colors:["red","blue"], fecha_nac:"2/15/2006"},
    {name:"Jack", apellidos:'Sparrow', address:"9996 Random Road", city:"Los Angeles", unidad:"Compañeros", amount:2, active:"maybe", colors:["green"], fecha_nac:"1/23/2004"},    
    {name:"Bob", apellidos:'Esponja', address:"9899 Random Road", city:"Los Angeles", unidad:'Manada', amount:0, active:"maybe", colors:["green"], fecha_nac:"1/23/2004"},
    {name:"Baden", apellidos:'Powell', address:"1723 Some Street", city:"San Francisco", unidad:'Manada', amount:2, active:"yes", colors:["red"], fecha_nac:"4/19/2007"},
    {name:"Alan", apellidos:'Cox', address:"3241 Another Ave", city:"New York", unidad:'Pioneros', amount:0, active:"no", colors:["red","blue"], fecha_nac:"2/15/2006"},
    {name:"Bob I.", apellidos:'Uncle', address:"9909 Random Road", city:"Los Angeles", unidad:'Manada', amount:4, active:"maybe", colors:["green"], fecha_nac:"1/23/2004"},
    {name:"Elizabeth", apellidos:"O'Neall", address:"3721 Another Ave", city:"New York", unidad:'Pioneros', amount:6, active:"no", colors:["red","blue"], fecha_nac:"2/15/2006"},
    {name:"Will", apellidos:'Tarner', address:"9989 Random Road", city:"Los Angeles", unidad:'Manada', amount:1, active:"maybe", colors:["green"], fecha_nac:"1/23/2004"},
    {name:"John M.", apellidos:'Smith', apellidos:'Smith', address:"1293 Some Street", city:"San Francisco", unidad:'Manada', amount:5, active:"yes", colors:["red"], fecha_nac:"4/19/2007"},
    {name:"Caperucita", apellidos:'Rouge', address:"3621 Another Ave", city:"New York", unidad:'Pioneros', amount:3, active:"no", colors:["red","blue"], fecha_nac:"2/15/2006"},
    {name:"Hanna", apellidos:'Montana', address:"9959 Random Road", city:"Los Angeles", unidad:'Manada', amount:0, active:"maybe", colors:["green"], fecha_nac:"1/23/2004"},
    {name:"John P.",  apellidos:'Smith', address:"6123 Some Street", city:"San Francisco", unidad:'Manada', amount:7, active:"yes", colors:["red"], fecha_nac:"4/19/2007"},
    {name:"Nemo", apellidos: 'Dori', address:"3281 Another Ave", city:"New York", unidad:'Pioneros', amount:1, active:"no", colors:["red","blue"], fecha_nac:"2/15/2006"}
];

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
}
</script>
</body>
</html>