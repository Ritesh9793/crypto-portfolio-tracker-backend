package com.CryptoProject.CryptoInfosys.repository;

import com.CryptoProject.CryptoInfosys.model.ExchangeAccount;
import com.CryptoProject.CryptoInfosys.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ExchangeAccountRepository extends JpaRepository<ExchangeAccount, Long> {

    List<ExchangeAccount> findByUser(User user);

    List<ExchangeAccount> findByUserEmail(String userEmail);

    void deleteByUserAndExchangeIgnoreCase(User user, String exchange);
}
