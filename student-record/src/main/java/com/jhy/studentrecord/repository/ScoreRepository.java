package com.jhy.studentrecord.repository;

import com.jhy.studentrecord.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Integer> {

    @Query("select avg(s.score) from Score s where s.studentId =:studentId")
    Double avgByStudent(@Param("studentId") int studentId);

    @Query("select avg(s.score) from Score s where s.subjectId =:subjectId")
    Double avgBySubject(@Param("subjectId")int subjectId);

    @Query("select s.subjectId from Score s where s.studentId =:studentId")
    List<Integer> findSubjectIdBystudentId(@Param("studentId") int studentId);

    @Query("select s.studentId from Score s where s.subjectId =:subjectId")
    List<Integer> findStudentIdBysubjectId(@Param("subjectId") int subjectId);

    Score findBySubjectIdAndStudentId(int subjectId, int studentId);

    @Transactional
    void deleteBySubjectIdAndStudentId(int subjectId, int studentId);

}
