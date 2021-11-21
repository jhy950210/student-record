package com.jhy.studentrecord.api;

import com.jhy.studentrecord.dto.Result;
import com.jhy.studentrecord.dto.ScoreDto;
import com.jhy.studentrecord.entity.Score;
import com.jhy.studentrecord.entity.Student;
import com.jhy.studentrecord.entity.Subject;
import com.jhy.studentrecord.error.ErrorMsg;
import com.jhy.studentrecord.repository.ScoreRepository;
import com.jhy.studentrecord.repository.StudentRepository;
import com.jhy.studentrecord.repository.SubjectRepository;
import com.jhy.studentrecord.service.ScoreService;
import com.jhy.studentrecord.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/students/{studentId}/subjects/{subjectId}/scores", produces = MediaTypes.HAL_JSON_VALUE)
public class ScoreApiController {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final ScoreRepository scoreRepository;
    private final ScoreService scoreService;


    @PostMapping
    public ResponseEntity postScore(@PathVariable("studentId") int studentId, @PathVariable("subjectId") int subjectId, @RequestBody @Valid ScoreDto scoreDto, BindingResult result){

        ResponseEntity<Result> scoreResult = getBadRequestOrNotFoundResponse(studentId, subjectId, result);
        if (scoreResult != null) return scoreResult;

        Score score = Score.builder()
                .studentId(studentId)
                .subjectId(subjectId)
                .score(scoreDto.getScore())
                .build();

        scoreRepository.save(score);


        URI createUri = linkTo(StudentApiController.class).toUri();
        Result res = Result.builder().build();

        return ResponseEntity.created(createUri).body(res);
    }

    @PutMapping
    public ResponseEntity putScore(@PathVariable("studentId") int studentId, @PathVariable("subjectId") int subjectId, @RequestBody @Valid ScoreDto scoreDto, BindingResult result){

        ResponseEntity<Result> scoreResult = getBadRequestOrNotFoundResponse(studentId, subjectId, result);
        if (scoreResult != null) return scoreResult;

        scoreService.update(studentId,subjectId,scoreDto.getScore());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity deleteScore(@PathVariable("studentId") int studentId, @PathVariable("subjectId") int subjectId){

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

        scoreRepository.deleteBySubjectIdAndStudentId(subjectId,studentId);

        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Result> getBadRequestOrNotFoundResponse(int studentId, int subjectId, BindingResult result) {
        if(result.hasErrors()){
            log.info("error : [{}]", result);
            ErrorMsg errorMsg = ErrorMsg.builder()
                    .message("필수조건 불충족.")
                    .code("BAD_REQUEST_BODY")
                    .build();

            Result scoreResult = Result.builder()
                    .error(errorMsg)
                    .build();

            return ResponseEntity.badRequest().body(scoreResult);
        }

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
        return null;
    }
}
