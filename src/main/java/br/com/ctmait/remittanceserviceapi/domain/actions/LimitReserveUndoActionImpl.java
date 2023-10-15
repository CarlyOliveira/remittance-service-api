package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.LimitReserveUndoAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.LimitReserveUndoActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Action
public class LimitReserveUndoActionImpl implements LimitReserveUndoAction {

    private static final Logger log = LoggerFactory.getLogger(LimitReserveUndoActionImpl.class);

    private final AccountRepository accountRepository;

    public LimitReserveUndoActionImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void execute(Remittance remittance){

        log.info("LRUAI-E-00 Undo reserve limit for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            Objects.requireNonNull(remittance.getPayer(), "remittance payer cannot null");
            var accountPayer = accountRepository.getById(remittance.getPayer().getAccountId());
            remittance.getPayer().setLimit(accountPayer.getTransactionalLimitDaily());
            remittance.visit(this::limitReserveUndo);
            log.info("LRUAI-E-01 Undo reserve limit for remittance {} finished", remittance);
        }catch (Exception exception){
            log.error("LRUAI-E-02 Undo reserve limit for remittance {} error {} ", remittance, exception);
            throw new LimitReserveUndoActionException(exception);
        }
    }

    private void limitReserveUndo(Remittance remittance){
        accountRepository.updateLimit(remittance.getPayer().getAccountId(), this.getNewLimitValue(remittance));
    }

    private BigDecimal getNewLimitValue(Remittance remittance){
        return remittance.getPayer().getLimit().getValue().add(remittance.getValue()).setScale(5, RoundingMode.HALF_DOWN);
    }
}
