package com.mikhailkarpov.bankaccountservice.events;

import java.math.BigDecimal;

public class MoneyWithdrawEvent extends BaseEvent<String> {

    private final BigDecimal amount;

    public MoneyWithdrawEvent(String id, BigDecimal amount) {
        super(id);
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
