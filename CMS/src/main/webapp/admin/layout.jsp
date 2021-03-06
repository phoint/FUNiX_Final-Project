<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${pageInfo.title}</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="<c:url value="css/admin.css"/>">
<script type="text/javascript"
	src="<c:url value="../vendor/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="../vendor/ckfinder/ckfinder.js"/>"></script>
</head>
<body>
	<header>
		<div class="container-fluid bg-dark">
			<div class="row">
				<nav
					class="navbar navbar-expand-lg navbar-dark w-100 justify-content-end p-0">
					<div class="collapse navbar-collapse" id="navbarSupportedContent">
						<ul class="navbar-nav mr-auto">
							<li class="nav-item active"><a class="nav-link" href="<c:url value="/"/>">Home
									<span class="sr-only">(current)</span>
							</a></li>
						</ul>
					</div>
					<div class="dropdown">
						<a class="nav-link" href="#" id="navbarDropdown" role="button"
							data-toggle="dropdown"> <i id="user-login"
							class="far fa-user ml-sm-3"></i>
						</a>
						<div
							class="dropdown-menu dropdown-menu-left dropdown-menu-lg-right bg-dark text-lg-right"
							aria-labelledby="navbarDropdown">
							<c:if test="${empty loginUser}">
								<a class="dropdown-item" href="login">Login</a>
								<a class="dropdown-item" href="register">Register</a>
								<a class="dropdown-item" href="#">Subcribe</a>
								<a class="dropdown-item" href="login?action=resetPwd">Forgot
									Password</a>
							</c:if>
							<c:if test="${not empty loginUser}">
								<a class="dropdown-item"
									href="<c:url value="/account/password-change"/>">Change
									Password</a>
								<a class="dropdown-item" href="login?action=logout">Logout</a>
							</c:if>
						</div>
					</div>
				</nav>
			</div>
		</div>
	</header>
	<div class="container-fluid">
		<div class="row">
			<nav id="sidebarMenu"
				class="col-md-3 col-lg-2 d-md-block bg-dark sidebar collapse">
				<div class="brand text-white text-center">
					<a href="<c:url value="/"/>" role="button">
            <img src="${applicationScope.webOpt.logoPath}" width="100px">
          </a>
				</div>
				<div class="sidebar-sticky pt-3">
					<ul class="nav flex-column">
						<li class="nav-item"><a class="nav-link text-light active"
							href="#"> <span data-feather="home"></span> Dashboard <span
								class="sr-only">(current)</span>
						</a></li>
						<li class="nav-item"><a class="nav-link text-light"
							href="<c:url value="posts"/>"> <span data-feather="file"></span>
								Post
						</a></li>
						<li class="nav-item"><a class="nav-link text-light"
							href="<c:url value="categories"/>"> <span
								data-feather="folder"></span> Category
						</a></li>
						<li class="nav-item"><a class="nav-link text-light"
							href="<c:url value="users"/>"> <span data-feather="users"></span>
								User
						</a></li>
						<li class="nav-item"><a class="nav-link text-light"
							href="<c:url value="subcribers"/>"> <span data-feather="user-minus"></span>
								Subcriber
						</a></li>
						<li class="nav-item"><a class="nav-link text-light"
							href="<c:url value="comments"/>"> <span
								data-feather="message-square"></span> Comment
						</a></li>
						<li class="nav-item"><a class="nav-link text-light" href="<c:url value="option"/>">
								<span data-feather="settings"></span> Setting
						</a></li>
					</ul>
				</div>
			</nav>
			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4 pt-3">
				<jsp:include page="${pageInfo.contentUrl}"></jsp:include>
			</main>
		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js"></script>
	<script src="<c:url value="../vendor/jquery.twbsPagination.min.js"/>"></script>
	<script src="<c:url value="js/admin.js"/>"></script>
	<script type="text/javascript">
		var maxItem = 10;
		var totalPage = ${page.totalPage};
		var currentPage = ${page.currentPage};
		$(function() {
			window.pagObj = $('#pagination').twbsPagination({
				totalPages : totalPage,
				visiblePages : 3,
				startPage : currentPage,
				onPageClick : function(event, page) {
					console.info(page + ' (from options)');
				}
			}).on('page', function(event, page) {
				console.info(page + ' (from event listening)');
				$('#currentPage').val(page);
				$('#maxItem').val(10);
				$('#pagination-info').submit();
			});
		});
	</script>
</body>
</html>