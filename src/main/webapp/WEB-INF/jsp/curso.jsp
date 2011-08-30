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

<body onmousedown="whichElement(event)">>
    
    
    <div id="doc" class="yui-t7">
        <jsp:include page="header.jsp"></jsp:include>
        <form:form modelAttribute="inscripciones" method="POST">
            <div id="bd">
                <div class="yui-g">
                    
                    <div class="yui-g first">
                        <h1 id="hform">
                            <c:choose>
                              <c:when test="${asociado.id > 0}"><spring:message code="curso.AJ.titulo.modificar" /></c:when>
                              <c:otherwise><spring:message code="curso.AJ.titulo.nuevo" /></c:otherwise>
                            </c:choose>
                        </h1>
                    </div>
                    
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
                          <label id="nombre" cssClass="content">${asociado.nombre}</label>
                          <label id="apellido1" cssClass="content">${asociado.primerapellido}</label>
                          <label id="apellido2" cssClass="content">${asociado.segundoapellido}</label>        
                          <label id="email" cssClass="content">${asociado.email} ></label>
                      </h3>
                  </div>
                          
                  <c:set var="aj"        value="AJ"/>
                  <c:set var="fc"        value="FC"/>
                  <c:set var="mtl"       value="MTL"/>
                  <c:set var="manali"    value="MANALI"/>
                  <c:set var="fa"        value="FA"/>
                  <c:set var="tipoCurso" value="${tipoCurso}"/>

                  <c:if test="${aj == tipoCurso}"></c:if>
                  
                  <div class="yui-g first">
                      <div class="yui-g first legend"><h2><fmt:message key="cursos.f.monograficos" /></h2></div>
                  </div>
                      
                  <c:out value="<script language='JavaScript'>" escapeXml="false"></c:out>
                    var elementsMonograficos=[], idsFijos='';
                  <c:out value="</script>" escapeXml="false"></c:out>
                                
                  <form:input id="txtMonograficosElegidos" path="monograficosElegidos" cssClass="textbox w3u" />
                  
                  <!-- aqui esta lo interesante -->
                  <c:forEach var="monograficosEnCursosDeBloque" items="${monograficosEnCursos}" varStatus="status">
                      <div class="yui-g first legend"><h3>${monograficosEnCursosDeBloque[0].bloque}</h3></div>
                      <c:set var="disabled"        value=""/>
                      <c:forEach var="monograficosEnCursos" items="${monograficosEnCursosDeBloque}" varStatus="status2">          
                          <div class="filed ">
                              
                                                                
                              <c:forEach var="monograficoCursoAJ" items="${monograficosCursoAJ}" varStatus="status3">
                                  <c:if test="${monograficoCursoAJ.monografico.id == monograficosEnCursos.monografico.id}">
                                      <c:set var="disabled"        value="DISABLED"/>
                                  </c:if>
                                  
                                  <c:if test="${(monograficoCursoAJ.ronda > monograficosEnCursos.monografico.fechafin && 
                                                monograficoCursoAJ.ronda > monograficosEnCursos.monografico.fechainicio) 
                                                ||
                                                (monograficoCursoAJ.fechafin < monograficosEnCursos.monografico.fechafin && 
                                                monograficoCursoAJ.fechafin  < monograficosEnCursos.monografico.fechainicio) 
                                        }">
                                      <c:set var="disabled"        value="DISABLED"/>
                                  </c:if>                                  
                              </c:forEach>
                              <c:choose>
                                  <c:when test ="${monograficosEnCursosDeBloque[0].fijo== true}">
                                      <label for="txt_${monograficosEnCursos.monografico.nombre}">
                                          <font size="4" > ${monograficosEnCursos.monografico.nombre}        </font>
                                      </label>
                                      
                                      
                                      <c:out value="<script language='JavaScript'>" escapeXml="false"></c:out>
                              
                                        idsFijos = idsFijos+',' + ${monograficosEnCursos.monografico.id};
                              <c:out value="</script>" escapeXml="false"></c:out>
                                      
                                      
                                      
                                      
                                      
                                      
                                  </c:when>
                                  <c:otherwise>
                                      <c:choose>
                                          <c:when test ="${monograficosEnCursos.bloqueunico== true}">
                                              <INPUT  NAME="${monograficosEnCursos.bloque}" type="radio" id="rb_${monograficosEnCursos.monografico.nombre}" ${disabled}/>

                                              <label for="rb_${monograficosEnCursos.monografico.nombre}">
                                                  <font size="4"> 
                                                      ${monograficosEnCursos.monografico.nombre}        
                                                  </font>
                                              </label>
                                              <c:set var="idElement" value="rb_${monograficosEnCursos.monografico.nombre}"/>

                                          </c:when >

                                          <c:otherwise>
                                              <INPUT NAME="${monograficosEnCursos.bloque}" type="checkbox" id="cb_${monograficosEnCursos.monografico.nombre}" ${disabled}/>                                                
                                              <label for="cb_${monograficosEnCursos.monografico.nombre}">
                                                  <font size="4" > ${monograficosEnCursos.monografico.nombre}   estado:${disabled}     </font> 
                                              </label>
                                              
                                              <c:set var="idElement" value="cb_${monograficosEnCursos.monografico.nombre}"/>
                                          </c:otherwise>
                                      </c:choose>
                                         <label id="fechas "><font color="green">Del ${monograficosEnCursos.monografico.fechainicio} al ${monograficosEnCursos.monografico.fechafin} </font></label>

                            <!--  
                                                          <label><font color="red">plazas ${monograficosEnCursos.monografico.plazasdisponibles}/ ${monograficosEnCursos.monografico.plazastotales}</font></label>   
                              <c:if test="${monograficosEnCursos.monografico.plazasdisponibles == monograficosEnCursos.monografico.plazastotales}">
                                  <label><font color="red">lista espera:${monograficosEnCursos.monografico.listaespera}</font>  </label>
                              </c:if>
  
                              <c:if test="${monograficosEnCursos.monografico.plazasdisponibles < monograficosEnCursos.monografico.plazastotales}">
                                  <label><font color="green">lista espera:${monograficosEnCursos.monografico.listaespera}</font>  </label>
                              </c:if>
                            !-->
                                  
                              <!-- esto lo utilizaremos para administrar los elementos que no se puedan por fechas incompatibles -->
                              <c:out value="<script language='JavaScript'>" escapeXml="false"></c:out>
                              
                                elementsMonograficos.push({
                                         nombre: "${idElement}",
                                    fechaInicio: "${monograficosEnCursos.monografico.fechainicio}", 
                                       fechaFin: "${monograficosEnCursos.monografico.fechafin}",
                                             id: "${monograficosEnCursos.monografico.id}", 
                                });                                  
                              <c:out value="</script>" escapeXml="false"></c:out>
                                  </c:otherwise>
                              </c:choose>       
                          </div>
                      </c:forEach>
                  </c:forEach>
              </c:if>
                      
              <c:if test = "${asociado.tipo == 'A'}"></c:if>
              <c:if test = "${asociado.tipo == 'S'}"></c:if>

              <div class="yui-g form-action">
                  <div class="yui-g first">
                      <c:if test="${asociado.id > 0}">
                          <input type="button" value="<fmt:message key="btn.eliminar" />" class="button delete" onclick="javascript:cudu.remove()" />
                      </c:if>
                  </div>
                  
                  <div class="yui-g">
                      <input type="button" value="<fmt:message key="btn.volver" />" class="button back" onclick="javascript:window.history.back();" />
                      <input type="submit" value="<fmt:message key="btn.guardar" />" class="button save" />
                  </div>
              </div>
            </div>
        </form:form>

        <div id="ft"><fmt:message key="app.copyright" /></div>

        <div id="stddlg" class="popupdlg">
            <div class="yui-t7">
                <div class="bd">
                    <div class="yui-g legend">
                        <h1><fmt:message key="asociado.d.eliminar" /></h1>
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
    

