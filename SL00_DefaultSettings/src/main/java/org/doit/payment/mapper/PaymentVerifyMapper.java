package org.doit.payment.mapper;

import org.doit.payment.domain.PaymentCompareDTO;

public interface PaymentVerifyMapper {
	
	/**
	 * imp_uid로 DB에서 merchant_uid와 amount를 조회
	 * @param impUid 아임포트 결제 고유번호
	 * @return PaymentCompareDTO 검증용 결제 정보
	 */
	PaymentCompareDTO getPaymentCompareInfo(String impUid);
	
	/**
	 * merchant_uid로 DB에서 merchant_uid와 amount를 조회
	 * @param merchantUid 가맹점 주문번호
	 * @return PaymentCompareDTO 검증용 결제 정보
	 */
	PaymentCompareDTO getPaymentCompareInfoByMerchantUid(String merchantUid);
	
	/**
	 * merchant_uid로 결제 상태를 PAID로 업데이트하고 paid_at 시간을 설정
	 * @param merchantUid 가맹점 주문번호
	 * @return 업데이트 성공 건수
	 */
	int updatePaymentStatusToPaid(String merchantUid);
} 