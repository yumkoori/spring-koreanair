package org.doit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Import(AopConfig.class)  // AOP 설정을 가져옴
public class WebConfig implements WebMvcConfigurer {
    // Web 관련 설정에서 AOP Config를 Import
} 