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
</style>
</head>
<body>
<div id="doc" class="yui-t7">
<div id="hd"></div>
<div id="bd">
  <div class="yui-g">
    <div class="yui-g first">
        <img src="<c:url value="/s/theme/img/db-logo.png" />" alt="cudu" />
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
        </div>
      </form>
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
</body>
</html>