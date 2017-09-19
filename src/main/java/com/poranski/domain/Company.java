package com.poranski.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private Set<Customer> customers = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "company_inventory_item",
               joinColumns = @JoinColumn(name="companies_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="inventory_items_id", referencedColumnName="id"))
    private Set<InventoryItem> inventoryItems = new HashSet<>();

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

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Company phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Company employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public Company addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setCompany(this);
        return this;
    }

    public Company removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setCompany(null);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public Company customers(Set<Customer> customers) {
        this.customers = customers;
        return this;
    }

    public Company addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.setCompany(this);
        return this;
    }

    public Company removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.setCompany(null);
        return this;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public Company inventoryItems(Set<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
        return this;
    }

    public Company addInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItems.add(inventoryItem);
        return this;
    }

    public Company removeInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItems.remove(inventoryItem);
        return this;
    }

    public void setInventoryItems(Set<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
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
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
