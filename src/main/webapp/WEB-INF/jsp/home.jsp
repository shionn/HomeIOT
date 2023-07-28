<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta name="mobile-web-app-capable" content="yes" />
<title>HomeIOT</title>
</head>
<body>
${captor.lastValue} ${captor.lastValueDate}
<canvas data-captor="101"></canvas>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="<spring:url value="js/scripts.js"/>"></script>
<script src="<spring:url value="js/autochart.js"/>"></script>
</body>
</html>