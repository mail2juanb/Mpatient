package com.microdiab.mpatient.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;


@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "lastname is mandatory")
    private String lastname;

    @NotBlank(message = "firstname is mandatory")
    private String firstname;

    @NotNull(message = "dateofbirth is mandatory")
    @Past(message = "dateofbirth must be in the past")
    private LocalDate dateofbirth;

    @NotBlank(message = "gender is mandatory")
    private String gender;

    private String address;

    private String phone;


    public Patient() {
    }

    public Patient(Long id, String lastname, String firstname, LocalDate dateofbirth, String gender, String address, String phone) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.dateofbirth = dateofbirth;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public LocalDate getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(LocalDate dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
