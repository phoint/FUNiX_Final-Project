<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-header">
	<h2 class="d-inline-flex mr-3">Option</h2>
	   <c:if test="${not empty param.error or not empty error }">
      <div class="alert alert-danger" role="alert">${param.error}
        ${error}</div>
    </c:if>
    <c:if test="${not empty param.message or not empty message}">
      <div class="alert alert-success" role="alert">${param.message}
        ${message}</div>
    </c:if>
</div>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-8 col-lg-6">
			<form action="<c:url value="option" />" method="post">
				<div class="form-group row">
					<label for="username"
						class="col-4 col-form-label col-form-label-sm">Logo</label>
					<div class="col-8">
						<div class="input-group mb-3">
						  <div class="w-100">
						    <img src="${applicationScope.webOpt.logoPath}"	alt="" width="150px">
						  </div>
							<input id="logoPath" type="text" class="form-control" readonly name="logoPath" 
							value="${applicationScope.webOpt.logoPath}">
							<div class="input-group-append">
								<button class="btn btn-outline-secondary" type="button"
									id="button-addon2" onclick="BrowseServer('logoPath');">Browse</button>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group row">
					<label for="email" class="col-4 col-form-label">Banner</label>
					<div class="col-8">
							<div class="input-group mb-3">
						  <div class="w-100">
                <img src="${applicationScope.webOpt.bannerPath}" alt="" width="300px">
              </div>
								<input id="bannerPath" type="text" class="form-control" readonly name="bannerPath" 
								value="${applicationScope.webOpt.bannerPath}" >
								<div class="input-group-append">
									<button class="btn btn-outline-secondary" type="button"
										id="button-addon2" onclick="BrowseServer('bannerPath');">Browse</button>
								</div>
							</div>
					</div>
				</div>
				<div class="d-flex flex-row-reverse">
					<input class="btn btn-primary" type="submit" value="Save">
				</div>
			</form>
		</div>
	</div>
</div>
<script>
	function BrowseServer( functionData ) {
	  // You can use the "CKFinder" class to render CKFinder in a page:
	  var finder = new CKFinder();
	
	  // The path for the installation of CKFinder (default = "/ckfinder/").
	  finder.basePath = '../vendor/ckfinder';
	  finder.selectActionFunction = SetFileField;
	  finder.selectActionData = functionData;
	  finder.popup();
	}

	// This is a sample function which is called when a file is selected in CKFinder.
	function SetFileField( fileUrl, data ) {
		document.getElementById( data["selectActionData"] ).value = fileUrl;
		document.getElementById( data["selectActionData"] ).previousElementSibling.lastElementChild.src = fileUrl;
		}
</script>