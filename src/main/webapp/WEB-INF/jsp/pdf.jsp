<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="app.title" /></title>
<style type="text/css">
body { font-family: 'Gilsans', Calibri, Arial; font-size: 10pt; }
tr.even { background-color: #DEDEDE; }
th { background-color: #CCC; }
td,th { padding: 3px 5px; }
</style>



<script language="javascript">
function guardarcomo()
{
    var WebBrowser = '<OBJECT ID="WebBrowser1" WIDTH=0 HEIGHT=0 CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>';
    document.body.insertAdjacentHTML('beforeEnd', WebBrowser);
    WebBrowser.ExecWB(4, 0);
    WebBrowser.outerHTML = "";
}
</script>
</head>
<body>

<div style="border: 1px solid #CCC; padding: 5px; padding-top: 6px; margin-bottom: 2px; height: 15px">
<img src="<c:url value="/s/theme/favicon.png" />" style="float:left; margin-right: 5px" />
<div style="float:left;"><strong>Cudú</strong></div>
<div style="float:right"><c:out value="${userStamp}" /> - <c:out value="${timestamp}" /> - Total: <c:out value="${total}" /></div>
</div>
<table border="1" style="border-collapse: collapse; border: 1px solid #333; width: 100%">
<thead>
	<tr>
		<c:forEach var="columna" items="${columnas}">
		<th><spring:message code="listados.c.${columna}" text="${columna}" /></th>
		</c:forEach>
	</tr>
</thead>
<c:forEach var="asociado" items="${asociados}" varStatus="status">
<tbody>
<c:choose>
	<c:when test="${status.count % 2 == 0}"><tr class="even"></c:when>
	<c:otherwise><tr></c:otherwise>
</c:choose>
	<%--<c:forEach items="${columnas}" varStatus="status">
		<td><c:out value="${asociado[status.count-1]}" /> </td>
	</c:forEach>--%>
	<c:forEach var="i" begin="0" end="${numeroColumnas-1}">
		<td><spring:escapeBody><c:out value="${asociado[i]}" /></spring:escapeBody></td>
	</c:forEach>
</tr>
</c:forEach>
</tbody>
</table>
<script src="<c:url value="/s/jquery/jquery-1.4.2.js" />" type="text/javascript"></script>
<%

    String[] pdf = { "wkhtmltopdf http://www.google.com /home/cudu/Escritorio/listado.pdf" };

    Runtime.getRuntime().exec("wkhtmltopdf http://www.google.com  /home/cudu/proyectoCudu/cudu_pruebas/src/main/webapp/filesClient/listado.pdf" );
%>



<p>Archivo PDF para descargar: <a href="<c:url value="s/filesClient/listado.pdf"/>" >pdf</a></p>

Oprime el botón para guardar esta página...<BR>
<input type="submit" value="  Guardar  " onClick=guardarcomo() name="miboton">
<a href="archivo"> </a>

</body>
</html>