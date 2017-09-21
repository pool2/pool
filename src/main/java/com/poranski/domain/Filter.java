package com.poranski.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Filter.
 */
@Entity
@Table(name = "filter")
public class Filter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "jhi_size")
    private Integer size;

    @Column(name = "model_number")
    private String modelNumber;

    @Column(name = "brand")
    private String brand;

    @Column(name = "replaced_date")
    private LocalDate replacedDate;

    @ManyToOne
    private Note note;

    @ManyToOne
    private FilterBrand filterBrand;

    @ManyToOne
    private FilterType filterType;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Filter type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public Filter size(Integer size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public Filter modelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
        return this;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getBrand() {
        return brand;
    }

    public Filter brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public LocalDate getReplacedDate() {
        return replacedDate;
    }

    public Filter replacedDate(LocalDate replacedDate) {
        this.replacedDate = replacedDate;
        return this;
    }

    public void setReplacedDate(LocalDate replacedDate) {
        this.replacedDate = replacedDate;
    }

    public Note getNote() {
        return note;
    }

    public Filter note(Note note) {
        this.note = note;
        return this;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public FilterBrand getFilterBrand() {
        return filterBrand;
    }

    public Filter filterBrand(FilterBrand filterBrand) {
        this.filterBrand = filterBrand;
        return this;
    }

    public void setFilterBrand(FilterBrand filterBrand) {
        this.filterBrand = filterBrand;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public Filter filterType(FilterType filterType) {
        this.filterType = filterType;
        return this;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
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
        Filter filter = (Filter) o;
        if (filter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Filter{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", size='" + getSize() + "'" +
            ", modelNumber='" + getModelNumber() + "'" +
            ", brand='" + getBrand() + "'" +
            ", replacedDate='" + getReplacedDate() + "'" +
            "}";
    }
}
