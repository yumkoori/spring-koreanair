<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
/* /src/main/webapp/css/checkinApply.css */
@charset "UTF-8";

/* --- 전체 페이지 레이아웃 --- */
body {
    background-color: #f4f7fb;
    font-family: 'Nanum Gothic', sans-serif;
}

.page-container {
    max-width: 760px;
    margin: 50px auto;
    padding: 40px 50px;
    background-color: #fff;
    border-radius: 16px;
    box-shadow: 0 8px 30px rgba(0, 37, 108, 0.1);
}

/* --- 페이지 헤더 --- */
.page-header {
    text-align: left;
    margin-bottom: 30px;
    padding-bottom: 20px;
    border-bottom: 1px solid #eee;
}

.page-header h1 {
    font-size: 24px;
    font-weight: 800;
    color: #111;
    margin-bottom: 8px;
}

.page-header p {
    font-size: 15px;
    color: #555;
}

/* --- 폼 섹션 --- */
.form-section {
    margin-bottom: 40px;
}

.form-section h2 {
    font-size: 16px;
    color: #fff;
    background-color: #0d2c5a; /* 어두운 남색 배경 */
    padding: 12px 20px;
    border-radius: 6px;
    font-weight: 700;
    margin-bottom: 25px;
}

.input-group {
    margin-bottom: 20px;
}

.input-group label {
    display: block;
    font-size: 14px;
    font-weight: 700;
    color: #555;
    margin-bottom: 8px;
}

.input-group input[type="text"],
.input-group input[type="tel"],
.input-group input[type="email"],
.input-group select {
    width: 100%;
    padding: 12px 15px;
    font-size: 15px;
    border: 1px solid #ccc;
    border-radius: 6px;
    box-sizing: border-box;
    transition: all 0.2s ease;
}

.input-group input:focus {
    outline: none;
    border-color: #0064de;
    box-shadow: 0 0 0 3px rgba(0, 100, 222, 0.15);
}

.input-group input[readonly] {
    background-color: #f5f5f5;
    color: #777;
    cursor: not-allowed;
}

.input-group-row {
    display: flex;
    gap: 15px;
    align-items: flex-end;
}

.input-group-row .input-group {
    flex: 1;
}

.input-group.country-code {
    flex: 0 0 120px;
}

/* --- 동의 섹션 --- */
.agreement-section h2 {
    background-color: #0d2c5a; /* 제목 스타일 통일 */
}

.agreement-item {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
    padding: 10px;
    border: 1px solid #f0f0f0;
    border-radius: 6px;
}

.agreement-item input[type="checkbox"] {
    width: 18px;
    height: 18px;
    margin-right: 12px;
    accent-color: #0064de;
    flex-shrink: 0;
}

.agreement-item label {
    font-size: 15px;
    font-weight: 500;
}

.agreement-item .required-text {
    color: #d9534f;
    font-weight: 700;
    margin-left: 6px;
    font-size: 14px;
}

.agreement-item .btn-view {
    margin-left: auto;
    background: #f0f0f0;
    border: 1px solid #ddd;
    border-radius: 4px;
    padding: 4px 10px;
    font-size: 12px;
    cursor: pointer;
    white-space: nowrap;
}
.agreement-item .btn-view:hover {
    background-color: #e0e0e0;
}

/* --- 버튼 영역 --- */
.form-actions {
    display: flex;
    justify-content: center;
    gap: 15px;
    margin-top: 40px;
    padding-top: 30px;
    border-top: 1px solid #eee;
}

.btn {
    padding: 12px 36px;
    font-size: 16px;
    font-weight: 700;
    border-radius: 8px;
    border: 1px solid;
    cursor: pointer;
    text-decoration: none;
    transition: all 0.2s ease;
}

.btn-secondary {
    background-color: #fff;
    color: #555;
    border-color: #ccc;
}
.btn-secondary:hover {
    background-color: #f5f5f5;
    border-color: #bbb;
}

.btn-primary {
    background-color: #0064de;
    color: #fff;
    border-color: #0064de;
}
.btn-primary:hover {
    background-color: #0056c0;
    border-color: #0056c0;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 100, 222, 0.2);
}

