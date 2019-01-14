package main.java.com.security;

import java.util.HashMap;
import java.util.Map;

public class Auth {
    private static final Map<String, String> USERS = new HashMap<String, String>();

    static {
        USERS.put("admin", "pass");
    }
    public static boolean validate(String user, String password){
        String validUserPassword = USERS.get(user);
        return validUserPassword != null && validUserPassword.equals(password);
    }
}

