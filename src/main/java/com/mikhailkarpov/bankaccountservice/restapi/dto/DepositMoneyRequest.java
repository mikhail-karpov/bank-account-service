package com.mikhailkarpov.bankaccountservice.restapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DepositMoneyRequest {

    @NotNull(message = "amount must be provided")
    @Min(value = 1, message = "non-positive amount")
    private Integer amount;
}
