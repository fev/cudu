if (typeof cudu == "undefined" || !cudu) {
    var cudu = {
		dom: {},
		i8n: {},
		ui: { datatable: {} }
	};
}

/* ----------------------------------------------------------------------------- */

(function() {

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
	+ '&f_rama=' + filtros.rama
};

cudu.ui.datatable.table = function(columnas) {
	var cfg = {
		filasPorPagina : 15
	};

    YAHOO.widget.DataTable.Formatter.telefono = cudu.ui.datatable.phoneFormatter;
	YAHOO.widget.DataTable.Formatter.rama = cudu.ui.datatable.translatedValueFormatterCtor(cudu.i8n.ramas);
	YAHOO.widget.DataTable.Formatter.tipo = cudu.ui.datatable.translatedValueFormatterCtor(cudu.i8n.tipos);

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
        pageReportTemplate: cudu.i8n.tabla.PagActual,
        previousPageLinkLabel: cudu.i8n.tabla.PagAnterior,
        nextPageLinkLabel: cudu.i8n.tabla.PagSiguiente,
        firstPageLinkLabel: cudu.i8n.tabla.PagInicio,
        lastPageLinkLabel: cudu.i8n.tabla.PagFinal
    });

    this.filtros = {
		tipo: '',
		rama: ''
    };

    this.requestBuilder = function(oState, oSelf) {
        return cudu.ui.datatable.buildQuery(queryColumnas, oState.sortedBy.key, 
        		((oState.sortedBy.dir == YAHOO.widget.DataTable.CLASS_ASC) ? "asc" : "desc"),
        		oState.pagination.recordOffset,
        		oState.pagination.rowsPerPage,
        		cudu.dom.tabla.filtros);
    };

    var tablecfg = {
        initialRequest: cudu.ui.datatable.buildQuery(queryColumnas, columnas[0].key, 'desc', 0, cfg.filasPorPagina, this.filtros),
        generateRequest: this.requestBuilder,
        dynamicData: true,
        selectionMode: "standard",
        sortedBy: { key: columnas[0].key, dir: YAHOO.widget.DataTable.CLASS_DESC },
        paginator: this.paginador,
        draggableColumns: true,
        MSG_EMPTY: cudu.i8n.tabla.SinDatos,
        MSG_LOADING: cudu.i8n.tabla.Cargando,
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

        this.dataSource.sendRequest(
        	cudu.ui.datatable.buildQuery(queryColumnas, columnas[0].key, 'desc', 0, cfg.filasPorPagina, cudu.dom.tabla.filtros), 
        	oCallback);
    };
};


// TODO Refactorizar de aquí en adelante...

cudu.filtrarPor = {
	rama: function() {
		cudu.dom.tabla.filtros.rama = 'M,C';
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
      (new YAHOO.util.Anim(tcFilter, {height: { to: 75 }}, 0.9, YAHOO.util.Easing.bounceOut)).animate();
      tcFilter.isOpen = true;
    } else {
      (new YAHOO.util.Anim(tcFilter, {height: { to: 0 }}, 0.7, YAHOO.util.Easing.backIn)).animate();
      tcFilter.isOpen = false;
    }
}

})();

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
