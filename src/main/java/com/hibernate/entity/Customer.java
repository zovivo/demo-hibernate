package com.hibernate.entity;

import java.sql.Date;

public class Customer {

    private Integer id;
    private String name;
    private Integer gender;
    private Date dateOfBirth;
    private Contact contact;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Customer(String name, Integer gender, Date dateOfBirth, Contact contact) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.contact = contact;
    }

    public Customer() {
    }
}
