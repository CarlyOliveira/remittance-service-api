package br.com.ctmait.remittanceserviceapi.domain.process;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.RemittanceQueryAction;
import br.com.ctmait.remittanceserviceapi.abstraction.process.RemittanceQueryProcess;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.*;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Action
public class RemittanceQueryProcessImpl implements RemittanceQueryProcess {

    private static final Logger log = LoggerFactory.getLogger(RemittanceQueryProcessImpl.class);
    private final RemittanceQueryAction remittanceQueryAction;

    public RemittanceQueryProcessImpl(RemittanceQueryAction remittanceQueryAction){
        this.remittanceQueryAction = remittanceQueryAction;
    }

    @Override
    public void execute(Remittance remittance) throws RemittanceQueryActionException, RemittanceCreateProcessException {

        log.info("RCPI-E-00 Remittance query process for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            Objects.requireNonNull(remittance.getId(), "remittance id cannot null");
            remittance.visit(remittanceQueryAction::execute);
            log.info("RCPI-E-01 Remittance query process for remittance {} finished", remittance);
        }catch (RemittanceQueryActionException exception){
            log.error("RCPI-E-02 Remittance query process for remittance {} error on query action {} ", remittance, exception);
            throw exception;
        }catch (Exception exception){
            log.error("RCPI-E-03 Remittance query process for remittance {} error on query process{} ", remittance, exception);
            throw new RemittanceCreateProcessException(exception);
        }
    }
}
