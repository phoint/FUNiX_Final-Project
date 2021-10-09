<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-header">
	<h2 class="d-inline-flex mr-3">Post</h2>
	<a href="<c:url value="new-post"/>"
		class="btn btn-sm btn-outline-primary mb-3">Add New</a>
</div>
<div class="wrap my-1">
	<div class="d-inline-flex mr-2">
		<form class="form-inline" action="posts" method="post" id="multiselect">
			<select class="form-control form-control-sm mx-1" name="action">
				<option>Bulk actions</option>
				<option value="delete">Delete</option>
			</select>
			<button type="submit" class="btn btn-sm btn-outline-primary mx-1">Submit</button>
		</form>
	</div>
</div>
<div class="table-responsive">
	<table class="table table-striped table-sm">
		<thead>
			<tr>
				<th><input type="checkbox" id="select-all"></th>
				<th>Author</th>
				<th>Comment</th>
				<th>In response to</th>
				<th>Submitted on</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${comments.listResult}" var="comment">
				<tr>
					<td><input type="checkbox" name="id" class="select-item" value="${comment.id}" form="multiselect"></td>
					<td>
						<div>${comment.createdBy}</div>
					</td>
					<td>${comment.comContent}
						<div>
							<a href="<c:url value="comments"/>">Approve</a> <a
								class="ml-2"
								href="<c:url value="comments"/>">Delete</a>
						</div>
					</td>
					<td>${comment.submitTo }</td>
					<td>${comment.createdDate}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="container justify-content-center">
		<nav class="d-block" aria-label="Page navigation">
			<ul class="pagination" id="pagination"></ul>
		</nav>
	</div>
	<form action="<c:url value="posts"/>" id="pagination-info"
		method="get">
		<input type="hidden" name="maxItem" id="maxItem" /> <input
			type="hidden" name="currentPage" id="currentPage" />
	</form>
</div>
