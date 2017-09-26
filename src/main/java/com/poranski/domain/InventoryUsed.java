package com.poranski.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A InventoryUsed.
 */
@Entity
@Table(name = "inventory_used")
public class InventoryUsed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "note")
    private String note;

    @ManyToOne
    private InventoryItem inventoryItem;

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

    public InventoryUsed name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public InventoryUsed quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public InventoryUsed note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public InventoryUsed inventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
        return this;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
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
        InventoryUsed inventoryUsed = (InventoryUsed) o;
        if (inventoryUsed.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inventoryUsed.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InventoryUsed{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
