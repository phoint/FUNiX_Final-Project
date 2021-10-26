<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta name="Description" content="Enter your description here" />
<title>${pageInfo.title }</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Barlow:wght@100;300;400;700&family=Yeseva+One&family=Goldman&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="<c:url value="web/css/home.css"/>">
</head>
<body>
	<header>
		<div class="container position-relative">
			<div class="row">
				<nav
					class="navbar navbar-expand-lg navbar-dark bg-dark w-100 rounded position-absolute">
					<a class="navbar-brand" href="#">Navbar</a>
					<button class="navbar-toggler" type="button" data-toggle="collapse"
						data-target="#navbarSupportedContent"
						aria-controls="navbarSupportedContent" aria-expanded="false"
						aria-label="Toggle navigation">
						<span class="navbar-toggler-icon"></span>
					</button>

					<div class="collapse navbar-collapse" id="navbarSupportedContent">
						<ul class="navbar-nav mr-auto">
							<li class="nav-item active"><a class="nav-link" href="#">Home
									<span class="sr-only">(current)</span>
							</a></li>
							<li class="nav-item"><a class="nav-link" href="#">Link</a></li>
							<li class="nav-item"><a class="nav-link disabled" href="#"
								tabindex="-1" aria-disabled="true">Disabled</a></li>
						</ul>
						<form class="form-inline my-2 my-lg-0">
							<input class="form-control mr-sm-2" type="search"
								placeholder="Search" aria-label="Search">
							<button class="btn btn-outline-warning my-2 my-sm-0"
								type="submit">Search</button>
						</form>
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
								<a class="dropdown-item" href="login?action=resetPwd">Forgot Password</a>
								</c:if>
								<c:if test="${not empty loginUser}">								
								<a class="dropdown-item" href="account/password-change">Change Password</a>
								<a class="dropdown-item" href="login?action=logout">Logout</a>
								</c:if>
							</div>
						</div>
					</div>
				</nav>
			</div>
		</div>
	</header>
	<main role="main">
		<jsp:include page="${pageInfo.contentUrl}"></jsp:include>
	</main>
	<footer>
		<div class="container-fluid">
			<div class="mail-subcription row justify-content-center py-5">
				<div class="col-md-4 col-sm-6 text-center">
					<h2>Newsleter</h2>
					<h6>Đăng ký nhận tin mới mỗi ngày</h6>
					<form action="" method="post">
						<div class="input-group ">
							<input class="form-control" type="email" name=""
								placeholder="Email Address" aria-label="Recipient's "
								aria-describedby="my-addon">
							<div class="input-group-append">
								<input class="btn btn-outline-primary" type="button"
									value="Submit">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="row">
				<small>All Rights Reserved © 2021</small>
			</div>
		</div>
	</footer>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.1/umd/popper.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.min.js"></script>
	<script src="<c:url value="vendor/jquery.twbsPagination.min.js"/>"></script>
	<script src="<c:url value="web/js/scripts.js"/>"></script>
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