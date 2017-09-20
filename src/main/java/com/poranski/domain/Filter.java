package com.poranski.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "replaced_date")
    private LocalDate replacedDate;

    @OneToMany(mappedBy = "filter")
    @JsonIgnore
    private Set<FilterType> filterTypes = new HashSet<>();

    @OneToMany(mappedBy = "filter")
    @JsonIgnore
    private Set<FilterBrand> filterBrands = new HashSet<>();

    @ManyToOne
    private Note note;

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

    public Set<FilterType> getFilterTypes() {
        return filterTypes;
    }

    public Filter filterTypes(Set<FilterType> filterTypes) {
        this.filterTypes = filterTypes;
        return this;
    }

    public Filter addFilterType(FilterType filterType) {
        this.filterTypes.add(filterType);
        filterType.setFilter(this);
        return this;
    }

    public Filter removeFilterType(FilterType filterType) {
        this.filterTypes.remove(filterType);
        filterType.setFilter(null);
        return this;
    }

    public void setFilterTypes(Set<FilterType> filterTypes) {
        this.filterTypes = filterTypes;
    }

    public Set<FilterBrand> getFilterBrands() {
        return filterBrands;
    }

    public Filter filterBrands(Set<FilterBrand> filterBrands) {
        this.filterBrands = filterBrands;
        return this;
    }

    public Filter addFilterBrand(FilterBrand filterBrand) {
        this.filterBrands.add(filterBrand);
        filterBrand.setFilter(this);
        return this;
    }

    public Filter removeFilterBrand(FilterBrand filterBrand) {
        this.filterBrands.remove(filterBrand);
        filterBrand.setFilter(null);
        return this;
    }

    public void setFilterBrands(Set<FilterBrand> filterBrands) {
        this.filterBrands = filterBrands;
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
            ", replacedDate='" + getReplacedDate() + "'" +
            "}";
    }
}
