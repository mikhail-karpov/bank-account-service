package com.mikhailkarpov.bankaccountservice.aggregates;

import com.mikhailkarpov.bankaccountservice.commands.CreateAccountCommand;
import com.mikhailkarpov.bankaccountservice.commands.WithdrawMoneyCommand;
import com.mikhailkarpov.bankaccountservice.commands.DepositMoneyCommand;
import com.mikhailkarpov.bankaccountservice.events.AccountCreatedEvent;
import com.mikhailkarpov.bankaccountservice.events.MoneyDepositedEvent;
import com.mikhailkarpov.bankaccountservice.events.MoneyWithdrawEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private String id;

    private BigDecimal balance;

    protected AccountAggregate() {
        // Axon requires it
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        AccountCreatedEvent event = new AccountCreatedEvent(command.getId());
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    protected void handle(DepositMoneyCommand command) {
        BigDecimal depositAmount = command.getAmount();

        Assert.state(depositAmount.compareTo(BigDecimal.ZERO) > 0, "Non-positive amount");

        AggregateLifecycle.apply(new MoneyWithdrawEvent(id, depositAmount));
    }

    @CommandHandler
    protected void handle(WithdrawMoneyCommand command) {
        BigDecimal withdrawAmount = command.getAmount();

        Assert.state(withdrawAmount.compareTo(BigDecimal.ZERO) > 0, "Non-positive amount");
        Assert.state(withdrawAmount.compareTo(balance) <= 0, "Insufficient funds");

        AggregateLifecycle.apply(new MoneyDepositedEvent(id, withdrawAmount));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent event) {
        id = event.getId();
        balance = new BigDecimal(0);
    }

    @EventSourcingHandler
    protected void on(MoneyWithdrawEvent event) {
        BigDecimal amount = event.getAmount();
        balance = balance.subtract(amount);
    }

    @EventSourcingHandler
    protected void on(MoneyDepositedEvent event) {
        BigDecimal amount = event.getAmount();
        balance = balance.add(amount);
    }
}
