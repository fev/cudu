<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<h2>Spring MVC</h2>

<h3>Revenue Report</h3>


<table border="1" style="border-collapse: collapse; border: 1px solid #333; width: 100%">


		<c:forEach var="columna" items="${columnas}">
		<th><spring:message code="listados.c.${columna}" text="${columna}" /></th>
		</c:forEach>


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

</body>
</html>