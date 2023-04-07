package com.shoptbdddatn.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "last_name",length = 50)
    private String lastName;

    @Column(name = "first_name",length = 50)
    private String firstName;

    @Column(name = "phone_number",length = 50)
    private String phoneNumber;

    @Column(name = "address",length = 50)
    private String address;

    @Column(name = "city",length = 50)
    private String city;

    @Column(name = "email",length = 50,unique = true)
    private String email;

    @Column(name = "credit_limit")
    private int creditLimit;

    @OneToMany(targetEntity = Order.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Order> orders;


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public int getCreditLimit() {
        return creditLimit;
    }


    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }


    public List<Order> getOrders() {
        return orders;
    }


    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


    public Customer(int id, String lastName, String firstName, String phoneNumber, String address, String city,
            String email, int creditLimit, List<Order> orders) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.email = email;
        this.creditLimit = creditLimit;
        this.orders = orders;
    }

    public Customer(){
        super();
    }
}
