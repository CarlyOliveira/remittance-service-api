package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.PayerEnrichmentAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.PayerEnrichmentActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Action
public class PayerEnrichmentActionImpl implements PayerEnrichmentAction {

    private static final Logger log = LoggerFactory.getLogger(PayerEnrichmentActionImpl.class);

    private final UserRepository userRepository;

    public PayerEnrichmentActionImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Remittance remittance){

        log.info("CBAI-E-00 Payer enrichment for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            Objects.requireNonNull(remittance.getPayer(), "remittance payer cannot null");
            Objects.requireNonNull(remittance.getPayer().getDocument(), "remittance payer document cannot null");
            Objects.requireNonNull(remittance.getPayer().getDocument().getValue(), "remittance payer document value cannot null");
            var userPayer = userRepository.getByDocument(remittance.getPayer().getDocument().getValue());
            remittance.getPayer().setUserName(userPayer.getName());
            log.info("CBAI-E-01 Payer enrichment for remittance {} finished", remittance);
        }catch (Exception exception){
            log.error("CBAI-E-02 Payer enrichment for remittance {} error {} ", remittance, exception);
            throw new PayerEnrichmentActionException(exception);
        }
    }
}
