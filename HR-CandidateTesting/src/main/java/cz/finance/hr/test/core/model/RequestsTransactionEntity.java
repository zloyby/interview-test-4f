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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RequestsTransaction")
public class RequestsTransactionEntity {

    @Id
    @GenericGenerator(
            name = "sequenceGeneratorRequestsTransaction",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "RequestsTransaction_sequence"),
                    @Parameter(name = "optimizer", value = "pooled"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "100")
            }
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequenceGeneratorRequestsTransaction"
    )
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
