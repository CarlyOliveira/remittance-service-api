package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.RemittanceEffectivationAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.CheckBalanceException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.GetExchangeRateActionException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Action
public class RemittanceEffectivationActionImpl implements RemittanceEffectivationAction {

    private static final Logger log = LoggerFactory.getLogger(RemittanceEffectivationActionImpl.class);


    @Override
    public void execute(Remittance remittance) throws CheckBalanceException, RemittanceException {

        log.info("REAI-E-00 Effectivation for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            //TODO IMPLEMENTAR
            log.info("REAI-E-01 Effectivation for remittance {} finished", remittance);
        }catch (Exception exception){
            log.error("REAI-E-02 Effectivation for remittance {} error {} ", remittance, exception);
            throw new GetExchangeRateActionException(exception);
        }
    }
}
