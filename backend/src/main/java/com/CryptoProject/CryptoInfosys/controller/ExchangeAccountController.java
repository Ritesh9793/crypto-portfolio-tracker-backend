package com.CryptoProject.CryptoInfosys.controller;

import com.CryptoProject.CryptoInfosys.dto.AddExchangeAccountRequest;
import com.CryptoProject.CryptoInfosys.dto.ExchangeAccountResponse;
import com.CryptoProject.CryptoInfosys.dto.ExchangeSyncResponse;
import com.CryptoProject.CryptoInfosys.service.ExchangeAccountService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-accounts")
@CrossOrigin(origins = "http://localhost:3000")
public class ExchangeAccountController {

    private final ExchangeAccountService exchangeAccountService;

    public ExchangeAccountController(ExchangeAccountService exchangeAccountService) {
        this.exchangeAccountService = exchangeAccountService;
    }

    @GetMapping
    public List<ExchangeAccountResponse> getUserExchangeAccounts(Authentication authentication) {
        return exchangeAccountService.getUserExchanges(authentication.getName());
    }

    @PostMapping
    public ExchangeAccountResponse addExchangeAccount(
            @RequestBody AddExchangeAccountRequest request,
            Authentication authentication
    ) {
        return exchangeAccountService.addExchange(
                authentication.getName(),
                request.getExchange(),
                request.getApiKey(),
                request.getApiSecret(),
                request.getLabel(),
                request.getBaseUrl()
        );
    }

    @DeleteMapping("/{exchange}")
    public void deleteExchangeAccount(
            @PathVariable String exchange,
            Authentication authentication
    ) {
        exchangeAccountService.deleteExchange(authentication.getName(), exchange);
    }

    @GetMapping("/sync/{exchange}")
    public ExchangeSyncResponse syncExchange(
            @PathVariable String exchange,
            Authentication authentication
    ) {
        return exchangeAccountService.syncExchangeBalances(authentication.getName(), exchange);
    }
}
