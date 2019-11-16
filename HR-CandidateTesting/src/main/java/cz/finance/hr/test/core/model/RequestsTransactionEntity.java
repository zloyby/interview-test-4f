package cz.finance.hr.test.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RequestsTransaction")
public class RequestsTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private Long ip;

    @Column
    private Long cityId;

    @Column
    private Long regionId;

    @Column
    private Long countryId;

    @Column
    private Long created;

    @PrePersist
    protected void prePersist() {
        if (this.created == null) created = System.currentTimeMillis();
    }
}
