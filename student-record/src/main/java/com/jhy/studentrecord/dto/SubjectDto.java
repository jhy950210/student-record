package com.jhy.studentrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {
    @Size(min = 1, max = 12)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]*$")
    private String name;
}
