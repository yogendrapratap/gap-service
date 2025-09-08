package com.gap.learning.gapservice.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "products")
public class Products {

    @Id
    private String id;
    private String productCode;
    private String productName;
    private String description;
    private BigDecimal price;
    private String size;
    private Boolean availability;
    private Integer quantity;
    private Double carbonFootPrint;

    public String getId() {
        return id;
    }

    public Products setId(String id) {
        this.id = id;
        return this;
    }

    public String getProductCode() {
        return productCode;
    }

    public Products setProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public Products setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Products setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Products setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getSize() {
        return size;
    }

    public Products setSize(String size) {
        this.size = size;
        return this;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public Products setAvailability(Boolean availability) {
        this.availability = availability;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Products setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Double getCarbonFootPrint() {
        return carbonFootPrint;
    }

    public Products setCarbonFootPrint(Double carbonFootPrint) {
        this.carbonFootPrint = carbonFootPrint;
        return this;
    }
}
