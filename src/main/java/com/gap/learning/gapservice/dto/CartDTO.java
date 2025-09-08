package com.gap.learning.gapservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CartDTO {

    @NotBlank(message = "Product code cannot be blank")
    private String productCode;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 100, message = "Quantity cannot exceed 100")
    private Integer quantity;

    public CartDTO(String productCode, Integer quantity) {
        this.productCode = productCode;
        this.quantity = quantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public CartDTO setProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CartDTO setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
