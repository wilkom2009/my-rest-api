<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
<title>My REST api for blog app</title>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>

<spring:url value="/" var="urlHome" />
<spring:url value="/posts/create" var="urlAddUser" />

<nav class="navbar navbar-inverse ">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${urlHome}">My Blog</a>
		</div>
		<div id="navbar">
			<ul class="nav navbar-nav">
				<li class="active"><a href="${urlAddUser}">Creer post</a></li>
			</ul>
		</div>
	</div>
</nav>