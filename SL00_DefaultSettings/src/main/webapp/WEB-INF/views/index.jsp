<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=yes">
    <meta name="format-detection" content="telephone=no">
    <title>항공사 웹사이트</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700;800&display=swap" rel="stylesheet">
    
    <script>
        // contextPath 설정 - JavaScript에서 사용할 수 있도록 전역 변수로 설정
        window.contextPath = '${pageContext.request.contextPath}';
    </script>
    
    <style>
        .lookup-result-wrapper { padding: 30px; }
        .lookup-result-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px; }
        .lookup-status-container { display: flex; align-items: center; gap: 10px; }
        .lookup-status { font-size: 16px; font-weight: 700; color: #0064de; }
        .lookup-booking-id { font-size: 14px; color: #fff; background-color: #0064de; padding: 5px 10px; border-radius: 15px; }
        .lookup-another { font-size: 14px; color: #555; text-decoration: none; }
        .lookup-another:hover { text-decoration: underline; }
        .lookup-result-body { display: flex; justify-content: space-between; align-items: center; }
        .lookup-route-info { display: flex; align-items: center; gap: 20px; }
        .lookup-route-airports { display: flex; align-items: center; gap: 15px; font-size: 24px; font-weight: 800; }
        .lookup-route-airports .fa-plane { color: #0064de; font-size: 20px; }
        .lookup-route-airports .airport-details { line-height: 1.2; }
        .lookup-route-airports .airport-name { font-size: 14px; font-weight: 400; color: #666; }
        .lookup-flight-time { font-size: 14px; color: #333; }
        .lookup-actions .btn-more { background: #60a5fa; color: white; padding: 12px 30px; border-radius: 20px; text-decoration: none; transition: background-color 0.3s; font-weight: 700; border: none; cursor: pointer; font-size: 14px;}
        .lookup-actions .btn-more:hover { background-color: #3b82f6; }
        .hidden { display: none !important; }
        .no-booking-container { text-align: center; margin: 20px auto; padding: 30px; background-color: #f8fbff; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        .no-booking-message { font-size: 16px; color: #333; font-weight: 500; margin-bottom: 15px; }
        .btn-lookup-another { display: inline-flex; align-items: center; justify-content: center; background: #0064de; color: white; padding: 10px 20px; border-radius: 20px; text-decoration: none; transition: background-color 0.3s; }
        .btn-lookup-another i { margin-right: 5px; }
        .btn-lookup-another:hover { background-color: #0051a3; }
        
        /* 공항 검색 드롭다운 스타일 */
        .airport-dropdown {
            position: fixed;
            background: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            width: 350px;
            max-height: 400px;
            z-index: 10000;
            font-family: 'Nanum Gothic', sans-serif;
        }
        
        .dropdown-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px 20px;
            border-bottom: 1px solid #eee;
            background: #f8f9fa;
            border-radius: 8px 8px 0 0;
        }
        
        .dropdown-header span {
            font-weight: 600;
            color: #333;
            font-size: 14px;
        }
        
        .close-btn {
            background: none;
            border: none;
            font-size: 20px;
            color: #666;
            cursor: pointer;
            padding: 0;
            width: 20px;
            height: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .close-btn:hover {
            color: #333;
        }
        
        .search-box {
            padding: 15px 20px;
            border-bottom: 1px solid #eee;
        }
        
        .search-box input {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            outline: none;
        }
        
        .search-box input:focus {
            border-color: #0066cc;
        }
        
        .search-results {
            max-height: 250px;
            overflow-y: auto;
        }
        
        .dropdown-item {
            padding: 12px 20px;
            cursor: pointer;
            border-bottom: 1px solid #f0f0f0;
            transition: background-color 0.2s;
        }
        
        .dropdown-item:hover {
            background-color: #f8f9fa;
        }
        
        .dropdown-item:last-child {
            border-bottom: none;
        }
        
        .dropdown-item.no-result {
            color: #999;
            text-align: center;
            cursor: default;
        }
        
        .dropdown-item.no-result:hover {
            background-color: transparent;
        }
        
        .airport-item {
            display: flex;
            align-items: center;
            gap: 12px;
        }
        
        .airport-item .airport-code {
            font-weight: 700;
            font-size: 16px;
            color: #0066cc;
            min-width: 45px;
        }
        
        .airport-info {
            flex: 1;
        }
        
        .airport-info .airport-name {
            font-weight: 600;
            color: #333;
            font-size: 14px;
            margin-bottom: 2px;
        }
        
        .airport-info .airport-full-name {
            font-size: 12px;
            color: #666;
        }
        
        /* 출발지/도착지 입력 필드 클릭 가능하도록 스타일 추가 */
        .airport-input {
            cursor: pointer;
            transition: background-color 0.2s;
        }
        
        .airport-input:hover {
            background-color: #f8f9fa;
        }
        
        /* 복귀일 입력 필드 스타일 */
        .date-section {
            display: flex;
            gap: 15px;
        }
        
        .date-input {
            flex: 1;
        }
        
        .return-date {
            transition: opacity 0.3s ease, transform 0.3s ease;
        }
    </style>
</head>
<body class="airline-main-body">
    <jsp:include page="common/header.jsp" />

    <section class="booking-widget">
        <div class="airline-container">
            <div class="booking-card">
                <div class="booking-tabs">
                    <button class="booking-tab-btn active" data-tab="flight">항공권 예매</button>
                    <button class="booking-tab-btn" data-tab="checkin">예약 조회</button>
                    <button class="booking-tab-btn" data-tab="schedule">체크인</button>
                    <button class="booking-tab-btn" data-tab="status">출도착/스케줄</button>
                </div>
                
                <div class="booking-content active" id="flight">
                    <div class="trip-type-section">
                        <div class="trip-type-buttons">
                            <button class="trip-type-btn active" data-type="round">왕복</button>
                            <button class="trip-type-btn" data-type="oneway">편도</button>
                            <button class="trip-type-btn" data-type="multi">다구간</button>
                        </div>
                        <div class="special-options">
                            <label class="checkbox-label">
                                <input type="checkbox" id="award-ticket">
                                <span class="checkmark"></span>
                                가까운 날짜 함께 조회
                            </label>
                        </div>
                    </div>
                    
                    <div class="route-section">
                        <div class="route-inputs">
                            <div class="airport-input departure">
                                <div class="airport-code">CJU</div>
                                <div class="airport-name">제주</div>
                            </div>
                            <button class="swap-route-btn">
                                <i class="fas fa-exchange-alt"></i>
                            </button>
                            <div class="airport-input arrival">
                                <div class="airport-code">GMP</div>
                                <div class="airport-name">서울/김포</div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- 출발지 검색 드롭다운 -->
                    <div id="departure-dropdown" class="airport-dropdown" style="display: none;">
                        <div class="dropdown-header">
                            <span>출발지를 선택하세요</span>
                            <button id="departure-close" class="close-btn">&times;</button>
                        </div>
                        <div class="search-box">
                            <input type="text" id="departure-search" placeholder="도시명 또는 공항코드를 입력하세요">
                        </div>
                        <div id="departure-results" class="search-results"></div>
                    </div>
                    
                    <!-- 도착지 검색 드롭다운 -->
                    <div id="arrival-dropdown" class="airport-dropdown" style="display: none;">
                        <div class="dropdown-header">
                            <span>도착지를 선택하세요</span>
                            <button id="arrival-close" class="close-btn">&times;</button>
                        </div>
                        <div class="search-box">
                            <input type="text" id="arrival-search" placeholder="도시명 또는 공항코드를 입력하세요">
                        </div>
                        <div id="arrival-results" class="search-results"></div>
                    </div>
                    
                    <div class="booking-details">
                        <div class="date-section">
                            <div class="date-input">
                                <label>출발일</label>
                                <input type="date" value="2025-07-15" id="departureDate">
                            </div>
                            <div class="date-input return-date" id="returnDateSection">
                                <label>복귀일</label>
                                <input type="date" value="2025-07-16" id="returnDate">
                            </div>
                        </div>
                        
                        <div class="passenger-section">
                            <div class="passenger-input">
                                <label>탑승객</label>
                                <select>
                                    <option>성인 1명</option>
                                    <option>성인 2명</option>
                                    <option>성인 3명</option>
                                    <option>성인 4명</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="class-section">
                            <div class="class-input">
                                <label>좌석 등급</label>
                                <select>
                                    <option>선택하세요</option>
                                    <option>일반석</option>
                                    <option>비즈니스석</option>
                                    <option>일등석</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="search-section">
                            <form id="searchForm" action="${pageContext.request.contextPath}/api/search/flight" method="get">
                                <!-- Hidden inputs for form submission -->
                                <input type="hidden" name="departure" id="departureInput">
                                <input type="hidden" name="arrival" id="arrivalInput">
                                <input type="hidden" name="departureDate" id="departureDateInput">
                                <input type="hidden" name="returnDate" id="returnDateInput">
                                <input type="hidden" name="passengers" id="passengersInput">
                                <input type="hidden" name="seatClass" id="seatClassInput">
                                <input type="hidden" name="tripType" id="tripTypeInput">
                                <button type="submit" class="search-flights-btn">항공편 검색</button>
                            </form>
                        </div>
                    </div>
                </div>
                
<div class="booking-content" id="checkin">
    <div class="lookup-result-wrapper">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <h3 style="text-align:center; margin-bottom: 20px;">'${sessionScope.user.koreanName}'님의 예약 내역</h3>
                <c:choose>
                    <c:when test="${not empty sessionScope.userBookings}">
                        <div class="lookup-result-body" style="background: #f8fbff; border-radius: 18px; box-shadow: 0 4px 18px rgba(0,100,222,0.08); padding: 32px 28px; margin-bottom: 28px; display: flex; align-items: stretch;">
                            <a href="${pageContext.request.contextPath}/lookupForm.do" class="lookup-another lookup-another-abs" style="position: absolute; top: 32px; right: 28px;">
                                다른 예약 조회 <i class="fas fa-chevron-right" style="font-size:12px;"></i>
                            </a>
                            <div style="flex:1; display:flex; flex-direction:column; justify-content:space-between;">
                                <div class="lookup-result-header" style="margin-bottom:18px;">
                                    <div class="lookup-status-container">
                                        <span class="lookup-status">구매 완료</span>
                                        <span class="lookup-booking-id">예약번호 : ${sessionScope.userBookings[0].bookingId}</span>
                                    </div>
                                </div>
                                <div class="lookup-route-info" style="display:flex; align-items:center; gap: 40px;">
                                    <div class="lookup-route-airports">
                                        <div class="airport-details" style="text-align:center;">
                                            <div style="font-size:32px; font-weight:800;">${sessionScope.userBookings[0].departureAirportId}</div>
                                            <div class="airport-name">${sessionScope.userBookings[0].departureAirportName}</div>
                                        </div>
                                        <i class="fas fa-plane"></i>
                                        <div class="airport-details" style="text-align:center;">
                                            <div style="font-size:32px; font-weight:800;">${sessionScope.userBookings[0].arrivalAirportId}</div>
                                            <div class="airport-name">${sessionScope.userBookings[0].arrivalAirportName}</div>
                                        </div>
                                    </div>
                                    <div style="display:flex; flex-direction:column; justify-content:center;">
                                        <div class="lookup-flight-time" style="font-size:16px; color:#222;">
                                            <fmt:formatDate value="${sessionScope.userBookings[0].departureTime}" pattern="yyyy년 MM월 dd일(E) HH:mm"/>
                                            ~
                                            <fmt:formatDate value="${sessionScope.userBookings[0].arrivalTime}" pattern="HH:mm"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="lookup-actions" style="display:flex; align-items:flex-end; margin-left:40px;">
                                <button type="button" class="btn-more" id="loginLookupBtn"
                                    data-bookingid="${sessionScope.userBookings[0].bookingId}"
                                    data-departuredate="<fmt:formatDate value='${sessionScope.userBookings[0].departureTime}' pattern='yyyy-MM-dd'/>"
                                    data-lastname="${sessionScope.userBookings[0].lastName}"
                                    data-firstname="${sessionScope.userBookings[0].firstName}">
                                    더 보기
                                </button>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="no-booking-container">
                            <div class="no-booking-message">
                                예약된 내역이 없습니다.
                            </div>
                            <a href="${pageContext.request.contextPath}/lookupForm.do" class="btn btn-lookup-another">
                                <i class="fa-solid fa-ticket"></i>
                                <span>다른 예약 조회하기</span>
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:when>
            
            <c:otherwise>
                <div class="form-description">
                    <p></p>
                </div>
                <div id="bookingErrorBox" class="booking-error hidden">
                    <p id="bookingErrorMessage"></p>
                </div>
                
                <form class="checkin-form" action="${pageContext.request.contextPath}/reservation/lookup.htm" method="post" accept-charset="UTF-8" onsubmit="return false;">
                    <div class="checkin-inputs">
                        <div class="input-group">
                            <label for="lookupBookingId">예약번호 또는 항공권번호</label>
                            <input type="text" id="lookupBookingId" name="bookingId" placeholder="예약번호 6자리 입력" required>
                        </div>
                        <div class="input-group">
                            <label for="lookupDepartureDate">출발일</label>
                            <input type="date" id="lookupDepartureDate" name="departureDate" required>
                        </div>
                        <div class="input-group">
                            <label for="lookupLastName">성 (영문)</label>
                            <input type="text" id="lookupLastName" name="lastName" placeholder="HONG" required>
                        </div>
                        <div class="input-group">
                            <label for="lookupFirstName">이름 (영문)</label>
                            <input type="text" id="lookupFirstName" name="firstName" placeholder="GILDONG" required>
                        </div>
                        <div class="search-section">
                            <button type="submit" class="search-flights-btn">조회</button>
                        </div>
                    </div>
                    
                    <div class="form-notice" style="margin-top: 20px;">
                        <label class="checkbox-label">
                            <input type="checkbox" name="agreeInfo" required>
                            <span class="checkmark"></span>
                            [필수] 본인의 예약 정보이거나 승객으로부터 조회를 위임 받은 예약 정보입니다.
                        </label>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</div>
					<%-- '체크인' 탭 (최종 수정) --%>
<div class="booking-content" id="schedule">
    <div class="lookup-result-wrapper">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                   <h3 style="text-align:center; margin-bottom: 20px;">'${sessionScope.user.koreanName}'님의 예약으로 체크인</h3>
                <c:choose>
                    <c:when test="${not empty sessionScope.userBookings}">
                        <div class="lookup-result-body" style="background: #f8fbff; border-radius: 18px; box-shadow: 0 4px 18px rgba(0,100,222,0.08); padding: 32px 28px; margin-bottom: 28px; display: flex; align-items: stretch;">
                            <a href="${pageContext.request.contextPath}/checkupForm.do" class="lookup-another lookup-another-abs" style="position: absolute; top: 32px; right: 28px;">
                                다른 체크인 조회 <i class="fas fa-chevron-right" style="font-size:12px;"></i>
                            </a>
                            <div style="flex:1; display:flex; flex-direction:column; justify-content:space-between;">
                                <div class="lookup-result-header" style="margin-bottom:18px;">
                                    <div class="lookup-status-container">
                                        <span class="lookup-status">구매 완료</span>
                                        <span class="lookup-booking-id">예약번호 : ${sessionScope.userBookings[0].bookingId}</span>
                                    </div>
                                </div>
                                <div class="lookup-route-info" style="display:flex; align-items:center; gap: 40px;">
                                    <div class="lookup-route-airports">
                                        <div class="airport-details" style="text-align:center;">
                                            <div style="font-size:32px; font-weight:800;">${sessionScope.userBookings[0].departureAirportId}</div>
                                            <div class="airport-name">${sessionScope.userBookings[0].departureAirportName}</div>
                                        </div>
                                        <i class="fas fa-plane"></i>
                                        <div class="airport-details" style="text-align:center;">
                                            <div style="font-size:32px; font-weight:800;">${sessionScope.userBookings[0].arrivalAirportId}</div>
                                            <div class="airport-name">${sessionScope.userBookings[0].arrivalAirportName}</div>
                                        </div>
                                    </div>
                                    <div style="display:flex; flex-direction:column; justify-content:center;">
                                        <div class="lookup-flight-time" style="font-size:16px; color:#222;">
                                            <fmt:formatDate value="${sessionScope.userBookings[0].departureTime}" pattern="yyyy년 MM월 dd일(E) HH:mm"/>
                                            ~
                                            <fmt:formatDate value="${sessionScope.userBookings[0].arrivalTime}" pattern="HH:mm"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="lookup-actions" style="display:flex; align-items:flex-end; margin-left:40px;">
                                <button type="button" class="btn-more" id="loginCheckinBtn"
                                    data-bookingid="${sessionScope.userBookings[0].bookingId}"
                                    data-departuredate="<fmt:formatDate value='${sessionScope.userBookings[0].departureTime}' pattern='yyyy-MM-dd'/>"
                                    data-lastname="${sessionScope.userBookings[0].lastName}"
                                    data-firstname="${sessionScope.userBookings[0].firstName}">
                                    체크인
                                </button>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="no-booking-container">
                            <div class="no-booking-message">체크인 가능한 예약 내역이 없습니다.</div>
                            <a href="${pageContext.request.contextPath}/checkin/lookup.htm" class="btn btn-lookup-another">
                                <i class="fa-solid fa-ticket"></i>
                                <span>다른 예약으로 체크인하기</span>
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <div class="form-description">
                    <p></p>
                </div>
                
                <div id="checkinErrorBox" class="booking-error hidden">
                    <p id="checkinErrorMessage"></p>
                </div>
                
                <form id="checkinLookupForm" action="${pageContext.request.contextPath}/checkin/lookup.htm" method="post" accept-charset="UTF-8" onsubmit="return false;">
                    <div class="schedule-inputs">
                        <div class="input-group">
                            <label for="checkinBookingId">예약/항공권 번호</label>
                            <input type="text" id="checkinBookingId" name="bookingId" placeholder="예약번호 6자리" required>
                        </div>
                        <div class="input-group">
                            <label for="checkinDepartureDate">출발일</label>
                            <input type="date" id="checkinDepartureDate" name="departureDate" required>
                        </div>
                        <div class="input-group">
                            <label for="checkinLastName">성 (영문)</label>
                            <input type="text" id="checkinLastName" name="lastName" placeholder="HONG" required>
                        </div>
                        <div class="input-group">
                            <label for="checkinFirstName">이름 (영문)</label>
                            <input type="text" id="checkinFirstName" name="firstName" placeholder="GILDONG" required>
                        </div>
                        <div class="search-section">
                            <button type="submit" class="search-flights-btn">체크인 시작</button>
                        </div>
                    </div>
                    
                    <div class="form-notice" style="margin-top: 20px;">
                        <label class="checkbox-label">
                            <input type="checkbox" name="agreeInfo" required>
                            <span class="checkmark"></span>
                            [필수] 본인의 예약 정보이거나 승객으로부터 조회를 위임 받은 예약 정보입니다.
                        </label>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</div>

                <div class="booking-content" id="status">
                    <div class="status-form">
                        <div class="status-options">
                            <button class="status-btn active">출도착 조회</button>
                            <button class="status-btn">수간 스케줄</button>
                            <button class="status-btn">출/도착지</button>
                            <button class="status-btn">편명</button>
                        </div>
                        <div class="status-inputs">
                            <div class="route-inputs">
                                <div class="airport-input">
                                    <div class="airport-code">From</div>
                                    <div class="airport-name">출발지</div>
                                </div>
                                <button class="swap-route-btn">
                                    <i class="fas fa-exchange-alt"></i>
                                </button>
                                <div class="airport-input">
                                    <div class="airport-code">To</div>
                                    <div class="airport-name">도착지</div>
                                </div>
                            </div>
                            <div class="date-input">
                                <label>출발일</label>
                                <input type="date" value="2025-07-15">
                            </div>
                            <div class="search-section">
                                <button class="search-flights-btn">조회</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="banner">
        <div class="airline-container">
            <div class="banner-slider">
                <div class="slide active" style="background-image: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)), url('https://images.unsplash.com/photo-1436491865332-7a61a109cc05?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');">
                    <div class="slide-content">
                        <h2>유럽 노선 특가 이벤트</h2>
                        <p>꿈꾸던 유럽 여행, 지금 특별한 가격으로 만나보세요</p>
                        <a href="#" class="btn airline-btn">자세히 보기</a>
                    </div>
                </div>
                <div class="slide" style="background-image: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)), url('https://images.unsplash.com/photo-1540339832862-474599807836?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');">
                    <div class="slide-content">
                        <h2>프리미엄 신규 기내 서비스</h2>
                        <p>더욱 편안하고 특별한 여행을 위한 새로운 기내 서비스를 소개합니다</p>
                        <a href="#" class="btn airline-btn">자세히 보기</a>
                    </div>
                </div>
                <div class="slide" style="background-image: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)), url('https://images.unsplash.com/photo-1464038008305-ee8def75f234?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');">
                    <div class="slide-content">
                        <h2>마일리지 더블 적립 혜택</h2>
                        <p>이번 달 특별 프로모션, 모든 노선 마일리지 두 배 적립</p>
                        <a href="#" class="btn airline-btn">자세히 보기</a>
                    </div>
                </div>
            </div>
            <div class="banner-nav">
                <button class="prev airline-btn-nav"><i class="fas fa-chevron-left"></i></button>
                <div class="dots">
                    <span class="dot active"></span>
                    <span class="dot"></span>
                    <span class="dot"></span>
                </div>
                <button class="next airline-btn-nav"><i class="fas fa-chevron-right"></i></button>
            </div>
        </div>
    </section>

    <section class="features">
        <div class="airline-container">
            <div class="feature-box">
                <div class="feature-icon">
                    <i class="fas fa-map-marked-alt"></i>
                </div>
                <h3>취항지 안내</h3>
                <p>전 세계 40개국 120개 도시로 편리하게 여행하세요</p>
                <a href="#" class="feature-link airline-link">더 알아보기</a>
            </div>
            <div class="feature-box">
                <div class="feature-icon">
                    <i class="fas fa-plane"></i>
                </div>
                <h3>항공기 소개</h3>
                <p>최신 항공기 보유로 안전하고 쾌적한 여행을 약속합니다</p>
                <a href="#" class="feature-link airline-link">더 알아보기</a>
            </div>
            <div class="feature-box">
                <div class="feature-icon">
                    <i class="fas fa-gift"></i>
                </div>
                <h3>마일리지 혜택</h3>
                <p>다양한 적립과 사용으로 더 많은 혜택을 누리세요</p>
                <a href="#" class="feature-link airline-link">더 알아보기</a>
            </div>
            <div class="feature-box">
                <div class="feature-icon">
                    <i class="fas fa-headset"></i>
                </div>
                <h3>고객 지원</h3>
                <p>연중무휴 고객센터 운영으로 문의사항을 빠르게 해결해 드립니다</p>
                <a href="#" class="feature-link airline-link">더 알아보기</a>
            </div>
        </div>
    </section>

    <section class="destinations">
        <div class="airline-container">
            <h2 class="section-title">인기 여행지</h2>
            <div class="destination-grid">
                <div class="destination-card">
                    <div class="destination-img airline-img" style="background-image: url('https://images.unsplash.com/photo-1499856871958-5b9627545d1a?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');"></div>
                    <div class="destination-info">
                        <h3>파리</h3>
                        <p class="price">편도 <span>650,000원~</span></p>
                        <a href="#" class="btn-outline airline-btn">예매하기</a>
                    </div>
                </div>
                <div class="destination-card">
                    <div class="destination-img airline-img" style="background-image: url('https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');"></div>
                    <div class="destination-info">
                        <h3>뉴욕</h3>
                        <p class="price">편도 <span>780,000원~</span></p>
                        <a href="#" class="btn-outline airline-btn">예매하기</a>
                    </div>
                </div>
                <div class="destination-card">
                    <div class="destination-img airline-img" style="background-image: url('https://images.unsplash.com/photo-1513407030348-c983a97b98d8?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');"></div>
                    <div class="destination-info">
                        <h3>도쿄</h3>
                        <p class="price">편도 <span>250,000원~</span></p>
                        <a href="#" class="btn-outline airline-btn">예매하기</a>
                    </div>
                </div>
                <div class="destination-card">
                    <div class="destination-img airline-img" style="background-image: url('https://images.unsplash.com/photo-1506973035872-a4ec16b8e8d9?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');"></div>
                    <div class="destination-info">
                        <h3>시드니</h3>
                        <p class="price">편도 <span>720,000원~</span></p>
                        <a href="#" class="btn-outline airline-btn">예매하기</a>
                    </div>
                </div>
                <div class="destination-card">
                    <div class="destination-img airline-img" style="background-image: url('https://images.unsplash.com/photo-1528181304800-259b08848526?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');"></div>
                    <div class="destination-info">
                        <h3>방콕</h3>
                        <p class="price">편도 <span>280,000원~</span></p>
                        <a href="#" class="btn-outline airline-btn">예매하기</a>
                    </div>
                </div>
                <div class="destination-card">
                    <div class="destination-img airline-img" style="background-image: url('https://images.unsplash.com/photo-1552832230-c0197dd311b5?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');"></div>
                    <div class="destination-info">
                        <h3>로마</h3>
                        <p class="price">편도 <span>680,000원~</span></p>
                        <a href="#" class="btn-outline airline-btn">예매하기</a>
                    </div>
                </div>
            </div>
            <div class="view-more">
                <a href="#" class="btn airline-btn">더 많은 여행지 보기</a>
            </div>
        </div>
    </section>

    <section class="news">
        <div class="airline-container">
            <h2 class="section-title">항공사 소식</h2>
            <div class="news-grid">
                <div class="news-card">
                    <div class="news-img airline-img" style="background-image: url('https://images.unsplash.com/photo-1436491865332-7a61a109cc05?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');"></div>
                    <div class="news-content">
                        <span class="news-date">2023.07.15</span>
                        <h3>신규 노선 오픈 안내</h3>
                        <p>2023년 9월부터 새롭게 선보이는 대양주 노선에 대한 안내입니다.</p>
                        <a href="#" class="read-more airline-link">자세히 보기</a>
                    </div>
                </div>
                <div class="news-card">
                    <div class="news-img airline-img" style="background-image: url('https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');"></div>
                    <div class="news-content">
                        <span class="news-date">2023.07.08</span>
                        <h3>프리미엄 기내식 리뉴얼</h3>
                        <p>최고의 셰프와 협업한 새로운 기내식 메뉴를 소개합니다.</p>
                        <a href="#" class="read-more airline-link">자세히 보기</a>
                    </div>
                </div>
                <div class="news-card">
                    <div class="news-img airline-img" style="background-image: url('https://images.unsplash.com/photo-1530521954074-e64f6810b32d?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');"></div>
                    <div class="news-content">
                        <span class="news-date">2023.07.01</span>
                        <h3>여름 시즌 특별 프로모션</h3>
                        <p>여름 휴가철 맞이 특별 할인 프로모션을 확인하세요.</p>
                        <a href="#" class="read-more airline-link">자세히 보기</a>
                    </div>
                </div>
            </div>
            <div class="view-more">
                <a href="#" class="btn airline-btn">모든 소식 보기</a>
            </div>
        </div>
    </section>

    <jsp:include page="common/footer.jsp" />

    <script src="${pageContext.request.contextPath}/js/index.js"></script>
</body>
</html>