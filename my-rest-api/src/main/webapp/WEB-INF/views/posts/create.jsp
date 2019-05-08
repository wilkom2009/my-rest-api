<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../templates/header.jsp" />

<div class="container">

	<h1>Cr�ation d'un post</h1>
	<br />

	<spring:url value="/posts" var="postActionUrl" />

	<form:form class="form-horizontal" method="post" modelAttribute="create" action="${postActionUrl}">

		<form:hidden path="id" />

		<spring:bind path="title">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Titre</label>
				<div class="col-sm-10">
					<form:input path="title" type="text" class="form-control " id="title" placeholder="Titre du post" />
					<form:errors path="title" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="body">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Contenu texte</label>
				<div class="col-sm-10">
					<form:textarea path="body" rows="5" class="form-control" id="body" placeholder="Contenu du post" />
					<form:errors path="body" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
			<button type="submit" class="btn-lg btn-primary pull-right">Ajouter</button>
			</div>
		</div>
	</form:form>

</div>

<jsp:include page="../templates/footer.jsp" />

</body>
</html>