package com.poranski.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Appointment.
 */
@Entity
@Table(name = "appointment")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private LocalDate startTime;

    @Column(name = "end_time")
    private LocalDate endTime;

    @Lob
    @Column(name = "notes")
    private String notes;

    @OneToOne
    @JoinColumn(unique = true)
    private Image image;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Pool pool;

    @ManyToMany
    @JoinTable(name = "appointment_inventory_used",
               joinColumns = @JoinColumn(name="appointments_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="inventory_useds_id", referencedColumnName="id"))
    private Set<InventoryUsed> inventoryUseds = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public Appointment startTime(LocalDate startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public Appointment endTime(LocalDate endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public String getNotes() {
        return notes;
    }

    public Appointment notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Image getImage() {
        return image;
    }

    public Appointment image(Image image) {
        this.image = image;
        return this;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Appointment employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Pool getPool() {
        return pool;
    }

    public Appointment pool(Pool pool) {
        this.pool = pool;
        return this;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public Set<InventoryUsed> getInventoryUseds() {
        return inventoryUseds;
    }

    public Appointment inventoryUseds(Set<InventoryUsed> inventoryUseds) {
        this.inventoryUseds = inventoryUseds;
        return this;
    }

    public Appointment addInventoryUsed(InventoryUsed inventoryUsed) {
        this.inventoryUseds.add(inventoryUsed);
        return this;
    }

    public Appointment removeInventoryUsed(InventoryUsed inventoryUsed) {
        this.inventoryUseds.remove(inventoryUsed);
        return this;
    }

    public void setInventoryUseds(Set<InventoryUsed> inventoryUseds) {
        this.inventoryUseds = inventoryUseds;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Appointment appointment = (Appointment) o;
        if (appointment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appointment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Appointment{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
