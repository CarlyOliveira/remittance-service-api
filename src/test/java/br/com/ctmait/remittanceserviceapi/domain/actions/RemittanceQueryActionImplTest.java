package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceNotFoundException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceQueryActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
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
public class RemittanceQueryActionImplTest {

    @Mock
    private RemittanceRepository remittanceRepository;
    @InjectMocks
    private RemittanceQueryActionImpl remittanceQueryAction;

    private static final String TRANSACTION_ID = "3e09b1d3-c592-4657-8252-462430574644";

    @Test
    void success() {
        var remittance = new Remittance();
        remittance.setId(TRANSACTION_ID);
        doNothing().when(remittanceRepository).load(remittance);
        assertDoesNotThrow(() -> remittance.visit(remittanceQueryAction::execute));
        verify(remittanceRepository, times(1)).load(any());
    }
    @Test
    void remittanceNotFoundException() {
        var remittance = new Remittance();
        remittance.setId(TRANSACTION_ID);
        doThrow(RemittanceNotFoundException.class).when(remittanceRepository).load(remittance);
        assertThrows(RemittanceNotFoundException.class, () -> remittance.visit(remittanceQueryAction::execute));
        verify(remittanceRepository, times(1)).load(any());
    }
    @Test
    void remittanceQueryActionExceptionByRepository() {
        var remittance = new Remittance();
        remittance.setId(TRANSACTION_ID);
        doThrow(RuntimeException.class).when(remittanceRepository).load(remittance);
        assertThrows(RemittanceQueryActionException.class, () -> remittance.visit(remittanceQueryAction::execute));
        verify(remittanceRepository, times(1)).load(any());
    }
    @Test
    void remittanceQueryActionExceptionByRemittanceIdNull() {
        var remittance = new Remittance();
        remittance.setId(null);
        assertThrows(RemittanceQueryActionException.class, () -> remittance.visit(remittanceQueryAction::execute));
        verify(remittanceRepository, never()).load(any());
    }
    @Test
    void remittanceQueryActionExceptionByRemittanceNull() {
        Remittance remittance = null;
        assertThrows(RemittanceQueryActionException.class, () -> remittanceQueryAction.execute(remittance));
        verify(remittanceRepository, never()).load(any());
    }
}
