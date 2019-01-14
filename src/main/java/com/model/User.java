package main.java.com.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private static final Map<String, User> USERS = new HashMap<>();

    private String id;

    private User(String id) {
        this.id = id;
    }
    public static User of(String id) {
        User user = USERS.get(id);
        if (user == null) {
            user = new User(id);
            USERS.put(id, user);
        }
        return user;
    }

    public String getId() {
        return id;
    }

}
