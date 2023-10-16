package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.ConvertRemittanceValueActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ConvertRemittanceValueActionImplTest {

    @InjectMocks
    private ConvertRemittanceValueActionImpl convertRemittanceValueAction;


    @Test
    void success() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        assertDoesNotThrow(() -> remittance.visit(convertRemittanceValueAction::execute));
    }
    @Test
    void convertRemittanceValueActionExceptionValueCannotBeZero() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setValue(BigDecimal.ZERO);
        assertThrows(ConvertRemittanceValueActionException.class, () -> remittance.visit(convertRemittanceValueAction::execute));
    }
    @Test
    void convertRemittanceValueActionExceptionValueCannotBeNull() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setValue(null);
        assertThrows(ConvertRemittanceValueActionException.class, () -> remittance.visit(convertRemittanceValueAction::execute));
    }
    @Test
    void convertRemittanceValueActionExceptionExchangeRateCannotBeZero() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setExchangeRate(BigDecimal.ZERO);
        assertThrows(ConvertRemittanceValueActionException.class, () -> remittance.visit(convertRemittanceValueAction::execute));
    }
    @Test
    void convertRemittanceValueActionExceptionExchangeRateCannotBeNull() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setExchangeRate(null);
        assertThrows(ConvertRemittanceValueActionException.class, () -> remittance.visit(convertRemittanceValueAction::execute));
    }
    @Test
    void convertRemittanceValueActionExceptionByRemittanceNull() {
        Remittance remittance = null;
        assertThrows(ConvertRemittanceValueActionException.class, () -> convertRemittanceValueAction.execute(remittance));
    }
    @Test
    void convertRemittanceValueActionExceptionByRemittancePayerNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setPayer(null);
        assertThrows(ConvertRemittanceValueActionException.class, () -> convertRemittanceValueAction.execute(remittance));
    }
    @Test
    void convertRemittanceValueActionExceptionByRemittancePayerBalanceNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.getPayer().setBalance(null);
        assertThrows(ConvertRemittanceValueActionException.class, () -> convertRemittanceValueAction.execute(remittance));
    }
    @Test
    void convertRemittanceValueActionExceptionByRemittancePayerBalanceCurrencyNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.getPayer().getBalance().setCurrency(null);
        assertThrows(ConvertRemittanceValueActionException.class, () -> convertRemittanceValueAction.execute(remittance));
    }
    @Test
    void convertRemittanceValueActionExceptionByRemittanceReceiverNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setReceiver(null);
        assertThrows(ConvertRemittanceValueActionException.class, () -> convertRemittanceValueAction.execute(remittance));
    }
    @Test
    void convertRemittanceValueActionExceptionByRemittanceReceiverAccountCurrencyNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.getReceiver().setAccountCurrency(null);
        assertThrows(ConvertRemittanceValueActionException.class, () -> convertRemittanceValueAction.execute(remittance));
    }
}
