<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../templates/header.jsp" />

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

	<spring:url value="/posts/${post.id}/comments/add" var="addUrl" />

	<h1>Titre : ${post.title}</h1>
	<br />

	<div class="row">
		<div class="col-sm-10">${post.body}</div>
	</div>

	<br />

	<div class="row">
		<label class="col-sm-2">Créé le : </label>
		<div class="col-sm-4">${post.createdAt}</div>
		<label class="col-sm-2">Modifié le : </label>
		<div class="col-sm-4">${post.updatedAt}</div>
	</div>

	<br />
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<button class="btn-primary pull-right"
				onclick="location.href='${addUrl}'">Ajouter un commentaire</button>
		</div>
	</div>
	<br />
	<h2>Commentaires postés</h2>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>#ID</th>
				<th>Commentaire</th>
				<th>Crée le</th>
				<th>Modifié le</th>
				<th>Actions</th>
			</tr>
		</thead>

		<c:forEach var="comment" items="${commentsList}">
			<tr>
				<td>${comment.id}</td>
				<td>${comment.body}</td>
				<td>${comment.createdAt}</td>
				<td>${comment.updatedAt}</td>
				<td><spring:url
						value="/posts/${post.id}/comments/${comment.id}/delete"
						var="deleteUrl" /> <spring:url
						value="/posts/${post.id}/comments/${comment.id}/update"
						var="updateUrl" />

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