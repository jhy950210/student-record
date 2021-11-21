package com.jhy.studentrecord.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class Score {
    @Id
    @GeneratedValue
    @Column(name = "scoreId")
    private int id;

    private int studentId;
    private int subjectId;
    private int score;
}
