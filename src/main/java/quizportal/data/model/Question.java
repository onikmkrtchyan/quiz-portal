package quizportal.data.model;

import lombok.Getter;
import lombok.Setter;
import quizportal.data.model.base.AbstractEntity;
import quizportal.data.model.enums.TestSubjectEnum;
import quizportal.utils.constants.EntityName;

import javax.persistence.*;

@Entity
@Table(name = EntityName.QUESTION)
@Getter
@Setter
public class Question extends AbstractEntity {
    @Column(name = "TITLE", nullable = false, unique = true)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DEFAULT_OPTION")
    private String defaultOption;

    @Column(name = "OPTION_ONE")
    private String optionOne;

    @Column(name = "OPTION_TWO")
    private String optionTwo;

    @Column(name = "OPTION_THREE")
    private String optionThree;

    @Column(name = "OPTION_FOUR")
    private String optionFour;

    @Column(name = "CORRECT_OPTION", nullable = false)
    private String correctOption;

    @Column(name = "COMPLEXITY_LEVEL", nullable = false)
    private Byte complexityLevel;

    @Column(name = "SUBJECT")
    @Enumerated(EnumType.STRING)
    private TestSubjectEnum subject;
}
