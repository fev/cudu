if (typeof cudu == "undefined" || !cudu) {
    var cudu = {
		dom: {},
		i8n: {},
		ui: {datatable: {},
                      chart: {} 
                }
                
	};
}

/* ----------------------------------------------------------------------------- */

(function() {

cudu.noop = function() { };

cudu.ui.datatable.asociacionFormatter = function(elLiner, oRecord, oColumn, oData) {
	if (oData == null) return;

	var asociacion;
	if (oData == '0') asociacion = 'SdA';
	if (oData == '1') asociacion = 'SdC';
	if (oData == '2') asociacion = 'MEV';
	
	elLiner.innerHTML = asociacion;
};

cudu.ui.datatable.phoneFormatter = function(elLiner, oRecord, oColumn, oData) {
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

cudu.ui.datatable.translatedValueFormatterCtor = function(translationData) {
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

cudu.ui.datatable.buildQuery = function(queryColumnas, campoOrden, orden, inicio, resultados, filtros) {
	return queryColumnas
	+ '&s=' + campoOrden
	+ '&d=' + orden
	+ '&i=' + inicio
	+ '&r=' + resultados 
	+ '&f_tipo=' + filtros.tipo
	+ '&f_rama=' + filtros.rama.join(',')
};

cudu.ui.datatable.panelSeleccionColumnas = function(columnas) {
	var overlaySC = new YAHOO.widget.Overlay("overlaySelectorColumnas", {visible: false, 
		context:["btnSelectorColumnas","tl","bl", ["beforeShow", "windowResize"]],
		effect: {effect: YAHOO.widget.ContainerEffect.FADE, duration:0.25} 
	});
	
	var anchor;
	var fragment = document.createDocumentFragment();
	for	(var i = 0; i < columnas.length; i++) {
		anchor = document.createElement('a');
		anchor.columnKey = columnas[i].key;
		anchor.setAttribute('href', 'javascript:cudu.noop()');
		if (!columnas[i].hidden || columnas[i].hidden == false) {anchor.setAttribute('class', 'sel');}
		anchor.innerHTML = columnas[i].label;
		fragment.appendChild(anchor);
	}
	
	var body = document.createElement('div');
	body.setAttribute('class', 'yui-g');
	body.appendChild(fragment);
	overlaySC.setBody(body);
	overlaySC.setFooter("<a id='btnSCCerrar' href='javascript:cudu.noop()'>Cerrar</a>");
	overlaySC.render(document.body);
	overlaySC.isVisible = false;
	
	YAHOO.util.Event.delegate(body, "click", function (event, element, container) {
		if (YAHOO.util.Dom.hasClass(element, "sel")) {
			YAHOO.util.Dom.removeClass(element, "sel");
			cudu.dom.tabla.tabla.hideColumn(element.columnKey);
//			cudu.dom.tabla.toogleColumn(element.columnKey, false);
		}
		else {
			YAHOO.util.Dom.addClass(element, "sel");
			cudu.dom.tabla.tabla.showColumn(element.columnKey);
//			cudu.dom.tabla.toogleColumn(element.columnKey, true);
		}
//		cudu.dom.tabla.reload();
//		cudu.dom.tabla.tabla.insertColumn(element.columnKey); 
	}, "a");
    
	overlaySC.subscribe("show", function() {YAHOO.util.Dom.addClass('btnSelectorColumnas', 'selected');this.isVisible = true;});
	overlaySC.subscribe("hide", function() {YAHOO.util.Dom.removeClass('btnSelectorColumnas', 'selected');this.isVisible = false;});
	YAHOO.util.Event.addListener('btnSCCerrar', 'click', function() {overlaySC.hide();});
        YAHOO.util.Event.addListener('btnSelectorColumnas', 'click', function() {
    	if (overlaySC.isVisible == true)
    		overlaySC.hide();
    	else
    		overlaySC.show();
    });
    
    return overlaySC;
};

cudu.ui.datatable.panelSeleccionFilas = function(obj) {

	var overlaySF = new YAHOO.widget.Overlay("overlaySelectorFilas", {visible: false,
		context:["btnList","tl","bl", ["beforeShow", "windowResize"]],
		effect: {effect: YAHOO.widget.ContainerEffect.FADE, duration:0.25}
	});

	var anchor;
	var fragment = document.createDocumentFragment();
	for	(var i = 0; i < obj.filas.length; i++) {
		anchor = document.createElement('a');
		anchor.columnKey = obj.filas[i].label;
                anchor.key = obj.filas[i].key
		
		anchor.innerHTML = obj.filas[i].label;
		fragment.appendChild(anchor);
	}

	var body = document.createElement('div');
	body.setAttribute('class', 'yui-g');
	body.appendChild(fragment);
        overlaySF.setFooter("<a id='btnSFCerrar' href='javascript:cudu.noop()'>Cerrar</a>");
	overlaySF.setBody(body);
	overlaySF.render(document.body);
	overlaySF.isVisible = false;

	YAHOO.util.Event.delegate(body, "click", function (event, element, container) {
                obj.filasPorPagina = Number(element.key);




                cudu.dom.tabla = new cudu.ui.datatable.table({
		columnas: obj.columnas,
                filas: obj.filas,
                filasPorPagina: obj.filasPorPagina,
		dataSourceUrl:obj.dataSourceUrl
                });

                
	}, "a");

	overlaySF.subscribe("show", function() {YAHOO.util.Dom.addClass('btnList', 'selected');this.isVisible = true;});
	overlaySF.subscribe("hide", function() {YAHOO.util.Dom.removeClass('btnList', 'selected');this.isVisible = false;});
        YAHOO.util.Event.addListener('btnSFCerrar', 'click', function() {overlaySF.hide();});
        YAHOO.util.Event.addListener('btnList', 'click', function() {
    	if (overlaySF.isVisible == true)
    		overlaySF.hide();
    	else
    		overlaySF.show();
    });
    

    return overlaySF;
};

cudu.ui.datatable.table = function(cfg) {
	var columnas = cfg.columnas;
        var filas = cfg.filas;
        //Desplegable selector filas
        

	cfg.filasPorPagina = cfg.filasPorPagina|| 5;

	YAHOO.widget.DataTable.Formatter.asociacion = cudu.ui.datatable.asociacionFormatter;
        YAHOO.widget.DataTable.Formatter.telefono = cudu.ui.datatable.phoneFormatter;
	YAHOO.widget.DataTable.Formatter.rama = cudu.ui.datatable.translatedValueFormatterCtor(cudu.i8n.ramas);
	YAHOO.widget.DataTable.Formatter.tipo = cudu.ui.datatable.translatedValueFormatterCtor(cudu.i8n.tipos);
	

        
        // Desplegable selector columnas
	cudu.dom.selectorColumnas = cudu.ui.datatable.panelSeleccionColumnas(columnas);
	this.toogleColumn = function(key, visible) {
		console.log(key + ': ' + visible);
		for (var i = 0; i < columnas.length; i++) {
			if (columnas[i].key == key) {
				columnas[i].hidden = !visible;
				break;
			} 
		}
		this.queryColumnas = this.buildColumnQuery(columnas, false);
	};
        

	this.buildColumnQuery = function(columnas, honest) {
		// TODO modificar, considerar columnas ocultas al imprimir
		var _honest = honest || false;
		// console.log(_honest);
		var sb = [];
		for (var i = 0; i < columnas.length; i++) {
			// if (!columnas[i].hidden || columnas[i].pk)
				sb.push(columnas[i].key);
		}
		return 'c=' + sb.join(',');
	};
	this.queryColumnas = this.buildColumnQuery(columnas);

        this.dataSource = new YAHOO.util.DataSource(cfg.dataSourceUrl + "?");
    this.dataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
    this.dataSource.responseSchema = {
    	resultsList: "result.data", 
        fields:columnas,
        metaFields: {totalRecords: "result.totalRecords"}

        
    };

    this.paginador = new YAHOO.widget.Paginator({
        containers: ['paginador'],
        pageLinks: 10,
        rowsPerPage: cfg.filasPorPagina,
        template: "{CurrentPageReport} {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink}",
        pageReportTemplate: cudu.i8n.tabla.PagActual,
        previousPageLinkLabel: cudu.i8n.tabla.PagAnterior,
        nextPageLinkLabel: cudu.i8n.tabla.PagSiguiente,
        firstPageLinkLabel: cudu.i8n.tabla.PagInicio,
        lastPageLinkLabel: cudu.i8n.tabla.PagFinal
    });

    this.filtros = {
		tipo: '',
		rama: []
    };

    this.requestBuilder = function(oState, oSelf) {
        return cudu.ui.datatable.buildQuery(cudu.dom.tabla.queryColumnas, oState.sortedBy.key, 
        		((oState.sortedBy.dir == YAHOO.widget.DataTable.CLASS_ASC) ? "asc" : "desc"),
        		oState.pagination.recordOffset,
        		oState.pagination.rowsPerPage,
        		cudu.dom.tabla.filtros);
    };

    var initialRequestUrl = cudu.ui.datatable.buildQuery(this.queryColumnas, columnas[0].key, 'desc', 0, cfg.filasPorPagina, this.filtros);




   // cudu.dom.btnImprimir.href = "listados/imprimir?" + initialRequestUrl;
    var tablecfg = {
        initialRequest: initialRequestUrl,
        generateRequest: this.requestBuilder,
        dynamicData: true,
        selectionMode: "standard",
        sortedBy: {key: columnas[0].key, dir: YAHOO.widget.DataTable.CLASS_DESC},
        paginator: this.paginador,
        draggableColumns: true,
        MSG_EMPTY: cudu.i8n.tabla.SinDatos,
        MSG_LOADING: cudu.i8n.tabla.Cargando,
        MSG_SORTASC: 'Pulse para ordenar de menor a mayor.',
        MSG_SORTDESC: 'Pulse para ordenar de mayor a menor.',
        dateOptions: {format:"%d/%m/%Y", locale:"es"},
        formatRow: myRowFormatter, //ENABLE the row formatter
        width: "100%"
    };

    this.tabla = new YAHOO.widget.DataTable("listado", 
        columnas,
        this.dataSource,
        tablecfg
    );

this.tabla.subscribe("rowMouseoverEvent", this.tabla.onEventHighlightRow);
    this.tabla.subscribe("rowMouseoutEvent", this.tabla.onEventUnhighlightRow);
    this.tabla.subscribe("rowClickEvent", this.tabla.onEventSelectRow);
    this.tabla.subscribe("theadCellDblclickEvent", this.tabla.onEventSelectColumn);
    
    columnSelect ="ninguna";
    
    this.tabla.subscribe("cellClickEvent", function (e) {
        columnSelect = this.getColumn(e.target)
        this.tabla.hideColumn(columnSelect);

         this.selectColumn(this.getColumn(e.target));

    });
       

    this.tabla.subscribe("rowSelectEvent", function(e) {
       if(columnSelect.field !="id")
        {
         window.location = 'asociado/' + e.record.getData().id; 
       }
    });


    // TODO, URL para imprimir
    this.tabla.subscribe("dataReturnEvent", function(status, b) {
    	cudu.dom.btnImprimir.href = "listados/imprimir?" + status.request;
    });

    // TODO, URL para hacer pdfs
    this.tabla.subscribe("dataReturnEvent", function(status, b) {
    	cudu.dom.btnPdf .href = "listados/pdf?" + status.request;
    });
    
    // this.tabla.subscribe("postRenderEvent", serviceStatus.endProgress);
    this.tabla.__showTableMessage = this.tabla.showTableMessage;
    this.tabla.showTableMessage = function(sHTML, sClassName) {
        if (sClassName == YAHOO.widget.DataTable.CLASS_LOADING) {
            // serviceStatus.startProgress();
        } else {
            this.__showTableMessage(sHTML, sClassName);
            // serviceStatus.endProgress();
        }
    };

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
        
        var requestUrl = cudu.ui.datatable.buildQuery(cudu.dom.tabla.queryColumnas, 
        		columnas[0].key, 'desc', 0, cfg.filasPorPagina, cudu.dom.tabla.filtros);
        this.dataSource.sendRequest(requestUrl, oCallback);
        // cudu.dom.btnImprimir.href = "listados/imprimir?" + requestUrl;
    };
};

// TODO Refactorizar de aquí en adelante...

cudu.filtrarPor = {
	rama: function(codigo) {
		var filtro = cudu.dom.tabla.filtros.rama;
		if (typeof codigo == "undefined" || !codigo) {
			cudu.dom.tabla.filtros.rama = [];
		}

		var indice = filtro.indexOf(codigo);
		if (indice == -1)
			filtro.push(codigo);
		else
			filtro.splice(indice, 1);
		
		cudu.dom.tabla.reload();
	},

	tipo: function() {
		if (YAHOO.util.Dom.hasClass(cudu.dom.ctrlTipoCualquiera, 'selected')) {
			cudu.dom.tabla.filtros.tipo = '';
		} else {
			var tipos = [];
			if (YAHOO.util.Dom.hasClass(cudu.dom.chkTipoJ, 'selected')) tipos.push('J');
			if (YAHOO.util.Dom.hasClass(cudu.dom.chkTipoK, 'selected')) tipos.push('K');
			if (YAHOO.util.Dom.hasClass(cudu.dom.chkTipoC, 'selected')) tipos.push('C');
			cudu.dom.tabla.filtros.tipo = tipos.join(',');
		}
		cudu.dom.tabla.reload();
	}
};

cudu.ui.toogleFilter = function() {
	var tcFilter = cudu.dom.tcFilter;
    if (!tcFilter.isOpen) {
      (new YAHOO.util.Anim(tcFilter, {height: {to: 75}}, 0.9, YAHOO.util.Easing.bounceOut)).animate();
      tcFilter.isOpen = true;
      YAHOO.util.Dom.addClass('btnFiltro', 'selected'); 
    } else {
      (new YAHOO.util.Anim(tcFilter, {height: {to: 0}}, 0.7, YAHOO.util.Easing.backIn)).animate();
      tcFilter.isOpen = false;
      YAHOO.util.Dom.removeClass('btnFiltro', 'selected');
    }
};

cudu.ui.expandirUI = function() {
	var doc = YAHOO.util.Dom.get('doc');
	if (doc) {
		doc.setAttribute('id', 'doc3');
		cudu.dom.lblBtnExpandir.innerHTML = cudu.i8n.btnExpandir.Contraer;
		cudu.dom.imgBtnExpandir.src = cudu.i8n.btnExpandir.imgContraer;
	} else {
		YAHOO.util.Dom.get('doc3').setAttribute('id', 'doc');
		cudu.dom.lblBtnExpandir.innerHTML = cudu.i8n.btnExpandir.Expandir;
		cudu.dom.imgBtnExpandir.src = cudu.i8n.btnExpandir.imgExpandir;
	}	
};

})();

/*
var marcarPastelitos = false;*/

var myRowFormatter = function(elTr, oRecord) {
    if (oRecord.getData('ramas') == 'C') {
        YAHOO.util.Dom.addClass(elTr, 'markC');
        YAHOO.util.Dom.replaceClass(elTr.parentNode, "down", "up");
    }
    else if (oRecord.getData('ramas') == 'M') {
        YAHOO.util.Dom.addClass(elTr, 'markM');
        YAHOO.util.Dom.replaceClass(elTr.parentNode, "up", "down");
    }
    else if (oRecord.getData('ramas') == 'E') {
        YAHOO.util.Dom.addClass(elTr, 'markE');
        YAHOO.util.Dom.replaceClass(elTr.parentNode, "up", "down");
    }
    else if (oRecord.getData('ramas') == "P") {
        YAHOO.util.Dom.addClass(elTr, 'markP');
    }
    else  if (oRecord.getData('ramas') == "R") {
        YAHOO.util.Dom.addClass(elTr, 'markR');
    }
    return true;
};
/*
function resaltarVentas() {
    if (dom.chkPasteles.checked) {
      marcarPastelitos = true;
    } else {
      marcarPastelitos = false;
    }   
    YAHOO.example.Basic.oDT.refreshView();
} */


//This prototype is provided by the Mozilla foundation and
//is distributed under the MIT license.
//http://www.ibiblio.org/pub/Linux/LICENSES/mit.license

if (!Array.prototype.indexOf) {
	Array.prototype.indexOf = function(elt /*, from*/) {
		var len = this.length;

		var from = Number(arguments[1]) || 0;
		from = (from < 0) ? Math.ceil(from) : Math.floor(from);
		if (from < 0)
			from += len;

		for (; from < len; from++) {
			if (from in this && this[from] === elt)
				return from;
		}
		return -1;
	};
}