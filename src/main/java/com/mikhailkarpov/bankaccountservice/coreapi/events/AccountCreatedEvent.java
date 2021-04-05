package com.mikhailkarpov.bankaccountservice.coreapi.events;

import lombok.Data;

@Data
public class AccountCreatedEvent {

    private final String accountId;

    private final Integer balance;
}
