<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="key" required="true" %>
<%@ attribute name="single" type="java.lang.Boolean" required="false" %>
<c:set var="image" value="fmwarn" />
<c:if test="${single}">
  <c:set var="singleCss" value="single"/>
  <c:set var="image" value="fmok" />
</c:if>
<div id="${id}" class="yui-g mp ${singleCss}">
  <div class="mp-hd">
    <img src="<c:url value="/s/theme/img/${image}.png" />" /><span><fmt:message key="${key}" /></span>
  </div>
  <div class="mp-bd">
    <jsp:doBody />
  </div>
</div>