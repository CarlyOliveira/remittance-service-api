package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.RemittanceQueryAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceNotFoundException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceQueryActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.RemittanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Action
public class RemittanceQueryActionImpl implements RemittanceQueryAction {

    private static final Logger log = LoggerFactory.getLogger(RemittanceQueryActionImpl.class);

    private final RemittanceRepository remittanceRepository;

    public RemittanceQueryActionImpl(RemittanceRepository remittanceRepository) {
        this.remittanceRepository = remittanceRepository;
    }

    @Override
    public void execute(Remittance remittance) throws RemittanceNotFoundException, RemittanceQueryActionException {

        log.info("CLAI-E-00 Query for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            Objects.requireNonNull(remittance.getId(), "remittance id cannot null");
            remittance.visit(remittanceRepository::load);
            log.info("CLAI-E-01 Query for remittance {} finished", remittance);
        }catch (RemittanceNotFoundException exception){
            log.error("CLAI-E-02 Query for remittance {} error {} ", remittance, exception);
            throw exception;
        }catch (Exception exception){
            log.error("CLAI-E-03 Query for remittance {} error {} ", remittance, exception);
            throw new RemittanceQueryActionException(exception);
        }
    }
}
