/*
package org.warranty.warranty_service.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.warranty.warranty_service.model.Account;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSaveUserDetails(){
        Account account = new Account(null, "John", "Doe", "john@example.com", "userId123",
                "password123", LocalDateTime.now(),
                LocalDateTime.now().plusDays(90), true, "Region1",
                1234567890L);

        Account savedAccount = accountRepository.save(account);
        assertNotNull(savedAccount);
        assertEquals("john@example.com", savedAccount.getEmail());
    }

    @Test
    public void testExistsByUserId() {
        Account account = new Account(null, "John", "Doe", "john@example.com", "userId123", "password123", LocalDateTime.now(), LocalDateTime.now().plusDays(90), true, "Region1", 1234567890L);
        accountRepository.save(account);

        boolean exists = accountRepository.existsByUserId("userId123");

        assertTrue(exists);
    }
}
*/
