package org.warranty.warranty_service.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.warranty.warranty_service.model.Account;
import org.warranty.warranty_service.payload.request.AccountRequest;
import org.warranty.warranty_service.payload.response.AccountResponse;
import org.warranty.warranty_service.service.AccountService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUserDetails_Success(){
        AccountRequest accountRequest = new AccountRequest(
                "John", "Doe", "john@example.com", "userId123",
                "password123", LocalDateTime.now(),
                LocalDateTime.now().plusDays(90), true,
                "Region1", 1234567890L);

        Account account = new Account(
                1, "John", "Doe", "john@example.com", "userId123",
                "password123", LocalDateTime.now(),
                LocalDateTime.now().plusDays(90), true, "Region1",
                1234567890L);

        when(accountService.saveUserDetails(accountRequest)).thenReturn(account);

        ResponseEntity<String> response = accountController.saveUserDetails(accountRequest);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals("User Details saved successfully with the user id userId123",response.getBody());
    }

    @Test
    public void testUserSaveDetails_UserExists(){
        AccountRequest accountRequest = new AccountRequest("John", "Doe", "john@example.com", "userId123",
                "password123", LocalDateTime.now(),
                LocalDateTime.now().plusDays(90), true, "Region1",
                1234567890L);

        when(accountService.saveUserDetails(accountRequest)).thenReturn(null);

        ResponseEntity<String> response = accountController.saveUserDetails(accountRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User already exists",response.getBody());

    }

    @Test
    public void testGetAllUsers(){
        Page<AccountResponse> mPage = mock(Page.class);
        when(accountService.getAllUsers(0,5,"userId","asc")).thenReturn(mPage);

        Page<AccountResponse> result = accountController.getAllUsersData(0,5,"userId","asc");

        assertNotNull(result);
        verify(accountService, times(1)).getAllUsers(0,5,"userId","asc");
    }

    @Test
    public void testDeleteAllUsers(){
        ResponseEntity<String> response = accountController.deleteAllUserDetails();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("All users deleted succesfully", response.getBody());
    }
}
