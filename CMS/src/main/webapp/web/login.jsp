<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${pageInfo.title}</title>
  <link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
    integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
    crossorigin="anonymous">
  <link rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link
    href="https://fonts.googleapis.com/css2?family=Barlow:wght@100;300;400;700&
    family=Yeseva+One&family=Goldman&display=swap"
    rel="stylesheet">
<link rel="stylesheet" href="<c:url value="web/css/login.css"/>">

<script
  src="https://cdn.ckeditor.com/ckeditor5/29.2.0/classic/ckeditor.js"></script>
<script src="https://ckeditor.com/apps/ckfinder/3.5.0/ckfinder.js"></script>
</head>
<body>
<div class="form-signin">
	<form class="p-4 border bg-white" action="<c:url value="Login"/>" method="post">
		<h1 class="h3 mb-3 text-center">Log In</h1>
		<c:if test="${not empty loginUser.loginMessage }"><div class="alert alert-danger" role="alert">
        ${loginUser.loginMessage}
    </div></c:if>
		<input type="hidden" name="action" value="dologin">
		<div class="form-group">
			<label for="inputEmail">Email address</label> 
			<input type="text" name="username" placeholder="Username"
				id="inputEmail" class="form-control" required autofocus >
		</div>
		<div class="form-group">
			<label for="inputPassword">Password</label> 
			<input type="password" name="password" placeholder="Password"
				id="inputPassword" class="form-control" required>
		</div>
		<div class="btn-toolbar justify-content-between">
			<div class="form-check form-check-inline">
				<input id="my-input" class="form-check-input" type="checkbox"
					name="" value="true"> <label for="my-input"
					class="form-check-label">Remember me</label>
			</div>
			<div class="btn-group">
				<button class="btn btn-sm btn-primary" type="submit">Sign
					in</button>
			</div>
		</div>
	</form>
	<div class="other-option m-4">
		<small class="d-block"><a href="">Lost your password</a></small> <small
			class="d-block mt-2"><a href="">Back to homepage</a></small>
	</div>
</div>
</body>
</html>