package br.com.ctmait.remittanceserviceapi.abstraction.actions;

import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;

@FunctionalInterface
public interface BalanceReserveUndoAction {
    void execute(final Remittance remittance);
}
