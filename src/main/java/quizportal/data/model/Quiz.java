package quizportal.data.model;

import lombok.Getter;
import lombok.Setter;
import quizportal.data.model.base.AbstractEntity;
import quizportal.data.model.enums.TestSubjectEnum;
import quizportal.utils.constants.EntityName;

import javax.persistence.*;
import java.time.Duration;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = EntityName.QUIZZES)
public class Quiz extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String url;

    @Column
    private Duration duration;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TestSubjectEnum testSubject;

    @Column(name = "QUESTIONS_COUNT")
    private Byte questionsCount;

    @Column(name = "ACCEPTANCE_SCORE")
    private String acceptanceScore;

    @OneToMany(mappedBy = "quiz")
    private List<Participant> participants;
}
