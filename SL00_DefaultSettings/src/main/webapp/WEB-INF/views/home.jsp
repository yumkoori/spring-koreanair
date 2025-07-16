<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

<h3>
  <a href="/time">/time</a>
  
  <!--
      1. <a href="/time">/time</a>  요청
      2. org.doit.ik.mapper.TimeMapper 인터페이스
      3. ORM( mybatis ) 
         (1) pom.xml        
			<dependency>
			    <groupId>org.mybatis</groupId>
			    <artifactId>mybatis</artifactId>
			    <version>3.4.6</version>
			</dependency>
			<dependency>
			    <groupId>org.mybatis</groupId>
			    <artifactId>mybatis-spring</artifactId>
			    <version>1.3.2</version>
			</dependency>
         (2) org.doit.ik.mapper 패키지 안에 mybatis-config.xml 생성
         (3) src/main/resources 폴더 
               ㄴorg 폴더 생성
                  ㄴ doit 폴더 생성
                    ㄴ ik 폴더 생성
                      ㄴ mapper 폴더 생성
                          ㄴ TimeMapper.xml 추가 (== TimeMapper.java 인터페이스명 )
                             매퍼파일
        (4) log4jdbc.log4j2.properties 파일 추가
            log4j.xml 수정   
        (5) root-context.xml 파일 수정     
        (6) ojdbc6.jar 설정        
     4.  views 폴더
           ㄴ time.jsp 추가
     5. 커맨드 핸들러 추가 
       HomeController.java 복사해서 TimeMybatisController.java                    
   -->
  
  
</h3>

</body>
</html>
