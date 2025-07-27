package org.doit.payment.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.doit.payment.domain.PaymentPrepareDTO;
import org.doit.payment.mapper.PaymentPrepareMapper;

public class PaymentPrepareServiceTest {

    @Mock
    private PaymentPrepareMapper paymentPrepareMapper;

    @InjectMocks
    private PaymentPrepareService paymentPrepareService;

    private PaymentPrepareDTO validDto;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        validDto = new PaymentPrepareDTO(
            "BOOK123456",
            "MERCH123456",
            "card",
            "50000",
            "2024-01-01 10:00:00"
        );
    }

    @Test
    public void testProcessPaymentPrepare_ValidInput_Success() throws Exception {
        // Given
        when(paymentPrepareMapper.insertPayment(any(PaymentPrepareDTO.class), anyString())).thenReturn(1);
        when(paymentPrepareMapper.getPaymentRequestLogCount()).thenReturn(0);
        when(paymentPrepareMapper.insertPaymentRequestLog(anyInt(), anyString(), any(PaymentPrepareDTO.class))).thenReturn(1);

        // When
        boolean result = paymentPrepareService.processPaymentPrepare(validDto);

        // Then
        assertTrue("결제 준비 처리가 성공해야 합니다", result);
        verify(paymentPrepareMapper, times(1)).insertPayment(any(PaymentPrepareDTO.class), anyString());
        verify(paymentPrepareMapper, times(1)).insertPaymentRequestLog(anyInt(), anyString(), any(PaymentPrepareDTO.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessPaymentPrepare_NullDto_ThrowsException() throws Exception {
        // When
        paymentPrepareService.processPaymentPrepare(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessPaymentPrepare_EmptyMerchantUid_ThrowsException() throws Exception {
        // Given
        validDto.setMerchantUid("");

        // When
        paymentPrepareService.processPaymentPrepare(validDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessPaymentPrepare_EmptyBookingId_ThrowsException() throws Exception {
        // Given
        validDto.setBookingId("");

        // When
        paymentPrepareService.processPaymentPrepare(validDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessPaymentPrepare_EmptyPaymentMethod_ThrowsException() throws Exception {
        // Given
        validDto.setPaymentMethod("");

        // When
        paymentPrepareService.processPaymentPrepare(validDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessPaymentPrepare_EmptyAmount_ThrowsException() throws Exception {
        // Given
        validDto.setAmount("");

        // When
        paymentPrepareService.processPaymentPrepare(validDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessPaymentPrepare_EmptyCreatedAt_ThrowsException() throws Exception {
        // Given
        validDto.setCreatedAt("");

        // When
        paymentPrepareService.processPaymentPrepare(validDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessPaymentPrepare_InvalidAmount_ZeroValue_ThrowsException() throws Exception {
        // Given
        validDto.setAmount("0");

        // When
        paymentPrepareService.processPaymentPrepare(validDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessPaymentPrepare_InvalidAmount_NegativeValue_ThrowsException() throws Exception {
        // Given
        validDto.setAmount("-1000");

        // When
        paymentPrepareService.processPaymentPrepare(validDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessPaymentPrepare_InvalidAmount_ExceedsLimit_ThrowsException() throws Exception {
        // Given
        validDto.setAmount("10000001"); // 1천만원 초과

        // When
        paymentPrepareService.processPaymentPrepare(validDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessPaymentPrepare_InvalidAmount_NotNumeric_ThrowsException() throws Exception {
        // Given
        validDto.setAmount("invalid_amount");

        // When
        paymentPrepareService.processPaymentPrepare(validDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessPaymentPrepare_InvalidPaymentMethod_ThrowsException() throws Exception {
        // Given
        validDto.setPaymentMethod("invalid_method");

        // When
        paymentPrepareService.processPaymentPrepare(validDto);
    }

    @Test
    public void testProcessPaymentPrepare_ValidPaymentMethods_Success() throws Exception {
        // Given
        String[] validMethods = {"card", "kakaopay", "tosspay"};
        when(paymentPrepareMapper.insertPayment(any(PaymentPrepareDTO.class), anyString())).thenReturn(1);
        when(paymentPrepareMapper.getPaymentRequestLogCount()).thenReturn(0);
        when(paymentPrepareMapper.insertPaymentRequestLog(anyInt(), anyString(), any(PaymentPrepareDTO.class))).thenReturn(1);

        // When & Then
        for (String method : validMethods) {
            validDto.setPaymentMethod(method);
            boolean result = paymentPrepareService.processPaymentPrepare(validDto);
            assertTrue("결제 방법 " + method + "는 유효해야 합니다", result);
        }
    }

    @Test(expected = Exception.class)
    public void testSavePaymentInfo_PaymentInsertFails_ThrowsException() throws Exception {
        // Given
        when(paymentPrepareMapper.insertPayment(any(PaymentPrepareDTO.class), anyString())).thenReturn(0);

        // When
        paymentPrepareService.savePaymentInfo(validDto);
    }

    @Test(expected = Exception.class)
    public void testSavePaymentInfo_LogInsertFails_ThrowsException() throws Exception {
        // Given
        when(paymentPrepareMapper.insertPayment(any(PaymentPrepareDTO.class), anyString())).thenReturn(1);
        when(paymentPrepareMapper.getPaymentRequestLogCount()).thenReturn(0);
        when(paymentPrepareMapper.insertPaymentRequestLog(anyInt(), anyString(), any(PaymentPrepareDTO.class))).thenReturn(0);

        // When
        paymentPrepareService.savePaymentInfo(validDto);
    }

    @Test
    public void testSavePaymentInfo_Success() throws Exception {
        // Given
        when(paymentPrepareMapper.insertPayment(any(PaymentPrepareDTO.class), anyString())).thenReturn(1);
        when(paymentPrepareMapper.getPaymentRequestLogCount()).thenReturn(5);
        when(paymentPrepareMapper.insertPaymentRequestLog(eq(6), anyString(), any(PaymentPrepareDTO.class))).thenReturn(1);

        // When
        boolean result = paymentPrepareService.savePaymentInfo(validDto);

        // Then
        assertTrue("결제 정보 저장이 성공해야 합니다", result);
        verify(paymentPrepareMapper, times(1)).insertPayment(any(PaymentPrepareDTO.class), anyString());
        verify(paymentPrepareMapper, times(1)).getPaymentRequestLogCount();
        verify(paymentPrepareMapper, times(1)).insertPaymentRequestLog(eq(6), anyString(), any(PaymentPrepareDTO.class));
    }

    @Test
    public void testSavePaymentInfo_DatabaseException_ThrowsException() throws Exception {
        // Given
        when(paymentPrepareMapper.insertPayment(any(PaymentPrepareDTO.class), anyString()))
            .thenThrow(new RuntimeException("Database connection failed"));

        // When & Then
        try {
            paymentPrepareService.savePaymentInfo(validDto);
            fail("예외가 발생해야 합니다");
        } catch (Exception e) {
            assertTrue("예외 메시지에 오류 정보가 포함되어야 합니다", 
                e.getMessage().contains("결제 정보 저장 중 오류가 발생했습니다"));
        }
    }
} 