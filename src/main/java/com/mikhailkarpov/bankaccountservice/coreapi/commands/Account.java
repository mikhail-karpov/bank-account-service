package com.mikhailkarpov.bankaccountservice.coreapi.commands;

import com.mikhailkarpov.bankaccountservice.coreapi.events.AccountCreatedEvent;
import com.mikhailkarpov.bankaccountservice.coreapi.events.MoneyDepositedEvent;
import com.mikhailkarpov.bankaccountservice.coreapi.events.MoneyWithdrawnEvent;
import com.mikhailkarpov.bankaccountservice.coreapi.exceptions.WithdrawMoneyException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Axon requires it
public class Account {

    @AggregateIdentifier
    private String accountId;

    private Integer balance;

    @CommandHandler
    public Account(CreateAccountCommand command) {
        AccountCreatedEvent event = new AccountCreatedEvent(command.getAccountId(), 0);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    protected void handle(DepositMoneyCommand command) {
        MoneyDepositedEvent event = new MoneyDepositedEvent(accountId, command.getTransactionId(), command.getAmount());
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    protected void handle(WithdrawMoneyCommand command) {
        Integer amount = command.getAmount();

        if (amount > balance) {
            throw new WithdrawMoneyException("Insufficient amount");
        }

        MoneyWithdrawnEvent event = new MoneyWithdrawnEvent(accountId, command.getTransactionId(), amount);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.balance = 0;
    }

    @EventSourcingHandler
    protected void on(MoneyDepositedEvent event) {
        this.balance += event.getAmount();
    }

    @EventSourcingHandler
    protected void on(MoneyWithdrawnEvent event) {
        this.balance -= event.getAmount();
    }
}
