package com.jhy.studentrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectScoreDto {
    private int id;
    private String name;
    private int score;
}
