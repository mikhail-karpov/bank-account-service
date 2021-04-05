package com.mikhailkarpov.bankaccountservice.coreapi.queries;

import lombok.Data;

@Data
public class FindAccountBalanceQuery {

    private final String accountId;

}
