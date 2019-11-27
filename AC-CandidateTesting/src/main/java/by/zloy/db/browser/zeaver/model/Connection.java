package by.zloy.db.browser.zeaver.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Connection", indexes = {
        @Index(columnList = "name", name = "name_connection_idx")
})
public class Connection extends AbstractBaseEntity {

    @Id
    @GenericGenerator(
            name = "sequenceGeneratorConnection",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "sequenceGeneratorConnection"),
                    @Parameter(name = "optimizer", value = "pooled"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "100")
            }
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequenceGeneratorConnection"
    )
    private Long id;

    @Column
    private String name;

    @Column
    private String host;

    @Column
    private String port;

    @Column
    private String database;

    @Column
    private String user;

    //TODO: encode password
    @Column
    private String password;
}
