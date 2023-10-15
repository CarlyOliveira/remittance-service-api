package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.ReceiverEnrichmentAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.PayerIsNotOwnerAccountException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.ReceiverEnrichmentActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.account.Account;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.AccountRepository;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Action
public class ReceiverEnrichmentActionImpl implements ReceiverEnrichmentAction {

    private static final Logger log = LoggerFactory.getLogger(ReceiverEnrichmentActionImpl.class);

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;


    public ReceiverEnrichmentActionImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void execute(Remittance remittance){

        log.info("REAI-E-00 Receiver enrichment for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            Objects.requireNonNull(remittance.getReceiver(), "remittance receiver cannot null");
            Objects.requireNonNull(remittance.getReceiver().getDocument(), "remittance receiver document cannot null");
            Objects.requireNonNull(remittance.getReceiver().getDocument().getValue(), "remittance receiver document value cannot null");
            remittance.visit(this::enrichmentUserNameReceiver);
            remittance.visit(this::enrichmentAccountCurrencyReceiver);
            log.info("REAI-E-01 Receiver enrichment for remittance {} finished", remittance);
        }catch (Exception exception){
            log.error("REAI-E-02 Receiver enrichment for remittance {} error {} ", remittance, exception);
            throw new ReceiverEnrichmentActionException(exception);
        }
    }

    private void enrichmentAccountCurrencyReceiver(Remittance remittance){
        var accountReceiver = accountRepository.getById(remittance.getReceiver().getAccountId());
        validateReceiverIsOwnerAccount(accountReceiver, remittance);
        remittance.getReceiver().setAccountCurrency(accountReceiver.getBalance().getCurrency());
        log.info("REAI-E-01 Receiver enrichment accountCurrency {} finished", remittance);
    }

    private void enrichmentUserNameReceiver(Remittance remittance){
        var userReceiver = userRepository.getByDocument(remittance.getReceiver().getDocument().getValue());
        remittance.getReceiver().setUserName(userReceiver.getName());
        log.info("REAI-E-01 Receiver enrichment userName {} finished", remittance);
    }
    private void validateReceiverIsOwnerAccount(Account accountReceiver, Remittance remittance){
        if(!accountReceiver.getOwnerId().equalsIgnoreCase(remittance.getReceiver().getDocument().getValue())) {
            throw new PayerIsNotOwnerAccountException("Receiver " + remittance.getReceiver().getDocument().getValue() + " not is owner of account " + accountReceiver.getId());
        }
    }
}
