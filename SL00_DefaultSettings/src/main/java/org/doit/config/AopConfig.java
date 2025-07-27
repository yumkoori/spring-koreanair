package org.doit.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {
    "org.doit.aop",           // AOP 클래스들
    "org.doit.payment"        // 결제 관련 서비스 클래스들
})
public class AopConfig {
    // Java Configuration으로 AOP 설정
    // XML 파일을 건드리지 않고 AOP를 활성화
} 