<script type="text/javascript" src="<c:url value="/s/scripts/date.js" />"></script>
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
    whichElement = function (event){
        var tname;
        tname=event.srcElement.id;

        var posBuscada =-1;
       if(document.getElementById(tname).checked==false)
       {
            
            for(i = 0; i < elementsMonograficos.length;i++)
            {
                if(elementsMonograficos[i].nombre==tname)
                    posBuscada = i;
            }

            if(posBuscada>=0)
            {
                //alert("You clicked on a " +posBuscada +" "+ tname + " element.");
                var idsRecogidos=elementsMonograficos[posBuscada].id;
                
                for(i = 0; i<elementsMonograficos.length;i++)
                {
                    if(i!=posBuscada)
                    {
                        var elem = elementsMonograficos[i].nombre;
                        
                        if(document.getElementById(elem).checked){
                            var fecha1 =Date.parse(elementsMonograficos[posBuscada].fechaInicio),
                                fecha2 =Date.parse(elementsMonograficos[posBuscada].fechaInicio),
                                fecha3 = Date.parse(elementsMonograficos[i].fechaFin),
                                fecha4 = Date.parse(elementsMonograficos[i].fechaInicio);
                            if(!(
                               (
                                fecha1.compareTo(fecha3)>0
                                &&
                                fecha1.compareTo(fecha4)>0
                               )
                                ||
                               (
                                fecha2.compareTo(fecha3)<0
                                &&
                                fecha2.compareTo(fecha4)<0
                                )
                            ))                       
                            {
                               document.getElementById(elem).checked=false;
                            }
                            
                                
                            
                        }
                    }
                }
            }
       }
       var idsRecogidos = '';
       if(posBuscada>-1 && document.getElementById(tname).checked==false)
       {
        idsRecogidos =elementsMonograficos[posBuscada].id;
       }
       for(i = 0; i< elementsMonograficos.length;i++)
       {
           if(posBuscada!=i)
           {
               var elem = elementsMonograficos[i].nombre;
               if(document.getElementById(elem).checked==true){
                   idsRecogidos = idsRecogidos + ','+elementsMonograficos[i].id;
                   document.getElementById('nombre').innerText = i+'-'+posBuscada;
               }
           }

       }
       
       if(idsFijos!='')
        {
            idsRecogidos = idsRecogidos+idsFijos;
        }

        document.getElementById('txtMonograficosElegidos').value = idsRecogidos ;
        
    }
});
</script>
</body>
</html>
