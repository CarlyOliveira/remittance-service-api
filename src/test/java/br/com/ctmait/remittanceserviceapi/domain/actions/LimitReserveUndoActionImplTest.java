package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.LimitReserveUndoActionException;
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
public class LimitReserveUndoActionImplTest {

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private LimitReserveUndoActionImpl limitReserveUndoAction;

    private static final String TRANSACTION_ID = "3e09b1d3-c592-4657-8252-462430574644";

    @Test
    void success() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setId(TRANSACTION_ID);
        when(accountRepository.getById(any())).thenReturn(UtilTest.generateAccountPfReal());
        doNothing().when(accountRepository).updateLimit(any(), any());
        assertDoesNotThrow(() -> remittance.visit(limitReserveUndoAction::execute));
        verify(accountRepository, times(1)).getById(any());
        verify(accountRepository, times(1)).updateLimit(any(), any());
    }
    @Test
    void limitReserveUndoActionExceptionByRepositoryUpdateLimit() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        when(accountRepository.getById(any())).thenReturn(UtilTest.generateAccountPfReal());
        doThrow(LimitReserveUndoActionException.class).when(accountRepository).updateLimit(any(), any());
        assertThrows(LimitReserveUndoActionException.class, () -> remittance.visit(limitReserveUndoAction::execute));
        verify(accountRepository, times(1)).getById(any());
        verify(accountRepository, times(1)).updateLimit(any(), any());
    }
    @Test
    void limitReserveUndoActionExceptionByRepositoryGetById() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doThrow(LimitReserveUndoActionException.class).when(accountRepository).getById(any());
        assertThrows(LimitReserveUndoActionException.class, () -> remittance.visit(limitReserveUndoAction::execute));
        verify(accountRepository, times(1)).getById(any());
        verify(accountRepository, never()).updateLimit(any(), any());
    }
    @Test
    void limitReserveUndoActionExceptionByRemittanceIdNull() {
        var remittance = new Remittance();
        remittance.setId(null);
        assertThrows(LimitReserveUndoActionException.class, () -> remittance.visit(limitReserveUndoAction::execute));
        verify(accountRepository, never()).getById(any());
        verify(accountRepository, never()).updateLimit(any(), any());
    }
    @Test
    void limitReserveUndoActionExceptionByRemittanceNull() {
        Remittance remittance = null;
        assertThrows(LimitReserveUndoActionException.class, () -> limitReserveUndoAction.execute(remittance));
        verify(accountRepository, never()).getById(any());
        verify(accountRepository, never()).updateLimit(any(), any());
    }
}
