<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../templates/header.jsp" />

<body>

	<div class="container">

		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>

		<h1>Liste des posts publiés</h1>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>#ID</th>
					<th>Titre</th>
					<th>Actions</th>
				</tr>
			</thead>

			<c:forEach var="mypost" items="${posts}">
				<tr>
					<td>${mypost.id}</td>
					<td>${mypost.title}</td>
					<td><spring:url value="/posts/${mypost.id}" var="readUrl" />
						<spring:url value="/posts/${mypost.id}/delete" var="deleteUrl" />
						<spring:url value="/posts/${mypost.id}/update" var="updateUrl" />

						<button class="btn btn-info" onclick="location.href='${readUrl}'">Détail</button>
						<button class="btn btn-primary"
							onclick="location.href='${updateUrl}'">Modifier</button>
						<button class="btn btn-danger"
							onclick="this.disabled=true;post('${deleteUrl}')">Supprimer</button></td>
				</tr>
			</c:forEach>
		</table>

	</div>

	<jsp:include page="../templates/footer.jsp" />

</body>
</html>