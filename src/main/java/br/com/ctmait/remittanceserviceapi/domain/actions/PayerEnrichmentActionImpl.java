package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.PayerEnrichmentAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.PayerEnrichmentActionException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.PayerIsNotOwnerAccountException;
import br.com.ctmait.remittanceserviceapi.domain.models.account.Account;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.AccountRepository;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Action
public class PayerEnrichmentActionImpl implements PayerEnrichmentAction {

    private static final Logger log = LoggerFactory.getLogger(PayerEnrichmentActionImpl.class);

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public PayerEnrichmentActionImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void execute(Remittance remittance){

        log.info("PEAI-E-00 Payer enrichment for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            Objects.requireNonNull(remittance.getPayer(), "remittance payer cannot null");
            Objects.requireNonNull(remittance.getPayer().getAccountId(), "remittance payer accountId cannot null");
            Objects.requireNonNull(remittance.getPayer().getDocument(), "remittance payer document cannot null");
            Objects.requireNonNull(remittance.getPayer().getDocument().getValue(), "remittance payer document value cannot null");
            remittance.visit(this::enrichmentUserNamePayer);
            remittance.visit(this::enrichmentBalanceAndTransacionalLimitPayer);
            log.info("PEAI-E-03 Payer enrichment for remittance {} finished", remittance);
        }catch (Exception exception){
            log.error("PEAI-E-04 Payer enrichment for remittance {} error {} ", remittance, exception);
            throw new PayerEnrichmentActionException(exception);
        }
    }

    private void enrichmentBalanceAndTransacionalLimitPayer(Remittance remittance){
        var accountPayer = accountRepository.getById(remittance.getPayer().getAccountId());
        validatePayerIsOwnerAccount(accountPayer,remittance);
        remittance.getPayer().setBalance(accountPayer.getBalance());
        remittance.getPayer().setLimit(accountPayer.getTransactionalLimitDaily());
        log.info("PEAI-E-01 Payer enrichment balance and transacional limit {} finished", remittance);
    }

    private void enrichmentUserNamePayer(Remittance remittance){
        var userPayer = userRepository.getByDocument(remittance.getPayer().getDocument().getValue());
        remittance.getPayer().setUserName(userPayer.getName());
        log.info("PEAI-E-02 Payer enrichment userName {} finished", remittance);
    }
    private void validatePayerIsOwnerAccount(Account accountPayer, Remittance remittance){
        if(!accountPayer.getOwnerId().equalsIgnoreCase(remittance.getPayer().getDocument().getValue())) {
            throw new PayerIsNotOwnerAccountException("Payer " + remittance.getPayer().getDocument().getValue() + " not is owner of account " + accountPayer.getId());
        }
    }
}
