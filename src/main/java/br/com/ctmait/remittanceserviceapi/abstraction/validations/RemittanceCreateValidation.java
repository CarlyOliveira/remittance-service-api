package br.com.ctmait.remittanceserviceapi.abstraction.validations;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceCreateValidationException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.models.Remittance;

@FunctionalInterface
public interface RemittanceCreateValidation {
    void execute(final Remittance remittance) throws RemittanceCreateValidationException, RemittanceException;
}
