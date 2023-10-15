package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.BalanceReserveUndoAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.BalanceReserveUndoActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Action
public class BalanceReserveUndoActionImpl implements BalanceReserveUndoAction {

    private static final Logger log = LoggerFactory.getLogger(BalanceReserveUndoActionImpl.class);

    private final AccountRepository accountRepository;

    public BalanceReserveUndoActionImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void execute(Remittance remittance){

        log.info("BRUAI-E-00 Undo reserve balance for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            Objects.requireNonNull(remittance.getPayer(), "remittance payer cannot null");
            var accountPayer = accountRepository.getById(remittance.getPayer().getAccountId());
            remittance.getPayer().setBalance(accountPayer.getBalance());
            remittance.visit(this::balanceReserveUndo);
            log.info("BRUAI-E-01 Undo reserve balance for remittance {} finished", remittance);
        }catch (Exception exception){
            log.error("BRUAI-E-02 Undo reserve balance for remittance {} error {} ", remittance, exception);
            throw new BalanceReserveUndoActionException(exception);
        }
    }

    private void balanceReserveUndo(Remittance remittance){
        accountRepository.updateBalance(remittance.getPayer().getAccountId(), this.getNewBalanceValue(remittance));
    }

    private BigDecimal getNewBalanceValue(Remittance remittance){
        return remittance.getPayer().getBalance().getValue().add(remittance.getValue()).setScale(5, RoundingMode.HALF_DOWN);
    }
}
