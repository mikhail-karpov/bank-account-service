package com.mikhailkarpov.bankaccountservice.coreapi.events;

import lombok.Data;

@Data
public class MoneyWithdrawnEvent {

    private final String accountId;

    private final String transactionId;

    private final Integer amount;
}
