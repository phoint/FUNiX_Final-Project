<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-header">
	<h2 class="d-inline-flex mr-3">Subcribers</h2>
	<!-- <div class="alert alert-success" role="alert">${message}</div> -->
</div>
<div class="container-fluid">
	<div class="row">
		<c:if test="${not empty message}">
			<div id="message" class="alert alert-success">${message}</div>
		</c:if>
		<c:if test="${not empty error}">
			<div id="error" class="alert alert-danger">${error}</div>
		</c:if>
	</div>
	<div class="row">
		<div class="col-lg-4">
			<form method="post" action="subcribers">
				<div class="form-group row">
					<label class="col-lg-3 col-form-label" for="name">Username</label>
					<div class="col-lg-9">
						<input id="name" class="form-control" type="text" name="username"
							value="${subcribers.username}"
							<c:if test="${not empty param.id}">readonly</c:if>> <small
							id="nameHelpBlock" class="form-text text-muted"> The name
							is using for login.</small>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-lg-3 col-form-label" for="displayName">Name</label>
					<div class="col-lg-9">
						<input id="displayName" class="form-control" type="text"
							name="displayName" value="${subcribers.displayName}"> <small
							id="slugHelpBlock" class="form-text text-muted"> The name
							is how it appears on your site</small>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-lg-3 col-form-label" for="email">Email</label>
					<div class="col-lg-9">
						<input id="email" class="form-control" type="text" name="email"
							value="${subcribers.email}" <c:if test="${subcribers.registeredFrom ne 'form'}">readonly</c:if>> <small id="slugHelpBlock"
							class="form-text text-muted"> The email for receiving
							weekly/ monthly news</small>
					</div>
				</div>
				<c:if test="${not empty param.action && param.action eq 'edit'}">
				  <input type="hidden" name="action" value="edit">
				  <input type="hidden" name="id" value="${param.id}">
				  <c:if test="${subcribers.registeredFrom eq 'form'}">	  
					<div class="form-group row">
						<label for="password" class="col-lg-3 col-form-label">Password</label>
						<div class="col-lg-9">
							<input type="submit"
								formaction="<c:url value="subcribers?action=resetPwd"/>"
								value="reset">
						</div>
					</div>
					</c:if>
				</c:if>
				<div class="d-flex flex-row-reverse align-items-center">
					<button class="btn btn-primary" type="submit">${not empty param.action && param.action eq 'edit' ? 'Update' : 'Add New'}</button>
					<div class="mx-3">
						<a class="d-block" href="<c:url value="subcribers"/>">Cancel</a>
					</div>
				</div>
			</form>
		</div>
		<div class="col-lg-8 mt-5 mt-lg-0">
			<div class="wrap my-1">
				<div class="d-inline-flex mr-2">
					<form class="form-inline" action="users" method="post"
						id="multiselect"
						onsubmit="return confirm('Do you really want to delete item(s)?');">
						<select class="form-control form-control-sm mx-1" name="action">
							<option>Bulk actions</option>
							<option value="delete">Delete</option>
						</select>
						<button type="submit" class="btn btn-sm btn-outline-primary mx-1">Submit</button>
					</form>
				</div>
				<div class="d-inline-flex mr-2">
					<form class="form-inline" action="users" method="get">
						<input class="form-control form-control-sm" type="text"
							name="searchKey" value="${searchKey}" placeholder="Search...">
						<c:if test="${empty searchKey}">
							<button type="submit" class="btn btn-sm btn-outline-primary mx-1">Search</button>
						</c:if>
					</form>
					<c:if test="${not empty searchKey}">
						<a href="<c:url value="users"/>" role="button"
							class="btn btn-sm btn-outline-primary mx-1">Clear</a>
					</c:if>
				</div>
			</div>
			<div class="table-responsive">
				<table class="table table-striped table-sm">
					<thead>
						<tr>
							<th><input type="checkbox" id="select-all"></th>
							<th>Username</th>
							<th>Name</th>
							<th>Email</th>
							<th>From</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${subcribers.listResult}" var="subcriber">
							<tr>
								<td><input type="checkbox" name="id" class="select-item"
									value="${subcriber.id}" form="multiselect"></td>
								<td>
									<div>${subcriber.username}</div>
									<div>
										<a href="<c:url value="?action=edit&id=${subcriber.id}"/>">Edit</a>
										<a href="<c:url value="?action=delete&id=${subcriber.id}"/>"
											class="ml-2" onclick="return confirm('Are you sure?');">Delete
										</a>
									</div>
								</td>
								<td>${subcriber.displayName}</td>
								<td>${subcriber.email}</td>
								<td>${subcriber.registeredFrom}</td>
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
					<input type="hidden" name="maxItem" id="maxItem" /> <input
						type="hidden" name="currentPage" id="currentPage" />
				</form>
			</div>
		</div>
	</div>
</div>