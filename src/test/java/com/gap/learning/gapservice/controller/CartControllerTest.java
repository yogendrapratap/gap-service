package com.gap.learning.gapservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gap.learning.gapservice.config.TokenService;
import com.gap.learning.gapservice.dto.CartListDTO;
import com.gap.learning.gapservice.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartControllerTest {
    @Mock
    private CartService cartService;
    @Mock
    private TokenService tokenService;
    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("addToCart returns OK for valid cart and userId")
    void addToCart_ReturnsOkForValidCartAndUserId() throws JsonProcessingException {
        CartListDTO cartListDTO = new CartListDTO().setCartDTO(
                java.util.List.of(new com.gap.learning.gapservice.dto.CartDTO("P1", 2)));
        String userId = "user123";
        when(cartService.addToCart(cartListDTO, userId)).thenReturn("Added to cart");
        ResponseEntity<String> response = cartController.addToCart(cartListDTO, userId);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Added to cart", response.getBody());
        verify(cartService).addToCart(cartListDTO, userId);
    }

    @Test
    @DisplayName("addToCart propagates JsonProcessingException from service")
    void addToCart_PropagatesJsonProcessingException_FromService() throws JsonProcessingException {
        CartListDTO cartListDTO = new CartListDTO().setCartDTO(
                java.util.List.of(new com.gap.learning.gapservice.dto.CartDTO("P1", 2)));
        String userId = "user123";
        when(cartService.addToCart(cartListDTO, userId)).thenThrow(new JsonProcessingException("error") {
        });
        assertThrows(JsonProcessingException.class, () -> cartController.addToCart(cartListDTO, userId));
    }

    @Test
    @DisplayName("addToCart works with empty cart list")
    void addToCart_WorksWithEmptyCartList() throws JsonProcessingException {
        CartListDTO cartListDTO = new CartListDTO().setCartDTO(java.util.Collections.emptyList());
        String userId = "user123";
        when(cartService.addToCart(cartListDTO, userId)).thenReturn("Added to cart");
        ResponseEntity<String> response = cartController.addToCart(cartListDTO, userId);


    }

}

