package com.solidice.springbootrsocketserver.users;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository {
    private final List<User> usersList = new ArrayList<User>() {
        {
            add(new User("user1@email.com", "user1", 1));
            add(new User("user2@email.com", "user2", 2));
            add(new User("user3@email.com", "user3", 3));
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
