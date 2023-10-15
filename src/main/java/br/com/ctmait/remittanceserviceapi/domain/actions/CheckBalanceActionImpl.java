package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.CheckBalanceAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.CheckBalanceActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Action
public class CheckBalanceActionImpl implements CheckBalanceAction {

    private static final Logger log = LoggerFactory.getLogger(CheckBalanceActionImpl.class);

    private final AccountRepository accountRepository;

    public CheckBalanceActionImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void execute(Remittance remittance){

        log.info("CBAI-E-00 Check balance for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            Objects.requireNonNull(remittance.getPayer(), "remittance payer cannot null");
            var accountPayer = accountRepository.getById(remittance.getPayer().getAccountId());
            remittance.getPayer().setBalance(accountPayer.getBalance());
            remittance.visit(this::checkBalance);
            remittance.visit(this::balanceReserve);
            log.info("CBAI-E-01 Check balance for remittance {} finished", remittance);
        }catch (Exception exception){
            log.error("CBAI-E-02 Check balance for remittance {} error {} ", remittance, exception);
            throw new CheckBalanceActionException(exception);
        }
    }

    private void checkBalance(Remittance remittance){
        if(remittance.getValue().compareTo(remittance.getPayer().getBalance().getValue()) > 0){
            throw new CheckBalanceActionException("not balance");
        }
    }

    private void balanceReserve(Remittance remittance){
        accountRepository.updateBalance(remittance.getPayer().getAccountId(), this.getNewBalanceValue(remittance));
    }

    private BigDecimal getNewBalanceValue(Remittance remittance){
        return remittance.getPayer().getBalance().getValue().subtract(remittance.getValue()).setScale(5, RoundingMode.HALF_DOWN);
    }
}
