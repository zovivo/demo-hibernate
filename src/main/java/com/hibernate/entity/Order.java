package com.hibernate.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private Integer id;
    private Customer customer;
    private Date orderDate;
    private List<Item> items = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Order(Customer customer, Date orderDate, List<Item> items) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.items = items;
    }

    public Order() {
    }
}
