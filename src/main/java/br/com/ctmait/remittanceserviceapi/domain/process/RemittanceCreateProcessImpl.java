package br.com.ctmait.remittanceserviceapi.domain.process;

import br.com.ctmait.remittanceserviceapi.abstraction.process.RemittanceCreateProcess;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.CheckBalanceException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceCreateProcessException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Action
public class RemittanceCreateProcessImpl implements RemittanceCreateProcess {

    private static final Logger log = LoggerFactory.getLogger(RemittanceCreateProcessImpl.class);

    @Override
    public void execute(Remittance remittance) throws CheckBalanceException, RemittanceException {

        log.info("RCPI-E-00 Remittance create process for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            log.info("RCPI-E-01 Remittance create process for remittance {} finished", remittance);
        }catch (Exception exception){
            log.error("RCPI-E-02 Remittance create process for remittance {} error {} ", remittance, exception);
            throw new RemittanceCreateProcessException(exception);
        }
    }
}
