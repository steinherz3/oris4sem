package ru.itis.secondsemwork.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Aspect
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "logger", value = "enabled", havingValue = "false", matchIfMissing = true)
public class LoggerConfig {

    @Bean
    public LoggerAspect loggingAspect(){
        return new LoggerAspect();
    }


    @AfterThrowing(value = "ru.itis.secondsemwork.logging.LoggerAspect.toLog()", throwing = "exception")
    public void throwingLog(JoinPoint joinPoint, Throwable exception){
        log.error("method [{}] throw exception", joinPoint.getSignature(),exception);
    }

}
