package com.mikhailkarpov.bankaccountservice.projections;

import com.mikhailkarpov.bankaccountservice.coreapi.events.AccountCreatedEvent;
import com.mikhailkarpov.bankaccountservice.coreapi.events.MoneyDepositedEvent;
import com.mikhailkarpov.bankaccountservice.coreapi.events.MoneyWithdrawnEvent;
import com.mikhailkarpov.bankaccountservice.coreapi.queries.FindAccountBalanceQuery;
import com.mikhailkarpov.bankaccountservice.coreapi.exceptions.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountBalanceProjection {

    private final AccountBalanceRepository accountRepository;

    @EventHandler
    @Transactional
    protected void on(AccountCreatedEvent event) {
        AccountBalanceEntity entity = new AccountBalanceEntity(event.getAccountId(), event.getBalance());
        accountRepository.save(entity);
    }

    @EventHandler
    @Transactional
    protected void on(MoneyDepositedEvent event) {

        String accountId = event.getAccountId();
        AccountBalanceEntity balanceEntity = accountRepository.findById(accountId).orElseThrow(() -> {
            String message = String.format("Account with id=%s not found", accountId);
            return new AccountNotFoundException(message);
        });

        Integer balance = balanceEntity.getBalance();
        balance += event.getAmount();
        balanceEntity.setBalance(balance);
    }

    @EventHandler
    @Transactional
    protected void on(MoneyWithdrawnEvent event) {

        String accountId = event.getAccountId();
        AccountBalanceEntity balanceEntity = accountRepository.findById(accountId).orElseThrow(() -> {
            String message = String.format("Account with id=%s not found", accountId);
            return new RuntimeException(message);
        });

        Integer balance = balanceEntity.getBalance();
        balance -= event.getAmount();
        balanceEntity.setBalance(balance);
    }

    @QueryHandler
    @Transactional(readOnly = true)
    public AccountBalanceEntity findAccountById(FindAccountBalanceQuery query) {
        String accountId = query.getAccountId();
        return accountRepository.findById(accountId).orElse(null);
    }
}
