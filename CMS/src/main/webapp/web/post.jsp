<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container post-detail">
	<section class="row justify-content-center">
		<div class="col-sm-8 heading-container">
			<c:forEach items="${post.categories}" var="category">
				<c:if test="${category.used}">
					<div class="post-properties d-inline-flex py-1 mr-2">
						<a class="category-display text-decoration-none" href="<c:url value="?category=${category.id}"/>">${category.name}</a>
					</div>
				</c:if>
			</c:forEach>
			<h1>${post.title}</h1>
		</div>
	</section>
	<section class="row">
		<div class="col">
			<div class="feature-img">
				<img class="post-listing-img" src="${post.feature}" alt="">
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
							<c:if
								test="${comment.confirm or comment.createdBy == sessionScope.loginUser.id}">
								<div class="media mt-4">
									<img class="mr-3 rounded-circle" alt="Bootstrap Media Preview"
										src="http://placeimg.com/300/300/any" />
									<div class="media-body">
										<div class="row">
											<div class="col-8">
												<h6 class="m-0">${comment.author.username}</h6>
												<span class="p-0">- ${comment.createdDate }</span>
											</div>
											<div class="col-4">
												<div class="pull-right reply text-right">
													<a role="button" class="reply-button"><span><i
															class="fa fa-reply"></i> reply</span></a>
												</div>
											</div>
										</div>
										<p>${comment.comContent}</p>
										<!-- The reply comment box -->
										<c:if test="${not empty sessionScope.loginUser}">
											<div class="row reply-comment-block">
												<div class="col">
													<div class="bg-light p-2">
														<form action="<c:url value="/"/>" method="POST">
															<div class="d-flex flex-row align-items-start">
																<img class="rounded-circle"
																	src="https://i.imgur.com/RpzrMR2.jpg" width="40">
																<textarea class="form-control ml-1 shadow-none textarea"
																	name="comContent">${comContent}</textarea>
																<input type="hidden" name="submitTo" value="${post.id}">
																<input type="hidden" name="replyTo"
																	value="${comment.id}"> <input type="hidden"
																	name="action" value="comment"> <input
																	type="hidden" name="confirm" value="false">
															</div>
															<div class="mt-2 text-right">
																<button class="btn btn-primary btn-sm shadow-none"
																	type="submit">Post comment</button>
																<button
																	class="btn btn-outline-primary btn-sm ml-1 shadow-none reset-reply-comment"
																	type="button">Cancel</button>
															</div>
														</form>
													</div>
												</div>
											</div>
										</c:if>
										<!-- The replies of comment -->
										<c:forEach items="${comment.replies}" var="reply">
											<c:if
												test="${reply.confirm or reply.createdBy == sessionScope.loginUser.id}">
												<div class="media mt-3">
													<a class="pr-3" href="#"><img class="rounded-circle"
														alt="Bootstrap Media Another Preview"
														src="http://placeimg.com/300/300/any" /></a>
													<div class="media-body">
														<h6 class="m-0">${reply.author.username}</h6>
														<span>- ${reply.createdDate}</span>
														<p>${reply.comContent}</p>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</div>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</div>
				<!-- Post new comment -->
<%-- 				<c:if test="${not empty sessionScope.loginUser}"> --%>
					<div class="row new-comment-block">
						<div class="col">
							<div class="bg-light p-2">
								<form action="<c:url value="/"/>" method="POST">
									<div class="d-flex flex-row align-items-start">
										<img class="rounded-circle"
											src="https://i.imgur.com/RpzrMR2.jpg" width="40">
										<textarea id="new-comment"
											class="form-control ml-1 shadow-none textarea"
											name="comContent">${comContent}</textarea>
										<input type="hidden" name="action" value="comment"> <input
											type="hidden" name="submitTo" value="${post.id}"> <input
											type="hidden" name="confirm" value="false">
									</div>
									<div class="mt-2 text-right">
										<button class="btn btn-primary btn-sm shadow-none"
											type="submit">Post comment</button>
										<button id="reset-button"
											class="btn btn-outline-primary btn-sm ml-1 shadow-none"
											type="button">Cancel</button>
									</div>
								</form>
							</div>
						</div>
					</div>
<%-- 				</c:if> --%>
<%-- 				<c:if test="${empty sessionScope.loginUser }">
					<div class="row">
						<div class="col">
						  <a href="<c:url value="login?from=${pageContext.request.pathInfo}?${pageContext.request.queryString}"/>">Log in</a> or <a href="<c:url value="register?from=${pageContext.request.requestURI}"/>">Sign up</a> to write comment.
						</div>
					</div>
				</c:if> --%>
			</div>

			<!-- End Comment section -->
		</div>
		<!-- Right Sidebar -->
		<div class="col-md-3">
			<h4>Danh mục</h4>
			<c:forEach items="${post.categories}" var="category">
				<div class="post-properties py-1 position-relative">
					<a class="category-display text-decoration-none stretched-link" href="<c:url value="?category=${category.id}"/>">${category.name}</a>
				</div>
			</c:forEach>
		</div>
		<!-- End Right Sidebar -->
	</section>
</div>