package org.warranty.warranty_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.warranty.warranty_service.model.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByUserId(String userId);

   Optional<Account> findByUserId(String username);
}
