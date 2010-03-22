<%@include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/reset-fonts-grids/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/base/base-min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/datatable/assets/skins/sam/datatable.css" />" />
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
<div id="doc" class="yui-t7">
<jsp:include page="header.jsp"></jsp:include>
<div id="bd">
  <div class="yui-g tc-tb">
	<a href="<c:url value="/asociado.mvc" />" class="save">
		<img src="<c:url value="/s/theme/img/tango/document-new.png" />" />
		<span>Nuevo</span>
	</a>
	<a href="javascript:toogleFilter()">
		<img src="<c:url value="/s/theme/img/tango/edit-find.png" />" />
		<span>Filtrar</span>
	</a>
    <a href="#">
      <img src="<c:url value="/s/theme/img/tango/select-column.png" />" />
      <span>Columnas</span>
    </a>
	<a href="#">
		<img src="<c:url value="/s/theme/img/tango/document-save.png" />" />
		<span>Guardar</span>
	</a>
	<a href="#" class="delete">
		<img src="<c:url value="/s/theme/img/tango/edit-delete-row.png" />" />
		<span>Eliminar</span>
	</a>
	<a href="<c:url value="/dashboard.mvc" />">
		<img src="<c:url value="/s/theme/img/tango/edit-undo.png" />" />
		<span>Volver</span>
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
  </div>
</div>
<div id="ft"><fmt:message key="app.copyright" /></div>
</div>
<script type="text/javascript" src="<c:url value="/s/yui/yahoo-dom-event/yahoo-dom-event.js" />"></script> 
<script type="text/javascript" src="<c:url value="/s/yui/dragdrop/dragdrop-min.js" />"></script> 
<script type="text/javascript" src="<c:url value="/s/yui/element/element-min.js" />"></script> 
<script type="text/javascript" src="<c:url value="/s/yui/animation/animation-min.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/yui/datasource/datasource-min.js" />"></script> 
<script type="text/javascript" src="<c:url value="/s/yui/datatable/datatable-min.js" />"></script> 
<script type="text/javascript">

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

YAHOO.util.Event.addListener(window, "load", function() {
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
 
        var myDataTable = new YAHOO.widget.DataTable("listado", myColumnDefs, myDataSource, {draggableColumns:true, formatRow: myRowFormatter});
                
        return {
            oDS: myDataSource,
            oDT: myDataTable
        };
    }();
});
</script>
</body>
</html>