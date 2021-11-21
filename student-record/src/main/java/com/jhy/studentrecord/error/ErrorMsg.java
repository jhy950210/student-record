package com.jhy.studentrecord.error;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorMsg {
    private String code;
    private String message;
}
