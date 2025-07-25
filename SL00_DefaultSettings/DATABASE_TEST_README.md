# MariaDB 연결 테스트 가이드

## 📋 개요
이 프로젝트에서 MariaDB 연결 상태를 확인하는 방법을 안내합니다.

## 🗄️ 데이터베이스 설정 정보
- **호스트**: air.chkmcmk8aoyu.ap-northeast-2.rds.amazonaws.com:3306
- **데이터베이스**: air_db
- **사용자**: admin
- **드라이버**: org.mariadb.jdbc.Driver
- **연결 풀**: HikariCP

## 🔧 설정 파일들

### 1. Maven 의존성 (`pom.xml`)
```xml
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>3.3.1</version>
</dependency>
```

### 2. Spring 설정 (`src/main/webapp/WEB-INF/spring/root-context.xml`)
- HikariCP를 사용한 커넥션 풀 설정
- MyBatis SqlSessionFactory 설정
- 트랜잭션 매니저 설정

### 3. MyBatis 매퍼 설정
- `src/main/java/org/doit/ik/mapper/TestMapper.java`: 인터페이스
- `src/main/resources/org/doit/ik/mapper/TestMapper.xml`: SQL 쿼리

## 🧪 테스트 방법

### 방법 1: 웹 브라우저를 통한 테스트
1. 톰캣 서버를 시작합니다
2. 웹 브라우저에서 다음 URL로 접속:
   ```
   http://localhost:8080/ik/ik/test
   ```
3. 연결 결과와 데이터베이스 정보가 화면에 표시됩니다

### 방법 2: JSON 응답 확인
브라우저에서 다음 URL로 접속하여 JSON 형태로 결과 확인:
```
http://localhost:8080/ik/ik/test/json
```

### 방법 3: Java 클래스 직접 실행
IDE에서 다음 클래스를 실행:
```
src/main/java/org/doit/ik/util/DatabaseConnectionTest.java
```

## 📊 테스트 결과 예시

### 성공적인 연결 시
```
1. MariaDB 드라이버 로드 성공!
2. 데이터베이스 연결 성공!
3. 쿼리 실행 성공!
   - 결과: MariaDB 연결 성공!
   - 현재 시간: 2024-01-15 14:30:25
   - DB 버전: 10.6.16-MariaDB
========================================
🎉 MariaDB 연결 테스트 완료!
모든 테스트가 성공적으로 완료되었습니다.
========================================
```

### 연결 실패 시
```
❌ 데이터베이스 연결 실패: Connection refused
   SQLState: 08S01
   에러 코드: 0
```

## 🔍 문제 해결

### 자주 발생하는 문제들

1. **연결 거부 (Connection refused)**
   - 네트워크 연결 확인
   - 방화벽 설정 확인
   - 데이터베이스 서버 상태 확인

2. **인증 실패 (Access denied)**
   - 사용자명과 비밀번호 확인
   - 데이터베이스 권한 확인

3. **드라이버 로드 실패**
   - MariaDB JDBC 드라이버가 클래스패스에 있는지 확인
   - Maven 의존성이 제대로 설정되었는지 확인

## 📁 생성된 파일 목록

1. **컨트롤러**
   - `src/main/java/org/doit/ik/controller/TestDb.java`

2. **매퍼**
   - `src/main/java/org/doit/ik/mapper/TestMapper.java`
   - `src/main/resources/org/doit/ik/mapper/TestMapper.xml`

3. **유틸리티**
   - `src/main/java/org/doit/ik/util/DatabaseConnectionTest.java`

4. **뷰**
   - `src/main/webapp/WEB-INF/views/ik/dbTest.jsp`

## 💡 추가 기능

- 웹 인터페이스를 통한 시각적 결과 확인
- JSON API를 통한 프로그래밍적 접근
- 데이터베이스 버전 정보 표시
- 현재 시간 조회로 실시간 연결 확인

---

**참고**: 이 테스트는 MariaDB AWS RDS 인스턴스에 연결하므로 인터넷 연결이 필요합니다. 