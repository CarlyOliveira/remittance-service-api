package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.CheckLimitAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.CheckBalanceException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.CheckLimitException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Action
public class CheckLimitActionImpl implements CheckLimitAction {

    private static final Logger log = LoggerFactory.getLogger(CheckLimitActionImpl.class);

    private final AccountRepository accountRepository;

    public CheckLimitActionImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void execute(Remittance remittance) throws CheckBalanceException, RemittanceException {

        log.info("CLAI-E-00 Check limit for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            var accountPayer = accountRepository.getById(remittance.getPayer().getAccountId());
            remittance.getPayer().setLimit(accountPayer.getTransactionalLimitDaily());
            remittance.visit(this::checkLimit);
            remittance.visit(this::limitReserve);
            log.info("CLAI-E-01 Check limit for remittance {} finished", remittance);
        }catch (Exception exception){
            log.error("CLAI-E-02 Check limit for remittance {} error {} ", remittance, exception);
            throw new CheckLimitException(exception);
        }
    }

    private void checkLimit(Remittance remittance){
        if(remittance.getValue().compareTo(remittance.getPayer().getLimit().getValue()) > 0){
            throw new CheckLimitException("not limit");
        }
    }

    private void limitReserve(Remittance remittance){
        accountRepository.updateLimit(remittance.getPayer().getAccountId(), this.getNewLimitValue(remittance));
    }

    private BigDecimal getNewLimitValue(Remittance remittance){
        return remittance.getPayer().getLimit().getValue().subtract(remittance.getValue()).setScale(2, RoundingMode.HALF_DOWN);
    }
}
