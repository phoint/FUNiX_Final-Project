<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageInfo.title}</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" 
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" 
          crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="css/admin.css"/>">
    <script src="https://cdn.ckeditor.com/ckeditor5/29.2.0/classic/ckeditor.js"></script>
    <script src="https://ckeditor.com/apps/ckfinder/3.5.0/ckfinder.js"></script>
  </head>
  <body>
    <div class="container-fluid">
      <div class="row">
        <nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-dark sidebar collapse">
          <div class="brand text-white text-center">
            <h1>LOGO</h1>
          </div>
          <div class="sidebar-sticky pt-3">
            <ul class="nav flex-column">
              <li class="nav-item">
                <a class="nav-link text-light active" href="#">
                  <span data-feather="home"></span>
                  Dashboard <span class="sr-only">(current)</span>
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link text-light" href="#">
                  <span data-feather="file"></span>
                  Post
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link text-light" href="#">
                  <span data-feather="shopping-cart"></span>
                  Category
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link text-light" href="#">
                  <span data-feather="users"></span>
                  User
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link text-light" href="#">
                  <span data-feather="bar-chart-2"></span>
                  Comment
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link text-light" href="#">
                  <span data-feather="layers"></span>
                  Setting
                </a>
              </li>
            </ul>
          </div>
        </nav>
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4 pt-3">
          <jsp:include page="${pageInfo.contentUrl}"></jsp:include>
        </main>
      </div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" 
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" 
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" 
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js"></script>
    <script src="<c:url value="vendor/jquery.twbsPagination.min.js"/>"></script>
    <script src="<c:url value="js/admin.js"/>"></script>
    <script type="text/javascript">
    var totalPage = ${posts.totalPage};
    var currentPage = ${posts.currentPage};
      $(function () {
          window.pagObj = $('#pagination').twbsPagination({
              totalPages: totalPage,
              visiblePages: 3,
              startPage: currentPage,
              onPageClick: function (event, page) {
            	  console.info(page + ' (from options)');
              }
          }).on('page', function (event, page) {
              console.info(page + ' (from event listening)');
              $('#currentPage').val(page);
              $('#maxItem').val(10);
              $('#pagination-info').submit();
          });
      });
    </script>
  </body>
</html>