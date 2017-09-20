package com.poranski.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @OneToOne
    @JoinColumn(unique = true)
    private Image image;

    @OneToOne
    @JoinColumn(unique = true)
    private WaterTest waterTest;

    @OneToMany(mappedBy = "appointment")
    @JsonIgnore
    private Set<WaterTest> waterTests = new HashSet<>();

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Pool pool;

    @ManyToOne
    private Note note;

    @ManyToMany
    @JoinTable(name = "appointment_inventory_used",
               joinColumns = @JoinColumn(name="appointments_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="inventory_useds_id", referencedColumnName="id"))
    private Set<InventoryUsed> inventoryUseds = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "appointment_task",
               joinColumns = @JoinColumn(name="appointments_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tasks_id", referencedColumnName="id"))
    private Set<Task> tasks = new HashSet<>();

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

    public WaterTest getWaterTest() {
        return waterTest;
    }

    public Appointment waterTest(WaterTest waterTest) {
        this.waterTest = waterTest;
        return this;
    }

    public void setWaterTest(WaterTest waterTest) {
        this.waterTest = waterTest;
    }

    public Set<WaterTest> getWaterTests() {
        return waterTests;
    }

    public Appointment waterTests(Set<WaterTest> waterTests) {
        this.waterTests = waterTests;
        return this;
    }

    public Appointment addWaterTest(WaterTest waterTest) {
        this.waterTests.add(waterTest);
        waterTest.setAppointment(this);
        return this;
    }

    public Appointment removeWaterTest(WaterTest waterTest) {
        this.waterTests.remove(waterTest);
        waterTest.setAppointment(null);
        return this;
    }

    public void setWaterTests(Set<WaterTest> waterTests) {
        this.waterTests = waterTests;
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

    public Note getNote() {
        return note;
    }

    public Appointment note(Note note) {
        this.note = note;
        return this;
    }

    public void setNote(Note note) {
        this.note = note;
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

    public Set<Task> getTasks() {
        return tasks;
    }

    public Appointment tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Appointment addTask(Task task) {
        this.tasks.add(task);
        return this;
    }

    public Appointment removeTask(Task task) {
        this.tasks.remove(task);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
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
            "}";
    }
}
