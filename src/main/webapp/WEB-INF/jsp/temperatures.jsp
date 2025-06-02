<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
<jsp:attribute name="content">

	<nav>
		<ul>
			<c:forEach items="${descriptions}" var="desc">
				<li><a href="<spring:url value="/temperatures/${desc}"/>">${desc.name}</a></li>
			</c:forEach>
		</ul>
	</nav>


	<canvas data-title="${description.name}" data-captor="<spring:url value="/chart/${description}"/>"></canvas>


</jsp:attribute>
</t:template>
