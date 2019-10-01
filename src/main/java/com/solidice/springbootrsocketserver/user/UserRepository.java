package com.solidice.springbootrsocketserver.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository {
    private final List<User> usersList = new ArrayList<User>() {
        {
            for (var i = 0; i < 10; i++) {
                User u = User
                    .builder()
                    .id(i)
                    .username("user" + i)
                    .email("user" + i + "@email.com")
                    .build();
                add(u);
            }
        }
    };

    public List<User> getUsers() {
        return usersList;
    }

    public User getUserById(int id) {
        return usersList
            .stream()
            .filter(u -> u.getId() == id)
            .findFirst()
            .orElse(null);
    }
}
