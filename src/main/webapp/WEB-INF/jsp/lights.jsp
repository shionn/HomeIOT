<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
<jsp:attribute name="content">


<table>
	<thead>
		<tr>
			<th>#</th>
			<th>Light</th>
			<th>Couleur</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${lights}" var="l">
			<spring:url value="/lights/" var="url"/>
			<tr>
				<td>
					<c:if test="${l.on}" ><a href="${url}${l.state.id}/off" style="color:green">⏻</a></c:if>
					<c:if test="${not l.on}" ><a href="${url}${l.state.id}/on" style="color:red">⏻</a></c:if>
				</td>
				<td>${l.name}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>


</jsp:attribute>
</t:template>
