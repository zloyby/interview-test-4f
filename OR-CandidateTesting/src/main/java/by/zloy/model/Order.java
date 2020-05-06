package by.zloy.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Access(value = AccessType.FIELD)
@Entity(name = "Orders")
@Table(name = "ORDERS")
@NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.orderId = :orderId")
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

    @Deprecated
    protected Order() {
        super();
    }

    public Order(String orderId, Coffee coffee, OffsetDateTime readyDateTime, Machine machine) {
        super();
        this.orderId = orderId;
        this.coffee = coffee;
        this.readyDateTime = readyDateTime;
        this.machine = machine;
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
}
