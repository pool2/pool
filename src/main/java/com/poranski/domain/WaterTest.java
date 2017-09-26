package com.poranski.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A WaterTest.
 */
@Entity
@Table(name = "water_test")
public class WaterTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ph")
    private Float ph;

    @Column(name = "chlorine")
    private Float chlorine;

    @Column(name = "total_alkalinity")
    private Float totalAlkalinity;

    @Column(name = "calcium_hardness")
    private Float calciumHardness;

    @Column(name = "cyanuric_acid")
    private Float cyanuricAcid;

    @Column(name = "total_dissolved_solids")
    private Float totalDissolvedSolids;

    @Column(name = "date_time")
    private LocalDate dateTime;

    @Column(name = "note")
    private String note;

    @ManyToOne
    private Appointment appointment;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPh() {
        return ph;
    }

    public WaterTest ph(Float ph) {
        this.ph = ph;
        return this;
    }

    public void setPh(Float ph) {
        this.ph = ph;
    }

    public Float getChlorine() {
        return chlorine;
    }

    public WaterTest chlorine(Float chlorine) {
        this.chlorine = chlorine;
        return this;
    }

    public void setChlorine(Float chlorine) {
        this.chlorine = chlorine;
    }

    public Float getTotalAlkalinity() {
        return totalAlkalinity;
    }

    public WaterTest totalAlkalinity(Float totalAlkalinity) {
        this.totalAlkalinity = totalAlkalinity;
        return this;
    }

    public void setTotalAlkalinity(Float totalAlkalinity) {
        this.totalAlkalinity = totalAlkalinity;
    }

    public Float getCalciumHardness() {
        return calciumHardness;
    }

    public WaterTest calciumHardness(Float calciumHardness) {
        this.calciumHardness = calciumHardness;
        return this;
    }

    public void setCalciumHardness(Float calciumHardness) {
        this.calciumHardness = calciumHardness;
    }

    public Float getCyanuricAcid() {
        return cyanuricAcid;
    }

    public WaterTest cyanuricAcid(Float cyanuricAcid) {
        this.cyanuricAcid = cyanuricAcid;
        return this;
    }

    public void setCyanuricAcid(Float cyanuricAcid) {
        this.cyanuricAcid = cyanuricAcid;
    }

    public Float getTotalDissolvedSolids() {
        return totalDissolvedSolids;
    }

    public WaterTest totalDissolvedSolids(Float totalDissolvedSolids) {
        this.totalDissolvedSolids = totalDissolvedSolids;
        return this;
    }

    public void setTotalDissolvedSolids(Float totalDissolvedSolids) {
        this.totalDissolvedSolids = totalDissolvedSolids;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public WaterTest dateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public String getNote() {
        return note;
    }

    public WaterTest note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public WaterTest appointment(Appointment appointment) {
        this.appointment = appointment;
        return this;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
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
        WaterTest waterTest = (WaterTest) o;
        if (waterTest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), waterTest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WaterTest{" +
            "id=" + getId() +
            ", ph='" + getPh() + "'" +
            ", chlorine='" + getChlorine() + "'" +
            ", totalAlkalinity='" + getTotalAlkalinity() + "'" +
            ", calciumHardness='" + getCalciumHardness() + "'" +
            ", cyanuricAcid='" + getCyanuricAcid() + "'" +
            ", totalDissolvedSolids='" + getTotalDissolvedSolids() + "'" +
            ", dateTime='" + getDateTime() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
