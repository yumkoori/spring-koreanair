package org.doit.payment.domain;

public class RefundProcessDTO {
    private String bookingId;
    private String merchantUid;
    private int userNo;
    private String amount;
    private String refundReason;
    private String bookingPw; // 비회원 예약 비밀번호

    // 기본 생성자
    public RefundProcessDTO() {}

    // 회원 위변조 검사용 생성자 (기존)
    public RefundProcessDTO(String bookingId, int userNo) {
        this.bookingId = bookingId;
        this.userNo = userNo;
    }

    // 비회원 위변조 검사용 생성자 (새로 추가)
    public RefundProcessDTO(String bookingId, String bookingPw) {
        this.bookingId = bookingId;
        this.bookingPw = bookingPw;
    }

    // 환불 처리용 생성자 (기존)
    public RefundProcessDTO(String bookingId, String merchantUid, int userNo, String amount, String refundReason) {
        this.bookingId = bookingId;
        this.merchantUid = merchantUid;
        this.userNo = userNo;
        this.amount = amount;
        this.refundReason = refundReason;
    }

    // Getter와 Setter 메서드들
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getMerchantUid() {
        return merchantUid;
    }

    public void setMerchantUid(String merchantUid) {
        this.merchantUid = merchantUid;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getBookingPw() {
        return bookingPw;
    }

    public void setBookingPw(String bookingPw) {
        this.bookingPw = bookingPw;
    }

    @Override
    public String toString() {
        return "RefundProcessDTO{" +
                "bookingId='" + bookingId + '\'' +
                ", merchantUid='" + merchantUid + '\'' +
                ", userNo=" + userNo +
                ", amount='" + amount + '\'' +
                ", refundReason='" + refundReason + '\'' +
                ", bookingPw='" + (bookingPw != null ? "****" : null) + '\'' +
                '}';
    }
} 