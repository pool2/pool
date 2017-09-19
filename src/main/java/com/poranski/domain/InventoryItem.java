package com.poranski.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A InventoryItem.
 */
@Entity
@Table(name = "inventory_item")
public class InventoryItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

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

    public InventoryItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public InventoryItem quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
        InventoryItem inventoryItem = (InventoryItem) o;
        if (inventoryItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inventoryItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }
}
