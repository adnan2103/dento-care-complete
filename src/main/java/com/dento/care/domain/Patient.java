package com.dento.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.dento.care.domain.enumeration.Gender;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Max(value = 99)
    @Column(name = "age")
    private Integer age;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @ManyToOne
    @JsonIgnore
    private User doctor;

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Treatment> treatments = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContactNumber> contactNumbers = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Email> emails = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Address> addresses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Patient name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public Patient gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public Patient age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Patient photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Patient photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public Patient treatments(Set<Treatment> treatments) {
        this.treatments = treatments;
        return this;
    }

    public Patient addTreatment(Treatment treatment) {
        this.treatments.add(treatment);
        treatment.setPatient(this);
        return this;
    }

    public Patient removeTreatment(Treatment treatment) {
        this.treatments.remove(treatment);
        treatment.setPatient(null);
        return this;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public Patient appointments(Set<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    public Patient addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        appointment.setPatient(this);
        return this;
    }

    public Patient removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
        appointment.setPatient(null);
        return this;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<ContactNumber> getContactNumbers() {
        return contactNumbers;
    }

    public Patient contactNumbers(Set<ContactNumber> contactNumbers) {
        this.contactNumbers = contactNumbers;
        return this;
    }

    public Patient addContactNumber(ContactNumber contactNumber) {
        this.contactNumbers.add(contactNumber);
        contactNumber.setPatient(this);
        return this;
    }

    public Patient removeContactNumber(ContactNumber contactNumber) {
        this.contactNumbers.remove(contactNumber);
        contactNumber.setPatient(null);
        return this;
    }

    public void setContactNumbers(Set<ContactNumber> contactNumbers) {
        this.contactNumbers = contactNumbers;
    }

    public Set<Email> getEmails() {
        return emails;
    }

    public Patient emails(Set<Email> emails) {
        this.emails = emails;
        return this;
    }

    public Patient addEmail(Email email) {
        this.emails.add(email);
        email.setPatient(this);
        return this;
    }

    public Patient removeEmail(Email email) {
        this.emails.remove(email);
        email.setPatient(null);
        return this;
    }

    public void setEmails(Set<Email> emails) {
        this.emails = emails;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Patient addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Patient addAddress(Address address) {
        this.addresses.add(address);
        address.setPatient(this);
        return this;
    }

    public Patient removeAddress(Address address) {
        this.addresses.remove(address);
        address.setPatient(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Patient patient = (Patient) o;
        if (patient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", gender='" + getGender() + "'" +
            ", age='" + getAge() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + photoContentType + "'" +
            "}";
    }
}
