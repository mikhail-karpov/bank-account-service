package com.mikhailkarpov.bankaccountservice.projections;

import org.springframework.data.repository.CrudRepository;

public interface AccountBalanceRepository extends CrudRepository<AccountBalanceEntity, String> {

}
