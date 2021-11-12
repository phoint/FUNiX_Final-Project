<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-header">
	<h2 class="d-inline-flex mr-3">Categories</h2>
</div>
<div class="container-fluid">
		<c:if test="${not empty message}">
			<div id="message" class="alert alert-success">${message}</div>
		</c:if>
		<c:if test="${not empty error}">
			<div id="error" class="alert alert-danger">${error}</div>
		</c:if>
	<div class="row">
		<div class="col-lg-4">
			<form method="post" action="categories">
				<div class="form-group row">
					<label class="col-lg-3 col-form-label" for="name">Name</label>
					<div class="col-lg-9">
						<input id="name" class="form-control" type="text" name="name">
						<small id="nameHelpBlock" class="form-text text-muted">
							The name is how it appears on your site. </small>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-lg-3 col-form-label" for="slug">Slug</label>
					<div class="col-lg-9">
						<input id="slug" class="form-control" type="text" name="url">
						<small id="slugHelpBlock" class="form-text text-muted">
							The “slug” is the URL-friendly version of the name. It is usually
							all lowercase and contains only letters, numbers, and hyphens. </small>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-lg-3 col-form-label" for="slug">Description</label>
					<div class="col-lg-9">
						<textarea id="my-textarea" class="form-control" name="desc"
							rows="3"></textarea>
						<small id="descriptionHelpBlock" class="form-text text-muted">
							The description is not prominent by default; however, some themes
							may show it. </small>
					</div>
				</div>
				<button class="btn btn-primary" type="submit">Add New</button>
			</form>
		</div>
		<div class="col-lg-8 mt-5 mt-lg-0">
			<div class="wrap my-1">
				<div class="d-inline-flex mr-2">
					<form class="form-inline" action="categories" method="post"
						id="multiselect">
						<select class="form-control form-control-sm mx-1" name="action">
							<option>Bulk actions</option>
							<option value="delete">Delete</option>
						</select>
						<button type="submit" class="btn btn-sm btn-outline-primary mx-1">Submit</button>
					</form>
				</div>
				<div class="d-inline-flex mr-2">
					<form class="form-inline" action="categories" method="get">
						<input class="form-control form-control-sm" type="text"
							name="searchKey" value="${searchKey}" placeholder="Search...">
						<button type="submit" class="btn btn-sm btn-outline-primary mx-1">Search</button>
					</form>
				    <a role="button" class="btn btn-sm btn-outline-primary mx-1" 
				    href="<c:url value="categories" />">Clear</a>
				</div>
			</div>
			<div class="table-responsive">
				<table class="table table-striped table-sm">
					<thead>
						<tr>
							<th><input type="checkbox" id="select-all"></th>
							<th>Name</th>
							<th>Description</th>
							<th>Slug</th>
							<th>Count</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${categories.listResult}" var="cat">
							<tr>
								<td><c:if test="${cat.id != 1}">
										<input type="checkbox" name="id" class="select-item"
											value="${cat.id}" form="multiselect">
									</c:if></td>
								<td>
									<div>${cat.name}</div>

									<div>
										<a href="<c:url value="edit-category?id=${cat.id}"/>">Edit</a>
										<c:if test="${cat.id != 1}">
											<a
												href="<c:url value="categories?id=${cat.id}&action=delete"/>"
												class="ml-2">Delete</a>
										</c:if>
									</div>
								</td>
								<td>${empty cat.desc ? "-" :cat.desc}</td>
								<td>${cat.url}</td>
								<td><a href="<c:url value="posts?category=${cat.id}"/>"
                        class="ml-2">${cat.totalPost}</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="container justify-content-center">
					<nav class="d-block" aria-label="Page navigation">
						<ul class="pagination" id="pagination"></ul>
					</nav>
				</div>
				<form action="<c:url value="categories"/>"
					id="pagination-info" method="get">
					<input type="hidden" name="maxItem" id="maxItem" /> <input
						type="hidden" name="currentPage" id="currentPage" />
				</form>
			</div>
		</div>
	</div>
</div>