<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-header">
	<h2 class="d-inline-flex mr-3">Edit User</h2>
	<!-- <a href="" class="btn btn-sm btn-outline-primary mb-3">Add New</a> -->
	<p>${message}</p>
</div>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-8 col-lg-6">
			<form action="<c:url value="EditUser"/>" method="post">
        <input type="hidden" name="id" value="${user.id}">
				<div class="form-group row">
					<label for="username"
						class="col-4 col-form-label col-form-label-sm">Username</label>
					<div class="col-8">
						<input class="form-control form-control-sm" type="text"
							name="username" id="username" value="${user.username}">
					</div>
				</div>
				<div class="form-group row">
					<label for="email" class="col-4 col-form-label">Email</label>
					<div class="col-8">
						<input class="form-control form-control-sm" type="email"
							name="email" id="email" value="${user.email}">
					</div>
				</div>
				<div class="form-group row">
					<label for="displayName" class="col-4 col-form-label">Display
						Name</label>
					<div class="col-8">
						<input class="form-control form-control-sm" type="text"
							name="displayName" id="displayName" value="${user.displayName}">
					</div>
				</div>
				<div class="form-group row">
					<label for="password" class="col-4 col-form-label">Password</label>
					<div class="col-8">
						<input class="form-control form-control-sm" type="password"
							name="password" id="password" value="${user.password}">
					</div>
				</div>
				<div class="form-group row">
					<label for="password" class="col-4 col-form-label">Role</label>
					<div class="col-auto">
						<select id="my-select" class="form-control form-control-sm"
							name="role">
							<option value="true" ${user.role ? "selected" : ""}>Admin</option>
							<option value="false" ${not user.role ? "selected" : ""}>User</option>
						</select>
					</div>
				</div>
				<div class="d-flex flex-row-reverse">
					<input class="btn btn-primary" type="submit" name="submit"
						value="Update">
				</div>
			</form>
		</div>
	</div>
</div>