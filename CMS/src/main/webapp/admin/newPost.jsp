<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-header">
	<h2 class="d-inline-flex mr-3">Add New Post</h2>
	<!-- <a href="" class="btn btn-sm btn-outline-primary mb-3">Add New</a> -->
</div>
<c:if test="${not empty message}">
	<div id="message" class="alert alert-success">${message}</div>
</c:if>
<c:if test="${not empty error}">
	<div id="error" class="alert alert-danger">${error}</div>
</c:if>
<form action="new-post" method="post" enctype="multipart/form-data">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-8">
				<input type="hidden" value="${sessionScope.loginUser.id}"
					name="createdBy" />
				<div class="form-group">
					<input type="text" class="form-control form-control-lg"
						name="title" placeholder="Add Title" value="${p.title}">
				</div>
				<label>Permalink: </label><input type="text" name="postUrl"
					value="${p.postUrl}">
				<div class="mt-3">
					<textarea class="form-control" name="content" id="editor" cols="30"
						rows="10">${p.content}</textarea>
				</div>
				<div class="mt-3">
					<h6>Excerpt</h6>
					<textarea class="form-control" name="excerpt" id="excerpt" rows="4">${p.excerpt}</textarea>
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
								<option value="2"
									<c:if test="${p.postStatus == 2}">selected</c:if>>Waiting</option>
								<option value="1"
									<c:if test="${p.postStatus == 1}">selected</c:if>>Published</option>
								<option value="3"
									<c:if test="${p.postStatus == 3}">selected</c:if>>Private</option>
								<option value="0"
									<c:if test="${p.postStatus == 0}">selected</c:if>>Draft</option>
							</select>
						</div>
						<div class="form-group my-1">
							<span class="mr-1" data-feather="calendar"></span><label for="">Ngày
								đăng</label> <input
								class="form-control form-control-sm w-auto d-inline-block"
								type="date" name="publishDate" id="publishDate"
								value="${p.publishDate}">
						</div>
						<div class="form-group my-1">
							<span class="mr-1" data-feather="eye"></span><label for="">Hiển
								thị</label> <select
								class="form-control form-control-sm w-auto d-inline-block"
								name="isVisible" id="isVisible">
								<option value="1" selected>Public</option>
								<option value="0">Private</option>
							</select>
						</div>
						<div class="d-flex flex-row-reverse">
							<button class="btn btn-primary" type="submit">Save</button>
							<div class="row mr-3 align-items-center">
								<div class="col">
									<a href="<c:url value="posts"/>"
										onclick="return confirm('Are you sure?');">Cancle</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="card mb-4">
					<div class="card-header">
						<h5>Categories</h5>
					</div>
					<div class="card-body">
						<c:forEach items="${p.categories}" var="category">
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									name="new-categories" value="${category.id}"
									<c:if test="${category.used}">checked</c:if>> <label
									class="form-check-label">${category.name}</label>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="card mb-4">
					<div class="card-header">
						<h5>Feature Image</h5>
					</div>
					<div class="card-body">
						<a role="button" onclick="BrowseServer();"><img id="xFilePath" alt="" src="" width=150px height=150px></a>
						<a class="d-block mt-3" role="button" onclick="BrowseServer();">Set feature image</a>
						<input id="featurePath" type="hidden" name="feature" value="">
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<script>
  var editor = CKEDITOR.replace('editor');
  CKFinder.setupCKEditor(editor, '../vendor/ckfinder');
  
  function BrowseServer() {
      // You can use the "CKFinder" class to render CKFinder in a page:
      var finder = new CKFinder();
      finder.basePath = '../vendor/ckfinder';  // The path for the installation of CKFinder (default = "/ckfinder/").
      finder.selectActionFunction = SetFileField;
      finder.popup();
    }

  // This is a sample function which is called when a file is selected in CKFinder.
  function SetFileField(fileUrl) {
    $('#xFilePath').attr('src', fileUrl);
    $('#featurePath').val(fileUrl);
  }
</script>