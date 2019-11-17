package cz.finance.hr.test.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.net.InetAddress;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * IP address range as interval &lt;{@link #from}; {@link #to}&gt;.
 */
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IpAddressRange {

    private InetAddress from;
    private InetAddress to;

    public InetAddress getFrom() {
        return from;
    }

    public void setFrom(InetAddress from) {
        this.from = from;
    }

    public InetAddress getTo() {
        return to;
    }

    public void setTo(InetAddress to) {
        this.to = to;
    }
}
