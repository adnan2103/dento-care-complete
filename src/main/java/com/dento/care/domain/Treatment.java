package com.dento.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.dento.care.domain.enumeration.Status;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * A Treatment.
 */
@Entity
@Table(name = "treatment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Treatment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chief_complain_description")
    private String chiefComplainDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @CreatedDate
    @Column(name = "start_date")
    private Instant startDate = Instant.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate = Instant.now();

    @ManyToOne
    @JsonIgnore
    private Patient patient;

    @ManyToOne
    @JsonIgnore
    private User doctor;

    @OneToMany(mappedBy = "treatment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notes> notes = new HashSet<>();

    @OneToMany(mappedBy = "treatment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OralExamination> oralExaminations = new HashSet<>();

    @OneToMany(mappedBy = "treatment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "treatment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PreTreatmentImage> preTreatmentImages = new HashSet<>();

    @OneToMany(mappedBy = "treatment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PostTreatmentImage> postTreatmentImages = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChiefComplainDescription() {
        return chiefComplainDescription;
    }

    public Treatment chiefComplainDescription(String chiefComplainDescription) {
        this.chiefComplainDescription = chiefComplainDescription;
        return this;
    }

    public void setChiefComplainDescription(String chiefComplainDescription) {
        this.chiefComplainDescription = chiefComplainDescription;
    }

    public Status getStatus() {
        return status;
    }

    public Treatment status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Treatment startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Treatment lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public Treatment patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public Set<Notes> getNotes() {
        return notes;
    }

    public Treatment notes(Set<Notes> notes) {
        this.notes = notes;
        return this;
    }

    public Treatment addNotes(Notes notes) {
        this.notes.add(notes);
        notes.setTreatment(this);
        return this;
    }

    public Treatment removeNotes(Notes notes) {
        this.notes.remove(notes);
        notes.setTreatment(null);
        return this;
    }

    public void setNotes(Set<Notes> notes) {
        this.notes = notes;
    }

    public Set<OralExamination> getOralExaminations() {
        return oralExaminations;
    }

    public Treatment oralExaminations(Set<OralExamination> oralExaminations) {
        this.oralExaminations = oralExaminations;
        return this;
    }

    public Treatment addOralExamination(OralExamination oralExamination) {
        this.oralExaminations.add(oralExamination);
        oralExamination.setTreatment(this);
        return this;
    }

    public Treatment removeOralExamination(OralExamination oralExamination) {
        this.oralExaminations.remove(oralExamination);
        oralExamination.setTreatment(null);
        return this;
    }

    public void setOralExaminations(Set<OralExamination> oralExaminations) {
        this.oralExaminations = oralExaminations;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public Treatment payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public Treatment addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setTreatment(this);
        return this;
    }

    public Treatment removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setTreatment(null);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<PreTreatmentImage> getPreTreatmentImages() {
        return preTreatmentImages;
    }

    public Treatment preTreatmentImages(Set<PreTreatmentImage> preTreatmentImages) {
        this.preTreatmentImages = preTreatmentImages;
        return this;
    }

    public Treatment addPreTreatmentImage(PreTreatmentImage preTreatmentImage) {
        this.preTreatmentImages.add(preTreatmentImage);
        preTreatmentImage.setTreatment(this);
        return this;
    }

    public Treatment removePreTreatmentImage(PreTreatmentImage preTreatmentImage) {
        this.preTreatmentImages.remove(preTreatmentImage);
        preTreatmentImage.setTreatment(null);
        return this;
    }

    public void setPreTreatmentImages(Set<PreTreatmentImage> preTreatmentImages) {
        this.preTreatmentImages = preTreatmentImages;
    }

    public Set<PostTreatmentImage> getPostTreatmentImages() {
        return postTreatmentImages;
    }

    public Treatment postTreatmentImages(Set<PostTreatmentImage> postTreatmentImages) {
        this.postTreatmentImages = postTreatmentImages;
        return this;
    }

    public Treatment addPostTreatmentImage(PostTreatmentImage postTreatmentImage) {
        this.postTreatmentImages.add(postTreatmentImage);
        postTreatmentImage.setTreatment(this);
        return this;
    }

    public Treatment removePostTreatmentImage(PostTreatmentImage postTreatmentImage) {
        this.postTreatmentImages.remove(postTreatmentImage);
        postTreatmentImage.setTreatment(null);
        return this;
    }

    public void setPostTreatmentImages(Set<PostTreatmentImage> postTreatmentImages) {
        this.postTreatmentImages = postTreatmentImages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Treatment treatment = (Treatment) o;
        if (treatment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), treatment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Treatment{" +
            "id=" + getId() +
            ", chiefComplainDescription='" + getChiefComplainDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
