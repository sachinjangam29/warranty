package org.warranty.warranty_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.warranty.warranty_service.model.Account;
import org.warranty.warranty_service.payload.request.AccountRequest;
import org.warranty.warranty_service.payload.response.AccountResponse;
import org.warranty.warranty_service.repository.AccountRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUserDetails_Success(){
        AccountRequest accountRequest = new AccountRequest("John", "Doe", "john@example.com",
                "userId123", "password123",
                LocalDateTime.now(), LocalDateTime.now().plusDays(90),
                true, "Region1", 1234567890L);

        Account account = new Account(1, "John", "Doe", "john@example.com",
                "userId123", "password123",
                LocalDateTime.now(), LocalDateTime.now().plusDays(90), true,
                "Region1", 1234567890L);

        Mockito.when(accountRepository.existsByUserId("userId123")).thenReturn(false);
        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.saveUserDetails(accountRequest);
        assertNotNull(result);
        assertEquals("userId123",result.getUserId());
    }

    @Test
    public void testSaveUserDetails_UserExists(){
        AccountRequest accountRequest = new AccountRequest("John", "Doe", "john@example.com",
                "userId123", "password123",
                LocalDateTime.now(), LocalDateTime.now().plusDays(90),
                true, "Region1", 1234567890L);

        when(accountRepository.existsByUserId(anyString())).thenReturn(true);
        Account result = accountService.saveUserDetails(accountRequest);
        assertNull(result);
    }

    @Test
    public void testDeleteAllUsers(){
            doNothing().when(accountRepository).deleteAll();
            accountService.deleteAllUsers();
            verify(accountRepository,times(1)).deleteAll();
    }

    @Test
    public void testGetAllUsers() {
        List<Account> accounts = new ArrayList<>();
        Account account = new Account(1, "John", "Doe", "john@example.com", "userId123", "password123", LocalDateTime.now(), LocalDateTime.now().plusDays(90), true, "Region1", 1234567890L);
        accounts.add(account);

        Page<Account> accountPage = new PageImpl<>(accounts);
        when(accountRepository.findAll(any(PageRequest.class))).thenReturn(accountPage);

        Page<AccountResponse> result = accountService.getAllUsers(0, 5, "userId", "asc");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("john@example.com", result.getContent().get(0).getEmail());
    }

    @Test
    public void testGenerateRandomUserId() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = AccountService.class.getDeclaredMethod("generateRandomUserId");
        method.setAccessible(true);

        String userId = (String) method.invoke(accountService);

        assertNotNull(userId);
        assertEquals(5, userId.length());
    }

}
