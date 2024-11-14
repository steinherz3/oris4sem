package ru.itis.secondsemwork.handler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionInfo {

    private String exceptionName;
    private String message;
}