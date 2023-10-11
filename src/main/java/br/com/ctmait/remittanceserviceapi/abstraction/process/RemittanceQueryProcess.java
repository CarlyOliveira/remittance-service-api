package br.com.ctmait.remittanceserviceapi.abstraction.process;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceQueryProcessException;
import br.com.ctmait.remittanceserviceapi.domain.models.Remittance;

@FunctionalInterface
public interface RemittanceQueryProcess {
    void execute(final Remittance remittance) throws RemittanceQueryProcessException,RemittanceException;
}
