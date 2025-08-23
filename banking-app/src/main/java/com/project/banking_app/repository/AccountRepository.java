package com.project.banking_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.banking_app.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    Optional<Account> findByAccountNumberAndPassword(Long accountNumber, String password);

}
