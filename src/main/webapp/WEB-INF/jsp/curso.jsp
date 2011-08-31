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
                              <spring:message code="asociado.curso.${tipoCurso}" />
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
                    var idsRecogidos='';
                    var monograficosYPreferencias='';
                    var elementsMonograficos=[], idsFijos='',selMonograficos=[];
                  <c:out value="</script>" escapeXml="false"></c:out>
                                
                  <form:input id="txtMonograficosElegidos" path="monograficosElegidos" cssClass="hidden" />
                  <form:input id="txtFormatoMateriales" path="formatoMateriales" cssClass="hidden" value="P" />
                  <form:input id="txtIdiomaMateriales" path="idiomaMateriales" cssClass="hidden" value="C"/>
                  <form:input id="txtModoContacto" path="modoContacto" cssClass="hidden" value="P" />
                  <form:input id="txtidAsociado" path="idAsociado" cssClass="hidden" value ="${asociado.id}"/>
                  <form:input id="txtidCurso" path="idCurso" cssClass="hidden" value ="${curso.id}"/>
                  
                  <div class="yui-g first legend"><h3><fmt:message key="curso.inscripcion.preferencias" /></h3></div>
                  <label for="txt_IDIOMA">
                      <fmt:message key="curso.inscripcion.preferencias.idioma" />
                  </label>
                  <SELECT NAME="sel_idioma" SIZE="1" onChange="changePreferencia(this,'txtIdiomaMateriales');">                                                     
                        <OPTION VALUE="C"><fmt:message key="curso.inscripcion.preferencias.idioma.castellano" /></OPTION>
                        <OPTION VALUE="V"><fmt:message key="curso.inscripcion.preferencias.idioma.valenciano" /></OPTION> 
                  </SELECT>
                  
                  <label for="txt_formatoApuntes">
                      <fmt:message key="curso.inscripcion.preferencias.formatoapuntes" />
                  </label>
                  <SELECT NAME="sel_formato" SIZE="1" onChange="changePreferencia(this,'txtFormatoMateriales');"> 
                        <OPTION VALUE="P"><fmt:message key="curso.inscripcion.preferencias.formatoapuntes.papel" /></OPTION>
                        <OPTION VALUE="E"><fmt:message key="curso.inscripcion.preferencias.formatoapuntes.electronico" /></OPTION> 
                  </SELECT>
                      
                  <label for="txt_modoContacto">
                      <fmt:message key="curso.inscripcion.preferencias.modocontacto" />
                  </label>
                  <SELECT NAME="sel_Contacto" SIZE="1" onChange="changePreferencia(this,'txtModoContacto');"> 
                        <OPTION VALUE="P"><fmt:message key="curso.inscripcion.preferencias.modocontacto.postal" /></OPTION>
                        <OPTION VALUE="E"><fmt:message key="curso.inscripcion.preferencias.modocontacto.email" /></OPTION> 
                  </SELECT>
                  
                  
                  <!-- aqui esta lo interesante -->
                  <c:forEach var="monograficosEnCursosDeBloque" items="${monograficosEnCursos}" varStatus="status">
                      <div class="yui-g first legend"><h3>${monograficosEnCursosDeBloque[0].bloque}</h3></div>
                      <c:set var="disabled"        value=""/>
                      <c:forEach var="monograficosEnCursos" items="${monograficosEnCursosDeBloque}" varStatus="status2">
                          <div class="filed ">    
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
                                              <select id ="sel_${monograficosEnCursos.monografico.nombre}" name="sel_${monograficosEnCursos.monografico.nombre}" onChange="change(this);">
                                                  <c:forEach var="opcion" items="${monograficosEnCursosDeBloque}" varStatus="status4">
                                                      <c:choose>
                                                          <c:when test ="${status4.index == status2.index}">
                                                              <c:out value="<option selected='selected' value='${status4.index+1}'>${status4.index+1}</option> " escapeXml="false"></c:out>
                                                          </c:when>
                                                          <c:otherwise>
                                                              <c:out value="<option value='${status4.index+1}'>${status4.index+1}</option>" escapeXml="false"></c:out>
                                                          </c:otherwise>
                                                      </c:choose>
                                                  </c:forEach>
                                                  
                                                      <c:out value="<script language='JavaScript'>" escapeXml="false"></c:out>
                                                          selMonograficos.push({
                                                                 nombre: "sel_${monograficosEnCursos.monografico.nombre}",
                                                                     id: "${monograficosEnCursos.monografico.id}", 
                                                                 bloque: "${monograficosEnCursos.bloque}", 
                                                               selected: "${status2.index}"
                                                           });
                                                           
                                                            monograficosYPreferencias=monograficosYPreferencias+ ",${monograficosEnCursos.monografico.id}=${status2.index}";
                                                           
                                                       <c:out value="</script>" escapeXml="false"></c:out>                                                  
                                              </select>
                                              
                                              
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
                                                  <font size="4" > ${monograficosEnCursos.monografico.nombre}</font> 
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
                      
              <div class="yui-g form-action">                  
                  <div class="yui-g">
                      <input type="button" value="<fmt:message key="btn.volver" />" class="button back" onclick="javascript:window.history.back();" />
                      <input type="submit" value="<fmt:message key="btn.guardar" />" class="button save" />
                  </div>
              </div>
            </div>
        </form:form>
    <div id="ft"><fmt:message key="app.copyright" /></div>
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
	}
};
$(document).ready(function() {
    
    document.getElementById('txtMonograficosElegidos').value = monograficosYPreferencias;
    
    whichElement = function (event){
        var tname;
        tname=event.srcElement.id;

        var posBuscada =-1;

        for(i = 0; i < elementsMonograficos.length;i++)
        {
            if(elementsMonograficos[i].nombre==tname)
                posBuscada = i;
        }

        if(document.getElementById(tname).checked==false)
        {
            if(posBuscada>=0)
            {
                //alert("You clicked on a " +posBuscada +" "+ tname + " element.");
                idsRecogidos=elementsMonograficos[posBuscada].id;
                
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
       idsRecogidos = '';
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
               }
           }
       }
       
       
       //los que no cambian
       if(idsFijos!='')
        {
            idsRecogidos = idsRecogidos+idsFijos;
        }

        document.getElementById('txtMonograficosElegidos').value = idsRecogidos+","+monograficosYPreferencias;
        
    }    
});


