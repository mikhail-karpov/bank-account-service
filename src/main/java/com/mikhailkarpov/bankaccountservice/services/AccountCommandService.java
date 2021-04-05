package com.mikhailkarpov.bankaccountservice.services;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

public interface AccountCommandService {

    CompletableFuture<String> createAccount();

    CompletableFuture<Void> deposit(String accountId, BigDecimal amount);

    CompletableFuture<Void> withdraw(String accountId, BigDecimal amount);
}
