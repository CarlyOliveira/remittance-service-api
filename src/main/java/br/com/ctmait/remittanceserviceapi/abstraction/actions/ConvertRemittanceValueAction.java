package br.com.ctmait.remittanceserviceapi.abstraction.actions;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.ConvertRemittanceValueActionException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;

@FunctionalInterface
public interface ConvertRemittanceValueAction {
    void execute(final Remittance remittance) throws ConvertRemittanceValueActionException, RemittanceException;
}
