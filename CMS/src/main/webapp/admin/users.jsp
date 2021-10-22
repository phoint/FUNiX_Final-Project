<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-header">
	<h2 class="d-inline-flex mr-3">Post</h2>
	<a href="<c:url value="new-user"/>"
		class="btn btn-sm btn-outline-primary mb-3">Add New</a>
	<!-- <div class="alert alert-success" role="alert">${message}</div> -->
</div>
  <c:if test="${not empty message}"><div id="message" class="alert alert-success">${message}</div></c:if>
  <c:if test="${not empty error}"><div id="error" class="alert alert-danger">${error}</div></c:if>
<div class="wrap my-1">
	<div class="d-inline-flex mr-2">
		<form class="form-inline" action="users" method="post"
      id="multiselect" onsubmit="return confirm('Do you really want to delete item(s)?');">
      <select class="form-control form-control-sm mx-1" name="action">
        <option>Bulk actions</option>
        <option value="delete">Delete</option>
      </select>
      <button type="submit" class="btn btn-sm btn-outline-primary mx-1">Submit</button>
    </form>
	</div>
	<div class="d-inline-flex mr-2">
    <form class="form-inline" action="users" method="get">
      <input class="form-control form-control-sm" type="text" name="searchKey"
        value="${searchKey}" placeholder="Search...">
      <button type="submit" class="btn btn-sm btn-outline-primary mx-1">Search</button>
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
					<td>
				    <c:if test="${user.id != 1}"><input type="checkbox" name="id" class="select-item"
            value="${user.id}" form="multiselect"></c:if>
          </td>
					<td>
						<div>${user.username}</div>
						<div>
							<a href="<c:url value="edit-user?id=${user.id}"/>">Edit</a> 
							<c:if test="${user.id != 1}"><a href="<c:url value="users?id=${user.id}&action=delete"/>"
							   class="ml-2" onclick="return confirm('Are you sure?');">Delete
              </a></c:if>
						</div>
					</td>
					<td>${user.displayName }</td>
					<td>${user.email}</td>
					<td>${user.role ? "admin" : "user"}</td>
					<td>${user.totalPost}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="container justify-content-center">
		<nav class="d-block" aria-label="Page navigation">
			<ul class="pagination" id="pagination"></ul>
		</nav>
	</div>
	<form action="<c:url value="users"/>" id="pagination-info">
    <input type="hidden" name="searchKey" value="${searchKey}"> 
    <input type="hidden" name="maxItem" id="maxItem" /> 
    <input type="hidden" name="currentPage" id="currentPage" />
	</form>
</div>
