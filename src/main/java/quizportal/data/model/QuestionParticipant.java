package quizportal.data.model;

import lombok.Getter;
import lombok.Setter;
import quizportal.utils.constants.EntityName;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(
        name = EntityName.QUESTION_PARTICIPANT,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"QUESTION_ID", "PARTICIPANT_ID"})
        }
)
public class QuestionParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "PARTICIPANT_OPTION")
    private String participantOption;

    @Column(name = "EARNED_SCORE")
    private Float earnedScore;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTICIPANT_ID")
    private Participant participant;
}
