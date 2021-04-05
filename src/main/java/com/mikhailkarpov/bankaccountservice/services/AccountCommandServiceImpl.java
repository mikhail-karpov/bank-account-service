package com.mikhailkarpov.bankaccountservice.services;

import com.mikhailkarpov.bankaccountservice.commands.CreateAccountCommand;
import com.mikhailkarpov.bankaccountservice.commands.DepositMoneyCommand;
import com.mikhailkarpov.bankaccountservice.commands.WithdrawMoneyCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AccountCommandServiceImpl implements AccountCommandService {

    private final CommandGateway commandGateway;

    @Override
    public CompletableFuture<String> createAccount() {
        UUID accountId = UUID.randomUUID();
        CreateAccountCommand command = new CreateAccountCommand(accountId);
        return commandGateway.send(command);
    }

    @Override
    public CompletableFuture<Void> deposit(String accountId, BigDecimal amount) {
        DepositMoneyCommand command = new DepositMoneyCommand(accountId, amount);
        return commandGateway.send(command);
    }

    @Override
    public CompletableFuture<Void> withdraw(String accountId, BigDecimal amount) {
        WithdrawMoneyCommand command = new WithdrawMoneyCommand(accountId, amount);
        return commandGateway.send(command);
    }
}
