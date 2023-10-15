package br.com.ctmait.remittanceserviceapi.abstraction.process;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceNotFoundException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceQueryActionException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceQueryProcessException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;

@FunctionalInterface
public interface RemittanceQueryProcess {
    void execute(final Remittance remittance) throws RemittanceNotFoundException, RemittanceQueryActionException, RemittanceQueryProcessException;
}
