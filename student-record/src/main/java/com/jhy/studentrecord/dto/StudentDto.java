package com.jhy.studentrecord.dto;

import com.jhy.studentrecord.entity.SchoolType;
import com.jhy.studentrecord.validator.EnumNamePattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    @Size(min = 1, max = 16)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]*$")
    private String name;

    @Max(19)
    @Min(8)
    private int age;

    @Enumerated(EnumType.STRING)
    @EnumNamePattern(regexp = "ELEMENTARY|MIDDLE|HIGH")
    private SchoolType schoolType;

    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$")
    private String phoneNumber;
}
