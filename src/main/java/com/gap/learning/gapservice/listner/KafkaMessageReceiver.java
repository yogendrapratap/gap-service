package com.gap.learning.gapservice.listner;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gap.learning.gapservice.document.Cart;
import com.gap.learning.gapservice.dto.CartDTO;
import com.gap.learning.gapservice.dto.CartListDTO;
import com.gap.learning.gapservice.repository.CartRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaMessageReceiver {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CartRepository cartRepository;

    @KafkaListener(topics = "cart-topic", groupId = "group_id")
    public void onMessage(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        String userId = consumerRecord.key();
        CartListDTO cartListDTO = objectMapper.readValue(consumerRecord.value(), CartListDTO.class);
        List<Cart> newCartList = toEntityList(cartListDTO, userId);
        cartRepository.deleteByUserId(userId);
        List<Cart> saved = cartRepository.saveAll(newCartList);
    }


    public List<Cart> toEntityList(CartListDTO cartListDTO, String userId) {
        List<CartDTO> cartList = cartListDTO.getCartDTO();
        return cartList.stream().map( cartDTO -> {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductCode(cartDTO.getProductCode());
            cart.setQuantity(cartDTO.getQuantity());
            return cart;
        }).toList();
    }
}
