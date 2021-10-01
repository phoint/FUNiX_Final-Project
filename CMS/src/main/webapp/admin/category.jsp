<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-header">
	<h2 class="d-inline-flex mr-3">Categories</h2>
	<a href="" class="btn btn-sm btn-outline-primary mb-3">Add New</a>
</div>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-4">
			<form method="post" action="<c:url value="admin-categories"/>">
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
							<th>Name</th>
							<th>Description</th>
							<th>Slug</th>
							<th>Count</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${categories.listResult}" var="cat">
							<tr>
								<td><input type="checkbox" name="" id=""></td>
								<td>
									<div>${cat.name}</div>
									<div>
										<a href="<c:url value="EditCategory?id=${cat.id}"/>">Edit</a>
										<a
											href="<c:url value="admin-categories?id=${cat.id}&action=delete"/>"
											class="ml-2">Delete</a>
									</div>
								</td>
								<td>${empty cat.desc ? "-
											" :cat.desc}</td>
								<td>${cat.url}</td>
								<td>totalPost</td>
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
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>