package quizportal.data.model;

import lombok.Getter;
import lombok.Setter;
import quizportal.utils.constants.EntityName;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = EntityName.REFRESH_TOKEN)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String refreshToken;
}
