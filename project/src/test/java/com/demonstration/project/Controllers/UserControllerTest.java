package com.demonstration.project.Controllers;

import com.demonstration.project.Entities.User;
import com.demonstration.project.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
	public void testGetUserById() throws Exception {
	    UUID userId = UUID.randomUUID();
	    User user = new User(userId, "MikhailTest", "mikhailtest@test.test");
	    
	    when(userService.getUserById(userId)).thenReturn(Optional.of(user));

	    mockMvc.perform(get("/users/" + userId.toString()))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.username").value("MikhailTest"))
	        .andExpect(jsonPath("$.email").value("mikhailtest@test.test"));
	}


    @Test
    public void testCreateUser() throws Exception {
        UUID userId = UUID.randomUUID();
        User newUser = new User(userId, "Different_Test", "dtest@test.test");

        when(userService.saveUser(any(User.class))).thenReturn(newUser);

        String userJson = "{ \"username\": \"Different_Test\", \"email\": \"dtest@test.test\" }";
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("Different_Test"))
                .andExpect(jsonPath("$.email").value("dtest@test.test"));
    }

    @Test
    public void testDeleteUser_UserExists() throws Exception {
        UUID userId = UUID.randomUUID();
        User existingUser = new User(userId, "MikhailTest", "mikhailtest@test.test");

        when(userService.getUserById(userId)).thenReturn(Optional.of(existingUser));

        doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(delete("/users/{id}", userId.toString()))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    public void testDeleteUser_UserDoesNotExist() throws Exception {
        UUID userId = UUID.randomUUID();

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/users/{id}", userId.toString()))
                .andExpect(status().isNotFound());

        verify(userService, times(0)).deleteUser(userId);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(
                new User(UUID.randomUUID(), "MikhailTest", "mikhailtest@test.test"),
                new User(UUID.randomUUID(), "Other Test Guy", "other@test.test")
        ));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("MikhailTest"))
                .andExpect(jsonPath("$[1].username").value("Other Test Guy"));
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        String email = "mikhailtest@test.test";
        User user = new User(UUID.randomUUID(), "MikhailTest", email);

        when(userService.getUserByEmail(email)).thenReturn(user);

        mockMvc.perform(get("/users/email/" + email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("MikhailTest"))
                .andExpect(jsonPath("$.email").value(email));
    }
}
