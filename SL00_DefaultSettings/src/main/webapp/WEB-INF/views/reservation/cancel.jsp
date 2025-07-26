<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>예약취소 - 대한항공</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/index.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="<%= contextPath %>/resources/css/cancelReservation.css">
</head>
<body>
	<jsp:include page="../common/header.jsp" />
	<div class="cancel-page">
		<div class="container">
			<h1>예약취소</h1>
			
			<form id="cancelForm" action="<%= contextPath %>/reservation/cancel.htm" method="POST">
				<input type="hidden" name="bookingId" value="${cancelInfo.bookingId}">
				
				<div class="section">
					<h2>여정 정보</h2>
					<div class="itinerary-info">
						<div class="flight-header">
							예약번호: <strong>${cancelInfo.bookingId}</strong>
						</div>
						<div class="flight-path">
							<div class="airport">
								<div class="airport-code">${cancelInfo.departureAirportId}</div>
								<div class="airport-name">${cancelInfo.departureAirportName}</div>
								<div class="flight-schedule">
									<fmt:formatDate value="${cancelInfo.departureTime}" pattern="yyyy.MM.dd (E) HH:mm" />
								</div>
							</div>
							<div class="flight-arrow">
								<i class="fa-solid fa-arrow-right-long"></i>
							</div>
							<div class="airport" style="text-align: right;">
								<div class="airport-code">${cancelInfo.arrivalAirportId}</div>
								<div class="airport-name">${cancelInfo.arrivalAirportName}</div>
								<div class="flight-schedule">
									<fmt:formatDate value="${cancelInfo.arrivalTime}" pattern="yyyy.MM.dd (E) HH:mm" />
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="section">
					<h2>탑승객 및 취소 정보</h2>
					<div class="info-box">
						<ul class="info-table">
							<li><span class="label">탑승객</span> <span class="value">${cancelInfo.lastName} ${cancelInfo.firstName}</span></li>
							<li class="penalty"><span class="label">취소 위약금</span>
								<span class="value">₩<fmt:formatNumber value="${cancelInfo.penaltyFee}" pattern="#,##0" /></span></li>
							<li class="refund-total"><span class="label">총 환불 예정 금액</span> 
								<span class="value">₩<fmt:formatNumber value="${cancelInfo.totalRefundAmount}" pattern="#,##0" /></span>
							</li>
						</ul>
					</div>
				</div>
				
				<div class="section">
					<h2>신청자 정보</h2>
					<div class="applicant-info info-box">
						<ul class="info-table">
							<li><span class="label">이름</span> <span class="value">${cancelInfo.lastName} ${cancelInfo.firstName}</span></li>
							<li><span class="label">연락처</span> <span class="value">${cancelInfo.phone}</span></li>
							<li><span class="label">이메일</span> <span class="value">${cancelInfo.email}</span></li>
						</ul>
					</div>
				</div>
				
				<div class="section notice-section">
					<h2>안내사항</h2>
					<ul>
						<li>취소 위약금은 1인당 편도 기준으로 부과됩니다.</li>
						<li>취소 접수 후에는 되돌릴 수 없으니 신중하게 신청해 주시기 바랍니다.</li>
						<li>환불 금액은 실제 처리 시점에 따라 변동될 수 있으며, 최종 환불 내역은 이메일로 안내됩니다.</li>
					</ul>
					
					<div class="checkbox-group">
						<input type="checkbox" id="agreement1" name="agreement1" required>
						<label for="agreement1">취소 위약금 및 규정을 확인하였습니다. <span class="required">(필수)</span></label>
					</div>
					<div class="checkbox-group">
						<input type="checkbox" id="agreement2" name="agreement2" required>
						<label for="agreement2">상기 내용에 모두 동의하며 취소를 신청합니다. <span class="required">(필수)</span></label>
					</div>
				</div>
				
				<div class="form-actions">
					<button type="button" id="cancelBtn" class="btn btn-secondary">신청 취소</button>
					<button type="submit" class="btn btn-primary">예약취소</button>
				</div>
			</form>
		</div>
	</div>
	
	<script src="<%= contextPath %>/resources/js/cancelReservation.js"></script>
	<jsp:include page="../common/footer.jsp" />
</body>
</html> 