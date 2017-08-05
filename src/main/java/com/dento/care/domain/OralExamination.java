package com.dento.care.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OralExamination.
 */
@Entity
@Table(name = "oral_examination")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OralExamination implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    private Long cost;

    @ManyToOne
    private Treatment treatment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public OralExamination description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCost() {
        return cost;
    }

    public OralExamination cost(Long cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public OralExamination treatment(Treatment treatment) {
        this.treatment = treatment;
        return this;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OralExamination oralExamination = (OralExamination) o;
        if (oralExamination.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), oralExamination.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OralExamination{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", cost='" + getCost() + "'" +
            "}";
    }
}
