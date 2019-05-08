<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="templates/header.jsp" />

<body>

	<div class="container">

		<h1>Page d erreur</h1>

		<p>${exception.message}</p>

	</div>

	<jsp:include page="templates/footer.jsp" />

</body>
</html>