/* --- 좌석 정보 섹션 --- */
.seat-info {
    background-color: #f8f9fa;
    border: 1px solid #e9ecef;
    border-radius: 8px;
    padding: 15px;
    margin-bottom: 20px;
}

.seat-info h3 {
    font-size: 14px;
    font-weight: 700;
    color: #333;
    margin-bottom: 10px;
}

.seat-status {
    display: flex;
    align-items: center;
    gap: 10px;
}

.seat-status .status-indicator {
    width: 12px;
    height: 12px;
    border-radius: 50%;
    background-color: #28a745;
}

.seat-status .status-text {
    font-size: 14px;
    color: #555;
}

.seat-status .seat-number {
    font-weight: 700;
    color: #0064de;
}
</style>

<main class="page-container">
    <div class="page-header">
        <h1>온라인 체크인</h1>
        <p>탑승권은 입력하신 연락처로 전송됩니다. 정보를 정확하게 입력해주세요.</p>
    </div>

    <form id="applyForm" action="${pageContext.request.contextPath}/checkin/seat.htm" method="get">
        <input type="hidden" name="bookingId" value="${reservation.bookingId}">
        <input type="hidden" name="flightId" value="${reservation.flightId}">

        <div class="form-section">
            <h2>탑승객 연락처 정보</h2>
            <div class="input-group">
                <label for="passengerName">탑승객 이름</label>
                <input type="text" id="passengerName" name="passengerName" 
                       value="${reservation.lastName} ${reservation.firstName}" readonly>
            </div>

            <div class="input-group-row">
                <div class="input-group country-code">
                    <label for="countryCode">국가 번호</label>
                    <input type="text" id="countryCode" name="countryCode" value="+82 (대한민국)" readonly>
                </div>
                <div class="input-group">
                    <label for="phone">휴대폰 번호</label>
                    <input type="tel" id="phone" name="phone" value="${reservation.phone}" readonly>
                </div>
            </div>

            <div class="input-group">
                <label for="email">이메일 주소</label>
                <input type="email" id="email" name="email" value="${reservation.email}" readonly>
            </div>
        </div>

        <div class="form-section">
            <h2>좌석 정보</h2>
            <div class="seat-info">
                <h3>현재 좌석 상태</h3>
                <div class="seat-status">
                    <div class="status-indicator"></div>
                    <span class="status-text">좌석이 선택되었습니다.</span>
                    <span class="seat-number">${reservation.assignedSeat != null ? reservation.assignedSeat : '미선택'}</span>
                </div>
            </div>
        </div>

        <div class="form-section agreement-section">
            <h2>규정 및 동의</h2>
            <div class="agreement-item">
                <input type="checkbox" id="agreement1" name="agreement1">
                <label for="agreement1">위험물 안내 규정에 동의합니다.</label>
                <span class="required-text">(필수)</span>
                <button type="button" class="btn-view">보기</button>
            </div>
            <div class="agreement-item">
                <input type="checkbox" id="agreement2" name="agreement2">
                <label for="agreement2">개인정보 수집 및 이용에 동의합니다.</label>
                <span class="required-text">(필수)</span>
                <button type="button" class="btn-view">보기</button>
            </div>
        </div>

        <div class="form-actions">
            <button type="button" class="btn btn-secondary" onclick="history.back()">이전</button>
            <button type="submit" class="btn btn-primary">다음</button>
        </div>
    </form>
</main>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const applyForm = document.getElementById('applyForm');
    if (applyForm) {
        applyForm.addEventListener('submit', function(event) {
            const agreement1 = document.getElementById('agreement1');
            const agreement2 = document.getElementById('agreement2');

            // 필수 동의 항목이 하나라도 체크되지 않았다면
            if (!agreement1.checked || !agreement2.checked) {
                // 폼 제출(페이지 이동)을 막고 사용자에게 알림
                event.preventDefault(); 
                alert('필수 동의 항목을 모두 체크해주세요.');
            }
        });
    }
});
</script> 