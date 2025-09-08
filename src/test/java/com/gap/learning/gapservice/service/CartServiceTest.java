package com.gap.learning.gapservice.service;

import com.gap.learning.gapservice.document.Cart;
import com.gap.learning.gapservice.document.Users;
import com.gap.learning.gapservice.dto.CartDTO;
import com.gap.learning.gapservice.dto.CartListDTO;
import com.gap.learning.gapservice.exception.CartException;
import com.gap.learning.gapservice.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.SendResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductService productService;
    @Mock
    private UserService userService;
    @Mock
    private KafkaProducerService kafkaProducerService;
    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("addToCart adds new items and updates quantity for existing items")
    void addToCart_AddsAndUpdatesItemsSuccessfully() throws Exception {
        String userId = "user1";
        Users user = new Users();
        when(userService.getUsersDetailsById(userId)).thenReturn(user);
        CartDTO newItem1 = new CartDTO("P1", 2);
        CartDTO newItem2 = new CartDTO("P2", 1);
        CartDTO existingItem = new CartDTO("P1", 3);
        Cart existingCart = new Cart();
        existingCart.setProductCode("P1");
        existingCart.setQuantity(1);
        when(cartRepository.findAllByUserId(userId)).thenReturn(Collections.singletonList(existingCart));
        when(productService.searchProduct(any(), any())).thenReturn(null);
        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        future.complete(mock(SendResult.class));
        when(kafkaProducerService.sendMessageAsync(any(), eq(userId))).thenReturn(future);
        CartListDTO cartListDTO = new CartListDTO().setCartDTO(Arrays.asList(newItem1, newItem2));
        String result = cartService.addToCart(cartListDTO, userId);
        assertEquals("Cart Message send successfully.", result);
        verify(productService, times(2)).searchProduct(any(), any());
        verify(kafkaProducerService).sendMessageAsync(any(), eq(userId));
    }

    @Test
    @DisplayName("addToCart throws CartException when user not found")
    void addToCart_ThrowsException_WhenUserNotFound() {
        String userId = "user2";
        when(userService.getUsersDetailsById(userId)).thenReturn(null);
        CartListDTO cartListDTO = new CartListDTO().setCartDTO(Collections.emptyList());
        assertThrows(CartException.class, () -> cartService.addToCart(cartListDTO, userId));
    }

    @Test
    @DisplayName("addToCart returns failure message when Kafka send fails")
    void addToCart_ReturnsFailureMessage_WhenKafkaFails() throws Exception {
        String userId = "user3";
        Users user = new Users();
        when(userService.getUsersDetailsById(userId)).thenReturn(user);
        CartDTO newItem = new CartDTO("P3", 1);
        when(cartRepository.findAllByUserId(userId)).thenReturn(Collections.emptyList());
        when(productService.searchProduct(any(), any())).thenReturn(null);
        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Kafka error"));
        when(kafkaProducerService.sendMessageAsync(any(), eq(userId))).thenReturn(future);
        CartListDTO cartListDTO = new CartListDTO().setCartDTO(Collections.singletonList(newItem));
        String result = cartService.addToCart(cartListDTO, userId);
        assertEquals("Cart message failed!!", result);
    }

    @Test
    @DisplayName("addToCart handles empty cart list")
    void addToCart_HandlesEmptyCartList() throws Exception {
        String userId = "user4";
        Users user = new Users();
        when(userService.getUsersDetailsById(userId)).thenReturn(user);
        when(cartRepository.findAllByUserId(userId)).thenReturn(Collections.emptyList());
        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        future.complete(mock(SendResult.class));
        when(kafkaProducerService.sendMessageAsync(any(), eq(userId))).thenReturn(future);
        CartListDTO cartListDTO = new CartListDTO().setCartDTO(Collections.emptyList());
        String result = cartService.addToCart(cartListDTO, userId);
        assertEquals("Cart Message send successfully.", result);
    }

    @Test
    @DisplayName("convertToDTOList returns correct DTOs for cart list")
    void convertToDTOList_ReturnsCorrectDTOs() {
        Cart cart1 = new Cart();
        cart1.setProductCode("P1");
        cart1.setQuantity(2);
        Cart cart2 = new Cart();
        cart2.setProductCode("P2");
        cart2.setQuantity(3);
        List<CartDTO> dtos = cartService.convertToDTOList(Arrays.asList(cart1, cart2));
        assertEquals(2, dtos.size());
        assertEquals("P1", dtos.get(0).getProductCode());
        assertEquals(2, dtos.get(0).getQuantity());
        assertEquals("P2", dtos.get(1).getProductCode());
        assertEquals(3, dtos.get(1).getQuantity());
    }
}

