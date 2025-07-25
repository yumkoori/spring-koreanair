<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MariaDB 연결 테스트</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .header {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
        }
        .result-box {
            padding: 20px;
            border-radius: 8px;
            margin: 20px 0;
        }
        .success {
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
        }
        .error {
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #721c24;
        }
        .info-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .info-table th, .info-table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .info-table th {
            background-color: #f8f9fa;
            font-weight: bold;
        }
        .btn {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin: 10px 5px;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .btn-secondary {
            background-color: #6c757d;
        }
        .btn-secondary:hover {
            background-color: #545b62;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🗄️ MariaDB 연결 테스트</h1>
            <p>데이터베이스 연결 상태를 확인합니다</p>
        </div>

        <c:choose>
            <c:when test="${testResult == '성공'}">
                <div class="result-box success">
                    <h3>✅ ${message}</h3>
                    
                    <table class="info-table">
                        <tr>
                            <th>테스트 결과</th>
                            <td>${testValue}</td>
                        </tr>
                        <tr>
                            <th>현재 시간</th>
                            <td>${currentTime}</td>
                        </tr>
                        <tr>
                            <th>데이터베이스 버전</th>
                            <td>${dbVersion}</td>
                        </tr>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div class="result-box error">
                    <h3>❌ ${message}</h3>
                    <c:if test="${not empty error}">
                        <p><strong>오류 내용:</strong> ${error}</p>
                    </c:if>
                </div>
            </c:otherwise>
        </c:choose>

        <div style="text-align: center; margin-top: 30px;">
            <a href="${pageContext.request.contextPath}/ik/test" class="btn">다시 테스트</a>
            <a href="${pageContext.request.contextPath}/ik/test/json" class="btn btn-secondary">JSON 결과 보기</a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">홈으로</a>
        </div>

        <div style="margin-top: 30px; padding: 20px; background-color: #f8f9fa; border-radius: 5px;">
            <h4>📋 연결 정보</h4>
            <ul>
                <li><strong>드라이버:</strong> org.mariadb.jdbc.Driver</li>
                <li><strong>연결 URL:</strong> jdbc:mariadb://air.chkmcmk8aoyu.ap-northeast-2.rds.amazonaws.com:3306/air_db</li>
                <li><strong>사용자:</strong> admin</li>
                <li><strong>연결 풀:</strong> HikariCP</li>
            </ul>
        </div>
    </div>
</body>
</html> 