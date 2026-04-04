package com.CryptoProject.CryptoInfosys.service;

import com.CryptoProject.CryptoInfosys.dto.ExchangeAccountResponse;
import com.CryptoProject.CryptoInfosys.dto.ExchangeBalanceResponse;
import com.CryptoProject.CryptoInfosys.dto.ExchangeSyncResponse;
import com.CryptoProject.CryptoInfosys.model.ExchangeAccount;
import com.CryptoProject.CryptoInfosys.model.Holding;
import com.CryptoProject.CryptoInfosys.model.User;
import com.CryptoProject.CryptoInfosys.repository.ExchangeAccountRepository;
import com.CryptoProject.CryptoInfosys.repository.HoldingRepository;
import com.CryptoProject.CryptoInfosys.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeAccountService {

    private final ExchangeAccountRepository exchangeAccountRepo;
    private final UserRepository userRepo;
    private final HoldingRepository holdingRepository;

    public ExchangeAccountService(
            ExchangeAccountRepository exchangeAccountRepo,
            UserRepository userRepo,
            HoldingRepository holdingRepository
    ) {
        this.exchangeAccountRepo = exchangeAccountRepo;
        this.userRepo = userRepo;
        this.holdingRepository = holdingRepository;
    }

    public List<ExchangeAccountResponse> getUserExchanges(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return exchangeAccountRepo.findByUser(user)
                .stream()
                .sorted(Comparator.comparing(ExchangeAccount::getCreatedAt).reversed())
                .map(this::toResponse)
                .toList();
    }

    public ExchangeAccountResponse addExchange(
            String email,
            String exchange,
            String apiKey,
            String apiSecret,
            String label,
            String baseUrl
    ) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ExchangeAccount account = new ExchangeAccount();
        account.setUser(user);
        account.setExchange(exchange);
        account.setLabel(label == null || label.isBlank() ? exchange + " Primary" : label);
        account.setBaseUrl(baseUrl);
        account.setApiKey(apiKey);
        account.setApiSecret(apiSecret);

        return toResponse(exchangeAccountRepo.save(account));
    }

    public void deleteExchange(String email, String exchange) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        exchangeAccountRepo.deleteByUserAndExchangeIgnoreCase(user, exchange);
    }

    public ExchangeSyncResponse syncExchangeBalances(String email, String exchange) {
        List<ExchangeBalanceResponse> balances = holdingRepository.findByUser_Email(email)
                .stream()
                .filter(holding -> exchange.equalsIgnoreCase(holding.getExchange()))
                .map(this::toBalance)
                .collect(Collectors.toList());

        return new ExchangeSyncResponse(
                exchange.toUpperCase(),
                balances.isEmpty() ? "NO_BALANCES_FOUND" : "SYNCED_FROM_TRACKED_HOLDINGS",
                balances
        );
    }

    private ExchangeAccountResponse toResponse(ExchangeAccount account) {
        ExchangeAccountResponse response = new ExchangeAccountResponse();
        response.setId(account.getId());
        response.setExchange(account.getExchange());
        response.setLabel(account.getLabel());
        response.setBaseUrl(account.getBaseUrl());
        response.setMaskedApiKey(mask(account.getApiKey()));
        response.setHasApiSecret(account.getApiSecret() != null && !account.getApiSecret().isBlank());
        response.setCreatedAt(account.getCreatedAt());
        return response;
    }

    private ExchangeBalanceResponse toBalance(Holding holding) {
        return new ExchangeBalanceResponse(
                holding.getSymbol(),
                holding.getQuantity() == null ? BigDecimal.ZERO : holding.getQuantity(),
                BigDecimal.ZERO
        );
    }

    private String mask(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }
        if (value.length() <= 4) {
            return "****";
        }
        return value.substring(0, 2) + "****" + value.substring(value.length() - 2);
    }
}
