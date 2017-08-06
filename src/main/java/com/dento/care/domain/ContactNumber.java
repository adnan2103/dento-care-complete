package com.dento.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ContactNumber.
 */
@Entity
@Table(name = "contact_number")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContactNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Max(value = 9999)
    @Column(name = "contact_numebr", nullable = false)
    private Integer contactNumebr;

    @ManyToOne
    @JsonIgnore
    private Patient patient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getContactNumebr() {
        return contactNumebr;
    }

    public ContactNumber contactNumebr(Integer contactNumebr) {
        this.contactNumebr = contactNumebr;
        return this;
    }

    public void setContactNumebr(Integer contactNumebr) {
        this.contactNumebr = contactNumebr;
    }

    public Patient getPatient() {
        return patient;
    }

    public ContactNumber patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContactNumber contactNumber = (ContactNumber) o;
        if (contactNumber.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactNumber.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactNumber{" +
            "id=" + getId() +
            ", contactNumebr='" + getContactNumebr() + "'" +
            "}";
    }
}
