<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-header">
	<h2 class="d-inline-flex mr-3">Post</h2>
	<a href="<c:url value="NewUser"/>"
		class="btn btn-sm btn-outline-primary mb-3">Add New</a>
	<!-- <div class="alert alert-success" role="alert">${message}</div> -->
</div>
<div class="wrap my-1">
	<div class="d-inline-flex mr-2">
		<form class="form-inline" action="" method="post">
			<select class="form-control form-control-sm mx-1" name="" id="">
				<option>Bulk actions</option>
				<option>Delete</option>
				<option>Edit</option>
			</select>
			<button type="submit" class="btn btn-sm btn-outline-primary mx-1">Submit</button>
		</form>
	</div>
</div>
<div class="table-responsive">
	<table class="table table-striped table-sm">
		<thead>
			<tr>
				<th><input type="checkbox" name="" id=""></th>
				<th>Username</th>
				<th>Name</th>
				<th>Email</th>
				<th>Role</th>
				<th>Posts</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users.listResult}" var="user">
				<tr>
					<td><input type="checkbox" name="" id=""></td>
					<td>
						<div>${user.username}</div>
						<div>
							<a href="<c:url value="EditUser?id=${user.id}"/>">Edit</a> <a
								href="<c:url value="Users?id=${user.id}&action=delete"/>"
								class="ml-2">Delete</a>
						</div>
					</td>
					<td>${user.displayName }</td>
					<td>${user.email}</td>
					<td>${user.role ? "admin" : "user"}</td>
					<td>postCount</td>
				</tr>
			</c:forEach>
			<tr>
				<td><input type="checkbox" name="" id=""></td>
				<td>
					<div>title</div>
					<div>edit</div>
				</td>
				<td>data</td>
				<td>placeholder</td>
				<td>text</td>
				<td>
					<div>status</div>
					<div>date</div>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="container justify-content-center">
		<nav class="d-block" aria-label="Page navigation">
			<ul class="pagination" id="pagination"></ul>
		</nav>
	</div>
	<form action="<c:url value="/PostManagement"/>" id="pagination-info">
		<input type="hidden" name="maxItem" id="maxItem" /> <input
			type="hidden" name="currentPage" id="currentPage" />
	</form>
</div>
