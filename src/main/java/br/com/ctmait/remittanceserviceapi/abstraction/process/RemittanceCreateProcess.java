package br.com.ctmait.remittanceserviceapi.abstraction.process;

import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;

@FunctionalInterface
public interface RemittanceCreateProcess {
    void execute(final Remittance remittance);
}