function changePreferencia(currentbox,nombrePreferencia)
{    
    var valueSelected,i= -1;

    //localizar elemento seleccionado
    var len = currentbox.length;        

    for (i = 0; i < len; i++) 
    {
        if (currentbox[i].selected) {
            valueSelected = currentbox[i].value;
        } 
    }
    document.getElementById(nombrePreferencia).value =valueSelected;
    
}
   
function change(currentbox) {
    var boxSelected= currentbox.id;
    var valueSelected;  

    var i,idBoxSel = -1;

    //localizar elemento seleccionado
    var len = currentbox.length;        

    for (i = 0; i < len; i++) {
        if (currentbox[i].selected) {
            valueSelected = currentbox[i].value;
        } 
    }

    var idBoxSel = -1;

    //encontrar pos en el array del combobox
    for(i=0; i < selMonograficos.length && idBoxSel==-1;i++)
    {
        if(boxSelected == selMonograficos[i].nombre)
        {
            idBoxSel = i;
        }
    }

    var idSelected = valueSelected-1;
    monograficosYPreferencias=selMonograficos[idBoxSel].id+"="+idSelected;
    //cambiar valores. para ello se recorren todos los comboboxes y se ven sus valores
    for(i=0; i < selMonograficos.length ;i++)
    {
        if(i!=idBoxSel)
        {
            if(selMonograficos[i].bloque == selMonograficos[idBoxSel].bloque)
            {
                var boxToModify = document.getElementById(selMonograficos[i].nombre);
                var chosen = "",j;
                var idBoxSel2 = -1;

                //encontrar pos
                 for (j = 0; j < len; j++)
                 {
                    if (boxToModify[j].selected)
                    {
                        if(boxToModify[j].value ==valueSelected)
                        {
                            boxToModify[selMonograficos[idBoxSel].selected ].selected=true;
                            boxToModify[j].selected=false;

                            var selectedIndex = selMonograficos[idBoxSel].selected;
                            selMonograficos[idBoxSel].selected  = selMonograficos[i].selected;
                            selMonograficos[i].selected  = selectedIndex;
                        }
                    }
                }
            }
            monograficosYPreferencias=monograficosYPreferencias+ "," + selMonograficos[i].id+"="+selMonograficos[i].selected;
            document.getElementById('txtMonograficosElegidos').value = monograficosYPreferencias+idsRecogidos;
        }            
    }
}
</script>
</body>
</html>
