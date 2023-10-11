package br.com.ctmait.remittanceserviceapi.abstraction.validations;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceCreateValidationException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;

@FunctionalInterface
public interface RemittanceCreateValidation {
    void execute(final Remittance remittance) throws RemittanceCreateValidationException;
}
