package com.mikhailkarpov.bankaccountservice.events;

import java.math.BigDecimal;

public class MoneyDepositedEvent extends BaseEvent<String> {

    private final BigDecimal amount;

    public MoneyDepositedEvent(String accountId, BigDecimal amount) {
        super(accountId);
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
