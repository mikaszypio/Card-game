<!DOCTYPE html>
<html>
	<head>
		<title>Dummy checking</title>
		<link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="/main.css" rel="stylesheet">
		<script src="/webjars/jquery/jquery.min.js"></script>
	</head>
	<body>
		<noscript>
			<h2 style="color: #ff0000">Enable Javascript and reload this page!</h2>
		</noscript>
		Hello <%= session.getAttribute("userId") %>
	</body>
</html>
