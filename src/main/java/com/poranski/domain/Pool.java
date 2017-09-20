package com.poranski.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Pool.
 */
@Entity
@Table(name = "pool")
public class Pool implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_size")
    private Integer size;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Note note;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public Pool size(Integer size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Pool customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Note getNote() {
        return note;
    }

    public Pool note(Note note) {
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
        Pool pool = (Pool) o;
        if (pool.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pool.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pool{" +
            "id=" + getId() +
            ", size='" + getSize() + "'" +
            "}";
    }
}
