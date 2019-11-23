package by.zloy.db.browser.zeaver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@MappedSuperclass
abstract public class AbstractBaseEntity implements Persistable<Long> {

    private Long created;
    private Long updated;

    @Override
    @JsonIgnore
    public boolean isNew() {
        Long id = getId();
        return id == null || id <= 0;
    }

    @PrePersist
    public void prepareNewEntity() {
        created = System.currentTimeMillis();
    }

    @PreUpdate
    public void prepareUpdate() {
        updated = System.currentTimeMillis();
    }
}
