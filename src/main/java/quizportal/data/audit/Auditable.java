package quizportal.data.audit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<T> {
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private T createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(nullable = false, updatable = false)
    private T updatedBy;

    @LastModifiedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Column(updatable = false)
    private LocalDateTime removedAt;

    @Column(nullable = false)
    private Boolean removed = FALSE;

    @Column(updatable = false)
    private T removedBy;
}
