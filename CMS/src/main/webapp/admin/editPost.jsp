<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-header">
	<h2 class="d-inline-flex mr-3">Edit Post</h2>
	<a href="" class="btn btn-sm btn-outline-primary mb-3">Add New</a>
</div>
<form action="<c:url value="Edit"/>" method="post">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-8">
				<input type="text" class="form-control form-control-lg" name="title"
					placeholder="Add Title" value="${p.title}">
				<c:if test="${not empty p.postUrl}"><span><strong>Permalink: </strong></span><input type="text" name="postUrl" value="${p.postUrl}"/></c:if>
				<div class="mt-3">
					<textarea name="content" id="editor" cols="30" rows="10">${p.content}</textarea>
					<script>
             ClassicEditor
             .create( document.querySelector( '#editor' ) )
             .catch( error => {
                            console.error( error );
                        } );
           </script>
				</div>
				<div class="mt-3">
				  <h6>Excerpt</h6>
				  <textarea name="excerpt" id="excerpt" width="100%">${p.excerpt}</textarea>
				</div>
			</div>
			<div class="col-md-4">
				<div class="card mb-4">
					<div class="card-header">
						<h5>Publish</h5>
					</div>
					<div class="card-body">
						<div class="form-group my-1">
							<span class="mr-1" data-feather="book"></span><label for="">Trạng
								Thái</label> <select
								class="form-control form-control-sm w-auto d-inline-block"
								name="postStatus" id="postStatus">
								<option value="2" <c:if test="${p.postStatus == 2}">selected</c:if>>Waiting</option>
								<option value="1" <c:if test="${p.postStatus == 1}">selected</c:if>>Published</option>
								<option value="3" <c:if test="${p.postStatus == 3}">selected</c:if>>Private</option>
								<option value="0" <c:if test="${p.postStatus == 0}">selected</c:if>>Draft</option>
							</select>
						</div>
						<div class="form-group my-1">
							<span class="mr-1" data-feather="calendar"></span><label for="">Ngày
								đăng</label> <input
								class="form-control form-control-sm w-auto d-inline-block"
								type="date" name="publishDate" id="publishDate" value="${p.publishDate}">
						</div>
						<div class="form-group my-1">
							<span class="mr-1" data-feather="eye"></span><label for="">Hiển
								thị</label> <select
								class="form-control form-control-sm w-auto d-inline-block"
								name="isVisible" id="isVisible">
								<option value="true" <c:if test="${p.visible}">selected</c:if>>Public</option>
								<option value="fasle" <c:if test="${not p.visible}">selected</c:if>>Private</option>
							</select>
						</div>
						<div class="d-flex flex-row-reverse">
							<button class="btn btn-primary" type="submit">Save</button>
						</div>
					</div>
				</div>
				<div class="card mb-4">
					<div class="card-header">
						<h5>Categories</h5>
					</div>
					<div class="card-body">
						<div class="form-check">
							<input class="form-check-input" type="checkbox" value="1"
								id="defaultCheck1"> 
							<label class="form-check-label" 
							       for="defaultCheck1"> Covid-19 </label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox" value="2"
								id="defaultCheck1"> <label class="form-check-label"
								for="defaultCheck1"> Trẻ Em </label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox" value="3"
								id="defaultCheck1"> <label class="form-check-label"
								for="defaultCheck1"> Lao Động Nghèo </label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox" value="4"
								id="defaultCheck1"> <label class="form-check-label"
								for="defaultCheck1"> Bệnh Hiểm Nghèo </label>
						</div>
						<a href="#new-category" data-toggle="collapse" role="button"
							aria-expanded="false" aria-controls="new-category">+ Add new
							category</a>
						<div class="collapse" id="new-category">
							<div class="form-group">
								<input class="form-control form-control-sm" type="text">
							</div>
							<input class="btn btn-secondary" type="button" value="submit">
						</div>
					</div>
				</div>
				<div class="card mb-4">
					<div class="card-header">
						<h5>Feature Image</h5>
					</div>
					<div class="card-body">
						<a href="#new-feature-img" data-toggle="collapse" role="button"
							aria-expanded="false" aria-controls="new-feature-img">Set
							feature image</a>
						<div class="collapse" id="new-feature-img">
							<div class="form-group">
								<input class="form-control form-control-sm" type="text">
							</div>
							<input class="btn btn-secondary" type="button" value="submit">
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</form>