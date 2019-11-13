package cz.finance.hr.test.core.model;

import java.net.InetAddress;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IP address range as interval &lt;{@link #from}; {@link #to}&gt;.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IpAddressRange")
public class IpAddressRangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "address_from")
    private InetAddress from;

    @Column(name = "address_to")
    private InetAddress to;
}
