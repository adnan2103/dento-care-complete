package com.dento.care.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Appointment.
 */
@Entity
@Table(name = "appointment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appointment_start")
    private ZonedDateTime appointmentStart;

    @Column(name = "appointment_end")
    private ZonedDateTime appointmentEnd;

    @Column(name = "planned_treatment")
    private String plannedTreatment;

    @ManyToOne
    private Patient patient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getAppointmentStart() {
        return appointmentStart;
    }

    public Appointment appointmentStart(ZonedDateTime appointmentStart) {
        this.appointmentStart = appointmentStart;
        return this;
    }

    public void setAppointmentStart(ZonedDateTime appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public ZonedDateTime getAppointmentEnd() {
        return appointmentEnd;
    }

    public Appointment appointmentEnd(ZonedDateTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
        return this;
    }

    public void setAppointmentEnd(ZonedDateTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    public String getPlannedTreatment() {
        return plannedTreatment;
    }

    public Appointment plannedTreatment(String plannedTreatment) {
        this.plannedTreatment = plannedTreatment;
        return this;
    }

    public void setPlannedTreatment(String plannedTreatment) {
        this.plannedTreatment = plannedTreatment;
    }

    public Patient getPatient() {
        return patient;
    }

    public Appointment patient(Patient patient) {
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
        Appointment appointment = (Appointment) o;
        if (appointment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appointment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Appointment{" +
            "id=" + getId() +
            ", appointmentStart='" + getAppointmentStart() + "'" +
            ", appointmentEnd='" + getAppointmentEnd() + "'" +
            ", plannedTreatment='" + getPlannedTreatment() + "'" +
            "}";
    }
}
