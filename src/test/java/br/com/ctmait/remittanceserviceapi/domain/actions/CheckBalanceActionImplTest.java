package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.CheckBalanceActionException;
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
public class CheckBalanceActionImplTest {

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private CheckBalanceActionImpl checkBalanceAction;

    private static final String TRANSACTION_ID = "3e09b1d3-c592-4657-8252-462430574644";

    @Test
    void success() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setId(TRANSACTION_ID);
        doNothing().when(accountRepository).updateBalance(any(), any());
        assertDoesNotThrow(() -> remittance.visit(checkBalanceAction::execute));
        verify(accountRepository, times(1)).updateBalance(any(), any());
    }
    @Test
    void checkBalanceActionExceptionRootCauseNotBalance() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setValue(BigDecimal.valueOf(1000000.00));
        assertThrows(CheckBalanceActionException.class, () -> remittance.visit(checkBalanceAction::execute));
        verify(accountRepository, never()).updateBalance(any(), any());
    }
    @Test
    void checkBalanceActionExceptionByRepositoryUpdateBalance() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doThrow(CheckBalanceActionException.class).when(accountRepository).updateBalance(any(), any());
        assertThrows(CheckBalanceActionException.class, () -> remittance.visit(checkBalanceAction::execute));
        verify(accountRepository, times(1)).updateBalance(any(), any());
    }
    @Test
    void checkBalanceActionExceptionByRemittanceNull() {
        Remittance remittance = null;
        assertThrows(CheckBalanceActionException.class, () -> checkBalanceAction.execute(remittance));
        verify(accountRepository, never()).updateBalance(any(), any());
    }
    @Test
    void checkBalanceActionExceptionByRemittancePayerNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setPayer(null);
        assertThrows(CheckBalanceActionException.class, () -> checkBalanceAction.execute(remittance));
        verify(accountRepository, never()).updateBalance(any(), any());
    }
}
