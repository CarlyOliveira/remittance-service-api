package br.com.ctmait.remittanceserviceapi.domain.process;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.abstraction.actions.*;
import br.com.ctmait.remittanceserviceapi.abstraction.validations.RemittanceCreateValidation;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.*;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class RemittanceCreateProcessImplTest {

    @Mock
    private RemittanceCreateValidation remittanceCreateValidation;
    @Mock
    private PayerEnrichmentAction payerEnrichmentAction;
    @Mock
    private ReceiverEnrichmentAction receiverEnrichmentAction;
    @Mock
    private CheckBalanceAction checkBalanceAction;
    @Mock
    private CheckLimitAction checkLimitAction;
    @Mock
    private GetExchangeRateAction getExchangeRateAction;
    @Mock
    private ConvertRemittanceValueAction convertRemittanceValueAction;
    @Mock
    private RemittanceEffectivationAction remittanceEffectivationAction;
    @Mock
    private LimitReserveUndoAction limitReserveUndoAction;
    @Mock
    private BalanceReserveUndoAction balanceReserveUndoAction;

    @InjectMocks
    private RemittanceCreateProcessImpl remittanceCreateProcess;


    @Test
    void success() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doNothing().when(remittanceCreateValidation).execute(remittance);
        doNothing().when(payerEnrichmentAction).execute(remittance);
        doNothing().when(receiverEnrichmentAction).execute(remittance);
        doNothing().when(checkBalanceAction).execute(remittance);
        doNothing().when(checkLimitAction).execute(remittance);
        doNothing().when(getExchangeRateAction).execute(remittance);
        doNothing().when(convertRemittanceValueAction).execute(remittance);
        doNothing().when(remittanceEffectivationAction).execute(remittance);

        assertDoesNotThrow(() -> remittance.visit(remittanceCreateProcess::execute));

        verify(remittanceCreateValidation, times(1)).execute(remittance);
        verify(payerEnrichmentAction, times(1)).execute(remittance);
        verify(receiverEnrichmentAction, times(1)).execute(remittance);
        verify(checkBalanceAction, times(1)).execute(remittance);
        verify(checkLimitAction, times(1)).execute(remittance);
        verify(getExchangeRateAction, times(1)).execute(remittance);
        verify(convertRemittanceValueAction, times(1)).execute(remittance);
        verify(remittanceEffectivationAction, times(1)).execute(remittance);
        verify(limitReserveUndoAction, never()).execute(remittance);
        verify(balanceReserveUndoAction, never()).execute(remittance);

    }
    @Test
    void remittanceCreateProcessException() {
        Remittance remittance = null;
        assertThrows(RemittanceCreateProcessException.class, () -> remittanceCreateProcess.execute(remittance));
        verify(remittanceCreateValidation, never()).execute(remittance);
        verify(payerEnrichmentAction, never()).execute(remittance);
        verify(receiverEnrichmentAction, never()).execute(remittance);
        verify(checkBalanceAction, never()).execute(remittance);
        verify(checkLimitAction, never()).execute(remittance);
        verify(getExchangeRateAction, never()).execute(remittance);
        verify(convertRemittanceValueAction, never()).execute(remittance);
        verify(remittanceEffectivationAction, never()).execute(remittance);
        verify(limitReserveUndoAction, never()).execute(remittance);
        verify(balanceReserveUndoAction, never()).execute(remittance);
    }
    @Test
    void remittanceCreateValidationException() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doThrow(RemittanceCreateValidationException.class).when(remittanceCreateValidation).execute(remittance);
        assertThrows(RemittanceCreateValidationException.class, () -> remittanceCreateProcess.execute(remittance));
        verify(remittanceCreateValidation, times(1)).execute(remittance);
        verify(payerEnrichmentAction, never()).execute(remittance);
        verify(receiverEnrichmentAction, never()).execute(remittance);
        verify(checkBalanceAction, never()).execute(remittance);
        verify(checkLimitAction, never()).execute(remittance);
        verify(getExchangeRateAction, never()).execute(remittance);
        verify(convertRemittanceValueAction, never()).execute(remittance);
        verify(remittanceEffectivationAction, never()).execute(remittance);
        verify(limitReserveUndoAction, never()).execute(remittance);
        verify(balanceReserveUndoAction, never()).execute(remittance);
    }
    @Test
    void payerEnrichmentActionException() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doNothing().when(remittanceCreateValidation).execute(remittance);
        doThrow(PayerEnrichmentActionException.class).when(payerEnrichmentAction).execute(remittance);
        assertThrows(PayerEnrichmentActionException.class, () -> remittanceCreateProcess.execute(remittance));
        verify(remittanceCreateValidation, times(1)).execute(remittance);
        verify(payerEnrichmentAction, times(1)).execute(remittance);
        verify(receiverEnrichmentAction, never()).execute(remittance);
        verify(checkBalanceAction, never()).execute(remittance);
        verify(checkLimitAction, never()).execute(remittance);
        verify(getExchangeRateAction, never()).execute(remittance);
        verify(convertRemittanceValueAction, never()).execute(remittance);
        verify(remittanceEffectivationAction, never()).execute(remittance);
        verify(limitReserveUndoAction, never()).execute(remittance);
        verify(balanceReserveUndoAction, never()).execute(remittance);
    }
    @Test
    void receiverEnrichmentActionException() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doNothing().when(remittanceCreateValidation).execute(remittance);
        doNothing().when(payerEnrichmentAction).execute(remittance);
        doThrow(ReceiverEnrichmentActionException.class).when(receiverEnrichmentAction).execute(remittance);
        assertThrows(ReceiverEnrichmentActionException.class, () -> remittanceCreateProcess.execute(remittance));
        verify(remittanceCreateValidation, times(1)).execute(remittance);
        verify(payerEnrichmentAction, times(1)).execute(remittance);
        verify(receiverEnrichmentAction, times(1)).execute(remittance);
        verify(checkBalanceAction, never()).execute(remittance);
        verify(checkLimitAction, never()).execute(remittance);
        verify(getExchangeRateAction, never()).execute(remittance);
        verify(convertRemittanceValueAction, never()).execute(remittance);
        verify(remittanceEffectivationAction, never()).execute(remittance);
        verify(limitReserveUndoAction, never()).execute(remittance);
        verify(balanceReserveUndoAction, never()).execute(remittance);
    }
    @Test
    void checkBalanceActionException() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doNothing().when(remittanceCreateValidation).execute(remittance);
        doNothing().when(payerEnrichmentAction).execute(remittance);
        doNothing().when(receiverEnrichmentAction).execute(remittance);
        doThrow(CheckBalanceActionException.class).when(checkBalanceAction).execute(remittance);
        assertThrows(CheckBalanceActionException.class, () -> remittanceCreateProcess.execute(remittance));
        verify(remittanceCreateValidation, times(1)).execute(remittance);
        verify(payerEnrichmentAction, times(1)).execute(remittance);
        verify(receiverEnrichmentAction, times(1)).execute(remittance);
        verify(checkBalanceAction, times(1)).execute(remittance);
        verify(checkLimitAction, never()).execute(remittance);
        verify(getExchangeRateAction, never()).execute(remittance);
        verify(convertRemittanceValueAction, never()).execute(remittance);
        verify(remittanceEffectivationAction, never()).execute(remittance);
        verify(limitReserveUndoAction, never()).execute(remittance);
        verify(balanceReserveUndoAction, never()).execute(remittance);
    }
    @Test
    void checkLimitActionException() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doNothing().when(remittanceCreateValidation).execute(remittance);
        doNothing().when(payerEnrichmentAction).execute(remittance);
        doNothing().when(receiverEnrichmentAction).execute(remittance);
        doNothing().when(checkBalanceAction).execute(remittance);
        doNothing().when(balanceReserveUndoAction).execute(remittance);
        doThrow(CheckLimitActionException.class).when(checkLimitAction).execute(remittance);
        assertThrows(CheckLimitActionException.class, () -> remittanceCreateProcess.execute(remittance));
        verify(remittanceCreateValidation, times(1)).execute(remittance);
        verify(payerEnrichmentAction, times(1)).execute(remittance);
        verify(receiverEnrichmentAction, times(1)).execute(remittance);
        verify(checkBalanceAction, times(1)).execute(remittance);
        verify(checkLimitAction, times(1)).execute(remittance);
        verify(getExchangeRateAction, never()).execute(remittance);
        verify(convertRemittanceValueAction, never()).execute(remittance);
        verify(remittanceEffectivationAction, never()).execute(remittance);
        verify(limitReserveUndoAction, never()).execute(remittance);
        verify(balanceReserveUndoAction, times(1)).execute(remittance);
    }
    @Test
    void getExchangeRateActionException() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doNothing().when(remittanceCreateValidation).execute(remittance);
        doNothing().when(payerEnrichmentAction).execute(remittance);
        doNothing().when(receiverEnrichmentAction).execute(remittance);
        doNothing().when(checkBalanceAction).execute(remittance);
        doNothing().when(checkLimitAction).execute(remittance);
        doNothing().when(balanceReserveUndoAction).execute(remittance);
        doNothing().when(limitReserveUndoAction).execute(remittance);
        doThrow(GetExchangeRateActionException.class).when(getExchangeRateAction).execute(remittance);
        assertThrows(GetExchangeRateActionException.class, () -> remittanceCreateProcess.execute(remittance));
        verify(remittanceCreateValidation, times(1)).execute(remittance);
        verify(payerEnrichmentAction, times(1)).execute(remittance);
        verify(receiverEnrichmentAction, times(1)).execute(remittance);
        verify(checkBalanceAction, times(1)).execute(remittance);
        verify(checkLimitAction, times(1)).execute(remittance);
        verify(getExchangeRateAction, times(1)).execute(remittance);
        verify(convertRemittanceValueAction, never()).execute(remittance);
        verify(remittanceEffectivationAction, never()).execute(remittance);
        verify(limitReserveUndoAction, times(1)).execute(remittance);
        verify(balanceReserveUndoAction, times(1)).execute(remittance);
    }
    @Test
    void convertRemittanceValueActionException() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doNothing().when(remittanceCreateValidation).execute(remittance);
        doNothing().when(payerEnrichmentAction).execute(remittance);
        doNothing().when(receiverEnrichmentAction).execute(remittance);
        doNothing().when(checkBalanceAction).execute(remittance);
        doNothing().when(checkLimitAction).execute(remittance);
        doNothing().when(getExchangeRateAction).execute(remittance);
        doNothing().when(balanceReserveUndoAction).execute(remittance);
        doNothing().when(limitReserveUndoAction).execute(remittance);
        doThrow(ConvertRemittanceValueActionException.class).when(convertRemittanceValueAction).execute(remittance);
        assertThrows(ConvertRemittanceValueActionException.class, () -> remittanceCreateProcess.execute(remittance));
        verify(remittanceCreateValidation, times(1)).execute(remittance);
        verify(payerEnrichmentAction, times(1)).execute(remittance);
        verify(receiverEnrichmentAction, times(1)).execute(remittance);
        verify(checkBalanceAction, times(1)).execute(remittance);
        verify(checkLimitAction, times(1)).execute(remittance);
        verify(getExchangeRateAction, times(1)).execute(remittance);
        verify(convertRemittanceValueAction, times(1)).execute(remittance);
        verify(remittanceEffectivationAction, never()).execute(remittance);
        verify(limitReserveUndoAction, times(1)).execute(remittance);
        verify(balanceReserveUndoAction, times(1)).execute(remittance);
    }
    @Test
    void remittanceEffectivationActionException() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doNothing().when(remittanceCreateValidation).execute(remittance);
        doNothing().when(payerEnrichmentAction).execute(remittance);
        doNothing().when(receiverEnrichmentAction).execute(remittance);
        doNothing().when(checkBalanceAction).execute(remittance);
        doNothing().when(checkLimitAction).execute(remittance);
        doNothing().when(getExchangeRateAction).execute(remittance);
        doNothing().when(convertRemittanceValueAction).execute(remittance);
        doNothing().when(balanceReserveUndoAction).execute(remittance);
        doNothing().when(limitReserveUndoAction).execute(remittance);
        doThrow(RemittanceEffectivationActionException.class).when(remittanceEffectivationAction).execute(remittance);
        assertThrows(RemittanceEffectivationActionException.class, () -> remittanceCreateProcess.execute(remittance));
        verify(remittanceCreateValidation, times(1)).execute(remittance);
        verify(payerEnrichmentAction, times(1)).execute(remittance);
        verify(receiverEnrichmentAction, times(1)).execute(remittance);
        verify(checkBalanceAction, times(1)).execute(remittance);
        verify(checkLimitAction, times(1)).execute(remittance);
        verify(getExchangeRateAction, times(1)).execute(remittance);
        verify(convertRemittanceValueAction, times(1)).execute(remittance);
        verify(remittanceEffectivationAction, times(1)).execute(remittance);
        verify(limitReserveUndoAction, times(1)).execute(remittance);
        verify(balanceReserveUndoAction, times(1)).execute(remittance);
    }
}
