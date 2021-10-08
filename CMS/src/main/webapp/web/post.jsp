<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container post-detail">
	<section class="row justify-content-center">
		<div class="col-sm-8 heading-container">
			<c:forEach items="${post.categories}" var="category">
				<c:if test="${category.used}">
					<div class="post-properties d-inline-flex py-1 mr-2">
						<a class="category-display text-decoration-none" href="">${category.name}</a>
					</div>
				</c:if>
			</c:forEach>
			<h1>${post.title}</h1>
		</div>
	</section>
	<section class="row">
		<div class="col">
			<div class="feature-img">
				<img class="post-listing-img" src="images/lede.jpg" alt="">
			</div>
		</div>
	</section>
	<section class="content row justify-content-center">
		<div class="col-md-2 d-none d-md-block"></div>
		<div class="col-md-7">${post.content}</div>
		<div class="col-md-3">
			<h4>Danh mục</h4>
			<c:forEach items="${post.categories}" var="category">
          <div class="post-properties py-1">
            <a class="category-display text-decoration-none" href="">${category.name}</a>
          </div>
      </c:forEach>
		</div>
	</section>
</div>