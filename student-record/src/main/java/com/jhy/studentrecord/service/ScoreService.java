package com.jhy.studentrecord.service;

import com.jhy.studentrecord.dto.StudentScoreDto;
import com.jhy.studentrecord.dto.SubjectScoreDto;
import com.jhy.studentrecord.entity.Score;
import com.jhy.studentrecord.entity.Student;
import com.jhy.studentrecord.entity.Subject;
import com.jhy.studentrecord.repository.ScoreRepository;
import com.jhy.studentrecord.repository.StudentRepository;
import com.jhy.studentrecord.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public void update(int studentId, int subjectId, int newScore){
        Score bySubjectIdAndStudentId = scoreRepository.findBySubjectIdAndStudentId(subjectId, studentId);
        bySubjectIdAndStudentId.setScore(newScore);
    }

    public List<SubjectScoreDto> getSubjects(int studentId){
        List<SubjectScoreDto> res = new ArrayList<>();

        List<Integer> subjectIdBystudentId = scoreRepository.findSubjectIdBystudentId(studentId);

        for (int subjectId : subjectIdBystudentId) {
            Score score = scoreRepository.findBySubjectIdAndStudentId(subjectId, studentId);
            Subject byId = subjectRepository.getById(subjectId);

            SubjectScoreDto subjectScoreDto = SubjectScoreDto.builder()
                    .id(byId.getId())
                    .name(byId.getName())
                    .score(score.getScore())
                    .build();

            res.add(subjectScoreDto);
        }

        return res;
    }

    public List<StudentScoreDto> getStudents(int subjectId){
        List<StudentScoreDto> res = new ArrayList<>();

        List<Integer> studentIds = scoreRepository.findStudentIdBysubjectId(subjectId);

        for (int studentId : studentIds) {
            Score score = scoreRepository.findBySubjectIdAndStudentId(subjectId, studentId);
            Student byId = studentRepository.getById(studentId);

            StudentScoreDto studentScoreDto = StudentScoreDto.builder()
                    .id(byId.getId())
                    .name(byId.getName())
                    .score(score.getScore())
                    .build();

            res.add(studentScoreDto);
        }

        return res;
    }
}
