package quizportal.data.model.base;

import lombok.Getter;
import quizportal.data.audit.Auditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@MappedSuperclass
public class AbstractEntity extends Auditable<Long> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    @Column(unique = true, updatable = false, nullable = false, length = 36)
    private String uuid = UUID.randomUUID().toString();
}
