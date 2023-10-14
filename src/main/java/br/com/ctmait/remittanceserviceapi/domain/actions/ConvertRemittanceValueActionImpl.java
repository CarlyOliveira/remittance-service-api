package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.ConvertRemittanceValueAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.CheckBalanceException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.ConvertRemittanceValueActionException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;

@Action
public class ConvertRemittanceValueActionImpl implements ConvertRemittanceValueAction {

    private static final Logger log = LoggerFactory.getLogger(ConvertRemittanceValueActionImpl.class);
    private static final String EXCHANGE_RATE_NAME = "exchangeRate";
    private static final String REMITTANCE_VALUE_NAME = "remittanceValue";

    @Override
    public void execute(Remittance remittance) throws CheckBalanceException, RemittanceException {

        log.info("CRVAI-E-00 Convert value for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
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
        var convertedValue = remittance.getValue().multiply(remittance.getExchangeRate()).setScale(5, RoundingMode.HALF_DOWN);
        remittance.setConvertedValue(convertedValue);
    }
}
