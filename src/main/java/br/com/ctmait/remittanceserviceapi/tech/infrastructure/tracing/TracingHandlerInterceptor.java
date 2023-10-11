package br.com.ctmait.remittanceserviceapi.tech.infrastructure.tracing;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;


public class TracingHandlerInterceptor implements HandlerInterceptor {

  public TracingHandlerInterceptor() {
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    MDC.put("transactionID", getTransactionId(request));
    return HandlerInterceptor.super.preHandle(request, response, handler);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    MDC.clear();
    HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
  }

  private String getTransactionId(HttpServletRequest request){
    return Optional.ofNullable(request.getHeader("transactionId"))
        .filter(transactionId -> !transactionId.isBlank())
        .orElse(UUID.randomUUID().toString());
  }
}
