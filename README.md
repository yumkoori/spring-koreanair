# Korean Air (항공편 예매 시스템)

## 1. 프로젝트 개요

이 프로젝트는 Spring Framework를 기반으로 구축된 대한항공 클론 프로젝트입니다. 항공권 검색, 예매, 결제, 예약 관리 등 항공 예매 시스템의 핵심 기능을 웹 애플리케이션으로 구현했습니다.

## 2. 주요 기능

- **✈️ 항공권 검색**: 출발지, 도착지, 날짜, 인원, 좌석 등급을 선택하여 원하는 항공편을 검색할 수 있습니다. (왕복/편도 지원)
- **🎫 항공권 예약**: 검색된 항공편을 선택하고 탑승객 정보를 입력하여 예약을 진행할 수 있습니다.
- **💳 결제 시스템**: 외부 결제 API 연동을 가정하여 결제 준비 및 사전 검증 프로세스를 구현했습니다. (Toss Payments, KakaoPay 등)
- **🔐 회원 및 인증**: Spring Security를 활용하여 안전한 로그인/회원가입 및 인증/인가 기능을 제공합니다.
- **📄 예약 관리**: 비회원도 예약 번호로 예약 내역을 조회하거나, 회원의 경우 '마이페이지'에서 자신의 예약 목록을 관리할 수 있습니다.
- **💺 좌석 선택 및 체크인**: 예약 후 원하는 좌석을 선택하고 온라인 체크인을 진행하는 기능을 포함합니다.
- **⚙️ 관리자:** 관리자 페이지를 통해 시스템의 다양한 데이터를 관리합니다.

## 3. 사용 기술

### Backend
- **Language:** Java 17
- **Framework:** Spring Framework 5.0.7, Spring Security
- **Database:** MariaDB
- **Persistence:** MyBatis, HikariCP
- **Build Tool:** Maven

### Frontend
- **View:** JSP, Apache Tiles, JSTL
- **Styling:** CSS
- **Script:** JavaScript

### Testing & Others
- **Testing:** JUnit 4, Mockito
- **Logging:** SLF4J, Log4j
- **Server:** Apache Tomcat
- **Dependencies:** Lombok, Jackson, Quartz, etc.

## 4. 프로젝트 구조

```
.
├── pom.xml                 # Maven 프로젝트 설정 파일
└── src
    ├── main
    │   ├── java            # Java 소스 코드
    │   │   └── org/doit
    │   │       ├── airport     # 공항
    │   │       ├── booking     # 예매
    │   │       ├── flight      # 항공편
    │   │       ├── member      # 회원
    │   │       ├── payment     # 결제
    │   │       └── reservation # 예약
    │   ├── resources       # 설정 파일 (MyBatis, Log4j 등)
    │   └── webapp
    │       ├── resources   # CSS, JavaScript, 이미지
    │       └── WEB-INF
    │           ├── spring      # Spring 설정 (root, servlet, security)
    │           ├── views       # JSP 뷰 파일
    │           └── web.xml     # 웹 애플리케이션 배포 서술자
    └── test
        └── java            # JUnit 테스트 코드
```
