package org.warranty.warranty_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.warranty.warranty_service.model.Account;
import org.warranty.warranty_service.payload.request.AccountRequest;
import org.warranty.warranty_service.payload.response.AccountResponse;
import org.warranty.warranty_service.repository.AccountRepository;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account saveUserDetails(AccountRequest accountRequest){
        Account account = new Account();
        String userId;
        boolean exists;

        userId = generateRandomUserId();
        exists = accountRepository.existsByUserId(userId);

        if (!exists) {

            account.setUserId(userId);
            account.setActive(accountRequest.isActive());
            account.setEmail(accountRequest.getEmail());
            account.setRegion(accountRequest.getRegion());
            account.setFirstName(accountRequest.getFirstName());
            account.setLastName(accountRequest.getLastName());
            account.setPassword(accountRequest.getPassword());
            account.setContactNumber(accountRequest.getContactNumber());
            account.setCreationDateTime(LocalDateTime.now());
            account.setExpirationDateTime(LocalDateTime.now().plusDays(90));

            return accountRepository.save(account);

        }
        return null;
    }

    public void deleteAllUsers(){
       accountRepository.deleteAll();
    }


    public Page<AccountResponse> getAllUsers(int page, int size, String sortBy, String sortDirection){


        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Account> accounts = accountRepository.findAll(pageable);

        return accounts.map(account -> AccountResponse.builder()
                .email(account.getEmail())
                .userId(account.getUserId())
                .active(account.isActive())
                .contactNumber(account.getContactNumber())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .region(account.getRegion())
                .creationDateTime(account.getCreationDateTime())
                .expirationDateTime(account.getExpirationDateTime())
                .build());

    }
    private String generateRandomUserId(){
        Random random = new Random();

        char firstChar = (char) ('A' + random.nextInt(26));

        if(random.nextBoolean()){
            firstChar = Character.toLowerCase(firstChar);
        }

        String digits = random.ints(0,10)
                .limit(4)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());

        return firstChar+digits;
    }
}
