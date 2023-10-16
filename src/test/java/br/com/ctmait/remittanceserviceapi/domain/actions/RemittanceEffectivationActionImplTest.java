package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceEffectivationActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.AccountRepository;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.RemittanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RemittanceEffectivationActionImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private RemittanceRepository remittanceRepository;
    @InjectMocks
    private RemittanceEffectivationActionImpl remittanceEffectivationAction;


    @Test
    void success() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        when(accountRepository.getById(any())).thenReturn(UtilTest.generateAccountPfReal());
        doNothing().when(accountRepository).updateBalance(any(), any());
        doNothing().when(remittanceRepository).insert(any());
        assertDoesNotThrow(() -> remittance.visit(remittanceEffectivationAction::execute));
        verify(accountRepository, times(1)).getById(any());
        verify(accountRepository, times(1)).updateBalance(any(), any());
        verify(remittanceRepository, times(1)).insert(any());
    }
    @Test
    void remittanceEffectivationActionExceptionWithRollback() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        when(accountRepository.getById(any())).thenReturn(UtilTest.generateAccountPfReal());
        doNothing().when(accountRepository).updateBalance(any(), any());
        doThrow(RuntimeException.class).when(remittanceRepository).insert(any());
        assertThrows(RemittanceEffectivationActionException.class, () -> remittance.visit(remittanceEffectivationAction::execute));
        verify(accountRepository, times(2)).getById(any());
        verify(accountRepository, times(2)).updateBalance(any(), any());
        verify(remittanceRepository, times(1)).insert(any());
    }
    @Test
    void remittanceEffectivationActionExceptionRemittanceNull() {
        Remittance remittance = null;
        assertThrows(RemittanceEffectivationActionException.class, () -> remittanceEffectivationAction.execute(remittance));
        verify(accountRepository, never()).getById(any());
        verify(accountRepository, never()).updateBalance(any(), any());
        verify(remittanceRepository, never()).insert(any());
    }
    @Test
    void remittanceEffectivationActionExceptionRemittanceReceiverNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setReceiver(null);
        assertThrows(RemittanceEffectivationActionException.class, () -> remittance.visit(remittanceEffectivationAction::execute));
        verify(accountRepository, never()).getById(any());
        verify(accountRepository, never()).updateBalance(any(), any());
        verify(remittanceRepository, never()).insert(any());
    }
}
