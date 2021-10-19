<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row page-header">
	<h2 class="d-inline-flex mr-3">Post</h2>
	<a href="<c:url value="new-post"/>"
		class="btn btn-sm btn-outline-primary mb-3">Add New</a>
</div>
  <c:if test="${not empty message}"><div id="message" class="alert alert-success">${message}</div></c:if>
  <c:if test="${not empty error}"><div id="error" class="alert alert-danger">${error}</div></c:if>
<div class="wrap row justify-content-between my-1">
	<div class="d-inline-flex mr-2">
		<form class="form-inline" action="posts" method="post"
			id="multiselect" onsubmit="return confirm('Do you really want to delete item(s)?');">
			<select class="form-control form-control-sm mx-1" name="action">
				<option>Bulk actions</option>
				<option value="delete">Delete</option>
			</select>
			<button type="submit" class="btn btn-sm btn-outline-primary mx-1">Submit</button>
		</form>
		<form class="form-inline" action="<c:url value="posts"/>" method="get">
			<select class="form-control form-control-sm mx-1" name="category" id="category-filter">
				<option>Category</option>
				<c:forEach items="${posts.categories}" var="cat">
				<option value="${cat.id}" <c:if test="${cat.id eq category}">selected</c:if>>${cat.name}</option>
				</c:forEach>
			</select> 
			<!--<select class="form-control form-control-sm mx-1" name="" id="">
				<option>All Date</option>
				<option>Delete</option>
				<option>Edit</option>
			</select> <select class="form-control form-control-sm mx-1" name="" id="">
				<option>All Date</option>
				<option>Delete</option>
				<option>Edit</option>
			</select> -->
			<button type="submit" class="btn btn-sm btn-outline-primary mx-1">Submit</button>
		</form> 
	</div>  
	<div class="d-inline-flex mr-2">
		<form class="form-inline" action="posts" method="get">
			<input class="form-control form-control-sm" type="text" name="searchKey"
				value="${searchKey}" placeholder="Search...">
			<button type="submit" class="btn btn-sm btn-outline-primary mx-1">Search</button>
		</form>
	</div>
</div>
<div class="row table-responsive">
	<table class="table table-striped table-sm">
		<thead>
			<tr>
				<th><input type="checkbox" id="select-all"></th>
				<th>Title</th>
				<th>Author</th>
				<th>Categories</th>
				<th>Date</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${posts.listResult}" var="post">
				<tr>
					<td><input type="checkbox" name="id" class="select-item"
						value="${post.id}" form="multiselect"></td>
					<td>
						<div>${post.title}</div>
						<div>
							<a href="<c:url value="edit-post?id=${post.id}"/>">Edit</a> <a
								class="ml-2" onclick="return confirm('Are you sure?');"
								href="<c:url value="posts?id=${post.id}&action=delete"/>">Delete</a>
						</div>
					</td>
					<td>${post.author.username}</td>
					<td><c:forEach items="${post.categories}" var="category">
							<c:if test="${category.used}">
								<span>${category.name}</span>
							</c:if>
						</c:forEach></td>
					<td>${post.publishDate}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="container">
		<div class="row justify-content-center">
			<nav class="d-block" aria-label="Page navigation">
				<ul class="pagination" id="pagination"></ul>
			</nav>
		</div>
	</div>
	<div class="d-flex flex-row-reverse">
	   <a onclick="return confirm('Are you sure? Any change can not be undone');" href="<c:url value="posts?action=deleteAll"/>">Delete All</a>
	</div>
	<form action="<c:url value="posts"/>" id="pagination-info" method="get">
		<input class="form-control form-control-sm" type="hidden" name="searchKey"
			value="${searchKey}"> <input type="hidden" name="maxItem"
			id="maxItem" /> <input type="hidden" name="currentPage"
			id="currentPage" />
	</form>
</div>
