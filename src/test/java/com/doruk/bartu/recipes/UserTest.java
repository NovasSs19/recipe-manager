package com.doruk.bartu.recipes;

import com.doruk.bartu.recipes.user.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreation() {

        User user = new User();
        user.setEmail("test@example.com");
        user.setPasswordHash("password123");
        user.setRole("USER");


        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPasswordHash());
        assertEquals("USER", user.getRole());


        assertNull(user.getId());
    }
}