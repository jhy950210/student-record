package com.jhy.studentrecord.dto;

import com.jhy.studentrecord.error.ErrorMsg;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class Result {
    private Map<String, Object> data;
    private ErrorMsg error;
}
