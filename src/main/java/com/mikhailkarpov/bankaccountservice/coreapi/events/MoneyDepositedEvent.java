package com.mikhailkarpov.bankaccountservice.coreapi.events;

import lombok.Data;

@Data
public class MoneyDepositedEvent {

    private final String accountId;

    private final String transactionId;

    private final Integer amount;

}
