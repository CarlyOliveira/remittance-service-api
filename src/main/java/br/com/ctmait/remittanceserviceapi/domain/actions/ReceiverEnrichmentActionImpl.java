package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.ReceiverEnrichmentAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.ReceiverEnrichmentActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Action
public class ReceiverEnrichmentActionImpl implements ReceiverEnrichmentAction {

    private static final Logger log = LoggerFactory.getLogger(ReceiverEnrichmentActionImpl.class);

    private final UserRepository userRepository;

    public ReceiverEnrichmentActionImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Remittance remittance){

        log.info("CBAI-E-00 Receiver enrichment for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            Objects.requireNonNull(remittance.getReceiver(), "remittance receiver cannot null");
            Objects.requireNonNull(remittance.getReceiver().getDocument(), "remittance receiver document cannot null");
            Objects.requireNonNull(remittance.getReceiver().getDocument().getValue(), "remittance receiver document value cannot null");
            var userReceiver = userRepository.getByDocument(remittance.getReceiver().getDocument().getValue());
            remittance.getReceiver().setUserName(userReceiver.getName());
            log.info("CBAI-E-01 Receiver enrichment for remittance {} finished", remittance);
        }catch (Exception exception){
            log.error("CBAI-E-02 Receiver enrichment for remittance {} error {} ", remittance, exception);
            throw new ReceiverEnrichmentActionException(exception);
        }
    }
}
