package by.zloy.jpa;

import javax.persistence.*;
import java.io.Serializable;

@Access(value = AccessType.FIELD)
@Entity(name = "Orders")
@Table(name = "ORDERS")
@NamedQuery(name = "findById", query = "SELECT o FROM Orders o WHERE o.orderId = :orderId")
public class Order implements Serializable {

    @Id
    @Column(name = "ORDER_ID", nullable = false, updatable = false)
    private String orderId;

    @Basic(optional = false)
    @Column(name = "COFFEE", nullable = false)
    private Coffee coffee;

    @Basic(optional = false)
    @Column(name = "PROGRESS")
    private Integer progress;

    @Deprecated
    protected Order() {
        super();
    }

    public Order(String orderId, Coffee coffee, Integer progress) {
        super();
        this.orderId = orderId;
        this.coffee = coffee;
        this.progress = progress;
    }

    public String getOrderId() {
        return orderId;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public Integer getProgress() {
        return progress;
    }
}
