<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="form-signin">
	<form id="login-form" class="p-4 border bg-white needs-validation" action="<c:url value="login"/>" method="post" novalidate>
		<h1 class="h3 mb-3 text-center">Log In</h1>
		<c:if test="${not empty param.error or not empty error }"><div class="alert alert-danger" role="alert">
        ${param.error} ${error}
    </div></c:if>
		<c:if test="${not empty param.message or not empty message}"><div class="alert alert-success" role="alert">
        ${param.message} ${message}
    </div></c:if>
		<input type="hidden" name="action" value="dologin">
		<input type="hidden" name="from" value="${param.from}">
		<input type="hidden" name="p" value="${param.p}">
		
		<div class="form-group">
			<label for="inputUsername">Username</label> 
			<input type="text" name="username" placeholder="Username"
				id="inputUsername" class="form-control" required autofocus >
				<div class="invalid-feedback">This field cannot be left blank</div>
		</div>
		<div class="form-group">
			<label for="inputPassword">Password</label> 
			<input type="password" name="password" placeholder="Password"
				id="inputPassword" class="form-control" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d_@$!%*#?&\.]{8,}$">
				<div class="invalid-feedback">Password must be at least 8 character, one uppercase and one number</div>
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
			class="d-block mt-2"><a href="/">Back to homepage</a></small>
	</div>
</div>