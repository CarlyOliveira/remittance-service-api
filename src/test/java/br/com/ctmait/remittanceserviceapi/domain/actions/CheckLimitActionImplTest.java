package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.CheckLimitActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheckLimitActionImplTest {

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private CheckLimitActionImpl checkLimitAction;

    private static final String TRANSACTION_ID = "3e09b1d3-c592-4657-8252-462430574644";

    @Test
    void success() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setId(TRANSACTION_ID);
        doNothing().when(accountRepository).updateLimit(any(), any());
        assertDoesNotThrow(() -> remittance.visit(checkLimitAction::execute));
        verify(accountRepository, times(1)).updateLimit(any(), any());
    }
    @Test
    void checkLimitActionExceptionRootCauseNotLimit() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setValue(BigDecimal.valueOf(9999999.00));
        assertThrows(CheckLimitActionException.class, () -> remittance.visit(checkLimitAction::execute));
        verify(accountRepository, never()).updateLimit(any(), any());
    }
    @Test
    void checkLimitActionExceptionByRepositoryUpdateLimit() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doThrow(CheckLimitActionException.class).when(accountRepository).updateLimit(any(), any());
        assertThrows(CheckLimitActionException.class, () -> remittance.visit(checkLimitAction::execute));
        verify(accountRepository, times(1)).updateLimit(any(), any());
    }
    @Test
    void checkLimitActionExceptionByRemittanceNull() {
        Remittance remittance = null;
        assertThrows(CheckLimitActionException.class, () -> checkLimitAction.execute(remittance));
        verify(accountRepository, never()).updateLimit(any(), any());
    }
    @Test
    void checkLimitActionExceptionByRemittancePayerNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setPayer(null);
        assertThrows(CheckLimitActionException.class, () -> checkLimitAction.execute(remittance));
        verify(accountRepository, never()).updateLimit(any(), any());
    }
}
