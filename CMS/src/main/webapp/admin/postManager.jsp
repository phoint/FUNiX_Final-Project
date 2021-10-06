<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-header">
	<h2 class="d-inline-flex mr-3">Post</h2>
	<a href="<c:url value="NewPost"/>"
		class="btn btn-sm btn-outline-primary mb-3">Add New</a>
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
	<div class="d-inline-flex">
		<form class="form-inline" action="" method="post">
			<select class="form-control form-control-sm mx-1" name="" id="">
				<option>All Date</option>
				<option>Delete</option>
				<option>Edit</option>
			</select> <select class="form-control form-control-sm mx-1" name="" id="">
				<option>All Date</option>
				<option>Delete</option>
				<option>Edit</option>
			</select> <select class="form-control form-control-sm mx-1" name="" id="">
				<option>All Date</option>
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
				<th>Title</th>
				<th>Author</th>
				<th>Categories</th>
				<th>Tag</th>
				<th>Date</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${posts.listResult}" var="post">
				<tr>
					<td><input type="checkbox" name="" id=""></td>
					<td>
						<div>${post.title}</div>
						<div>
							<a href="<c:url value="Edit?id=${post.id}"/>">Edit</a> <a
								class="ml-2"
								href="<c:url value="admin-posts?id=${post.id}&action=delete"/>">Delete</a>
						</div>
					</td>
					<td>${post.author.username}</td>
					<td><c:forEach items="${post.categories}" var="category">
							<c:if test="${category.used}">
								<span>${category.name}</span>
							</c:if>
						</c:forEach></td>
					<td>text</td>
					<td>${post.publishDate}</td>
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
	<form action="<c:url value="/admin-posts"/>" id="pagination-info"
		method="get">
		<input type="hidden" name="maxItem" id="maxItem" /> <input
			type="hidden" name="currentPage" id="currentPage" />
	</form>
</div>