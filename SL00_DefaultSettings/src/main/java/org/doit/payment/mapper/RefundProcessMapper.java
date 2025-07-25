package org.doit.payment.mapper;

import org.apache.ibatis.annotations.Param;
import org.doit.payment.domain.RefundProcessDTO;

public interface RefundProcessMapper {
	
	/**
	 * booking_id와 user_no로 위변조 검사 후 merchant_uid 조회
	 * @param dto 환불 처리 정보 (bookingId, userNo)
	 * @return merchant_uid
	 */
	String validateAndGetMerchantUid(@Param("dto") RefundProcessDTO dto);
	
	/**
	 * merchant_uid로 결제 상태 업데이트
	 * @param merchantUid 가맹점 주문번호
	 * @param status 변경할 상태
	 * @return 업데이트 성공 건수
	 */
	int updateRefundStatus(@Param("merchantUid") String merchantUid, @Param("status") String status);
	
	/**
	 * booking_id로 예약 상태를 CANCELLED로 업데이트
	 * @param dto 환불 처리 정보 (bookingId)
	 * @return 업데이트 성공 건수
	 */
	int updateBookingStatus(@Param("dto") RefundProcessDTO dto);
	
	/**
	 * merchant_uid로 결제 금액 조회
	 * @param merchantUid 가맹점 주문번호
	 * @return 결제 금액
	 */
	String getPaymentAmount(@Param("merchantUid") String merchantUid);
} 