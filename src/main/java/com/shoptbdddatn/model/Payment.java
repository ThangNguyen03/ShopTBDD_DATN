package com.shoptbdddatn.model;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name="payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonIgnore
	private Order order;
	
	@Column(name = "check_number", length = 50, unique = true)
	@NotNull
	private String checkNumber;
	
	@Column(name = "payment_date")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date paymentDate;
	
	@Column(name = "ammount")
	@Digits(integer=8, fraction=2)
	private BigDecimal ammount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
    }

    public Payment(int id, Order order, String checkNumber, Date paymentDate,
            @Digits(integer = 8, fraction = 2) BigDecimal ammount) {
        this.id = id;
        this.order = order;
        this.checkNumber = checkNumber;
        this.paymentDate = paymentDate;
        this.ammount = ammount;
    }
    public Payment(){
        super();
    }
}
