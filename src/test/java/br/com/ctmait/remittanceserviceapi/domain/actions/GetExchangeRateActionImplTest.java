package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.GetExchangeRateActionException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.GetExchangeRateIntegrationException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.rest.client.ExchangeRateClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetExchangeRateActionImplTest {

    @Mock
    private ExchangeRateClient exchangeRateClient;
    @InjectMocks
    private GetExchangeRateActionImpl getExchangeRateAction;

    @Test
    void success() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doNothing().when(exchangeRateClient).loadExchangeRateOnRemittance(remittance);
        assertDoesNotThrow(() -> remittance.visit(getExchangeRateAction::execute));
        verify(exchangeRateClient, times(1)).loadExchangeRateOnRemittance(any());
        var exchangeDate = generateExchangeRateDay();
        assertEquals(exchangeDate, remittance.getExchangeRateDate());
    }
    @Test
    void getExchangeRateActionExceptionRootCauseGetExchangeRateIntegrationException() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doThrow(GetExchangeRateIntegrationException.class).when(exchangeRateClient).loadExchangeRateOnRemittance(remittance);
        assertThrows(GetExchangeRateActionException.class, () -> remittance.visit(getExchangeRateAction::execute));
        verify(exchangeRateClient, times(1)).loadExchangeRateOnRemittance(any());
    }
    @Test
    void remittanceQueryActionExceptionByRemittanceNull() {
        Remittance remittance = null;
        assertThrows(GetExchangeRateActionException.class, () -> getExchangeRateAction.execute(remittance));
        verify(exchangeRateClient, never()).loadExchangeRateOnRemittance(any());
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
