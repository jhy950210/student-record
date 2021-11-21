package com.jhy.studentrecord.repository;

import com.jhy.studentrecord.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);
}
