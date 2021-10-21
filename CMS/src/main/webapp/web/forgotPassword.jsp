<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="form-register">
	<form id="change-pwd" class="p-4 border bg-white needs-validation"
		action="login" method="post" novalidate>
		<h1 class="h3 mb-3 text-center">Forgot password</h1>
		<c:if test="${not empty error }">
			<div class="alert alert-danger" role="alert">${error}</div>
		</c:if>
		<c:if test="${not empty message }">
			<div class="alert alert-success" role="alert">${message}</div>
		</c:if>
		<div class="alert alert-warning" role="alert">Please provide the
			email connected with your account. A New password will be send to
			that mail. You could change the password after log in.</div>
		<input type="hidden" name="action" value="resetPwd">
		<div class="form-group">
			<label for="inputEmail">Email address</label> <input type="email"
				name="userMail" id="email" class="form-control"
				placeholder="Email address" required autofocus>
			<div class="invalid-feedback">This field cannot be left blank</div>
		</div>
		<div class="btn-toolbar justify-content-end">
			<div class="btn-group">
				<button class="btn btn-sm btn-primary" type="submit">Get
					password</button>
			</div>
		</div>
	</form>
</div>