package br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository;

import br.com.ctmait.remittanceserviceapi.domain.models.account.Account;

import java.math.BigDecimal;

public interface AccountRepository {
    Account getById(String accountId);
    void update(String accountId, BigDecimal balanceValue);
}
