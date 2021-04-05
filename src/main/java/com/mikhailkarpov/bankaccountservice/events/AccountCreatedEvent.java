package com.mikhailkarpov.bankaccountservice.events;

public class AccountCreatedEvent extends BaseEvent<String> {

    public AccountCreatedEvent(String accountId) {
        super(accountId);
    }
}
