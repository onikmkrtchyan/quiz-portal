package quizportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import quizportal.data.model.Question;
import quizportal.service.question.dto.QuestionRequestDto;
import quizportal.service.question.QuestionService;
import quizportal.service.question.dto.QuestionResponse;
import quizportal.service.question.dto.UpdateQuestionDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Validated
@PreAuthorize("@permission.check(@role.ROLE_ADMIN)")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public void create(@RequestBody @Valid QuestionRequestDto questionRequest) {
        questionService.create(questionRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getById(@PathVariable Long id) {
        QuestionResponse response = questionService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<QuestionResponse>> getAll() {
        List<QuestionResponse> response = questionService.getAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody @Valid UpdateQuestionDto question ) {
        Question response = questionService.updateQuestion(id,question);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id){
        questionService.delete(id);
    }
}
