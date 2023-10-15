package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.ConvertRemittanceValueAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.ConvertRemittanceValueActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Action
public class ConvertRemittanceValueActionImpl implements ConvertRemittanceValueAction {

    private static final Logger log = LoggerFactory.getLogger(ConvertRemittanceValueActionImpl.class);
    private static final String EXCHANGE_RATE_NAME = "exchangeRate";
    private static final String REMITTANCE_VALUE_NAME = "remittanceValue";

    @Override
    public void execute(Remittance remittance){

        log.info("CRVAI-E-00 Convert value for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            Objects.requireNonNull(remittance.getPayer(), "remittance payer cannot null");
            Objects.requireNonNull(remittance.getPayer().getBalance(), "remittance payer balance cannot null");
            Objects.requireNonNull(remittance.getPayer().getBalance().getCurrency(), "remittance payer balance currency cannot null");
            Objects.requireNonNull(remittance.getReceiver(), "remittance receiver cannot null");
            Objects.requireNonNull(remittance.getReceiver().getAccountCurrency(), "remittance receiver accountCurrency cannot null");
            remittance.visit(this::checkRemittanceValue);
            remittance.visit(this::checkExchangeRate);
            remittance.visit(this::executeConvertValue);
            log.info("CRVAI-E-01 Convert value for remittance {} finished", remittance);
        }catch (Exception exception){
            log.error("CRVAI-E-02 Convert value for remittance {} error {} ", remittance, exception);
            throw new ConvertRemittanceValueActionException(exception);
        }
    }

    private void checkRemittanceValue(Remittance remittance){
        Optional.ofNullable(remittance.getValue()).ifPresentOrElse(value -> this.valueCannotBeZero(value, REMITTANCE_VALUE_NAME), () -> this.valueCannotBeNull(REMITTANCE_VALUE_NAME));
    }
    private void checkExchangeRate(Remittance remittance){
        Optional.ofNullable(remittance.getExchangeRate()).ifPresentOrElse(value -> this.valueCannotBeZero(value, EXCHANGE_RATE_NAME), () -> this.valueCannotBeNull(EXCHANGE_RATE_NAME));
    }
    private void valueCannotBeZero(BigDecimal value, String valueName){
        if(value.compareTo(BigDecimal.ZERO) <= 0){
            throw new ConvertRemittanceValueActionException( valueName + " need are greater than 0.00");
        }
    }
    private void valueCannotBeNull(String valueName){
        throw new ConvertRemittanceValueActionException(valueName + " cannot null");
    }
    private void executeConvertValue(Remittance remittance){
        if(remittance.getPayer().getBalance().getCurrency().equals(remittance.getReceiver().getAccountCurrency())){
            throw new ConvertRemittanceValueActionException( "Accounts currency cannot be equals");
        }
        remittance.visit(remittance.getPayer().getBalance().getCurrency()::convertRemittanceValue);
    }

}
