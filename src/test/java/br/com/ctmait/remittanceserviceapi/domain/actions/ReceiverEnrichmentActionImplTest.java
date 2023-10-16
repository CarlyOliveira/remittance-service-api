package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.ReceiverEnrichmentActionException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.AccountRepository;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceiverEnrichmentActionImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ReceiverEnrichmentActionImpl receiverEnrichmentAction;


    @Test
    void success() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        when(userRepository.getByDocument(any())).thenReturn(UtilTest.generateUserPJ());
        when(accountRepository.getById(any())).thenReturn(UtilTest.generateAccountPjDolar());
        assertDoesNotThrow(() -> remittance.visit(receiverEnrichmentAction::execute));
        verify(accountRepository, times(1)).getById(any());
        verify(userRepository, times(1)).getByDocument(any());

    }
    @Test
    void receiverEnrichmentActionExceptionRootCauseReceiverIsNotOwnerAccountException() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        when(userRepository.getByDocument(any())).thenReturn(UtilTest.generateUserPF());
        when(accountRepository.getById(any())).thenReturn(UtilTest.generateAccountPfReal());
        assertThrows(ReceiverEnrichmentActionException.class, () -> remittance.visit(receiverEnrichmentAction::execute));
        verify(accountRepository, times(1)).getById(any());
        verify(userRepository, times(1)).getByDocument(any());
    }
    @Test
    void receiverEnrichmentActionExceptionByRepository() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doThrow(RuntimeException.class).when(userRepository).getByDocument(any());
        assertThrows(ReceiverEnrichmentActionException.class, () -> remittance.visit(receiverEnrichmentAction::execute));
        verify(accountRepository, never()).getById(any());
        verify(userRepository, times(1)).getByDocument(any());
    }
    @Test
    void receiverEnrichmentActionExceptionRemittanceNull() {
        Remittance remittance = null;
        assertThrows(ReceiverEnrichmentActionException.class, () -> receiverEnrichmentAction.execute(remittance));
        verify(accountRepository, never()).getById(any());
        verify(userRepository, never()).getByDocument(any());
    }
    @Test
    void receiverEnrichmentActionExceptionReceiverNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setReceiver(null);
        assertThrows(ReceiverEnrichmentActionException.class, () -> remittance.visit(receiverEnrichmentAction::execute));
        verify(accountRepository, never()).getById(any());
        verify(userRepository, never()).getByDocument(any());
    }
    @Test
    void receiverEnrichmentActionExceptionReceiverAccountIdNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.getReceiver().setAccountId(null);
        assertThrows(ReceiverEnrichmentActionException.class, () -> remittance.visit(receiverEnrichmentAction::execute));
        verify(accountRepository, never()).getById(any());
        verify(userRepository, never()).getByDocument(any());
    }
    @Test
    void receiverEnrichmentActionExceptionReceiverDocumentNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.getReceiver().setDocument(null);
        assertThrows(ReceiverEnrichmentActionException.class, () -> remittance.visit(receiverEnrichmentAction::execute));
        verify(accountRepository, never()).getById(any());
        verify(userRepository, never()).getByDocument(any());
    }
    @Test
    void receiverEnrichmentActionExceptionReceiverDocumentValueNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.getReceiver().getDocument().setValue(null);
        assertThrows(ReceiverEnrichmentActionException.class, () -> remittance.visit(receiverEnrichmentAction::execute));
        verify(accountRepository, never()).getById(any());
        verify(userRepository, never()).getByDocument(any());
    }
}
