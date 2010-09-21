<%@include file="taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="app.title" /></title>
<style type="text/css">
body { font-family: 'Gilsans', Calibri, Arial; font-size: 10pt; }
tr.even { background-color: #DEDEDE; }
th { background-color: #CCC; }
td,th { padding: 3px 5px; }
</style>
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
<script type="text/javascript">
$(document).ready(function() {
	window.print();
});
</script>
</body>
</html>