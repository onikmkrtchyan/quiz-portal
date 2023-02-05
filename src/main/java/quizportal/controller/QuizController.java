package quizportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import quizportal.data.model.enums.TestSubjectEnum;
import quizportal.service.quiz.dto.QuizDTO;
import quizportal.service.quiz.QuizGeneratorService;
import quizportal.utils.constants.StringObj;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
@Validated
@PreAuthorize("@permission.check(@role.ROLE_ADMIN) or @permission.check(@role.ROLE_HR)")
public class QuizController {

    private final QuizGeneratorService quizGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<StringObj> generate(@RequestParam TestSubjectEnum testSubject) {
        return ResponseEntity.ok(quizGeneratorService.generateUrl(testSubject));
    }

    @DeleteMapping("/deactivate")
    public void deactivate(@RequestParam String url) {
        quizGeneratorService.deactivate(url);
    }

    @GetMapping("/all")
    public ResponseEntity<QuizDTO> findAllParticipants(@RequestParam Long quizId) {
        return ResponseEntity.ok(quizGeneratorService.findAllParticipants(quizId));
    }
}
