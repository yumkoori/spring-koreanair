package org.doit.payment.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.doit.payment.domain.PaymentVerifyDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Value("${mail.smtp.host}")
    private String smtpHost;
    
    @Value("${mail.smtp.port}")
    private int smtpPort;
    
    @Value("${mail.smtp.username}")
    private String username;
    
    @Value("${mail.smtp.password}")
    private String password;
    
    @Value("${mail.smtp.auth}")
    private boolean auth;
    
    @Value("${mail.smtp.starttls.enable}")
    private boolean starttlsEnable;
    
    @Value("${mail.smtp.ssl.enable}")
    private boolean sslEnable;
    
    /**
     * 결제 완료 이메일을 전송합니다.
     * @param recipientEmail 수신자 이메일 주소
     * @param paymentInfo 결제 정보
     * @throws MessagingException 이메일 전송 중 오류 발생 시
     */
    public void sendPaymentConfirmationEmail(String recipientEmail, PaymentVerifyDTO paymentInfo) throws MessagingException {
        
        // SMTP 설정 - 단순하고 안전한 방식
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        // Gmail 전용 최적화 설정
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2 TLSv1.3");
        
        // 타임아웃 설정
        props.put("mail.smtp.connectiontimeout", "30000");
        props.put("mail.smtp.timeout", "30000");
        
        // 인증 정보 설정
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dltkaksfn@gmail.com", "yqvl zmzf scre uolx");
            }
        };
        
        // 세션 생성
        Session session = Session.getInstance(props, authenticator);
        
        try {
            // 메시지 생성
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("dltkaksfn@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("[항공예약시스템] 결제가 완료되었습니다");
            
            // 이메일 본문 내용 생성
            String emailContent = createEmailContent(paymentInfo);
            message.setContent(emailContent, "text/html; charset=utf-8");
            
            // 이메일 전송
            Transport.send(message);
            
            System.out.println("결제 완료 이메일 전송 성공 - 수신자: " + recipientEmail + ", 주문번호: " + paymentInfo.getMerchantUid());
            
        } catch (MessagingException e) {
            System.err.println("이메일 전송 실패 - 수신자: " + recipientEmail + ", 오류: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * 결제 완료 이메일 본문을 생성합니다.
     * @param paymentInfo 결제 정보
     * @return HTML 형식의 이메일 본문
     */
    private String createEmailContent(PaymentVerifyDTO paymentInfo) {
        StringBuilder content = new StringBuilder();
        
        content.append("<!DOCTYPE html>");
        content.append("<html>");
        content.append("<head>");
        content.append("<meta charset='UTF-8'>");
        content.append("<style>");
        content.append("body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }");
        content.append(".container { max-width: 600px; margin: 0 auto; padding: 20px; }");
        content.append(".header { background-color: #007bff; color: white; padding: 20px; text-align: center; }");
        content.append(".content { background-color: #f9f9f9; padding: 20px; border: 1px solid #ddd; }");
        content.append(".payment-info { background-color: white; padding: 15px; margin: 10px 0; border-radius: 5px; }");
        content.append(".label { font-weight: bold; color: #555; }");
        content.append(".value { color: #333; margin-left: 10px; }");
        content.append(".footer { text-align: center; margin-top: 20px; color: #666; font-size: 12px; }");
        content.append("</style>");
        content.append("</head>");
        content.append("<body>");
        
        content.append("<div class='container'>");
        content.append("<div class='header'>");
        content.append("<h1>✈️ 결제 완료 안내</h1>");
        content.append("</div>");
        
        content.append("<div class='content'>");
        content.append("<h2>안녕하세요!</h2>");
        content.append("<p>항공예약시스템을 이용해 주셔서 감사합니다.<br>");
        content.append("결제가 정상적으로 완료되었습니다.</p>");
        
        content.append("<div class='payment-info'>");
        content.append("<h3>📋 결제 정보</h3>");
        content.append("<p><span class='label'>주문번호:</span><span class='value'>").append(paymentInfo.getMerchantUid()).append("</span></p>");
        content.append("<p><span class='label'>결제금액:</span><span class='value'>").append(String.format("%,d", paymentInfo.getAmount())).append("원</span></p>");
        content.append("<p><span class='label'>결제방법:</span><span class='value'>").append(paymentInfo.getPayMethod() != null ? paymentInfo.getPayMethod() : "카드결제").append("</span></p>");
        
        if (paymentInfo.getPaidAt() != null) {
            content.append("<p><span class='label'>결제일시:</span><span class='value'>").append(paymentInfo.getPaidAt()).append("</span></p>");
        }
        content.append("</div>");
        
        content.append("<div class='payment-info'>");
        content.append("<h3>📞 고객지원</h3>");
        content.append("<p>문의사항이 있으시면 언제든지 연락 주세요.</p>");
        content.append("<p><span class='label'>고객센터:</span><span class='value'>1588-0000</span></p>");
        content.append("<p><span class='label'>운영시간:</span><span class='value'>평일 09:00 - 18:00</span></p>");
        content.append("</div>");
        
        content.append("<p style='margin-top: 20px;'>감사합니다.<br>");
        content.append("<strong>항공예약시스템 팀</strong></p>");
        content.append("</div>");
        
        content.append("<div class='footer'>");
        content.append("<p>본 메일은 발신전용입니다. 문의사항은 고객센터를 이용해 주세요.</p>");
        content.append("</div>");
        
        content.append("</div>");
        content.append("</body>");
        content.append("</html>");
        
        return content.toString();
    }
} 