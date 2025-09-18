package bg.softuni.invoice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import static lombok.AccessLevel.PROTECTED;

@Setter
@Getter
@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
public abstract class BaseEntity {

    @Id
    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private String id;

}
