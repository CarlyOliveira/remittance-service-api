package br.com.ctmait.remittanceserviceapi.abstraction.actions;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.ConvertRemittanceValuesActionException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;

@FunctionalInterface
public interface ConvertRemittanceValuesAction {
    void execute(final Remittance remittance) throws ConvertRemittanceValuesActionException, RemittanceException;
}
