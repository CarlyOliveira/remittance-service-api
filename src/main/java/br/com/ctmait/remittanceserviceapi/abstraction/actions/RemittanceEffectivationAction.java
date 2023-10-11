package br.com.ctmait.remittanceserviceapi.abstraction.actions;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceEffectivationActionException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;

@FunctionalInterface
public interface RemittanceEffectivationAction {
    void execute(final Remittance remittance) throws RemittanceEffectivationActionException, RemittanceException;
}
