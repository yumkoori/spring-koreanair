package org.doit.ik.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    
    private static final String DB_URL = "jdbc:mariadb://air.chkmcmk8aoyu.ap-northeast-2.rds.amazonaws.com:3306/air_db";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "Ssqwer1234!!";
    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    
    public static void main(String[] args) {
        testConnection();
    }
    
    public static void testConnection() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // 드라이버 로드
            Class.forName(DRIVER);
            System.out.println("1. MariaDB 드라이버 로드 성공!");
            
            // 데이터베이스 연결
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("2. 데이터베이스 연결 성공!");
            
            // 연결 테스트 쿼리 실행
            String testQuery = "SELECT 'MariaDB 연결 성공!' as result, NOW() as current_time, VERSION() as version";
            pstmt = conn.prepareStatement(testQuery);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("3. 쿼리 실행 성공!");
                System.out.println("   - 결과: " + rs.getString("result"));
                System.out.println("   - 현재 시간: " + rs.getString("current_time"));
                System.out.println("   - DB 버전: " + rs.getString("version"));
            }
            
            System.out.println("========================================");
            System.out.println("🎉 MariaDB 연결 테스트 완료!");
            System.out.println("모든 테스트가 성공적으로 완료되었습니다.");
            System.out.println("========================================");
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ 드라이버를 찾을 수 없습니다: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ 데이터베이스 연결 실패: " + e.getMessage());
            System.err.println("   SQLState: " + e.getSQLState());
            System.err.println("   에러 코드: " + e.getErrorCode());
        } catch (Exception e) {
            System.err.println("❌ 예상하지 못한 오류: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 리소스 정리
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
                System.out.println("리소스 정리 완료");
            } catch (SQLException e) {
                System.err.println("리소스 정리 중 오류: " + e.getMessage());
            }
        }
    }
    
    /**
     * 연결 상태를 확인하는 메서드
     */
    public static boolean isConnectionAvailable() {
        try {
            Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                return conn != null && !conn.isClosed();
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 데이터베이스 정보를 가져오는 메서드
     */
    public static void printDatabaseInfo() {
        try {
            Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                System.out.println("📊 데이터베이스 정보:");
                System.out.println("   - URL: " + DB_URL);
                System.out.println("   - 사용자: " + USERNAME);
                System.out.println("   - 드라이버: " + DRIVER);
                System.out.println("   - 자동 커밋: " + conn.getAutoCommit());
                System.out.println("   - 읽기 전용: " + conn.isReadOnly());
                System.out.println("   - 카탈로그: " + conn.getCatalog());
            }
        } catch (Exception e) {
            System.err.println("데이터베이스 정보 조회 실패: " + e.getMessage());
        }
    }
} 