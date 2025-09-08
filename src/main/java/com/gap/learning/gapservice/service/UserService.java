package com.gap.learning.gapservice.service;

import com.gap.learning.gapservice.document.Users;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public Users getUsersDetailsById(String userId) {

        Users p = new Users();
        p.setId(userId);
        p.setName("Yogendra");
        p.setUserName("yogendra");
        p.setPassword("yogendra@123");
        p.setRole("USER");

        return p;
    }

}
