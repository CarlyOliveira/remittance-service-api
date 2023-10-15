package br.com.ctmait.remittanceserviceapi.abstraction.actions;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceNotFoundException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceQueryActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;

@FunctionalInterface
public interface RemittanceQueryAction {
    void execute(final Remittance remittance) throws RemittanceNotFoundException, RemittanceQueryActionException;
}
