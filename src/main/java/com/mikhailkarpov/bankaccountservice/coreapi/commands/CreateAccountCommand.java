package com.mikhailkarpov.bankaccountservice.coreapi.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class CreateAccountCommand {

    @TargetAggregateIdentifier
    private final String accountId;

}
