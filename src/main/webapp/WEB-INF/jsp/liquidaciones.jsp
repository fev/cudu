<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><fmt:message key="app.title" /></title>
<link rel="icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link rel="shortcut icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link href='http://fonts.googleapis.com/css?family=Droid+Sans:regular,bold' rel='stylesheet' type='text/css' />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/reset-fonts-grids/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/base/base-min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/datatable/assets/skins/sam/datatable.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/theme/cudu.css" />" />
<style type="text/css">
div#bd { padding-left: 20px; }
.yui-skin-sam .yui-dt table { border-right: 0; }

div.ejercicio { border-top: 1px dotted #CCC; margin-top: 10px; height:3px; padding-top: 8px; }
div.ejercicio h1 { float: right; font-family: 'Droid Sans', arial, serif; font-size: 153.9% }
.liqs { margin-bottom: 15px; }
.liq { font-family: 'Droid Sans', arial, serif; cursor: pointer;
   min-width: 100px; min-height: 80px; border: 1px solid #CCC; float: left; margin: 0 10px 5px 0;
  -moz-border-radius: 4px; -webkit-border-radius: 4px; 
}
.liq:hover, div.g:hover { outline:0; -moz-box-shadow:0 0 6px #CECECE; -webkit-box-shadow:0 0 6px #CECECE; box-shadow:0 0 6px #CECECE; }
.liq div { margin: 8px }
.liq div:first-child { font-weight: bold; }

.wait { cursor: wait; padding-left: 28px; border: 1px dotted #EFEFEF; color: #555; margin-bottom: 10px; padding: 5px 0px 5px 28px;
  background: transparent url(<c:url value="/s/theme/img/liq-busy.png"/>) no-repeat 6px 5px; font-family: 'Droid Sans', arial, serif; }
.alert { background: transparent url(<c:url value="/s/theme/img/liq-alert.png"/>) no-repeat 6px 5px; cursor: default; }
div.ab img, div.ab span { display: block; float: left; margin-right: 5px; }
div.ab span.ab-alta { margin-right: 10px; }

/* Panel edición */
#liq-ed { border: 1px dashed #CCC; margin-bottom: 10px }
#liq-ed-title { margin: 8px; font-weight: bold; font-size: 131% }
#liq-ed-summ { margin: 0px 8px 8px 8px; }

#liq-ed-ft { background-color: #F6F6F6; padding: 8px 0 10px 0; text-align: right; }
#liq-ed-ft .button { margin-right: 8px; }
#liq-ed-ft .button:first-child { margin-right: 0 }
#liq-ed-ft .button:first-child a { 
  padding-left: 20px; background: transparent url(<c:url value="/s/theme/img/liq-accept.png"/>) no-repeat 1px 0px }

div.g { border: 1px solid #CECECE; float: left; margin: 0 5px 5px 0; padding: 5px; padding-right: 8px; cursor: pointer }
div.g:hover { background-color: #FFFBDB }
div.g span, div.g img { display: block; float: left; }
div.g span.gn { margin-right: 8px; }
div.g span.ga { margin-right: 5px; }

a#btngenliq { padding-left: 20px; background: transparent url(<c:url value="/s/theme/img/liq-add.png"/>) no-repeat 1px 0px }

.button { background: #F6F6F6 -webkit-gradient(linear, 0% 0%, 0% 100%, from(white), to(#EFEFEF)); 
  background-image: -webkit-gradient(linear, 0% 0%, 0% 100%, from(white), to(#EFEFEF)); border: 1px solid #CCC;
  border-radius: 3px 3px; color: black; cursor: pointer; height: 2.0833em; overflow: visible; padding: 0px 0.5em;
  vertical-align: middle; white-space: nowrap; font-size: 12px;
}
.button:hover, .button:focus { border-color:#999; background:#f3f3f3; outline:0;
  background-image:-moz-linear-gradient(top,#ffffff,#ebebeb);
  background-image:-webkit-gradient(linear,left top,left bottom,from(#ffffff),to(#ebebeb));
  -moz-box-shadow:0 0 3px #999; -webkit-box-shadow:0 0 3px #999; box-shadow:0 0 3px #999;
}
.button { width: 100px; padding: 5px; height: 50px }
.button a { color: black; text-decoration: none; font-family: 'Droid Sans', arial, serif; font-size: 12px; }

</style>
</head>
<body>
<div id="doc" class="yui-t7">
<jsp:include page="header.jsp"></jsp:include>

<div id="bd">

<div class="yui-g" style="margin-bottom: 15px">
  <span class="button"><a id="btngenliq" href="javascript:">Generar Liquidación</a></span>  
</div>

<div id="liq-wait" class="yui-g wait hidden">Generando liquidación. Por favor, espere...</div>

<div id="liq-ed" class="hidden">
  <div class="yui-gd">
    <div class="yui-u first">
      <div id="liq-ed-title">3 Enero</div>
      <div id="liq-ed-summ">Total Altas: 532<br />Total Bajas: 45</div>
    </div>
    <div class="yui-g" style="padding: 10px 0 5px 0">
      <div class="g">
        <span class="gn">Ain-Karen</span>
        <img src="<c:url value="/s/theme/img/liq-up.png"/>" /><span class="ga">12</span> 
        <img src="<c:url value="/s/theme/img/liq-down.png"/>" /><span>4</span> 
      </div>
    </div>
  </div>
  <div id="liq-ed-ft" class="yui-g">
    <span class="button"><a id="liq-ed-save" href="javascript:">Aceptar</a></span>
    <span class="button"><a id="liq-ed-cancel" href="javascript:">Cancelar</a></span>
  </div>
</div>

<c:choose>
<c:when test="${fn:length(liquidaciones) == 0}">
  <div class="yui-g wait alert">No existen liquidaciones.</div>
</c:when>
<c:otherwise>
  <c:set var="ultimoEjercicio" value="-1" />
  <c:forEach items="${liquidaciones}" var="liq">
    <c:if test="${ultimoEjercicio != liq.ejercicio}">
      <c:if test="${ultimoEjercicio != -1}">
        </div></div><!-- div.yui-g.liqs  -->
      </c:if>
      <c:set var="ultimoEjercicio" value="${liq.ejercicio}" />
      <div class="yui-g ejercicio"><h1>${liq.ejercicio}</h1></div>
      <div class="yui-g liqs"><div style="">
    </c:if>
    <div class="liq" data-ejercicio="${liq.ejercicio}" data-fecha="<fmt:formatDate value="${liq.fecha}" pattern="yyyy-MM-dd" />">
      <div><fmt:formatDate value="${liq.fecha}" pattern="dd MMMM" /></div>
      <div>Altas: ${liq.altas}</div>
      <div>Bajas: ${liq.bajas}</div>
    </div>
  </c:forEach>
  </div></div><!-- div.yui-g.liqs  -->
</c:otherwise>
</c:choose>

</div><%-- div#bd --%>
<div id="ft"><fmt:message key="app.copyright" /></div>
</div><%-- div#doc --%>

<script src="<c:url value="/s/jquery/jquery-1.4.2.min.js" />" type="text/javascript"></script>
<script type="text/javascript">
cudu = { };
cudu.i8n = {
	meses: ['enero', 'febrero', 'marzo', 'abril', 'mayo', 'junio', 'julio', 'agosto', 'septiembre', 'octubre', 'noviembre', 'diciembre']
};
cudu.liquidaciones = {
	generar: function() {
		$("#liq-wait").fadeIn();
		$.getJSON('liquidaciones/generar', function(data) {
			$("#liq-ed-ft").show();
			$("#liq-ed").slideDown();
			$("#liq-wait").fadeOut();
		});
	},
	detalle: function() {
		// TODO cambiar mensaje en #liq-wait
		// TODO asociación desde cfg de usuario
		$("#liq-ed-ft").hide();
		var params = {"ejercicio": $(this).attr("data-ejercicio"), 
					  "fecha": $(this).attr("data-fecha"), 
					  "asociacion": 1};
		$.getJSON('liquidaciones/obtener', params, function(data) {
			var f = new Date(data.fecha);
			$("#liq-ed-title").text(f.getDate() + " " + cudu.i8n.meses[f.getMonth()]);
			$("#liq-ed-summ").html("Altas: " + data.altas + "<br/>Bajas: " + data.bajas);
			$("#liq-ed").fadeIn();
		});
	}
};

$(document).ready(function() {
	$("#btngenliq").click(cudu.liquidaciones.generar);
	$(".liq").click(cudu.liquidaciones.detalle);
	$("#liq-ed-cancel").click(function() { $('#liq-ed').slideUp(); });
});
</script>
</body>
</html>



