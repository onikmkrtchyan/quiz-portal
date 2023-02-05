package quizportal.data.model;

import lombok.Getter;
import lombok.Setter;
import quizportal.data.model.base.AbstractEntity;
import quizportal.service.permission.RoleEnum;
import quizportal.utils.constants.EntityName;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = EntityName.USER)
public class User extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

}
