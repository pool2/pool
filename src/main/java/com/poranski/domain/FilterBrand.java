package com.poranski.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FilterBrand.
 */
@Entity
@Table(name = "filter_brand")
public class FilterBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Filter filter;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public FilterBrand name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Filter getFilter() {
        return filter;
    }

    public FilterBrand filter(Filter filter) {
        this.filter = filter;
        return this;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
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
        FilterBrand filterBrand = (FilterBrand) o;
        if (filterBrand.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filterBrand.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FilterBrand{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
