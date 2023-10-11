package br.com.ctmait.remittanceserviceapi.abstraction.actions;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.CheckBalanceException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.models.Remittance;

@FunctionalInterface
public interface CheckBalanceAction {
    void execute(final Remittance remittance) throws CheckBalanceException, RemittanceException;
}
