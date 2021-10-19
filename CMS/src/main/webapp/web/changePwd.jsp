<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="form-register">
	<form id="change-pwd" action="password-change"
		class="p-4 border bg-white needs-validation" method="post" novalidate>
		<h1 class="h3 mb-3 text-center">Change password</h1>
		<c:if test="${not empty error }">
			<div class="alert alert-danger" role="alert">${error}</div>
		</c:if>
		<c:if test="${not empty message }">
			<div class="alert alert-success" role="alert">${message}</div>
		</c:if>
		<div class="form-group">
			<label for="inputEmail">Current password</label> <input
				name="currPassword" type="password" id="currPwd"
				class="form-control" placeholder="Current password" required
				autofocus>
			<div class="invalid-feedback">This field cannot be left blank</div>
		</div>
		<div class="form-group">
			<label for="inputEmail">New password</label> <input name="password"
				type="password" id="newPwd" class="form-control"
				placeholder="New password" required>
			<div class="invalid-feedback">This field cannot be left blank</div>
		</div>
		<div class="form-group">
			<label for="inputEmail">Password re-type</label> <input
				name="confirmPassword" type="password" id="newPwd-retype"
				class="form-control" placeholder="Password re-type" required>
			<div class="invalid-feedback">Password re-type must be the same
				with new password</div>
		</div>
		<div class="btn-toolbar justify-content-end">
			<div class="btn-group">
				<button class="btn btn-sm btn-primary" type="submit">Update</button>
			</div>
		</div>
	</form>
</div>