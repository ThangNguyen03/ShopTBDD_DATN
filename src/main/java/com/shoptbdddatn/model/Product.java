package com.shoptbdddatn.model;

import java.math.BigDecimal;
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Nhập mã sản phẩm")
    @Size(min = 2,message = "Mã sản phẩm tối thiểu 2 ký tự")
    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_vendor")
    private String productVendor;

    @Column(name = "quantity_in_stock")
    private int quantityInStock;

    @Column(name = "buy_price")
	@Digits(integer=8, fraction=2)
    private BigDecimal buyPrice;

    @ManyToOne
	@JoinColumn(name = "product_line_id")
	@JsonIgnore
    private ProductLine productLine;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductVendor() {
        return productVendor;
    }

    public void setProductVendor(String productVendor) {
        this.productVendor = productVendor;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public ProductLine getProductLine() {
        return productLine;
    }

    public void setProductLine(ProductLine productLine) {
        this.productLine = productLine;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Product(int id,
            @NotEmpty(message = "Nhập mã sản phẩm") @Size(min = 2, message = "Mã sản phẩm tối thiểu 2 ký tự") String productCode,
            String productName, String productDescription, String productVendor, int quantityInStock,
            @Digits(integer = 8, fraction = 2) BigDecimal buyPrice, ProductLine productLine) {
        super();
        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productVendor = productVendor;
        this.quantityInStock = quantityInStock;
        this.buyPrice = buyPrice;
        this.productLine = productLine;
    }

    public Product() {
        super();
    }

    
}
