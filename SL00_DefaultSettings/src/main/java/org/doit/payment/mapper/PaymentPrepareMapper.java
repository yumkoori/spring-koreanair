package org.doit.payment.mapper;

import org.apache.ibatis.annotations.Param;
import org.doit.payment.domain.PaymentPrepareDTO;

public interface PaymentPrepareMapper {
	
	/**
	 * payment 테이블에 결제 정보 저장
	 * @param dto 결제 준비 정보
	 * @param paymentId 생성된 결제 ID
	 * @return 저장 성공 건수
	 */
	int insertPayment(@Param("dto") PaymentPrepareDTO dto, @Param("paymentId") String paymentId);
	
	/**
	 * payment_request_log 테이블의 현재 행 개수 조회
	 * @return 행 개수
	 */
	int getPaymentRequestLogCount();
	
	/**
	 * payment_request_log 테이블에 결제 요청 로그 저장
	 * @param requestLogId 요청 로그 ID
	 * @param paymentId 결제 ID
	 * @param dto 결제 준비 정보
	 * @return 저장 성공 건수
	 */
	int insertPaymentRequestLog(@Param("requestLogId") int requestLogId, @Param("paymentId") String paymentId, @Param("dto") PaymentPrepareDTO dto);
} 