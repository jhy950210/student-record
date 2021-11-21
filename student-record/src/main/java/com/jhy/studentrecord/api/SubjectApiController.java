package com.jhy.studentrecord.api;

import com.jhy.studentrecord.dto.*;
import com.jhy.studentrecord.entity.Student;
import com.jhy.studentrecord.entity.Subject;
import com.jhy.studentrecord.error.ErrorMsg;
import com.jhy.studentrecord.repository.ScoreRepository;
import com.jhy.studentrecord.repository.SubjectRepository;
import com.jhy.studentrecord.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/subjects", produces = MediaTypes.HAL_JSON_VALUE)
public class SubjectApiController {

    private final SubjectRepository subjectRepository;
    private final ScoreRepository scoreRepository;
    private final ScoreService scoreService;

    @PostMapping
    public ResponseEntity postStudent(@RequestBody @Valid Map<String, SubjectDto> list, BindingResult result){
        SubjectDto subjectDto = list.get("subject");

        if(result.hasErrors()){
            log.info("error : [{}]", result);
            ErrorMsg errorMsg = ErrorMsg.builder()
                    .message("필수조건 불충족.")
                    .code("BAD_REQUEST_BODY")
                    .build();

            Result subjectResult = Result.builder()
                    .error(errorMsg)
                    .build();

            return ResponseEntity.badRequest().body(subjectResult);
        }

        if(subjectRepository.existsByName(subjectDto.getName())){
            ErrorMsg errorMsg = ErrorMsg.builder()
                    .code("ALREADY_EXIST_SUBJECT")
                    .message("이미 존재하는 과목입니다. " + subjectDto.getName())
                    .build();
            Result subjectResult = Result.builder()
                    .error(errorMsg)
                    .build();

            return ResponseEntity.badRequest().body(subjectResult);
        }


        Subject subject = Subject.builder()
                .name(subjectDto.getName())
                .build();

        subjectRepository.save(subject);

        Result subjectResult = Result.builder().build();
        URI createUri = linkTo(SubjectApiController.class).toUri();

        return ResponseEntity.created(createUri).body(subjectResult);
    }

    @GetMapping
    public ResponseEntity getStudent(){
        List<Subject> subjectList = subjectRepository.findAll();

        HashMap<String, Object> map = new HashMap<>();
        map.put("subjects", subjectList);

        Result result = Result.builder()
                .data(map)
                .build();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity deleteStudent(@PathVariable("subjectId") int id){
        subjectRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{subjectId}/average-score")
    public ResponseEntity averageScore(@PathVariable("subjectId") int subjectId){

        if(!subjectRepository.existsById(subjectId)){
            ErrorMsg errorMsg = ErrorMsg.builder()
                    .message("과목을 찾을 수 없습니다. " + subjectId)
                    .code("SUBJECT_NOT_FOUND")
                    .build();

            Result scoreResult = Result.builder()
                    .error(errorMsg)
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(scoreResult);
        }

        HashMap<String, Object> map = new HashMap<>();
        Double avg = scoreRepository.avgBySubject(subjectId);
        List<StudentScoreDto> students = scoreService.getStudents(subjectId);

        map.put("averageScore", avg);
        map.put("students", students);

        Result result = Result.builder()
                .data(map)
                .build();

        return ResponseEntity.ok(result);
    }
}
