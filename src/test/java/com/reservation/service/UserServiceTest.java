package com.reservation.service;

import com.reservation.model.User;
import com.reservation.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        User user = new User("John Doe", "john@example.com", "0712345678");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());
    }

    @Test
    void testCreateUser_InvalidEmail() {
        User invalidUser = new User("John Doe", "invalid-email", "0712345678");

        when(userRepository.save(any(User.class)))
                .thenThrow(new ConstraintViolationException("Invalid email format", null));

        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            userService.createUser(invalidUser);
        });

        assertTrue(exception.getMessage().contains("Invalid email format"));
    }
}
