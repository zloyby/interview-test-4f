package by.zloy.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

@Access(value = AccessType.FIELD)
@Entity(name = "Machines")
@Table(name = "MACHINES")
@NamedQueries({
        @NamedQuery(name = "Machines.findAll", query = "SELECT m FROM Machines m"),
        @NamedQuery(name = "Machines.findById", query = "SELECT m FROM Machines m WHERE m.machineId = :machineId")
})
public class Machine implements Serializable {

    @Id
    @Column(name = "MACHINE_ID", nullable = false, updatable = false)
    private String machineId;

    @Basic(optional = false)
    @Column(name = "KITCHEN")
    private String kitchen;

    @Basic(optional = false)
    @Column(name = "FLOOR")
    private String floor;

    @Basic(optional = false)
    @Column(name = "VELOCITY")
    private Integer velocity; // Minutes for making 1 cup of coffee by this machine

    @Basic(optional = false)
    @Column(name = "AVAILABILITY", columnDefinition = "TIMESTAMP")
    private OffsetDateTime availability; // DateTime of all ordered coffee cups to be done

    @Deprecated
    protected Machine() {
        super();
    }

    public Machine(String machineId, String kitchen, String floor, Integer velocity, OffsetDateTime availability) {
        super();
        this.machineId = machineId;
        this.kitchen = kitchen;
        this.floor = floor;
        this.velocity = velocity;
        this.availability = availability;
    }

    public String getMachineId() {
        return machineId;
    }

    public String getKitchen() {
        return kitchen;
    }

    public String getFloor() {
        return floor;
    }

    public Integer getVelocity() {
        return velocity;
    }

    public OffsetDateTime getAvailability() {
        OffsetDateTime now = OffsetDateTime.now();
        return Objects.nonNull(availability) && availability.compareTo(now) > 0
                ? availability
                : OffsetDateTime.now();
    }

    public void setAvailability(OffsetDateTime availability) {
        this.availability = availability;
    }
}
