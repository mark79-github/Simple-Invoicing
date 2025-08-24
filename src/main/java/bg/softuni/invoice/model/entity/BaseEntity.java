package bg.softuni.invoice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.UuidGenerator;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private String id;

    protected BaseEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
