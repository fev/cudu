<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><fmt:message key="app.title" /></title>
<link rel="icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link rel="shortcut icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/reset-fonts-grids/reset-fonts-grids.css" />" />
<link rel="volvstylesheet" type="text/css" href="<c:url value="/s/yui/base/base-min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/theme/cudu.css" />" />
<style type="text/css">
.postmsg { border: 1px solid #CCC; padding: 4px; cursor: pointer }
.postmsg.ok { background-color: #E3FFE3; color: #64992C; border-color: #64992C; }

#secondary {  
   width: 400px;    
   height: 0px;    
   float: right;     
}  

#primary
{  
   width: 810px;    
   height: 0px;    
   float: left;   
}  
</style>
</head>
<body>

    <c:choose>
        <c:when test="${datosEstadisticaActual!=null}"> 
            <div class="yui-g first" id="secondary">
                <jsp:include page="header2Column.jsp"></jsp:include>
                <div id="container" ></div>
            </div>
        </c:when> 

        <c:otherwise>
            <div class="yui-g first" id="secondary">
                <jsp:include page="header2Column.jsp"></jsp:include>
                <div style="width: 400px; height: auto; background: WHITE; margin: 0; display: block;
                     border-bottom: 0px solid #EEE; height: 25px;    -moz-border-radius: 8px; -webkit-border-radius: 8px;">
                    <h2><fmt:message key="estadistica.h.sinestadistica" /></h2>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    
    <div id="primary" class="yui-t7">
        <jsp:include page="header.jsp"></jsp:include>
        <div id="bd">
            <div class="yui-g">
                <div class="yui-g first">
                    <h1><fmt:message key="grupo.h.titulo" /> <c:out value="${grupo.nombre}" /></h1>
                    
                    <c:choose>
                        <c:when test="${grupo.id != null}"><spring:message code="grupo.titulo.editar" /></c:when> 
                        <c:otherwise><spring:message code="grupo.titulo.nuevo" /></c:otherwise>
                    </c:choose>
                </div>
                <div class="yui-g"><%-- Parte derecha --%></div>
            </div>
                
            <form:form modelAttribute="grupo" method="POST">
                <c:set var="erroresValidacion" value="false" />
                <spring:hasBindErrors name="grupo">
                    <cudu:message id="mp01" key="frm.errores">
                        <c:set var="erroresValidacion" value="true" />
                        <form:errors id="mpbderr" path="*" />
                    </cudu:message>
                </spring:hasBindErrors>
  
                <c:if test="${param.ok != null && erroresValidacion == false}">
                    <cudu:message id="mp01" key="frm.ok" single="true" />
                </c:if>
 
                <div class="yui-g legend"><h2><fmt:message key="grupo.h.info" /></h2></div> 
                <div class="yui-g">
                    <div class="yui-g first">
                        <div class="field">
                            
                            
                            
                            <c:choose>
                        <c:when test="${grupo.id != null}">
                            <label for="lblId" class="w2u"><fmt:message key="grupo.f.id" /></label>
                            <span id="lblId" class="literal">
                                <abbr title="<fmt:message key="grupo.f.id.ayuda" />"><c:out value="${grupo.id}" /></abbr>
                            </span>
                            
                            </c:when> 
                        <c:otherwise>
                            <div class="field">
                                <label for="txtid" class="w4u"><fmt:message key="grupo.f.id" /></label>
                                <form:input id="txtid" path="id" cssClass="textbox w1u" cssErrorClass="textbox w1u error" />
                                <%-- <img src="<c:url value="/s/theme/img/calendar.png" />" alt="Elegir fecha" /> --%>
                            </div>
                        </c:otherwise>
                    </c:choose>
                            
                            
                            
                            
                        </div>
                        <div class="field required">
                            <label for="txtNombre" class="w2u"><fmt:message key="grupo.f.nombre" /></label>
                            <form:input id="txtNombre" path="nombre" cssClass="textbox w3u" cssErrorClass="textbox w3u error" />
                        </div>
                        
                        <div class="field required">
                            <label for="txtAsociacion" class="w2u"><fmt:message key="grupo.f.asociacion" /></label>
                            <form:input id="txtAsociacion" path="asociacion" cssClass="textbox w3u" cssErrorClass="textbox w3u error" />
                        </div>
                    </div>
                    <div class="yui-g">
                        <div class="field">
                            <label for="txtAniversario" class="w4u"><fmt:message key="grupo.f.aniversario" /></label>
                            <form:input id="txtAniversario" path="aniversario" cssClass="textbox w1u" cssErrorClass="textbox w1u error" />
                            <%-- <img src="<c:url value="/s/theme/img/calendar.png" />" alt="Elegir fecha" /> --%>
                        </div>
                        <div class="field">
                            <label for="txtEntPatr" class="w4u"><fmt:message key="grupo.f.entidadpatrocinadora" /></label>
                            <form:input id="txtEntPatr" path="entidadpatrocinadora" cssClass="textbox w3u" cssErrorClass="textbox w3u error" />
                        </div>
                    </div>
                </div>

                <div class="yui-g legend"><h2><fmt:message key="grupo.h.contacto" /></h2></div>
                <div class="yui-g">
                    <div class="yui-g first">
                        <div class="field required">
                            <label for="txtDireccion" class="w2u"><fmt:message key="grupo.f.direccion" /></label>
                            <form:input path="direccion" cssClass="textbox w3u" cssErrorClass="textbox w3u error" />
                        </div>
                        <div class="field required">
                            <label for="txtCodigoPostal" class="w2u"><fmt:message key="grupo.f.codigopostal" /></label>
                            <form:input id="txtCodigoPostal" path="codigopostal" cssClass="textbox w1u" cssErrorClass="textbox w1u error" />
                        </div>
                        <div class="field required">
                            <label for="txtProvincia" class="w2u"><fmt:message key="grupo.f.provincia" /></label>
                            <form:input id="txtProvincia" path="provincia" cssClass="textbox w3u"  cssErrorClass="textbox w3u error" />
                            <%--    <img id="imgProvincia" src="<c:url value="/s/theme/img/magnifier.png" />" alt="Buscar provincia." />
                            <span id="lblProvincia" class="literal"><c:out value="${asociado.provincia}" /></span> --%>
                        </div>
                        <div class="field required">
                            <label for="txtMunicipio" class="w2u"><fmt:message key="grupo.f.municipio" /></label>
                            <form:input id="txtMunicipio" path="municipio" cssClass="textbox w3u"  cssErrorClass="textbox w3u error" />
                            <%--    <img id="imgMunicipio" src="<c:url value="/s/theme/img/magnifier.png" />" alt="Buscar municipio." />
                            <span id="lblMunicipio" class="literal"><c:out value="${asociado.municipio}" /></span> --%>
                        </div>
                    </div>
                    <div class="yui-g">
                        <div class="field required">
                            <label for="txtTelefono"><fmt:message key="grupo.f.telefono1" /></label>
                            <form:input id="txtTelefono1" path="telefono1" cssClass="textbox w3u" cssErrorClass="textbox w3u error" maxlength="15" />
                        </div>
                        <div class="field ">
                            <label for="txtTelefono"><fmt:message key="grupo.f.telefono2" /></label>
                            <form:input id="txtTelefono2" path="telefono2" cssClass="textbox w3u" cssErrorClass="textbox w3u error" maxlength="15" />
                        </div>
                        <div class="field required">
                            <label for="txtEmail"><fmt:message key="grupo.f.email" /></label>
                            <form:input id="txtEmail" path="email" cssClass="textbox w3u" cssErrorClass="textbox w3u error" maxlength="100" />
                        </div>
                        <div class="field">
                            <label for="txtWeb"><fmt:message key="grupo.f.web" /></label>
                            <form:input id="txtWeb" path="web" cssClass="textbox w3u" cssErrorClass="textbox w3u error"  maxlength="300" />
                        </div>
                    </div>
                </div>
                <div class="yui-g form-action">
                    <div class="yui-g first">
                    <!--  <input type="submit" value="<fmt:message key="btn.eliminar" />" class="button delete" />-->
                    </div>
                </div>
                <div style="padding:20px" class="yui-g" align="right">
                    <input type="button" value="<fmt:message key="btn.volver" />" class="button back" onclick="javascript:back()" />
                    <input type="submit" value="<fmt:message key="btn.guardar" />" class="button save" />
                    <!--  <input type="button" value="<fmt:message key="btn.imprimir" />" class="button print" />-->
                </div>
            </form:form>
        </div>
        <div id="ft"><fmt:message key="app.copyright" /></div>
    </div>
</body>


<script src="<c:url value="/s/jquery/jquery-1.4.2.min.js" />" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/s/jquery/jquery-1.4.2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/scripts/estadistica/highcharts.js"/>"></script>
<script type="text/javascript">
function back() {
  document.location = '<c:url value="/" />'; 
}


/**
 * Grid theme for Highcharts JS
 * @author Torstein Hønsi
 */

Highcharts.theme = {
   colors: ['#50B432', '#ED561B', '#DDDF00', '#24CBE5',  '#6AF9C4'],
   chart: {
      backgroundColor: {
         linearGradient: [0, 0, 500, 500],
         stops: [
            [0, 'rgb(255, 255, 255)'],
            [1, 'rgb(240, 240, 255)']
         ]
      }
,
      borderWidth: 2,
      plotBackgroundColor: 'rgba(255, 255, 255, .9)',
      plotShadow: true,
      plotBorderWidth: 1
   },
   title: {
      style: { 
         color: '#000',
         font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
      }
   },
   subtitle: {
      style: { 
         color: '#666666',
         font: 'bold 12px "Trebuchet MS", Verdana, sans-serif'
      }
   },
   xAxis: {
      gridLineWidth: 1,
      lineColor: '#000',
      tickColor: '#000',
      labels: {
         style: {
            color: '#000',
            font: '11px Trebuchet MS, Verdana, sans-serif'
         }
      },
      title: {
         style: {
            color: '#333',
            fontWeight: 'bold',
            fontSize: '12px',
            fontFamily: 'Trebuchet MS, Verdana, sans-serif'

         }            
      }
   },
   yAxis: {
      minorTickInterval: 'auto',
      lineColor: '#000',
      lineWidth: 1,
      tickWidth: 1,
      tickColor: '#000',
      labels: {
         style: {
            color: '#000',
            font: '11px Trebuchet MS, Verdana, sans-serif'
         }
      },
      title: {
         style: {
            color: '#333',
            fontWeight: 'bold',
            fontSize: '12px',
            fontFamily: 'Trebuchet MS, Verdana, sans-serif'
         }            
      }
   },
   legend: {
      itemStyle: {         
         font: '9pt Trebuchet MS, Verdana, sans-serif',
         color: 'black'

      },
      itemHoverStyle: {
         color: '#039'
      },
      itemHiddenStyle: {
         color: 'gray'
      }
   },
   labels: {
      style: {
         color: '#99b'
      }
   }
};

// Apply the theme
var highchartsOptions = Highcharts.setOptions(Highcharts.theme);

var chart;
$(document).ready(function() {
   chart = new Highcharts.Chart({
      chart: {
         renderTo: 'container',
         zoomType: 'xy'
      },
      title: {
         text: '<fmt:message key="estadistica.asociados" />'
      },
     
      tooltip: {
         formatter: function() {
            var s;
            if (this.point.name) { // the pie chart
               s = ''+
                  this.point.name +': '+ this.y +' ';
            } else {
               s = ''+
                  this.x  +': '+ this.y;
            }
            return s;
         }
      },
      labels: {
         items: [{
            html: 'Actualmente',
            style: {
               left: '40px',
               top: '8px',
               color: 'black'            
            }
         }]
      },
      series: [{
         type: 'pie',
         name: 'Asociados por Trimestre',
         data: [ 
         {name: '<fmt:message key="rama.uno.C"/>',   y: ${datosEstadisticaActual.castores}, color: '#FF9900'},
        {name: '<fmt:message key="rama.uno.E" />',   y: ${datosEstadisticaActual.exploradores},    color: '#24CBE5'},
        {name: '<fmt:message key="rama.uno.M" />',   y: ${datosEstadisticaActual.lobatos},    color: '#FFF263'},
        {name: '<fmt:message key="rama.uno.P" />',   y: ${datosEstadisticaActual.pioneros},    color: '#ED561B'},
        {name: '<fmt:message key="rama.uno.R" />',   y: ${datosEstadisticaActual.companys},    color: '#64E572'},

        {name: '<fmt:message key="estadistica.asociado.tipo.kraal" />', y:${datosEstadisticaActual.scouters}, color: '#6600CC'},
        {name: '<fmt:message key="asociado.tipo.comite"/>', y:${datosEstadisticaActual.comite}, color: '#663333'}
     ],
         center: [70, 70],
         size: 100,
         showInLegend: false,
         dataLabels: {
            enabled: false
         }
      },{
         type: 'spline',
         name: '<fmt:message key="rama.uno.C"/>',
         color: '#FF9900',
         data: (function() {
                // generate an array of random data
                /*
                 * asociadosPorTrimestreYGrupo
                    castoresPorTrimestreYGrupo      
                 */
                
                var data = [],
                i;
                var castoresXTrimYGrup = <c:out value="${castoresPorTrimestreYGrupo}" />;
                
                for (i = 0; i <<c:out value="${castoresPorTrimestreYGrupo}" />.length;i++) {
                    data.push({

                            x: <c:out value="${ejeX}" />[i],
                            y: <c:out value="${castoresPorTrimestreYGrupo}" />[i]
                    });
                }
                return data;
        })()

      }, {
         type: 'spline',
         name: '<fmt:message key="rama.uno.M"/>',
         color: '#FFF263',
          data: (function() {
                // generate an array of random data
                /*
                 * asociadosPorTrimestreYGrupo
                    castoresPorTrimestreYGrupo      
                 */
                
                var data = [],
                i;
                var lobatosXTrimYGrup = <c:out value="${lobatosPorTrimestreYGrupo}" />;
                
                for (i = 0; i <<c:out value="${lobatosPorTrimestreYGrupo}" />.length;i++) {
                    data.push({

                            x: <c:out value="${ejeX}" />[i],
                            y: <c:out value="${lobatosPorTrimestreYGrupo}" />[i]
                    });
                }
                return data;
        })()
      }, {
         type: 'spline',
         name: '<fmt:message key="rama.uno.E"/>',
         color: '#24CBE5',
         data: (function() {
                // generate an array of random data
                /*
                 * asociadosPorTrimestreYGrupo
                    castoresPorTrimestreYGrupo      
                 */
                
                var data = [],
                i;
                var exploradoresXTrimYGrup = <c:out value="${exploradoresPorTrimestreYGrupo}" />;
                
                for (i = 0; i <<c:out value="${exploradoresPorTrimestreYGrupo}" />.length;i++) {
                    data.push({

                            x: <c:out value="${ejeX}" />[i],
                            y: <c:out value="${exploradoresPorTrimestreYGrupo}" />[i]
                    });
                }
                return data;
        })()
      }, {
         type: 'spline',
         color: '#64E572',
         name: '<fmt:message key="rama.uno.R"/>',
         data: (function() {
                // generate an array of random data
                /*
                 * asociadosPorTrimestreYGrupo
                    castoresPorTrimestreYGrupo      
                 */
                
                var data = [],
                i;
                var exploradoresXTrimYGrup = <c:out value="${companysPorTrimestreYGrupo}" />;
                
                for (i = 0; i <<c:out value="${companysPorTrimestreYGrupo}" />.length;i++) {
                    data.push({

                            x: <c:out value="${ejeX}" />[i],
                            y: <c:out value="${companysPorTrimestreYGrupo}" />[i]
                    });
                }
                return data;
        })()
      }, {
         type: 'spline',
         name: '<fmt:message key="asociado.tipo.comite"/>',
          color: '#663333',
          data: (function() {
                // generate an array of random data
                /*
                 * asociadosPorTrimestreYGrupo
                    castoresPorTrimestreYGrupo      
                 */
                
                var data = [],
                i;
                var lobatosXTrimYGrup = <c:out value="${comitePorTrimestreYGrupo}" />;
                
                for (i = 0; i <<c:out value="${comitePorTrimestreYGrupo}" />.length;i++) {
                    data.push({

                            x: <c:out value="${ejeX}" />[i],
                            y: <c:out value="${comitePorTrimestreYGrupo}" />[i]
                    });
                }
                return data;
        })()
      }, {
         type: 'spline',
         name: '<fmt:message key="rama.uno.P"/>',
         color:'#ED561B',
         data: (function() {
                // generate an array of random data
                /*
                 * asociadosPorTrimestreYGrupo
                    castoresPorTrimestreYGrupo      
                 */
                
                var data = [],
                i;
                var exploradoresXTrimYGrup = <c:out value="${pionerosPorTrimestreYGrupo}" />;
                
                for (i = 0; i <<c:out value="${pionerosPorTrimestreYGrupo}" />.length;i++) {
                    data.push({

                            x: <c:out value="${ejeX}" />[i],
                            y: <c:out value="${pionerosPorTrimestreYGrupo}" />[i]
                    });
                }
                return data;
        })()
      }, {
         type: 'spline',
         name: '<fmt:message key="estadistica.asociados"/>',
         color:'#ED5FFB',
         data: (function() {
                // generate an array of random data
                /*
                 * asociadosPorTrimestreYGrupo
                    castoresPorTrimestreYGrupo      
                 */
                
                var data = [],
                i;
                var exploradoresXTrimYGrup = <c:out value="${asociadosPorTrimestreYGrupo}" />;
                
                for (i = 0; i <<c:out value="${asociadosPorTrimestreYGrupo}" />.length;i++) {
                    data.push({

                            x: <c:out value="${ejeX}" />[i],
                            y: <c:out value="${asociadosPorTrimestreYGrupo}" />[i]
                    });
                }
                return data;
        })()
      },{
         type: 'spline',
         name: '<fmt:message key="asociado.tipo.kraal"/>',
         color: '#6600CC',
         data: (function() {
                // generate an array of random data
                /*
                 * asociadosPorTrimestreYGrupo
                    castoresPorTrimestreYGrupo      
                 */
                
                var data = [],
                i;
                var exploradoresXTrimYGrup = <c:out value="${scoutersPorTrimestreYGrupo}" />;
                
                for (i = 0; i <<c:out value="${scoutersPorTrimestreYGrupo}" />.length;i++) {
                    data.push({

                            x: <c:out value="${ejeX}" />[i],
                            y: <c:out value="${scoutersPorTrimestreYGrupo}" />[i]
                    });
                }
                return data;
        })()
      } ]
   });
  
   
});
</script>
</html>
