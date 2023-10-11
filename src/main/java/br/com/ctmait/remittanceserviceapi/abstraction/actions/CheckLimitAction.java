package br.com.ctmait.remittanceserviceapi.abstraction.actions;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.CheckLimitException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.models.Remittance;

@FunctionalInterface
public interface CheckLimitAction {
    void execute(final Remittance remittance) throws CheckLimitException, RemittanceException;
}
