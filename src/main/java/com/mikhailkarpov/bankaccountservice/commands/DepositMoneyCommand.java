package com.mikhailkarpov.bankaccountservice.commands;

import java.math.BigDecimal;

public class DepositMoneyCommand extends BaseCommand<String> {

    private final BigDecimal amount;

    public DepositMoneyCommand(String accountId, BigDecimal amount) {
        super(accountId);
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
