package br.com.ctmait.remittanceserviceapi.domain.process;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.*;
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
    private final PayerEnrichmentAction payerEnrichmentAction;
    private final ReceiverEnrichmentAction receiverEnrichmentAction;
    private final CheckBalanceAction checkBalanceAction;
    private final CheckLimitAction checkLimitAction;
    private final GetExchangeRateAction getExchangeRateAction;
    private final ConvertRemittanceValueAction convertRemittanceValueAction;
    private final RemittanceEffectivationAction remittanceEffectivationAction;
    private final LimitReserveUndoAction limitReserveUndoAction;
    private final BalanceReserveUndoAction balanceReserveUndoAction;

    public RemittanceCreateProcessImpl(RemittanceCreateValidation remittanceCreateValidation, PayerEnrichmentAction payerEnrichmentAction, ReceiverEnrichmentAction receiverEnrichmentAction, CheckBalanceAction checkBalanceAction, CheckLimitAction checkLimitAction, GetExchangeRateAction getExchangeRateAction, ConvertRemittanceValueAction convertRemittanceValueAction, RemittanceEffectivationAction remittanceEffectivationAction, LimitReserveUndoAction limitReserveUndoAction, BalanceReserveUndoAction balanceReserveUndoAction) {
        this.remittanceCreateValidation = remittanceCreateValidation;
        this.payerEnrichmentAction = payerEnrichmentAction;
        this.receiverEnrichmentAction = receiverEnrichmentAction;
        this.checkBalanceAction = checkBalanceAction;
        this.checkLimitAction = checkLimitAction;
        this.getExchangeRateAction = getExchangeRateAction;
        this.convertRemittanceValueAction = convertRemittanceValueAction;
        this.remittanceEffectivationAction = remittanceEffectivationAction;
        this.limitReserveUndoAction = limitReserveUndoAction;
        this.balanceReserveUndoAction = balanceReserveUndoAction;
    }

    @Override
    public void execute(Remittance remittance){

        log.info("RCPI-E-00 Remittance create process for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            remittance.visit(remittanceCreateValidation::execute);
            remittance.visit(payerEnrichmentAction::execute);
            remittance.visit(receiverEnrichmentAction::execute);
            remittance.visit(checkBalanceAction::execute);
            remittance.visit(checkLimitAction::execute);
            remittance.visit(getExchangeRateAction::execute);
            remittance.visit(convertRemittanceValueAction::execute);
            remittance.visit(remittanceEffectivationAction::execute);
            log.info("RCPI-E-01 Remittance create process for remittance {} finished", remittance);
        }catch (RemittanceCreateValidationException exception){
            log.error("RCPI-E-02 Remittance create process for remittance {} error on validation {} ", remittance, exception);
            throw exception;
        }catch (PayerEnrichmentActionException exception){
            log.error("RCPI-E-03 Remittance create process for remittance {} error on payer enrichment {} ", remittance, exception);
            throw exception;
        }catch (ReceiverEnrichmentActionException exception){
            log.error("RCPI-E-04 Remittance create process for remittance {} error on receiver enrichment {} ", remittance, exception);
            throw exception;
        }catch (CheckBalanceActionException exception){
            log.error("RCPI-E-05 Remittance create process for remittance {} error on check balance {} ", remittance, exception);
            throw exception;
        }catch (CheckLimitActionException exception){
            log.error("RCPI-E-06 Remittance create process for remittance {} error on check limit {} ", remittance, exception);
            remittance.visit(balanceReserveUndoAction::execute);
            throw exception;
        }catch (GetExchangeRateActionException exception){
            log.error("RCPI-E-07 Remittance create process for remittance {} error on get exchange rate {} ", remittance, exception);
            remittance.visit(balanceReserveUndoAction::execute);
            remittance.visit(limitReserveUndoAction::execute);
            throw exception;
        }catch (ConvertRemittanceValueActionException exception){
            log.error("RCPI-E-08 Remittance create process for remittance {} error on convert remittance{} ", remittance, exception);
            remittance.visit(balanceReserveUndoAction::execute);
            remittance.visit(limitReserveUndoAction::execute);
            throw exception;
        }catch (RemittanceEffectivationActionException exception){
            log.error("RCPI-E-09 Remittance create process for remittance {} error on effetivation remittance{} ", remittance, exception);
            remittance.visit(balanceReserveUndoAction::execute);
            remittance.visit(limitReserveUndoAction::execute);
            throw exception;
        }catch (Exception exception){
            log.error("RCPI-E-10 Remittance create process for remittance {} error on create process{} ", remittance, exception);
            throw new RemittanceCreateProcessException(exception);
        }
    }
}
