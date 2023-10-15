package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.GetExchangeRateAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.GetExchangeRateActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Action;
import br.com.ctmait.remittanceserviceapi.tech.rest.client.ExchangeRateClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

@Action
public class GetExchangeRateActionImpl implements GetExchangeRateAction {

    private static final Logger log = LoggerFactory.getLogger(GetExchangeRateActionImpl.class);

    private final ExchangeRateClient exchangeRateClient;

    public GetExchangeRateActionImpl(ExchangeRateClient exchangeRateClient) {
        this.exchangeRateClient = exchangeRateClient;
    }

    @Override
    public void execute(Remittance remittance){

        log.info("GERAI-E-00 Get exchange rate for remittance {} started", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            remittance.setExchangeRateDate(this.generateExchangeRateDay());
            remittance.visit(exchangeRateClient::loadExchangeRateOnRemittance);
            log.info("GERAI-E-01 Get exchange rate for remittance {} finished", remittance);
        }catch (Exception exception){
            log.error("GERAI-E-02 Get exchange rate remittance {} error {} ", remittance, exception);
            throw new GetExchangeRateActionException(exception);
        }
    }

    private LocalDate generateExchangeRateDay(){
        var exchangeRateDay = LocalDate.now();
        var today = exchangeRateDay.getDayOfWeek();
        if (today == DayOfWeek.SATURDAY){
            return exchangeRateDay.minusDays(1);
        }
        if (today == DayOfWeek.SUNDAY){
            return exchangeRateDay.minusDays(2);
        }
        return exchangeRateDay;
    }
}
