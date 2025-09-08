package com.gap.learning.gapservice.dto;

import java.math.BigDecimal;

public class ProductDTO {

    private String id;
    private String productCode;
    private String productName;
    private BigDecimal price;
    private String size;
    private Boolean availability;
    private Integer quantity;
    private Double carbonFootPrint;

    public String getId() {
        return id;
    }

    public ProductDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getProductCode() {
        return productCode;
    }

    public ProductDTO setProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public ProductDTO setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getSize() {
        return size;
    }

    public ProductDTO setSize(String size) {
        this.size = size;
        return this;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public ProductDTO setAvailability(Boolean availability) {
        this.availability = availability;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductDTO setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Double getCarbonFootPrint() {
        return carbonFootPrint;
    }

    public ProductDTO setCarbonFootPrint(Double carbonFootPrint) {
        this.carbonFootPrint = carbonFootPrint;
        return this;
    }
}
