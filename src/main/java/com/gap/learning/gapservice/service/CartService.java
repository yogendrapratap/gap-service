package com.gap.learning.gapservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gap.learning.gapservice.config.TokenService;
import com.gap.learning.gapservice.document.Cart;
import com.gap.learning.gapservice.document.Users;
import com.gap.learning.gapservice.dto.CartDTO;
import com.gap.learning.gapservice.dto.CartListDTO;
import com.gap.learning.gapservice.exception.CartException;
import com.gap.learning.gapservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private KafkaProducerService kafkaProducerService;


    public String addToCart(CartListDTO cartListDTO, String userId) throws JsonProcessingException {


        Users user = userService.getUsersDetailsById(userId);
        if(user == null) {
            throw new CartException("User not found with id: " + userId);
        }

        cartListDTO.getCartDTO().stream()
                //.map(CartDTO::getProductCode)
                .forEach(dto -> productService.searchProduct(dto.getProductCode(), dto.getQuantity()));

        List<Cart> existingCartItems = new ArrayList<>(cartRepository.findAllByUserId(userId));

        List<CartDTO> toAddList = new ArrayList<>(cartListDTO.getCartDTO());
        List<CartDTO> finalListToAdd = new ArrayList<>();

        for (CartDTO newItem : toAddList) {
            boolean matched = false;
            for (Cart existing : existingCartItems) {
                if (existing.getProductCode().equals(newItem.getProductCode())) {
                    existing.setQuantity(existing.getQuantity() + newItem.getQuantity());
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                finalListToAdd.add(newItem);
            }
        }

        List<CartDTO> existingCartDTOs = convertToDTOList(existingCartItems);

        finalListToAdd.addAll(existingCartDTOs);

        ///  Kafka Logic can be added here
        CompletableFuture<SendResult<String, String>>
                future = kafkaProducerService.sendMessageAsync(new CartListDTO().setCartDTO(finalListToAdd), userId);

        boolean result = waitForKafkaResult(future);





        return (result) ? "Cart Message send successfully." : "Cart message failed!!";
    }


    private boolean waitForKafkaResult(CompletableFuture<SendResult<String, String>> future) {
        try {
            future.get();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<CartDTO> convertToDTOList(List<Cart> cartList) {
        return cartList.stream()
                .map(cart -> new CartDTO(
                        cart.getProductCode(),
                        cart.getQuantity()))
                .collect(Collectors.toList());
    }

}
