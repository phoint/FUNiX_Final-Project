<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container heading-container">
	<div class="row">
		<div class="col-md-9 border-right border-secondary">
			<h2>Đã đăng nhập</h2>
			<h3>Tài khoản: ${sessionScope.loginUser.email}</h3>
			<p>Để đăng nhập tài khoản khác, vui lòng đăng xuất tài khoản hiện
				tại</p>
			<a href="<c:url value="/"/> ">Trở lại</a>
		</div>
	</div>
</div>