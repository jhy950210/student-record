package com.jhy.studentrecord.repository;

import com.jhy.studentrecord.entity.Student;
import com.jhy.studentrecord.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    boolean existsByName(String name);
}
