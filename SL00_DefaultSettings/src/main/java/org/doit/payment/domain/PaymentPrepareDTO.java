package org.doit.payment.domain;

public class PaymentPrepareDTO {
	
	private String bookingId;
	private String merchantUid;
	private String paymentMethod;
	private String amount;
	private String createdAt;
	

	
	public PaymentPrepareDTO(String bookingId, String merchantUid, String paymentMethod, String amount,
			String createdAt) {
		
		this.bookingId = bookingId;
		this.merchantUid = merchantUid;
		this.paymentMethod = paymentMethod;
		this.amount = amount;
		this.createdAt = createdAt;
	}

	public PaymentPrepareDTO() {
		// 기본 생성자
	}

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

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "PaymentPrepareDTO [bookingId=" + bookingId + ", merchantUid=" + merchantUid + ", paymentMethod="
				+ paymentMethod + ", amount=" + amount + ", createdAt=" + createdAt + "]";
	}
} 