package com.gap.learning.gapservice.service;

import com.gap.learning.gapservice.dto.ProductDTO;
import com.gap.learning.gapservice.exception.CartException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {

    public ProductDTO searchProduct(String productCode, Integer quantity) {

        ProductDTO product = new ProductDTO();
        product.setId("1");
        product.setProductCode(productCode);
        product.setProductName("Sample Product");
        product.setPrice(new BigDecimal("19.99"));
        product.setSize("M");
        product.setAvailability(true);
        product.setQuantity(100);
        product.setCarbonFootPrint(2.5);

        if (productCode.equals("P005")) {
            throw new CartException("Product not found with productCode: " + productCode);
        }

        if(quantity > product.getQuantity()) {
            throw new CartException("Product quantity not available for purchase for product code : " + productCode);
        }

        return product;

    }
}
