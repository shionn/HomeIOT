<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ attribute name="content" fragment="true"%>
<%@ attribute name="scripts" fragment="true"%>
<!DOCTYPE html>
<html color-mode="user">
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta name="mobile-web-app-capable" content="yes" />
<title>Painter Tracker</title>
<link rel="stylesheet" href="<spring:url value="/css/mvp.css"/>" />
<link rel="stylesheet" href="<spring:url value="/css/font-awesome.css"/>" />
<link rel="stylesheet" href="<spring:url value="/css/style.css"/>" />
<link rel="icon" href="<spring:url value="/img/favicon.gif"/>" type="image/x-icon">
<link rel="shortcut icon" href="<spring:url value="/img/favicon.gif"/>" type="image/x-icon"> </head>
</head>
<body>
	<header>
		<nav>
			<a href="<spring:url value="/"/>"><img src="<spring:url value="/img/favicon.gif"/>"/></a>
			<ul>
				<li><a href="<spring:url value="/temperatures"/>">Températures</a></li>
			</ul>
		</nav>
	</header>
	<main>
		<jsp:invoke fragment="content" />
	</main>
	<footer>
		<hr>
		<p>Home IOT by <a href="mailto:shionn@gmail.com">shionn</a></p>
	</footer>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<script src="<spring:url value="/js/scripts.js"/>"></script>
	<script src="<spring:url value="/js/autochart.js"/>"></script>
	<jsp:invoke fragment="scripts" />
</body>
</html>
