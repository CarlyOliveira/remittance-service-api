package br.com.ctmait.remittanceserviceapi.domain.actions;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.PayerEnrichmentActionException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.PayerIsNotOwnerAccountException;
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
public class PayerEnrichmentActionImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private PayerEnrichmentActionImpl payerEnrichmentAction;


    @Test
    void success() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        when(userRepository.getByDocument(any())).thenReturn(UtilTest.generateUserPF());
        when(accountRepository.getById(any())).thenReturn(UtilTest.generateAccountPfReal());
        assertDoesNotThrow(() -> remittance.visit(payerEnrichmentAction::execute));
        verify(accountRepository, times(1)).getById(any());
        verify(userRepository, times(1)).getByDocument(any());

    }
    @Test
    void payerEnrichmentActionExceptionRootCausePayerIsNotOwnerAccountException() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        when(userRepository.getByDocument(any())).thenReturn(UtilTest.generateUserPF());
        when(accountRepository.getById(any())).thenReturn(UtilTest.generateAccountPjReal());
        assertThrows(PayerEnrichmentActionException.class, () -> remittance.visit(payerEnrichmentAction::execute));
        verify(accountRepository, times(1)).getById(any());
        verify(userRepository, times(1)).getByDocument(any());
    }
    @Test
    void payerEnrichmentActionExceptionByRepository() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        doThrow(RuntimeException.class).when(userRepository).getByDocument(any());
        assertThrows(PayerEnrichmentActionException.class, () -> remittance.visit(payerEnrichmentAction::execute));
        verify(accountRepository, never()).getById(any());
        verify(userRepository, times(1)).getByDocument(any());
    }
    @Test
    void payerEnrichmentActionExceptionRemittanceNull() {
        Remittance remittance = null;
        assertThrows(PayerEnrichmentActionException.class, () -> payerEnrichmentAction.execute(remittance));
        verify(accountRepository, never()).getById(any());
        verify(userRepository, never()).getByDocument(any());
    }
    @Test
    void payerEnrichmentActionExceptionPayerNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setPayer(null);
        assertThrows(PayerEnrichmentActionException.class, () -> remittance.visit(payerEnrichmentAction::execute));
        verify(accountRepository, never()).getById(any());
        verify(userRepository, never()).getByDocument(any());
    }
    @Test
    void payerEnrichmentActionExceptionPayerAccountIdNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.getPayer().setAccountId(null);
        assertThrows(PayerEnrichmentActionException.class, () -> remittance.visit(payerEnrichmentAction::execute));
        verify(accountRepository, never()).getById(any());
        verify(userRepository, never()).getByDocument(any());
    }
    @Test
    void payerEnrichmentActionExceptionPayerDocumentNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.getPayer().setDocument(null);
        assertThrows(PayerEnrichmentActionException.class, () -> remittance.visit(payerEnrichmentAction::execute));
        verify(accountRepository, never()).getById(any());
        verify(userRepository, never()).getByDocument(any());
    }
    @Test
    void payerEnrichmentActionExceptionPayerDocumentValueNull() {
        Remittance remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.getPayer().getDocument().setValue(null);
        assertThrows(PayerEnrichmentActionException.class, () -> remittance.visit(payerEnrichmentAction::execute));
        verify(accountRepository, never()).getById(any());
        verify(userRepository, never()).getByDocument(any());
    }
}
