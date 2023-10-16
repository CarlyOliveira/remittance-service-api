package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.BalanceReserveUndoActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BalanceReserveUndoActionImplTest {

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private BalanceReserveUndoActionImpl balanceReserveUndoAction;

    private static final String TRANSACTION_ID = "3e09b1d3-c592-4657-8252-462430574644";

    @Test
    void success() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setId(TRANSACTION_ID);
        when(accountRepository.getById(any())).thenReturn(UtilTest.generateAccountPfReal());
        doNothing().when(accountRepository).updateBalance(any(), any());
        assertDoesNotThrow(() -> remittance.visit(balanceReserveUndoAction::execute));
        verify(accountRepository, times(1)).getById(any());
        verify(accountRepository, times(1)).updateBalance(any(), any());
    }
    @Test
    void balanceReserveUndoActionExceptionByRepositoryUpdateBalance() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        when(accountRepository.getById(any())).thenReturn(UtilTest.generateAccountPfReal());
        doThrow(BalanceReserveUndoActionException.class).when(accountRepository).updateBalance(any(), any());
        assertThrows(BalanceReserveUndoActionException.class, () -> remittance.visit(balanceReserveUndoAction::execute));
        verify(accountRepository, times(1)).getById(any());
        verify(accountRepository, times(1)).updateBalance(any(), any());
    }
    @Test
    void balanceReserveUndoActionExceptionByRepositoryGetById() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doThrow(BalanceReserveUndoActionException.class).when(accountRepository).getById(any());
        assertThrows(BalanceReserveUndoActionException.class, () -> remittance.visit(balanceReserveUndoAction::execute));
        verify(accountRepository, times(1)).getById(any());
        verify(accountRepository, never()).updateBalance(any(), any());
    }
    @Test
    void balanceReserveUndoActionExceptionByRemittanceIdNull() {
        var remittance = new Remittance();
        remittance.setId(null);
        assertThrows(BalanceReserveUndoActionException.class, () -> remittance.visit(balanceReserveUndoAction::execute));
        verify(accountRepository, never()).getById(any());
        verify(accountRepository, never()).updateBalance(any(), any());
    }
    @Test
    void balanceReserveUndoActionExceptionByRemittanceNull() {
        Remittance remittance = null;
        assertThrows(BalanceReserveUndoActionException.class, () -> balanceReserveUndoAction.execute(remittance));
        verify(accountRepository, never()).getById(any());
        verify(accountRepository, never()).updateBalance(any(), any());
    }
}
