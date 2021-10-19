<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="form-register">
	<form id="change-pwd" class="p-4 border bg-white needs-validation"
		novalidate>
		<h1 class="h3 mb-3 text-center">Change password</h1>
		<div class="alert alert-warning" role="alert">Content</div>
		<div class="form-group">
			<label for="inputEmail">Current password</label> <input
				type="password" id="currPwd" class="form-control"
				placeholder="Current password" required autofocus>
			<div class="invalid-feedback">This field cannot be left blank</div>
		</div>
		<div class="form-group">
			<label for="inputEmail">New password</label> <input type="password"
				id="newPwd" class="form-control" placeholder="New password" required>
			<div class="invalid-feedback">This field cannot be left blank</div>
		</div>
		<div class="form-group">
			<label for="inputEmail">Password re-type</label> <input
				type="password" id="newPwd-retype" class="form-control"
				placeholder="Password re-type" required>
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