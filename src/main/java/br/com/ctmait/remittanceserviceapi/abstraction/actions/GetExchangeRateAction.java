package br.com.ctmait.remittanceserviceapi.abstraction.actions;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.GetExchangeRateActionException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;

@FunctionalInterface
public interface GetExchangeRateAction {
    void execute(final Remittance remittance) throws GetExchangeRateActionException, RemittanceException;
}
