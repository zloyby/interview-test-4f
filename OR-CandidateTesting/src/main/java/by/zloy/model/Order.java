package by.zloy.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Access(value = AccessType.FIELD)
@Entity(name = "Orders")
@Table(name = "ORDERS")
@NamedQuery(name = "Orders.findByIdAndToken", query = "SELECT o FROM Orders o WHERE o.orderId = :orderId AND o.token = :token")
public class Order implements Serializable {

    @Id
    @Column(name = "ORDER_ID", nullable = false, updatable = false)
    private String orderId;

    @Basic(optional = false)
    @Column(name = "COFFEE", nullable = false)
    private Coffee coffee;

    @Basic(optional = false)
    @Column(name = "READY_DATE_TIME", columnDefinition = "TIMESTAMP")
    private OffsetDateTime readyDateTime; // OffsetDateTime when you coffee will be ready

    @ManyToOne
    @JoinColumn(name = "MACHINE_ID", referencedColumnName = "MACHINE_ID")
    private Machine machine;

    @Basic(optional = false)
    @Column(name = "TOKEN", nullable = false)
    private String token;

    @Deprecated
    protected Order() {
        super();
    }

    public Order(String orderId, Coffee coffee, OffsetDateTime readyDateTime, Machine machine, String token) {
        super();
        this.orderId = orderId;
        this.coffee = coffee;
        this.readyDateTime = readyDateTime;
        this.machine = machine;
        this.token = token;
    }

    public String getOrderId() {
        return orderId;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public OffsetDateTime getReadyDateTime() {
        return readyDateTime;
    }

    public Machine getMachine() {
        return machine;
    }

    public String getToken() {
        return token;
    }
}
