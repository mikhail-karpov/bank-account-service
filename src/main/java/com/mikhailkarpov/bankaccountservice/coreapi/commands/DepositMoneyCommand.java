package com.mikhailkarpov.bankaccountservice.coreapi.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class DepositMoneyCommand {

    @TargetAggregateIdentifier
    private final String accountId;

    private final String transactionId;

    private final Integer amount;
}
