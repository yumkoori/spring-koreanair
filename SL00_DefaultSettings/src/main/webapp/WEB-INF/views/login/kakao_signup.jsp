<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.doit.member.util.KakaoApiService.KakaoUserInfo" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>카카오 회원가입 - AirLogin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700;800&display=swap" rel="stylesheet">
    <!-- 다음 우편번호 서비스 -->
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body class="airline-main-body">
    <jsp:include page="../common/header.jsp" />
    <main class="main-content">
    <div class="container register-container">
        <h1>카카오 회원가입</h1>
        <p style="margin-bottom: 2rem; color: #666;">추가 정보를 입력해주세요</p>
        
        <%
            // 에러 메시지 표시
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <div class="error"><%= error %></div>
        <%
            }
            
            // 세션에서 카카오 사용자 정보 가져오기
            KakaoUserInfo kakaoUserInfo = (KakaoUserInfo) session.getAttribute("kakaoUserInfo");
            if (kakaoUserInfo == null) {
        %>
            <div class="error">세션이 만료되었습니다. 다시 로그인해주세요.</div>
            <div class="actions">
                <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">로그인 페이지로</a>
            </div>
        <%
            } else {
        %>
            <!-- 카카오 사용자 정보 표시 -->
            <div class="kakao-info" style="background: #f8f9fa; padding: 1rem; border-radius: 8px; margin-bottom: 1.5rem;">
                <h3>카카오 계정 정보</h3>
                <% if (kakaoUserInfo.getProfileImageUrl() != null) { %>
                    <img src="<%= kakaoUserInfo.getProfileImageUrl() %>" alt="프로필 이미지" 
                         style="width: 60px; height: 60px; border-radius: 50%; margin-bottom: 10px;">
                <% } %>
                <p><strong>닉네임:</strong> <%= kakaoUserInfo.getNickname() != null ? kakaoUserInfo.getNickname() : "미제공" %></p>
                <p><strong>이메일:</strong> <%= kakaoUserInfo.getEmail() != null ? kakaoUserInfo.getEmail() : "미제공" %></p>
            </div>
            
            <!-- 추가 정보 입력 폼 -->
            <form action="${pageContext.request.contextPath}/kakao/signup" method="post" onsubmit="return validateKakaoSignupForm()">
                <div class="form-group">
                    <label for="koreanName">한글 이름 *</label>
                    <input type="text" id="koreanName" name="koreanName" required 
                           placeholder="한글 이름을 입력하세요"
                           value="<%= kakaoUserInfo.getNickname() != null ? kakaoUserInfo.getNickname() : "" %>">
                </div>
                
                <div class="form-group">
                    <label for="englishName">영문 이름 *</label>
                    <input type="text" id="englishName" name="englishName" required 
                           placeholder="영문 이름을 입력하세요">
                </div>
                
                <div class="form-group">
                    <label for="birthDate">생년월일 *</label>
                    <input type="date" id="birthDate" name="birthDate" required>
                </div>
                
                <div class="form-group">
                    <label for="gender">성별 *</label>
                    <select id="gender" name="gender" required>
                        <option value="">선택하세요</option>
                        <option value="M">남성</option>
                        <option value="F">여성</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="phone">전화번호 *</label>
                    <input type="tel" id="phone" name="phone" required 
                           placeholder="010-1234-5678" pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}">
                </div>
                
                <div class="form-group">
                    <label for="address">자택 주소</label>
                    <div class="address-input-group">
                        <div class="address-search">
                            <input type="text" id="postcode" name="postcode" placeholder="우편번호" readonly>
                            <button type="button" class="btn-address-search" onclick="openPostcodeSearch()">
                                <i class="fas fa-search"></i> 주소 검색
                            </button>
                        </div>
                        <input type="text" id="roadAddress" name="roadAddress" placeholder="도로명주소" readonly>
                        <input type="text" id="jibunAddress" name="jibunAddress" placeholder="지번주소" readonly style="display: none;">
                        <input type="text" id="detailAddress" name="detailAddress" placeholder="상세주소를 입력하세요">
                        <input type="hidden" id="address" name="address">
                    </div>
                    <small class="address-help-text" style="margin-top: 1rem;">
                        <i class="fas fa-info-circle"></i>
                        * 표시는 필수 입력 사항입니다
                    </small>
                </div>
                
                <button type="submit" class="btn btn-primary">회원가입 완료</button>
            </form>
            
            <div class="actions" style="margin-top: 1rem;">
                <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary">취소</a>
            </div>
        <%
            }
        %>
        
        <div class="links">
            <a href="./">메인 페이지로 돌아가기</a>
            <p style="color: #999; font-size: 12px; margin-top: 2rem;">
                © 2024 AirLogin. 모든 권리 보유.
            </p>
        </div>
    </div>
    </main>
    
    <jsp:include page="../common/footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/index.js"></script>
    <script src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
</html> 