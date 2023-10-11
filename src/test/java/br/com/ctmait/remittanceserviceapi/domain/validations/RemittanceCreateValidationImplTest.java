package br.com.ctmait.remittanceserviceapi.domain.validations;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceCreateValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class RemittanceCreateValidationImplTest {

    @InjectMocks
    private RemittanceCreateValidationImpl remittanceCreateValidation;

    @Test
    void success() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        assertDoesNotThrow(() -> remittance.visit(remittanceCreateValidation::execute));
    }

    @Test
    void validateRemittance() {
        assertThrows(RemittanceCreateValidationException.class, () -> remittanceCreateValidation.execute(null));
    }

    @Test
    void validateValue() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setValue(null);
        assertThrows(RemittanceCreateValidationException.class, () -> remittance.visit(remittanceCreateValidation::execute));
    }

    @Test
    void validatePayer() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setPayer(null);
        assertThrows(RemittanceCreateValidationException.class, () -> remittanceCreateValidation.execute(remittance));
    }

    @Test
    void validatePayerUserId() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.getPayer().setUserId(null);
        assertThrows(RemittanceCreateValidationException.class, () -> remittance.visit(remittanceCreateValidation::execute));
    }

    @Test
    void validatePayerAccountId() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.getPayer().setAccountId(null);
        assertThrows(RemittanceCreateValidationException.class, () -> remittance.visit(remittanceCreateValidation::execute));
    }

    @Test
    void validateReceiver() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.setReceiver(null);
        assertThrows(RemittanceCreateValidationException.class, () -> remittanceCreateValidation.execute(remittance));
    }

    @Test
    void validateReceiverUserId() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.getReceiver().setUserId(null);
        assertThrows(RemittanceCreateValidationException.class, () -> remittance.visit(remittanceCreateValidation::execute));
    }

    @Test
    void validateReceiverAccountId() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        remittance.getReceiver().setAccountId(null);
        assertThrows(RemittanceCreateValidationException.class, () -> remittance.visit(remittanceCreateValidation::execute));
    }
}
