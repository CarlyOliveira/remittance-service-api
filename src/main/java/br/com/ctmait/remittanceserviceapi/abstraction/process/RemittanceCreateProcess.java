package br.com.ctmait.remittanceserviceapi.abstraction.process;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceCreateProcessException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceCreateValidationException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.models.Remittance;

@FunctionalInterface
public interface RemittanceCreateProcess {
    void execute(final Remittance remittance) throws RemittanceCreateProcessException, RemittanceCreateValidationException, RemittanceException;
}
