package quizportal.data.model;

import lombok.Getter;
import lombok.Setter;
import quizportal.data.model.base.AbstractEntity;
import quizportal.utils.constants.EntityName;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = EntityName.PARTICIPANT)
public class Participant extends AbstractEntity {

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "PHONE_NUMBER", unique = true)
    private String phoneNumber;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "IS_SUBMITTED", nullable = false)
    private Boolean isSubmitted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUIZ_ID")
    private Quiz quiz;

    @OneToMany(mappedBy = "participant")
    private List<QuestionParticipant> questionParticipant;
}
