package com.financeiro.devedor.config;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogHttpAspect {

  @Before("execution(* com.example.devedor.controller.*.*(..))")
  public void logEntrada(JoinPoint joinPoint) {
    log.info("===> MENSAGEM RECEBIDA: Método [{}] | Argumentos: {}", 
      joinPoint.getSignature().getName(), 
      Arrays.toString(joinPoint.getArgs()));
  }

  @AfterReturning(pointcut = "execution(* com.example.devedor.controller.*.*(..))", returning = "result")
  public void logSucesso(JoinPoint joinPoint, Object result) {
    log.info("<=== RESPOSTA ENVIADA: Método [{}] | Retorno: {}", 
      joinPoint.getSignature().getName(), 
      result);
  }

  @AfterThrowing(pointcut = "execution(* com.example.devedor.controller.*.*(..))", throwing = "ex")
  public void logErro(JoinPoint joinPoint, Exception ex) {
    log.error("!!!! ERRO DETECTADO: No método [{}] | Mensagem: {}", 
      joinPoint.getSignature().getName(), 
      ex.getMessage());
  }
}
