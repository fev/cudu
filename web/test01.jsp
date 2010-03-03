<%@include file="WEB-INF/jsp/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cudú</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/yui/reset-fonts-grids/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/yui/base/base-min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/theme/cudu.css" />" />
</head>
<body>
<div id="doc" class="yui-t7">
<div id="bd">
  <div class="yui-g tc-tb">
	<a href="#">
		<img src="<c:url value="/theme/img/tango/document-new.png" />" />
		<span>Nuevo</span>
	</a>
	<a href="#">
		<img src="<c:url value="/theme/img/tango/edit-find.png" />" />
		<span>Filtrar</span>
	</a>
	<a href="#" class="save">
		<img src="<c:url value="/theme/img/tango/document-save.png" />" />
		<span>Guardar</span>
	</a>
	<a href="#" class="delete">
		<img src="<c:url value="/theme/img/tango/edit-delete.png" />" />
		<span>Eliminar</span>
	</a>
	<a href="#">
		<img src="<c:url value="/theme/img/tango/edit-undo.png" />" />
		<span>Volver</span>
	</a>
  </div>
</div>
</div>
</body>
</html>