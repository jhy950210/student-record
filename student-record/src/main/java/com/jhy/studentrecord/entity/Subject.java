package com.jhy.studentrecord.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class Subject {
    @Id @GeneratedValue
    @Column(name = "subjectId")
    private int id;

    private String name;
}
