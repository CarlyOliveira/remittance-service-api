package br.com.ctmait.remittanceserviceapi.abstraction.actions;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceQueryActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.Remittance;

@FunctionalInterface
public interface RemittanceQueryAction {
    void execute(final Remittance remittance) throws RemittanceQueryActionException, RemittanceException;
}
