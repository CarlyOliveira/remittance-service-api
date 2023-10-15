package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.RemittanceEffectivationAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceEffectivationActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.account.Account;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.AccountRepository;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.RemittanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Action
public class RemittanceEffectivationActionImpl implements RemittanceEffectivationAction {

    private static final Logger log = LoggerFactory.getLogger(RemittanceEffectivationActionImpl.class);
    private final AccountRepository accountRepository;
    private final RemittanceRepository remittanceRepository;

    public RemittanceEffectivationActionImpl(AccountRepository accountRepository, RemittanceRepository remittanceRepository) {
        this.accountRepository = accountRepository;
        this.remittanceRepository = remittanceRepository;
    }

    @Override
    public void execute(Remittance remittance){

        log.info("REAI-E-00 Effectivation for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            Objects.requireNonNull(remittance.getReceiver(), "remittance receiver cannot null");
            var accountReceiver = accountRepository.getById(remittance.getReceiver().getAccountId());
            accountRepository.updateBalance(remittance.getReceiver().getAccountId(), this.getNewBalanceValue(accountReceiver, remittance.getConvertedValue()));
            remittanceEffectivation(remittance, accountReceiver);
            log.info("REAI-E-01 Effectivation for remittance {} finished", remittance);
        }catch (Exception exception){
            log.error("REAI-E-02 Effectivation for remittance {} error {} ", remittance, exception);
            throw new RemittanceEffectivationActionException(exception);
        }
    }
    private BigDecimal getNewBalanceValue(Account accountReceiver, BigDecimal convertedValue){
        return accountReceiver.getBalance().getValue().add(convertedValue).setScale(5, RoundingMode.HALF_DOWN);
    }
    private BigDecimal getNewBalanceValueRollback(Account accountReceiver, BigDecimal convertedValue){
        return accountReceiver.getBalance().getValue().subtract(convertedValue).setScale(5, RoundingMode.HALF_DOWN);
    }

    private void remittanceEffectivation(Remittance remittance, Account accountReceiver){
        try {
            remittance.visit(remittanceRepository::insert);
        }catch (Exception e){
            accountRepository.updateBalance(remittance.getReceiver().getAccountId(), this.getNewBalanceValueRollback(accountReceiver, remittance.getConvertedValue()));
            throw e;
        }
    }
}
