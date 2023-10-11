package br.com.ctmait.remittanceserviceapi.tech.infrastructure.tracing.config;

import br.com.ctmait.remittanceserviceapi.tech.infrastructure.tracing.TracingHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TracingConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new TracingHandlerInterceptor());
    WebMvcConfigurer.super.addInterceptors(registry);
  }

}
