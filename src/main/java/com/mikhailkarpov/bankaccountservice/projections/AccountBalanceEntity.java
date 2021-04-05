package com.mikhailkarpov.bankaccountservice.projections;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "AccountBalance")
@Table(name = "account")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA requires it
public class AccountBalanceEntity {

    @Id
    private String accountId;

    private Integer balance;

    public AccountBalanceEntity(String accountId, Integer balance) {
        this.accountId = accountId;
        this.balance = balance;
    }
}
