package com.mikhailkarpov.bankaccountservice.commands;

import java.util.UUID;

public class CreateAccountCommand extends BaseCommand<String> {

    public CreateAccountCommand(UUID id) {
        super(id.toString());
    }
}
