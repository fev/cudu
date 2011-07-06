<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><fmt:message key="app.title" /></title>
<link rel="icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link rel="shortcut icon" href="<c:url value="/s/theme/favicon.ico" />" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/reset-fonts-grids/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/s/yui/base/base-min.css" />" />
<%-- Prefetching --%>
<link rel="prefetch" href="<c:url value="/s/cdn/yui-lst.js" />" />
<link rel="prefetch" href="<c:url value="/s/theme/img/bg-bd.png" />" />
<link rel="prefetch" href="<c:url value="/s/theme/img/bg-hd.png" />" />
<link rel="prefetch" href="<c:url value="/s/theme/img/bg-ft.png" />" />
<style type="text/css">
html { background-color: transparent }
body { color: #333; background: #9bdbf7 url('<c:url value="/s/theme/img/bg.jpg" />') repeat-x 0 0;
       font-family:'Trebuchet MS',Arial,Helvetica,sans-serif; font-size:85%; }
div#hd { height: 120px; }
label { display:block; font-size:108%; color:#444; font-weight:bold; margin-bottom:3px; padding-left:3px; }
#j_username, #j_password { width:346px; height:26px; border: 1px solid #009ace; color:#555555;
font-family:'Trebuchet MS',Arial,Helvetica,sans-serif; font-size:116%; font-weight:bold;
padding-left:5px; padding-top:4px; }
form div { margin-bottom: 15px; }
input.button { width: 76px; height: 34px; padding: 0px 4px; cursor: pointer; -moz-border-radius: 4px; 
    -webkit-border-radius: 3px; border: 1px solid #B9E5FF; color: #333; background-color: #FFF; }
input.button.save { padding-left:30px; padding-right:8px; font-family:'Trebuchet MS',Arial,Helvetica,sans-serif;
    background: transparent url('<c:url value="/s/theme/img/tango/tick32.png" />') no-repeat 5px 2px; width: 100px }
input.button.save:hover { background-color: #E3FFE3; color: #64992C }

div.ht { display: block; text-decoration: none; border-bottom: 0px solid #EEE; height: 70px;
    -moz-border-radius: 8px; -webkit-border-radius: 8px; }
</style>
</head>
<body>
<div id="doc" class="yui-t7">
<div id="hd"></div>
<div id="bd">
  <div class="yui-g">
    <div class="yui-g first">
        <img src="<c:url value="/s/theme/img/db-logo.png" />" alt="cudu" />
        <!-- 3. Add the container -->
<!-- ;style="width: 380px; height: auto; background: WHITE; margin: 0 " -->

            <div style="width: 300px; height: auto; background: WHITE; margin: 0; display: block;
                 border-bottom: 0px solid #EEE; height: 70px;    -moz-border-radius: 8px; -webkit-border-radius: 8px;">
                <label for="j_username">  <fmt:message key="estadistica.grupos" /> : ${datosEstadistica.grupos}</label>
                <label for="j_username">  <fmt:message key="estadistica.voluntarios" /> : ${datosEstadistica.voluntarios}</label>
                <label for="j_username">  <fmt:message key="estadistica.asociados" /> :  ${datosEstadistica.asociados}</label>

                <div id="chartContainer" style="width: 300px; height: 400px; margin: 0 auto">
                </div>
        </div>

    </div>
    <div class="yui-g" style="background: transparent url('<c:url value="/s/theme/img/onepix40.png" />;')">
      <div style="margin: 15px;">
      <form name="f" action="j_spring_security_check" method="post">
        <div>
          <label for="j_username"><fmt:message key="login.f.usuario" /></label>
          <input type="text" name="j_username" id="j_username" />        
        </div>
        <div>
          <label for="j_password"><fmt:message key="login.f.password" /></label>
          <input type="password" name="j_password" id="j_password"/>
        </div>
        <div style="text-align: right">
          <%--<input type='checkbox' name='_spring_security_remember_me'/> Recordar contraseña.<br />--%>
          <input type="submit" class="button save" value="<fmt:message key="btn.login" />" />
          <div>
              <a href="<c:url value="recuperarpassword" />">
                  <fmt:message key="login.f.olvidocontrasenya"/>
            </a>
              
          </div>
        </div> 
      </form>
        <div>
            <!--<a href="preferencias"><fmt:message key="dashboard.preferencias" /></a> - -->
            <a href="<c:url value="/login?hl=es" />">Castellano</a>,
            <a href="<c:url value="/login?hl=ca" />">Valencià</a>
          </div>
          
      </div>
    </div>
  </div>
  <%--<div class="yui-g" style="margin-top: 10px">
    <div class="yui-g first"></div>
    <div class="yui-g" style="background: transparent url('<c:url value="/theme/img/onepix40.png" />;')">
      <a href="#">No recuerdo mi contraseña</a>
    </div>
  </div>--%>
</div>
</div>
<script type="text/javascript">
document.getElementById('j_username').focus();
</script>

<script type="text/javascript" src="<c:url value="/s/jquery/jquery-1.4.2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/s/scripts/estadistica/highcharts.js"/>"></script>

<!-- 1a) Optional: add a theme file -->
<!--
        <script type="text/javascript" src="../js/themes/gray.js"></script>
-->

<!-- 1b) Optional: the exporting module -->
<!-- <script type="text/javascript" src="<c:url value="/s/scripts/estadistica/modules/exporting.js"/>"></script> -->


<!-- 2. Add the JavaScript to initialize the chart on document ready -->
<script type="text/javascript">

  		var chart;
			$(document).ready(function() {
				chart = new Highcharts.Chart({
					chart: {
						renderTo: 'chartContainer',
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false
					},
					title: {
						text: '<fmt:message key="estadistica.asociados" />'
					},
					tooltip: {
						formatter: function() {
							var percent = parseInt(this.y/${datosEstadistica.asociados} *100);
                                                        return '<b>'+ this.point.name +'</b>: '+ this.y + '  ('+percent +'%)';
						}
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: false
							},
							showInLegend: true
						}
					},
				    series: [{
						type: 'pie',
						name: 'Browser share',
						data: [
							                              {name: '<fmt:message key="rama.uno.C" />',   y: ${datosEstadistica.castores}, color: '#FF9900'},
                                    {name: '<fmt:message key="rama.uno.E" />',   y: ${datosEstadistica.exploradores},    color: '#0081CC'},
                                    {name: '<fmt:message key="rama.uno.M" />',   y: ${datosEstadistica.lobatos},    color: '#FFFF00'},
                                    {name: '<fmt:message key="rama.uno.P" />',   y: ${datosEstadistica.pioneros},    color: '#CC3333'},
                                    {name: '<fmt:message key="rama.uno.R" />',   y: ${datosEstadistica.companys},    color: '#009933'},

                                    {name: '<fmt:message key="estadistica.asociado.tipo.kraal" />', y:${datosEstadistica.scouters}, color: '#6600CC'},
                                    {name: '<fmt:message key="asociado.tipo.comite" />', y:${datosEstadistica.comite}, color: '#663333'}
						]
					}]
				});
			});
				               
</script>
<!-- YUI Prefetching ...
<script type="text/javascript" src="<c:url value="/s/cdn/yui-lst.js" />"></script>
<script src="http://yui.yahooapis.com/3.2.0/build/yui/yui-min.js"></script>
 -->
</body>
</html>