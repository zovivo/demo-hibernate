package com.hibernate.entity;

public class Item {

    private Integer id;
    private Product product;
    private Integer quantity;
    private Order order;
    private Integer discount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Item(Product product, Integer quantity, Order order, Integer discount) {
        this.product = product;
        this.quantity = quantity;
        this.order = order;
        this.discount = discount;
    }

    public Item() {
    }
}
