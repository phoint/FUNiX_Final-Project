<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
	<div class="form-signin">
		<form id="login-form" class="p-4 border bg-white needs-validation"
			action="<c:url value="register"/>" method="post" novalidate>
			<h1 class="h3 mb-3 text-center">Register</h1>
			<c:if test="${not empty loginUser.loginMessage }">
				<div class="alert alert-danger" role="alert">
					${loginUser.loginMessage}</div>
			</c:if>
			<div class="form-group">
				<label for="inputUsername">Username</label> <input type="text"
					name="username" placeholder="Username" id="inputUsername"
					class="form-control" required autofocus>
				<div class="invalid-feedback">Please choose a username</div>
			</div>
			<div class="form-group">
				<label for="inputEmail">Email address</label> <input type="email"
					name="email" placeholder="Email address" id="inputMail"
					class="form-control" required>
				<div class="invalid-feedback">Please provide a valid email</div>
			</div>
			<div class="form-group">
				<label for="inputName">Display name</label> <input type="text"
					name="displayName" placeholder="Display name" id="inputName"
					class="form-control">
			</div>
			<div class="btn-toolbar justify-content-end">
				<div class="btn-group">
					<button class="btn btn-sm btn-primary" type="submit">Register</button>
				</div>
			</div>
			<div class="mt-3">
				<div id="g_id_onload"
					data-client_id="486895749872-mg1uv9uvfe7kvmff3si6mna3dcq57ca0.apps.googleusercontent.com"
					data-context="signup" data-ux_mode="popup"
					data-login_uri="http://localhost:8080/CMS/register" data-nonce=""
					data-auto_prompt="false"></div>

				<div class="g_id_signin" data-type="standard"
					data-shape="rectangular" data-theme="outline"
					data-text="$ {button.text}" data-size="medium" data-locale="en-US"
					data-logo_alignment="left"></div>
			</div>
		</form>
	</div>