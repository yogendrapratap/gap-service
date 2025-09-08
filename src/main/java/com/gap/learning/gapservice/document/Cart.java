package com.gap.learning.gapservice.document;

import org.springframework.data.annotation.Id;

public class Cart {

    @Id
    private String id;

    private String productCode;
    private String userId;
    private Integer quantity;

    public String getId() {
        return id;
    }

    public Cart setId(String id) {
        this.id = id;
        return this;
    }

    public String getProductCode() {
        return productCode;
    }

    public Cart setProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Cart setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Cart setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
