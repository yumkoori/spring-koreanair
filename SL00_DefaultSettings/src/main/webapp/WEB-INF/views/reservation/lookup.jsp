<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>예약 조회</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/index.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700;800&display=swap" rel="stylesheet">
    
    <script>
        // contextPath 정의 (JavaScript에서 사용)
        window.contextPath = '${pageContext.request.contextPath}';
        console.log("ContextPath in head:", window.contextPath);
        console.log("ContextPath length:", window.contextPath.length);
    </script>
</head>
<body class="airline-main-body">
    <jsp:include page="../common/header.jsp" />

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
    .lookup-actions .btn-more { background: #60a5fa; color: white; padding: 12px 30px; border-radius: 20px; text-decoration: none; transition: background-color 0.3s; font-weight: 700;}
    .lookup-actions .btn-more:hover { background-color: #3b82f6; }
    .booking-error { text-align: center; margin: 0 auto 20px auto; padding: 20px; background-color: #fff5f5; border: 1px solid #fed7d7; border-radius: 12px; }
    .booking-error p { font-size: 16px; color: #d93025; font-weight: 500; margin:0; }
    .no-booking-container { text-align: center; margin: 20px auto; padding: 30px; background-color: #f8fbff; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
    .no-booking-message { font-size: 16px; color: #333; font-weight: 500; margin-bottom: 15px; }
    .btn-lookup-another { display: inline-flex; align-items: center; justify-content: center; background: #0064de; color: white; padding: 10px 20px; border-radius: 20px; text-decoration: none; transition: background-color 0.3s; }
    .btn-lookup-another i { margin-right: 5px; }
    .btn-lookup-another:hover { background-color: #0051a3; }
    
    .hidden {
        display: none !important;
    }
</style>

<section class="booking-widget">
    <div class="airline-container">
        <div class="booking-card">
            <div class="booking-tabs">
                <button class="booking-tab-btn" onclick="location.href='${pageContext.request.contextPath}/'">항공권 예매</button>
                <button class="booking-tab-btn active" data-tab="checkin">예약 조회</button>
                <button class="booking-tab-btn" onclick="location.href='${pageContext.request.contextPath}/checkin/lookup.htm'">체크인</button>
                <button class="booking-tab-btn" onclick="location.href='${pageContext.request.contextPath}/schedule/'">출도착/스케줄</button>
            </div>
            
            <div class="booking-content active" id="checkin">
                <div class="lookup-result-wrapper">
                    <c:choose>
                        <%-- [조건 1] 사용자가 로그인한 경우 --%>
                        <c:when test="${not empty sessionScope.user}">
                            <h3 style="text-align:center; margin-bottom: 20px;">'${sessionScope.user.koreanName}'님의 예약 내역</h3>
                            <c:choose>
                                <%-- [조건 1-1] 로그인 사용자의 예약 내역이 있는 경우 --%>
                                <c:when test="${not empty sessionScope.userBookings}">
                                    <div class="lookup-result-body" style="background: #f8fbff; border-radius: 18px; box-shadow: 0 4px 18px rgba(0,100,222,0.08); padding: 32px 28px; margin-bottom: 28px; display: flex; align-items: stretch;">
                                        <a href="${pageContext.request.contextPath}/reservation/lookup.htm" class="lookup-another lookup-another-abs" style="position: absolute; top: 32px; right: 28px;">
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
                                            <a href="${pageContext.request.contextPath}/reservation/detail.htm?bookingId=${sessionScope.userBookings[0].bookingId}" class="btn-more">더 보기</a>
                                        </div>
                                    </div>
                                </c:when>
                                <%-- [조건 1-2] 로그인했지만 예약 내역이 없는 경우 --%>
                                <c:otherwise>
                                    <div class="no-booking-container">
                                        <div class="no-booking-message">
                                            예약된 내역이 없습니다.
                                        </div>
                                        <a href="${pageContext.request.contextPath}/reservation/lookup.htm" class="btn btn-lookup-another">
                                            <i class="fa-solid fa-ticket"></i>
                                            <span>다른 예약 조회하기</span>
                                        </a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        
                        <%-- [조건 2] 사용자가 로그인하지 않은 경우 --%>
                        <c:otherwise>
                            <div class="form-description">
                                <p></p>
                            </div>
                            <div id="bookingErrorBox" class="booking-error hidden">
                                <p id="bookingErrorMessage"></p>
                            </div>
                    
                            <form class="checkin-form" action="${pageContext.request.contextPath}/reservation/lookup.htm" method="post" onsubmit="return false;">
                                <div class="checkin-inputs">
                                    <div class="input-group">
                                        <label for="lookupBookingId">예약번호</label>
                                        <input type="text" id="lookupBookingId" name="bookingId" placeholder="예) BKDON001" required>
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
        </div>
    </div>
</section>

<script>
    // contextPath는 이미 head에서 정의됨
    console.log("ContextPath in body script:", window.contextPath);
    
    // 에러 메시지 표시 (detail 페이지로 이동할 때는 표시하지 않음)
    <c:if test="${not empty error}">
        document.getElementById('bookingErrorBox').classList.remove('hidden');
        document.getElementById('bookingErrorMessage').textContent = '${error}';
    </c:if>
    
    // AJAX 폼 처리 함수
    function handleFormSubmit(form) {
        console.log('AJAX 처리 함수 시작');
        
        // 필수 동의 체크
        const agreeCheckbox = form.querySelector('input[name="agreeInfo"]');
        if (agreeCheckbox && !agreeCheckbox.checked) {
            alert('[필수] 항목에 동의해주셔야 조회가 가능합니다.');
            return;
        }
        
        // 에러 메시지 숨기기
        const errorBox = document.getElementById('bookingErrorBox');
        if (errorBox) errorBox.classList.add('hidden');
        
        // AJAX 요청
        const formData = new FormData(form);
        console.log('FormData 생성 완료, AJAX 요청 시작');
        
        fetch(window.contextPath + '/reservation/lookup.htm', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                'Accept': 'application/json'
            },
            body: new URLSearchParams(formData)
        })
        .then(response => {
            console.log('응답 받음:', response);
            return response.json();
        })
        .then(data => {
            console.log('JSON 데이터:', data);
            if (data.success) {
                const redirectUrl = window.contextPath + '/' + data.redirectUrl;
                console.log('리다이렉트 URL:', redirectUrl);
                window.location.href = redirectUrl;
            } else {
                const errorMessageElement = document.getElementById('bookingErrorMessage');
                if (errorBox && errorMessageElement) {
                    errorMessageElement.textContent = data.error || '알 수 없는 오류가 발생했습니다.';
                    errorBox.classList.remove('hidden');
                }
            }
        })
        .catch(error => {
            console.error('AJAX 오류:', error);
            const errorMessageElement = document.getElementById('bookingErrorMessage');
            if (errorBox && errorMessageElement) {
                errorMessageElement.textContent = '조회 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.';
                errorBox.classList.remove('hidden');
            }
        });
    }

    // AJAX 폼 처리 - 간단한 버전
    document.addEventListener('DOMContentLoaded', function() {
        console.log('페이지 로드됨 - contextPath:', window.contextPath);
        
        const lookupForm = document.querySelector('.checkin-form');
        console.log('폼 찾기:', lookupForm ? '성공' : '실패');
        
        if (lookupForm) {
            console.log('이벤트 리스너 등록 시작');
            
            // 먼저 클릭 이벤트로 테스트
            const submitBtn = lookupForm.querySelector('button[type="submit"], input[type="submit"], .search-flights-btn');
            console.log('제출 버튼 찾기:', submitBtn);
            
            if (submitBtn) {
                submitBtn.addEventListener('click', function(event) {
                    console.log('버튼 클릭 이벤트 발생!');
                    event.preventDefault();
                    console.log('기본 동작 방지됨');
                    
                    // 여기서 AJAX 처리
                    handleFormSubmit(lookupForm);
                });
            }
            
            lookupForm.addEventListener('submit', function(event) {
                console.log('폼 제출 이벤트 발생 - AJAX 처리 시작');
                event.preventDefault();
                handleFormSubmit(this);
            });
            
            // 폼에 onsubmit 속성 직접 설정 (추가 안전장치)
            lookupForm.onsubmit = function(e) { 
                console.log('onsubmit 핸들러 실행됨');
                e.preventDefault();
                return false; 
            };
        }
    });
</script>

    <jsp:include page="../common/footer.jsp" />
</body>
</html> 