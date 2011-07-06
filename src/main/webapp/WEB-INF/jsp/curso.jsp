<%@include file="taglibs.jsp" %>
<%@page import ="java.util.List"%>
<%@page import ="org.scoutsfev.cudu.domain.Monografico"%>
<%@page import ="org.scoutsfev.cudu.domain.MonograficosEnCursos"%>
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
  	<c:choose>
  	  <c:when test="${asociado.id > 0}"><spring:message code="curso.AJ.titulo.modificar" /></c:when>
  	  <c:otherwise><spring:message code="curso.AJ.titulo.nuevo" /></c:otherwise>
  	</c:choose>
  </h1></div>
  <div class="yui-g" style="text-align:right; padding-top: 1px; margin-right: 13px">
    <img src="<c:url value="/s/theme/img/tango/document-save.png" />" />

    <a id="btnSave" href="<%=request.getContextPath()%>/s/filesClient/listado.pdf ">
      <img src="<c:url value="/s/theme/img/tango/save_24.png" />" />
      <span><fmt:message key="descarga.libroformacion" /></span>
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


  <c:if test = "${asociado.tipo != 'S'}">
    <div>
        <h3>
        <form:label path="nombre" cssClass="content">${asociado.nombre}</form:label>
        <form:label path="nombre" cssClass="content">${asociado.primerapellido}</form:label>
        <form:label path="nombre" cssClass="content">${asociado.segundoapellido}</form:label>        
        <form:label path="nombre" cssClass="content">< ${asociado.email} ></form:label>
        </h3>
    </div>


     <c:set var="aj" value="AJ"/>
     <c:set var="fc" value="FC"/>
     <c:set var="mtl" value="MTL"/>
     <c:set var="manali" value="MANALI"/>
     <c:set var="fa" value="FA"/>
     <c:set var="tipoCurso" value="${tipoCurso}"/>
    <c:if test="${aj == tipoCurso}"></c:if>
        <div class="yui-g first">    
            <div class="yui-g first legend"><h2><fmt:message key="asociado.f.rama" /></h2></div>

            <div class="field required">
                <a id="radioColonia" href="#" class="dropramas" >&nbsp;</a>
                <a id="radioManada" href="#" class="dropramas">&nbsp;</a>
                <a id="radioExploradores" href="#" class="dropramas">&nbsp;</a>
                <a id="radioPioneros" href="#" class="dropramas">&nbsp;</a>
                <a id="radioRutas" name="rama.rutas" href="#" class="dropramas">&nbsp;</a>
                <form:radiobutton id="chkRamasColonia" path="rama.colonia" cssClass="hidden" />
                <form:radiobutton id="chkRamasManada" path="rama.manada" cssClass="hidden" />
                <form:radiobutton id="chkRamasExploradores" path="rama.exploradores" cssClass="hidden" />
                <form:radiobutton id="chkRamasPioneros" path="rama.pioneros" cssClass="hidden" />
                <form:radiobutton id="chkRamasRutas" path="rama.rutas" cssClass="hidden" />
            </div>
        </div>
                
        <div class="yui-g first">
          <div class="yui-g first legend"><h2><fmt:message key="cursos.f.monograficos" /></h2></div>
        </div>         
        

        <c:forEach var="monograficosEnCursosDeBloque" items="${monograficosEnCursos}" varStatus="status">

        <div class="yui-g first legend"><h3>${monograficosEnCursosDeBloque[0].bloque}</h3></div>
            <c:forEach var="monograficosEnCursos" items="${monograficosEnCursosDeBloque}" varStatus="status">          
                <div class="filed ">
       
                    <c:choose>
                        <c:when test ="${monograficosEnCursos.bloqueunico== true}">
                                <form:radiobutton id="rb_${monograficosEnCursos.monografico.nombre}" cssClass="radio" path="${bloque}" value="${monograficosEnCursos.bloque}" name="${monograficosEnCursos.bloque}" title="${monograficosEnCursos.monografico.nombre}"/>
                                <label for="rb_${monograficosEnCursos.monografico.nombre}">${monograficosEnCursos.monografico.nombre}        plazas ${monograficosEnCursos.monografico.plazasdisponibles}/ ${monograficosEnCursos.monografico.plazastotales}</label>
                        </c:when >
                        <c:otherwise>
                                <form:checkbox id="cb_${monograficosEnCursos.monografico.nombre}" path="${bloque}" cssClass="checkbox" value="${monograficosEnCursos.bloque}" name="${monograficosEnCursos.monografico.nombre}"/>
                                <label for="cb_${monograficosEnCursos.monografico.nombre}">${monograficosEnCursos.monografico.nombre}        plazas ${monograficosEnCursos.monografico.plazasdisponibles}/ ${monograficosEnCursos.monografico.plazastotales}</label>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${monograficosEnCursos.monografico.plazasdisponibles == monograficosEnCursos.monografico.plazastotales}">
                        <label><font color="red">lista espera:${monograficosEnCursos.monografico.listaespera}</font>  </label>
                    </c:if>
                </div>
            </c:forEach>
        </c:forEach>      
  </c:if>
  <c:if test = "${asociado.tipo == 'A'}"></c:if>


    <c:if test = "${asociado.tipo == 'S'}">
    </c:if>

  <div class="yui-g form-action">
    <div class="yui-g first">
   	<c:if test="${asociado.id > 0}">
      <input type="button" value="<fmt:message key="btn.eliminar" />" class="button delete" onclick="javascript:cudu.remove()" />
    </c:if>
    </div>
    <div class="yui-g">
      <input type="button" value="<fmt:message key="btn.volver" />" class="button back" onclick="javascript:window.history.back();" />
      <input type="submit" value="<fmt:message key="btn.guardar" />" class="button save" />
      <%-- <input type="button" value="<fmt:message key="btn.imprimir" />" class="button print" /> --%>
    </div>
  </div>

</form:form>
<div id="ft"><fmt:message key="app.copyright" /></div>


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


cudu = {
	back: function() {
		document.location = '<c:url value="/dashboard" />';
	},

	remove: function() {
		$("#stddlg").center().fadeIn();
	},

	ui: {
		dropramas: $(".dropramas"),
		ramas: {
			"radioColonia": document.getElementById('chkRamasColonia'),
			"radioManada": document.getElementById('chkRamasManada'),
			"radioExploradores": document.getElementById('chkRamasExploradores'),
			"radioPioneros": document.getElementById('chkRamasPioneros'),
			"radioRutas": document.getElementById('chkRamasRutas')
		}
	}
  
};

$(document).ready(function() {

	// Seleccionar las ramas del asociado
	if (cudu.ui.ramas["radioColonia"].checked)
		$('#radioColonia').toggleClass("selected");
	if (cudu.ui.ramas["radioManada"].checked)
		$('#radioManada').toggleClass("selected");
	if (cudu.ui.ramas["radioExploradores"].checked)
		$('#radioExploradores').toggleClass("selected");
	if (cudu.ui.ramas["radioPioneros"].checked)
		$('#radioPioneros').toggleClass("selected");
	if (cudu.ui.ramas["radioRutas"].checked)
		$('#radioRutas').toggleClass("selected");

	// Atender al click sobre el selector de rama
	cudu.ui.dropramas.click(function(e) {

    		cudu.ui.dropramas.removeClass("selected"); // comentar para permitir selección múltiple


		$(this).toggleClass("selected");

		cudu.ui.dropramas.each(function() {
			var el = cudu.ui.ramas[this.id];
			if ($(this).hasClass('selected')) {
				el.checked = true;
			} else {
				el.checked = false;
			}
		});
	});
});
</script>
</body>
</html>
