package br.com.ctmait.remittanceserviceapi.domain.process;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.CheckBalanceAction;
import br.com.ctmait.remittanceserviceapi.abstraction.actions.CheckLimitAction;
import br.com.ctmait.remittanceserviceapi.abstraction.actions.ConvertRemittanceValueAction;
import br.com.ctmait.remittanceserviceapi.abstraction.actions.GetExchangeRateAction;
import br.com.ctmait.remittanceserviceapi.abstraction.process.RemittanceCreateProcess;
import br.com.ctmait.remittanceserviceapi.abstraction.validations.RemittanceCreateValidation;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.*;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Action
public class RemittanceCreateProcessImpl implements RemittanceCreateProcess {

    private static final Logger log = LoggerFactory.getLogger(RemittanceCreateProcessImpl.class);
    private final RemittanceCreateValidation remittanceCreateValidation;
    private final CheckBalanceAction checkBalanceAction;
    private final CheckLimitAction checkLimitAction;
    private final GetExchangeRateAction getExchangeRateAction;
    private final ConvertRemittanceValueAction convertRemittanceValueAction;

    public RemittanceCreateProcessImpl(RemittanceCreateValidation remittanceCreateValidation, CheckBalanceAction checkBalanceAction, CheckLimitAction checkLimitAction, GetExchangeRateAction getExchangeRateAction, ConvertRemittanceValueAction convertRemittanceValueAction) {
        this.remittanceCreateValidation = remittanceCreateValidation;
        this.checkBalanceAction = checkBalanceAction;
        this.checkLimitAction = checkLimitAction;
        this.getExchangeRateAction = getExchangeRateAction;
        this.convertRemittanceValueAction = convertRemittanceValueAction;
    }

    @Override
    public void execute(Remittance remittance) throws CheckBalanceException, RemittanceException {

        log.info("RCPI-E-00 Remittance create process for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            remittance.visit(remittanceCreateValidation::execute);
            remittance.visit(checkBalanceAction::execute);
            remittance.visit(checkLimitAction::execute);
            remittance.visit(getExchangeRateAction::execute);
            remittance.visit(convertRemittanceValueAction::execute);
            log.info("RCPI-E-01 Remittance create process for remittance {} finished", remittance);
        }catch (RemittanceCreateValidationException exception){
            log.error("RCPI-E-02 Remittance create process for remittance {} error on validation {} ", remittance, exception);
            throw exception;
        }catch (CheckBalanceException exception){
            log.error("RCPI-E-03 Remittance create process for remittance {} error on check balance {} ", remittance, exception);
            throw exception;
        }catch (CheckLimitException exception){
            log.error("RCPI-E-04 Remittance create process for remittance {} error on check limit {} ", remittance, exception);
            throw exception;
        }catch (GetExchangeRateActionException exception){
            log.error("RCPI-E-05 Remittance create process for remittance {} error on get exchange rate {} ", remittance, exception);
            throw exception;
        }catch (ConvertRemittanceValueActionException exception){
            log.error("RCPI-E-06 Remittance create process for remittance {} error on convert remittance{} ", remittance, exception);
            throw exception;
        }catch (Exception exception){
            log.error("RCPI-E-07 Remittance create process for remittance {} error on create process{} ", remittance, exception);
            throw new RemittanceCreateProcessException(exception);
        }
    }
}