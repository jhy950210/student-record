package com.jhy.studentrecord.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jhy.studentrecord.validator.EnumNamePattern;
import lombok.*;

import javax.persistence.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Getter
@Builder
public class Student {

    @Id @GeneratedValue
    @Column(name = "studentId")
    private int id;
    private String name;
    private int age;
    private String schoolType;
    private String phoneNumber;

}
