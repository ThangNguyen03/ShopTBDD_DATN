package com.shoptbdddatn.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "order_date")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date orderDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "required_date")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date requiredDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "shipped_date")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date shippedDate;
	
	@NotNull
	@Column(name = "status", length = 50)
	private String status;
	
	@Column(name = "comments", length = 255)
	private String comments;
	
	@Column(name = "ammount")
	@Digits(integer=10, fraction=2)
	private BigDecimal ammount;
	
	@Column(name = "check_number", length = 50, unique = true)
	@NotNull
	private String checkNumber;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	@JsonIgnore
	private Customer customer;
	
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Order(int id, Date orderDate, Date requiredDate, Date shippedDate, @NotNull String status, String comments,
            @Digits(integer = 10, fraction = 2) BigDecimal ammount, @NotNull String checkNumber, Customer customer,
            List<OrderDetail> orderDetails) {
        this.id = id;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.status = status;
        this.comments = comments;
        this.ammount = ammount;
        this.checkNumber = checkNumber;
        this.customer = customer;
        this.orderDetails = orderDetails;
    }

    public Order() {
    }

    
    
}
