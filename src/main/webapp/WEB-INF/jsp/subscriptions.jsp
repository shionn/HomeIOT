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
			<th>Nom</th>
			<th>Last Value</th>
			<th>Sub</th>
			<th>#</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${subscriptions}" var="sub">
			<tr>
				<td>${sub.captor.name}</td>
				<td>${sub.captor.lastValue} ${sub.captor.unit.symbol}</td>
				<td>${sub.host} <em>${sub.hostname}</em></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

</jsp:attribute>
</t:template>
