package com.nservices.mypet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LogDto implements ILogDto{
    private String message;
    private LocalDateTime dateTime;
}
