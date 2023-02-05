package quizportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import quizportal.service.participant.ParticipantService;
import quizportal.service.participant.dto.BaseParticipantDTO;
import quizportal.service.participant.dto.ParticipantSubmitDTO;
import quizportal.service.question.dto.QuestionEvaluateDTO;
import quizportal.service.test.dto.TestDTO;
import quizportal.utils.constants.StringObj;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/participant")
@RequiredArgsConstructor
@Validated
public class ParticipantController {
    private final ParticipantService participantService;

    @PostMapping("/{url}")
    public ResponseEntity<TestDTO> save(@PathVariable String url,
                                        @Valid @RequestBody BaseParticipantDTO baseParticipantDTO) {
        return ResponseEntity.ok(participantService.validateAndCreate(url, baseParticipantDTO));
    }

    @PutMapping("/submit")
    public ResponseEntity<StringObj> submit(@RequestParam String uuid,
                                            @RequestBody Set<ParticipantSubmitDTO> answers) {

        return ResponseEntity.ok(participantService.submit(uuid, answers));
    }

    @PutMapping("/review")
    public ResponseEntity<StringObj> reviewQuizAnswers(@RequestBody Set<QuestionEvaluateDTO> evaluation) {
        return ResponseEntity.ok(participantService.reviewQuizAnswers(evaluation));
    }
}


