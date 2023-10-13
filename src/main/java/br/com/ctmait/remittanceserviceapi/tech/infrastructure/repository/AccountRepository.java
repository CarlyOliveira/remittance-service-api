package br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository;

import br.com.ctmait.remittanceserviceapi.domain.models.account.Account;

import java.math.BigDecimal;

public interface AccountRepository {
    Account getById(String accountId);
    void updateBalance(String accountId, BigDecimal balanceValue);
    void updateLimit(String accountId, BigDecimal balanceValue);
}
