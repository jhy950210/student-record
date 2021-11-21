package com.jhy.studentrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDto {
    @Positive
    @Max(100)
    @Min(0)
    private int score;
}
