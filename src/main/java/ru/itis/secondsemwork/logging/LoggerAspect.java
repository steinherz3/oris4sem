package ru.itis.secondsemwork.logging;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggerAspect {


    @Pointcut("within(ru.itis.secondsemwork..*) && !execution(* ru.itis.secondsemwork..security..*(..)) ")
    public void toLog(){
    }
}