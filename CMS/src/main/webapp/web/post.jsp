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
		<div class="col-md-7">
			<div class="the-content">${post.content}</div>
			<!-- Comment section -->
			<div class="container mb-5 mt-5">
				<h4>Comments</h4>
				<div class="row">
					<div class="col">
						<c:forEach items="${post.comments}" var="comment">
							<div class="media mt-4">
								<img class="mr-3 rounded-circle" alt="Bootstrap Media Preview"
									src="http://placeimg.com/300/300/any" />
								<div class="media-body">
									<div class="row">
										<div class="col-8">
											<h6 class="m-0">Maria Smantha</h6>
											<span class="p-0">- 2 hours ago</span>
										</div>
										<div class="col-4">
											<div class="pull-right reply text-right">
												<a href="#"><span><i class="fa fa-reply"></i>
														reply</span></a>
											</div>
										</div>
									</div>
									<p>${comment.comContent}</p>
									<c:forEach items="${comment.replies}" var="reply">
										<div class="media mt-3">
											<a class="pr-3" href="#"><img class="rounded-circle"
												alt="Bootstrap Media Another Preview"
												src="http://placeimg.com/300/300/any" /></a>
											<div class="media-body">
												<h6 class="m-0">Simona Disa</h6>
												<span>- 3 hours ago</span>
												<p>${reply.comContent}</p>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<!-- End Comment section -->
		</div>
		<!-- Right Sidebar -->
		<div class="col-md-3">
			<h4>Danh mục</h4>
			<c:forEach items="${post.categories}" var="category">
				<div class="post-properties py-1">
					<a class="category-display text-decoration-none" href="">${category.name}</a>
				</div>
			</c:forEach>
		</div>
		<!-- End Right Sidebar -->
	</section>
</div>