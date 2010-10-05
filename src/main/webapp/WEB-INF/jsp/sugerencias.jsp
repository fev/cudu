<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="app.title" /></title>
<link rel="icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link rel="shortcut icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/reset-fonts-grids/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/base/base-min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/theme/cudu.css" />" />
<style type="text/css">
h2 { color: #C00; font-size: 123.1%; }
div#bd { padding-left: 20px; }
textarea { font-size: 138.5%; border: 1px solid #95A1A5; color: #043B4D; padding: 5px; width: 98%; margin-top: 10px }
textarea.error { background-color: #ffe7e7; border-color: #db4545; color: #db2020 }
input.button { float: right; margin-top: 10px; margin-left: 10px }

div.duda { border: 1px solid #CCC; -webkit-border-radius: 3px; -moz-border-radius: 3px; margin-top: 12px; min-height: 67px; cursor: pointer }
div.duda h3 { color: #333; margin: 10px 5px 6px 6px; min-height: 33px }
div.duda div.votos { font-size: 182%; float: left; width: 50px; border: 1px solid #DEDEDE;
	-webkit-border-radius: 4px; -moz-border-radius: 4px; background-color: #FFF; color: #555;
	height: 30px; padding-left: 0; padding-top: 3px; margin: 6px 15px 20px 6px; text-align: center }
div.duda div.fecha { float: right; margin: 10px 8px 0px 0px; color: #CCC }
div.duda div.votantes { clear:both; border-bottom: 1px solid #CCC; padding: 3px 10px; color: #4899CE; }
div.duda:hover { background-color: #FFF7ED; border-color:#ECDEB7; }
div.duda:hover div.votantes { color: #99803B !important; border-color:#ECDEB7; background-color: #FDF5DE; }

div.duda img { float:right; display:none }
div.duda.loading img { display: inherit }
</style>
</head>
<body>
<div id="doc" class="yui-t7 ayuda">
<jsp:include page="header.jsp"></jsp:include>
<div id="bd">
<div class="yui-g">
<h1>Sugerencias, dudas, cosas que no funcionan... ¿qué echas en falta?</h1>
<textarea id="txtSugerencia" rows="3"></textarea>
</div>
<div class="yui-g">
<p style="float:left; margin: 15px 0 0 5px;">Puedes pulsar sobre cualquier sugerencia de la lista para votarla.</p>
<input id="btnGuardar" type="submit" value="<fmt:message key="btn.afegir" />" class="button save" />
<input type="submit" value="<fmt:message key="btn.volver" />" class="button back" onclick="javascript:cudu.back()" />
</div>

<div id="listaSugerencias" class="yui-g">
<c:forEach items = "${sugerencias}" var="sugerencia">
<div id="duda-${sugerencia.pk}" class="duda" data-pk="${sugerencia.pk}">
	<div class="votantes">${sugerencia.votantes}
		<img src="<c:url value="/s/theme/img/feedback-loader.gif" />" />
	</div>
	<div class="fecha">${sugerencia.fecha}</div>
	<div class="votos">${sugerencia.votos}</div>
	<h3><spring:escapeBody>${sugerencia.texto}</spring:escapeBody></h3>
</div>
</c:forEach>

<%--
<div class="duda">
	<div class="votantes"><strong>xuano</strong>, laia, pedro, amaia, raquel, esther, paco, eli, luis, salva, ricardo, obispo
		<img src="<c:url value="/s/theme/img/feedback-loader.gif" />" />
	</div>
	<div class="fecha">12/09/2010 15:45</div>
	<div class="votos"><span>3</span></div>
	<h3>Incluir solo las columnas seleccionadas</h3>
</div>

<div class="duda">
	<div class="votantes"><strong>ramonet</strong>
		<img src="<c:url value="/s/theme/img/feedback-loader.gif" />" style="float:right" /></div>
	<div class="fecha">12/09/2010 15:45</div>
	<div class="votos">12</div>
	<h3>Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</h3>
</div>

<div class="duda">
	<div class="votantes"><strong>paula</strong>, rocio</div>
	<div class="fecha">12/09/2010 15:45</div>
	<div class="votos">14</div>
	<h3>Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</h3>
</div>
--%>

</div>

<div id="stddlg" class="popupdlg">
<div class="yui-t7">
<div class="bd">
   <div class="yui-g legend">
      <h1 style="margin-bottom: 10px">¡Votado!</h1>
      <p>Gracias por tu sugerencia.</p>
   </div>
 </div>
</div>
</div>

</div>
<div id="ft"><fmt:message key="app.copyright" /></div>

<div id="baseTmpl" class="duda hidden">
	<div class="votantes">usr_name</div>
	<div class="fecha">data_time</div>
	<div class="votos">1</div>
	<h3>texto.</h3>
</div>

</div>
<script src="<c:url value="/s/jquery/jquery-1.4.2.js" />" type="text/javascript"></script>
<script type="text/javascript">

jQuery.fn.center = function() {
	return this.each(function() {
		var e = jQuery(this);
		var offset = e.offset();
		var y = (offset.top + $(window).height() / 2) - (e.height() / 2);
		e.attr("style", "top: " + y + "px");
	});
};

var cudu = {
	username: '<c:out value="${username}" />',
	back: function() {
		document.location = '<c:url value="/dashboard" />'; 
	},
	dom: {
		baseTmpl: { 
			container: document.getElementById("baseTmpl")
		}
	}
};

function pad2(str) {
	var s = str + '';
	if (s.length == 1)
		return '0' + s;
	return s;
}

function obtenerFecha(d) {
	var date = pad2(d.getDate());
	var month = pad2(d.getMonth());
	var year = pad2(d.getFullYear());
	var hours = pad2(d.getHours());
	var minutes = pad2(d.getMinutes());
	var seconds =  pad2(d.getSeconds());
	return {
		legible: date + '/' + month + '/' + year + ' ' + hours + ':' + minutes + ':' + seconds,
		almacenable: '' + year + month + date + hours + minutes + seconds
	};
}

function obtenerFechaActual() {
	return obtenerFecha(new Date());
}

$(document).ready(function() {
	var fragment = $(cudu.dom.baseTmpl.container);
	cudu.dom.baseTmpl.container = fragment;
	cudu.dom.baseTmpl.votantes = fragment.children('.votantes');
	cudu.dom.baseTmpl.fecha = fragment.children('.fecha');
	cudu.dom.baseTmpl.texto = fragment.children('h3');
	
	$('#btnGuardar').click(function() {
		var texto = $('#txtSugerencia').val().trim();
		if (texto.length < 5) {
			$('#txtSugerencia').addClass('error');
			return;
		}
		$('#txtSugerencia').removeClass('error');
		
		var fecha = obtenerFechaActual();
		
		// GUARDAR
		var storageData = fecha.almacenable + texto;
		/* $.post('sugerencias', storageData, function(data) {
			console.log(data);
		}); */
		$.get('sugerencias/nueva?s=' + storageData, function(pk){
			cudu.dom.baseTmpl.container.attr('id', 'duda-' + pk);
		});

		cudu.dom.baseTmpl.fecha.text(fecha.legible);
		cudu.dom.baseTmpl.votantes.empty();
		cudu.dom.baseTmpl.votantes.append('<strong>' + cudu.username + '</strong>');
		cudu.dom.baseTmpl.texto.text(texto);
		
		$('#txtSugerencia').val('');
		cudu.dom.baseTmpl.container.clone().removeClass('hidden').appendTo('#listaSugerencias');
		$('#stddlg').center().fadeIn("fast").delay(1000).fadeOut();
	});
	
	/*
	var lista = $('listaSugerencias');
	for (var i = 0; i < sugerencias.length; i++) {
		var s = sugerencias[i];
		cudu.dom.baseTmpl.container.attr('id', 'duda-' + s.pk);
		cudu.dom.baseTmpl.fecha.text(s.fecha); // TODO Parse
		cudu.dom.baseTmpl.votantes.text(s.votantes.join(', '));
		cudu.dom.baseTmpl.texto.text(s.texto);
		cudu.dom.baseTmpl.container.clone().removeClass('hidden').appendTo('#listaSugerencias');
	}*/
	
	$(".duda").click(function() {
		var usado = $(this).attr("data-usado");
		if (usado == "1") {
			$(this).fadeOut(100).fadeIn(100);
			return;
		}
		
		$(this).addClass('loading');
		
		// TODO Cambiar método a PUT
		var rawPk = $(this).attr("data-pk");
		$.post('sugerencias/' + rawPk, function(data) {
			var container = $("#duda-" + rawPk);
			container.children(".votantes").text(data.u);
			container.children(".votos").text(data.v);
			container.attr("data-usado", "1");
		}, 'json');
		
		$(this).removeClass('loading');
		$('#stddlg').center().fadeIn("fast").delay(1000).fadeOut();
	});
});

var sugerencias = [
	{pk: 1, texto: 'Lipsunato eris', fecha: '19/05/2010', votantes: ['xuano', 'luis', 'adela'] },
	{pk: 2, texto: 'Quienqua pireit ne', fecha: '08/05/2010', votantes: ['luis', 'cudu'] }
];
</script>
</body>
</html>