package br.com.ctmait.remittanceserviceapi.domain.validations;

import br.com.ctmait.remittanceserviceapi.abstraction.validations.RemittanceCreateValidation;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceCreateValidationException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.annotations.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;

@Validation
public class RemittanceCreateValidationImpl implements RemittanceCreateValidation {

    private static final Logger log = LoggerFactory.getLogger(RemittanceCreateValidationImpl.class);

    @Override
    public void execute(Remittance remittance) throws RemittanceCreateValidationException, RemittanceException {

        log.info("RCVI-E-00 Validate remittance {} in create process", remittance);
        HashMap<String, String> errors = new HashMap<>();
        try{
            validateRemittance(remittance, errors);
            validateValue(remittance, errors);
            validatePayer(remittance, errors);
            validatePayerUserId(remittance, errors);
            validatePayerAccountId(remittance, errors);
            validateReceiver(remittance, errors);
            validateReceiverUserId(remittance, errors);
            validateReceiverAccountId(remittance, errors);
            hasErrorsThrowRemittanceCreateValidationException(errors);
        }catch (Exception exception){
            log.error("RCVI-E-01 Validate remittance {} with errors in create process", remittance, exception);
            throw new RemittanceCreateValidationException(exception);
        }
        log.info("RCVI-E-02 remittance {} validated in create process", remittance);
    }

    private void validateRemittance(Remittance remittance, HashMap<String, String> errors){
        try {
            notNullOrEmpty(remittance, " remittance cannot null");
        }catch (Exception e){
            errors.put("remittance", e.getMessage());
        }
    }

    private void validateValue(Remittance remittance, HashMap<String, String> errors){
        try {
            notNullOrEmpty(remittance.getValue(), " value cannot null");
        }catch (Exception e){
            errors.put("value", e.getMessage());
        }
    }

    private void validatePayer(Remittance remittance, HashMap<String, String> errors){
        try {
            notNullOrEmpty(remittance.getPayer(), " payer cannot null");
        }catch (Exception e){
            errors.put("payer", e.getMessage());
        }
    }

    private void validatePayerUserId(Remittance remittance, HashMap<String, String> errors){
        try {
            notNullOrEmpty(remittance.getPayer().getUserId(), " payer userId cannot null");
        }catch (Exception e){
            errors.put("payer userId", e.getMessage());
        }
    }

    private void validatePayerAccountId(Remittance remittance, HashMap<String, String> errors){
        try {
            notNullOrEmpty(remittance.getPayer().getAccountId(), " payer accountId cannot null");
        }catch (Exception e){
            errors.put("payer accountId", e.getMessage());
        }
    }

    private void validateReceiver(Remittance remittance, HashMap<String, String> errors){
        try {
            notNullOrEmpty(remittance.getReceiver(), " receiver cannot null");
        }catch (Exception e){
            errors.put("receiver", e.getMessage());
        }
    }

    private void validateReceiverUserId(Remittance remittance, HashMap<String, String> errors){
        try {
            notNullOrEmpty(remittance.getReceiver().getUserId(), " receiver userId cannot null");
        }catch (Exception e){
            errors.put("receiver userId", e.getMessage());
        }
    }

    private void validateReceiverAccountId(Remittance remittance, HashMap<String, String> errors){
        try {
            notNullOrEmpty(remittance.getReceiver().getAccountId(), " receiver accountId cannot null");
        }catch (Exception e){
            errors.put("receiver accountId", e.getMessage());
        }
    }

    private void hasErrorsThrowRemittanceCreateValidationException(HashMap<String, String> errors){
        if (errors.size() > 0){
            throw new RemittanceCreateValidationException(errors.toString());
        }
    }

    private void notNullOrEmpty(Object object, String message){
        if (ObjectUtils.isEmpty(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    private void notNullOrEmpty(String text, String message){
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
