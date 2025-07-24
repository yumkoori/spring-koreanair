<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    .mylist-wrapper { padding: 30px; }
    .mylist-header { text-align: center; margin-bottom: 30px; }
    .mylist-header h2 { font-size: 24px; font-weight: 800; color: #333; margin-bottom: 10px; }
    .mylist-header .user-info { font-size: 16px; color: #0064de; font-weight: 700; }
    
    .reservation-list { display: flex; flex-direction: column; gap: 20px; }
    .reservation-item { background: #f8fbff; border-radius: 18px; box-shadow: 0 4px 18px rgba(0,100,222,0.08); padding: 32px; }
    
    .reservation-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
    .booking-info { display: flex; align-items: center; gap: 15px; }
    .booking-status { font-size: 16px; font-weight: 700; color: #0064de; }
    .booking-id { font-size: 14px; color: #fff; background-color: #0064de; padding: 5px 10px; border-radius: 15px; }
    .booking-date { font-size: 14px; color: #666; }
    
    .flight-info { display: flex; align-items: center; gap: 40px; margin-bottom: 20px; }
    .route-airports { display: flex; align-items: center; gap: 15px; font-size: 24px; font-weight: 800; }
    .route-airports .fa-plane { color: #0064de; font-size: 20px; }
    .airport-details { text-align: center; }
    .airport-code { font-size: 32px; font-weight: 800; }
    .airport-name { font-size: 14px; font-weight: 400; color: #666; margin-top: 5px; }
    .flight-time { font-size: 16px; color: #222; }
    
    .reservation-actions { display: flex; gap: 15px; justify-content: flex-end; }
    .btn-action { padding: 10px 20px; border-radius: 20px; text-decoration: none; font-weight: 700; transition: background-color 0.3s; font-size: 14px; }
    .btn-detail { background: #60a5fa; color: white; }
    .btn-detail:hover { background: #3b82f6; }
    .btn-checkin { background: #0064de; color: white; }
    .btn-checkin:hover { background: #0051a3; }
    
    .no-reservation { text-align: center; margin: 20px auto; padding: 30px; background-color: #f8fbff; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
    .no-reservation-message { font-size: 16px; color: #333; font-weight: 500; margin-bottom: 15px; }
    .btn-book-now { display: inline-flex; align-items: center; justify-content: center; background: #0064de; color: white; padding: 10px 20px; border-radius: 20px; text-decoration: none; transition: background-color 0.3s; }
    .btn-book-now i { margin-right: 5px; }
    .btn-book-now:hover { background-color: #0051a3; }
    
    .error-message { text-align: center; margin: 20px auto; padding: 20px; background-color: #fff5f5; border: 1px solid #fed7d7; border-radius: 12px; }
    .error-message p { font-size: 16px; color: #d93025; font-weight: 500; margin: 0; }
</style>

<section class="booking-widget">
    <div class="airline-container">
        <div class="booking-card">
            <div class="booking-tabs">
                <button class="booking-tab-btn" onclick="location.href='${pageContext.request.contextPath}/'">항공권 예매</button>
                <button class="booking-tab-btn" onclick="location.href='${pageContext.request.contextPath}/reservation/lookup.htm'">예약 조회</button>
                <button class="booking-tab-btn" onclick="location.href='${pageContext.request.contextPath}/checkin/lookup.htm'">체크인</button>
                <button class="booking-tab-btn" onclick="location.href='${pageContext.request.contextPath}/schedule/'">출도착/스케줄</button>
            </div>
            
            <div class="booking-content active">
                <div class="mylist-wrapper">
                    <c:choose>
                        <c:when test="${not empty user}">
                            <div class="mylist-header">
                                <h2>내 예약 목록</h2>
                                <div class="user-info">'${user.koreanName}'님의 예약 내역</div>
                            </div>
                            
                            <c:choose>
                                <c:when test="${not empty reservationList and reservationList.size() > 0}">
                                    <div class="reservation-list">
                                        <c:forEach var="reservation" items="${reservationList}">
                                            <div class="reservation-item">
                                                <div class="reservation-header">
                                                    <div class="booking-info">
                                                        <span class="booking-status">구매 완료</span>
                                                        <span class="booking-id">예약번호: ${reservation.bookingId}</span>
                                                        <span class="booking-date">
                                                            <fmt:formatDate value="${reservation.createdAt}" pattern="yyyy년 MM월 dd일"/>
                                                        </span>
                                                    </div>
                                                </div>
                                                
                                                <div class="flight-info">
                                                    <div class="route-airports">
                                                        <div class="airport-details">
                                                            <div class="airport-code">${reservation.departureAirportId}</div>
                                                            <div class="airport-name">${reservation.departureAirportName}</div>
                                                        </div>
                                                        <i class="fas fa-plane"></i>
                                                        <div class="airport-details">
                                                            <div class="airport-code">${reservation.arrivalAirportId}</div>
                                                            <div class="airport-name">${reservation.arrivalAirportName}</div>
                                                        </div>
                                                    </div>
                                                    <div class="flight-time">
                                                        <div>
                                                            <strong>출발:</strong> 
                                                            <fmt:formatDate value="${reservation.departureTime}" pattern="yyyy년 MM월 dd일(E) HH:mm"/>
                                                        </div>
                                                        <div>
                                                            <strong>도착:</strong> 
                                                            <fmt:formatDate value="${reservation.arrivalTime}" pattern="yyyy년 MM월 dd일(E) HH:mm"/>
                                                        </div>
                                                        <div>
                                                            <strong>항공편:</strong> ${reservation.flightNumber}
                                                        </div>
                                                    </div>
                                                </div>
                                                
                                                <div class="reservation-actions">
                                                    <a href="${pageContext.request.contextPath}/reservation/detail.htm?bookingId=${reservation.bookingId}" class="btn-action btn-detail">
                                                        <i class="fas fa-info-circle"></i> 상세보기
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/checkin/detail.htm?bookingId=${reservation.bookingId}" class="btn-action btn-checkin">
                                                        <i class="fas fa-plane"></i> 체크인
                                                    </a>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="no-reservation">
                                        <div class="no-reservation-message">
                                            예약된 내역이 없습니다.
                                        </div>
                                        <a href="${pageContext.request.contextPath}/" class="btn-book-now">
                                            <i class="fa-solid fa-ticket"></i>
                                            <span>항공권 예매하기</span>
                                        </a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <div class="error-message">
                                <p>로그인이 필요합니다.</p>
                                <a href="${pageContext.request.contextPath}/auth/login.htm" class="btn-action btn-detail">
                                    <i class="fas fa-sign-in-alt"></i> 로그인
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</section> 