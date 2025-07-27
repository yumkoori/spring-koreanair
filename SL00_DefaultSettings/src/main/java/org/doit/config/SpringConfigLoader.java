package org.doit.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "org.doit.config")
@Import({AopConfig.class})
public class SpringConfigLoader {
    // Spring Context에서 Java Config를 자동으로 로드하기 위한 설정
} 