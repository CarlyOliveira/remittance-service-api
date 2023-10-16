package br.com.ctmait.remittanceserviceapi.domain.process;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.RemittanceQueryAction;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceNotFoundException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceQueryActionException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceQueryProcessException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RemittanceQueryProcessImplTest {

    @Mock
    private RemittanceQueryAction remittanceQueryAction;
    @InjectMocks
    private RemittanceQueryProcessImpl remittanceQueryProcess;

    private static final String TRANSACTION_ID = "3e09b1d3-c592-4657-8252-462430574644";

    @Test
    void success() {
        var remittance = new Remittance();
        remittance.setId(TRANSACTION_ID);
        doNothing().when(remittanceQueryAction).execute(remittance);
        assertDoesNotThrow(() -> remittance.visit(remittanceQueryProcess::execute));
        verify(remittanceQueryAction, times(1)).execute(remittance);
    }
    @Test
    void remittanceNotFoundException() {
        var remittance = new Remittance();
        remittance.setId(TRANSACTION_ID);
        doThrow(RemittanceNotFoundException.class).when(remittanceQueryAction).execute(remittance);
        assertThrows(RemittanceNotFoundException.class, () -> remittance.visit(remittanceQueryProcess::execute));
        verify(remittanceQueryAction, times(1)).execute(remittance);
    }
    @Test
    void remittanceQueryActionException() {
        var remittance = new Remittance();
        remittance.setId(TRANSACTION_ID);
        doThrow(RemittanceQueryActionException.class).when(remittanceQueryAction).execute(remittance);
        assertThrows(RemittanceQueryActionException.class, () -> remittance.visit(remittanceQueryProcess::execute));
        verify(remittanceQueryAction, times(1)).execute(remittance);
    }
    @Test
    void remittanceQueryProcessExceptionByRemittanceNull() {
        Remittance remittance = null;
        assertThrows(RemittanceQueryProcessException.class, () -> remittanceQueryProcess.execute(remittance));
        verify(remittanceQueryAction, never()).execute(remittance);
    }
    @Test
    void remittanceQueryProcessExceptionByRemittanceIdNull() {
        var remittance = new Remittance();
        remittance.setId(null);
        assertThrows(RemittanceQueryProcessException.class, () -> remittance.visit(remittanceQueryProcess::execute));
        verify(remittanceQueryAction, never()).execute(remittance);
    }
}
