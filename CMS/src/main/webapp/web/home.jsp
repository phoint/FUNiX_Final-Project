<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
	<div class="row">
		<div class="banner">
			<img class="img-fluid" src="images/lede.jpg" alt="">
		</div>
	</div>
</div>
<div class="container mt-md-5 mt-2">
	<div class="row">
		<div class="col-md-9 border-right border-secondary">
			<h2>Bài viết mới</h2>
			<div class="row row-cols-1 row-cols-sm-2">
				<c:forEach items="${posts.listResult}" var="post">
					<div class="col post-item">
						<div class="feature-img">
							<a href="<c:url value="/?p=${post.id}"/>"> <img
								class="post-listing-img img-fluid" src="${post.image.url}" alt="">
							</a>
						</div>
						<c:forEach items="${post.categories}" var="category">
							<c:if test="${category.used}">
								<div class="post-properties d-inline-flex py-1 mr-2">
									<a class="category-display text-decoration-none"
										href="<c:url value="?category=${category.id}"/>">${category.name}</a>
								</div>
							</c:if>
						</c:forEach>
						<h3 class="mb-0 pb-0">
							<a href="<c:url value="?p=${post.id}"/>">${post.title}</a>
						</h3>
						<span class="author"><em>${post.author.username}</em></span>
						<p class="pt-2">${post.excerpt}</p>
					</div>
				</c:forEach>
			</div>
			<div class="row justify-content-center mt-md-5 mt-2">
				<nav class="d-block" aria-label="Page navigation">
					<ul class="pagination" id="pagination"></ul>
				</nav>
			</div>
			<form action="<c:url value="/"/>" id="pagination-info" method="get">
				<input type="hidden" name="maxItem" id="maxItem" /> <input
					type="hidden" name="currentPage" id="currentPage" />
					<input type="hidden" name="category" id="category"/>
			</form>
		</div>
			<div class="col-md-3 sidebar">
				<h4>Danh mục</h4>
        <c:forEach items="${posts.categories}" var="category">
        <div class="post-properties py-1 position-relative">
          <a class="category-display text-decoration-none stretched-link" href="<c:url value="?category=${category.id}&title=${category.name }"/>">${category.name}</a>
        </div>
      </c:forEach>
			</div>
	</div>
</div>