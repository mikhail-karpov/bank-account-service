package com.mikhailkarpov.bankaccountservice.restapi.controllers;

import com.mikhailkarpov.bankaccountservice.coreapi.commands.CreateAccountCommand;
import com.mikhailkarpov.bankaccountservice.coreapi.commands.DepositMoneyCommand;
import com.mikhailkarpov.bankaccountservice.coreapi.commands.WithdrawMoneyCommand;
import com.mikhailkarpov.bankaccountservice.restapi.dto.DepositMoneyRequest;
import com.mikhailkarpov.bankaccountservice.restapi.dto.WithdrawMoneyRequest;
import com.mikhailkarpov.bankaccountservice.projections.AccountBalanceEntity;
import com.mikhailkarpov.bankaccountservice.coreapi.queries.FindAccountBalanceQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final CommandGateway commandGateway;

    private final QueryGateway queryGateway;

    @PostMapping("/accounts")
    public ResponseEntity<Object> createAccount(UriComponentsBuilder uriComponentsBuilder) {

        String accountId = UUID.randomUUID().toString();
        CreateAccountCommand command = new CreateAccountCommand(accountId);
        commandGateway.sendAndWait(command);

        URI location = uriComponentsBuilder.path("/accounts/{id}").build(accountId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> findAccountById(@PathVariable("id") String accountId) {

        AccountBalanceEntity accountBalance = queryGateway.query(
                new FindAccountBalanceQuery(accountId),
                ResponseTypes.instanceOf(AccountBalanceEntity.class))
                .join();

        return ResponseEntity.of(Optional.ofNullable(accountBalance));
    }

    @PostMapping("/accounts/{id}/deposits")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void depositMoney(@PathVariable("id") String accountId, @Valid @RequestBody DepositMoneyRequest request) {

        String transactionId = UUID.randomUUID().toString();
        DepositMoneyCommand command = new DepositMoneyCommand(accountId, transactionId, request.getAmount());
        commandGateway.send(command);
    }

    @PostMapping("/accounts/{id}/withdrawals")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void withdrawMoney(@PathVariable("id") String accountId, @Valid @RequestBody WithdrawMoneyRequest request) {

        String transactionId = UUID.randomUUID().toString();
        WithdrawMoneyCommand command = new WithdrawMoneyCommand(accountId, transactionId, request.getAmount());
        commandGateway.send(command);
    }
}
