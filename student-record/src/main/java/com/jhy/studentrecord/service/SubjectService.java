package com.jhy.studentrecord.service;

import com.jhy.studentrecord.entity.Student;
import com.jhy.studentrecord.entity.Subject;
import com.jhy.studentrecord.repository.StudentRepository;
import com.jhy.studentrecord.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectService {

    private SubjectRepository subjectRepository;
    private StudentRepository studentRepository;

    @Transactional
    public void post(Subject subject){

    }
    @Transactional
    public void update(int subjectId, int studentId, int newScore){
        // controller에서 예외 처리하기 때문에 존재하는 데이터만 조회함.
        Student student = studentRepository.getById(studentId);


    }
}
