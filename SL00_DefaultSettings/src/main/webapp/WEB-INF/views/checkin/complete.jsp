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
<title>체크인 완료 - 대한항공</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;700&display=swap" rel="stylesheet">
<style>
body {
    background-color: #f4f7fb;
    font-family: 'Noto Sans KR', sans-serif;
    margin: 0;
    padding: 0;
}

.complete-container {
    max-width: 600px;
    margin: 50px auto;
    background-color: #fff;
    border-radius: 16px;
    box-shadow: 0 8px 30px rgba(0, 37, 108, 0.1);
    overflow: hidden;
}

.complete-header {
    background-color: #0064de;
    color: #fff;
    padding: 30px;
    text-align: center;
}

.complete-header h1 {
    font-size: 24px;
    font-weight: 700;
    margin-bottom: 10px;
}

.complete-header p {
    font-size: 16px;
    opacity: 0.9;
}

.complete-content {
    padding: 40px 30px;
}

.success-message {
    text-align: center;
    margin-bottom: 30px;
}

.success-icon {
    font-size: 60px;
    color: #28a745;
    margin-bottom: 20px;
}

.success-title {
    font-size: 20px;
    font-weight: 700;
    color: #333;
    margin-bottom: 10px;
}

.success-subtitle {
    font-size: 16px;
    color: #666;
}

.reservation-info {
    background-color: #f8f9fa;
    border-radius: 12px;
    padding: 25px;
    margin-bottom: 30px;
}

.info-title {
    font-size: 18px;
    font-weight: 700;
    color: #333;
    margin-bottom: 20px;
}

.info-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 0;
    border-bottom: 1px solid #e9ecef;
}

.info-row:last-child {
    border-bottom: none;
}

.info-label {
    font-size: 14px;
    color: #666;
    font-weight: 500;
}

.info-value {
    font-size: 14px;
    color: #333;
    font-weight: 700;
}

.seat-info {
    background-color: #e8f5e8;
    border: 1px solid #28a745;
    border-radius: 8px;
    padding: 20px;
    margin-bottom: 30px;
}

.seat-title {
    font-size: 16px;
    font-weight: 700;
    color: #28a745;
    margin-bottom: 10px;
}

.seat-number {
    font-size: 24px;
    font-weight: 800;
    color: #28a745;
    text-align: center;
}

.action-buttons {
    display: flex;
    gap: 15px;
    justify-content: center;
}

.btn {
    padding: 12px 30px;
    font-size: 16px;
    font-weight: 700;
    border-radius: 8px;
    text-decoration: none;
    transition: all 0.2s ease;
}

.btn-primary {
    background-color: #0064de;
    color: #fff;
    border: 1px solid #0064de;
}

.btn-primary:hover {
    background-color: #0056c0;
    border-color: #0056c0;
}

.btn-secondary {
    background-color: #fff;
    color: #555;
    border: 1px solid #ccc;
}

.btn-secondary:hover {
    background-color: #f5f5f5;
    border-color: #bbb;
}
</style>
</head>
<body>
    <div class="complete-container">
        <div class="complete-header">
            <h1>체크인 완료</h1>
            <p>좌석 선택이 완료되었습니다</p>
        </div>
        
        <div class="complete-content">
            <div class="success-message">
                <div class="success-icon">
                    <i class="fa-solid fa-check-circle"></i>
                </div>
                <div class="success-title">체크인이 성공적으로 완료되었습니다!</div>
                <div class="success-subtitle">선택하신 좌석으로 탑승하실 수 있습니다.</div>
            </div>
            
            <div class="reservation-info">
                <div class="info-title">예약 정보</div>
                <div class="info-row">
                    <span class="info-label">예약번호</span>
                    <span class="info-value">${reservation.bookingId}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">탑승객</span>
                    <span class="info-value">${reservation.lastName} ${reservation.firstName}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">항공편</span>
                    <span class="info-value">${reservation.flightId}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">출발</span>
                    <span class="info-value">
                        <fmt:formatDate value="${reservation.departureTime}" pattern="MM월 dd일 (E) HH:mm" />
                    </span>
                </div>
                <div class="info-row">
                    <span class="info-label">경로</span>
                    <span class="info-value">${reservation.departureAirportId} → ${reservation.arrivalAirportId}</span>
                </div>
            </div>
            
            <div class="seat-info">
                <div class="seat-title">선택된 좌석</div>
                <div class="seat-number">${reservation.assignedSeat}</div>
            </div>
            
            <div class="action-buttons">
                <a href="<%= contextPath %>/reservation/detail.htm?bookingId=${reservation.bookingId}" class="btn btn-secondary">
                    <i class="fa-solid fa-arrow-left"></i> 예약 상세로
                </a>
                <a href="<%= contextPath %>/" class="btn btn-primary">
                    <i class="fa-solid fa-home"></i> 홈으로
                </a>
            </div>
        </div>
    </div>
</body>
</html> 