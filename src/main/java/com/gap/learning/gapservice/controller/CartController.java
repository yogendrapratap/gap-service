package com.gap.learning.gapservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.gap.learning.gapservice.config.TokenService;
import com.gap.learning.gapservice.config.User;
import com.gap.learning.gapservice.dto.CartListDTO;
import com.gap.learning.gapservice.exception.CartException;
import com.gap.learning.gapservice.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;
    

    @PostMapping(value = "/addToCart/user/{userId}")
    public ResponseEntity<String> addToCart(

            @Valid @RequestBody CartListDTO cartListDTO,
            @PathVariable("userId") String userId) throws JsonProcessingException {

        return ResponseEntity.ok(cartService.addToCart(cartListDTO, userId));
    }

}
