package com.jhy.studentrecord.api;

import com.jhy.studentrecord.dto.Result;
import com.jhy.studentrecord.dto.StudentDto;
import com.jhy.studentrecord.dto.SubjectScoreDto;
import com.jhy.studentrecord.error.ErrorMsg;
import com.jhy.studentrecord.entity.Student;
import com.jhy.studentrecord.repository.ScoreRepository;
import com.jhy.studentrecord.repository.StudentRepository;
import com.jhy.studentrecord.service.ScoreService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;

import javax.persistence.Access;
import javax.validation.Valid;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/students", produces = MediaTypes.HAL_JSON_VALUE)
public class StudentApiController {

    private final StudentRepository studentRepository;
    private final ScoreRepository scoreRepository;
    private final ScoreService scoreService;

    @PostMapping
    public ResponseEntity postStudent(@RequestBody @Valid Map<String,StudentDto> list, BindingResult result){
        StudentDto studentDto = list.get("student");

        if(result.hasErrors()){
            log.info("error : [{}]", result);
            ErrorMsg errorMsg = ErrorMsg.builder()
                    .message("필수조건 불충족.")
                    .code("BAD_REQUEST_BODY")
                    .build();

            Result studentResult = Result.builder()
                    .error(errorMsg)
                    .build();

            return ResponseEntity.badRequest().body(studentResult);
        }

        if(studentRepository.existsByPhoneNumber(studentDto.getPhoneNumber())){

            ErrorMsg errorMsg = ErrorMsg.builder()
                    .code("ALREADY_EXIST_STUDENT")
                    .message("이미 존재하는 학생입니다. " + studentDto.getPhoneNumber())
                    .build();

            Result studentResult = Result.builder()
                    .error(errorMsg)
                    .build();

            return ResponseEntity.badRequest().body(studentResult);
        }


        Student student = Student.builder()
                .name(studentDto.getName())
                .age(studentDto.getAge())
                .schoolType(studentDto.getSchoolType().name())
                .phoneNumber(studentDto.getPhoneNumber())
                .build();

        studentRepository.save(student);

        URI createUri = linkTo(StudentApiController.class).toUri();
        Result studentResult = Result.builder().build();

        return ResponseEntity.created(createUri).body(studentResult);
    }

    @GetMapping
    public ResponseEntity getStudent(){
        List<Student> studentList = studentRepository.findAll();

        HashMap<String, Object> map = new HashMap<>();
        map.put("students", studentList);

        Result result = Result.builder()
                .data(map)
                .build();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity deleteStudent(@PathVariable("studentId") int id){
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{studentId}/average-score")
    public ResponseEntity averageScore(@PathVariable("studentId") int studentId){

        if(!studentRepository.existsById(studentId)){
            ErrorMsg errorMsg = ErrorMsg.builder()
                    .message("학생을 찾을 수 없습니다. " + studentId)
                    .code("STUDENT_NOT_FOUND")
                    .build();

            Result scoreResult = Result.builder()
                    .error(errorMsg)
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(scoreResult);
        }

        HashMap<String, Object> map = new HashMap<>();
        Double avg = scoreRepository.avgByStudent(studentId);
        List<SubjectScoreDto> subjects = scoreService.getSubjects(studentId);

        map.put("averageScore", avg);
        map.put("subjects", subjects);

        Result result = Result.builder()
                .data(map)
                .build();

        return ResponseEntity.ok(result);
    }

}
