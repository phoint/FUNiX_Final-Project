<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-header">
	<h2 class="d-inline-flex mr-3">Edit Category</h2>
	<!-- <a href="" class="btn btn-sm btn-outline-primary mb-3">Add New</a> -->
</div>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-8">
			<form method="post" action="<c:url value="edit-category"/>">
			 <input type="hidden" name="id" value="${category.id}">
				<div class="form-group row">
					<label class="col-sm-3 col-form-label" for="name">Name</label>
					<div class="col-sm-9">
						<input id="name" class="form-control" type="text" name="name" value="${category.name}">
						<small id="nameHelpBlock" class="form-text text-muted">
							The name is how it appears on your site. </small>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-3 col-form-label" for="slug">Slug</label>
					<div class="col-sm-9">
						<input id="slug" class="form-control" type="text" name="url" value="${category.url}">
						<small id="slugHelpBlock" class="form-text text-muted">
							The “slug” is the URL-friendly version of the name. It is usually
							all lowercase and contains only letters, numbers, and hyphens. </small>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-3 col-form-label" for="slug">Description</label>
					<div class="col-sm-9">
						<textarea id="my-textarea" class="form-control" name="desc" rows="3">${category.desc}</textarea>
						<small id="descriptionHelpBlock" class="form-text text-muted">
							The description is not prominent by default; however, some themes
							may show it. </small>
					</div>
				</div>
				<button class="btn btn-primary" type="submit">Update</button>
			</form>
		</div>
	</div>
</div>