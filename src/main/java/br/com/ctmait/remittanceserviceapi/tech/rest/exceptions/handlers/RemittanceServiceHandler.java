package br.com.ctmait.remittanceserviceapi.tech.rest.exceptions.handlers;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.*;
import br.com.ctmait.remittanceserviceapi.tech.rest.exceptions.payload.ExceptionPayload;
import br.com.ctmait.remittanceserviceapi.tech.rest.exceptions.service.ExceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
@RequiredArgsConstructor
public class RemittanceServiceHandler {

    private final ExceptionService exceptionService;

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public ResponseEntity<ExceptionPayload> handleException(HttpClientErrorException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                ex.getStatusCode());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    @ResponseBody
    public ResponseEntity<ExceptionPayload> handleException(HttpServerErrorException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionPayload> handleException(Exception ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionPayload> handleException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionPayload> handleException(IllegalArgumentException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionPayload> handleException(NullPointerException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ExceptionPayload> handleException(MissingRequestHeaderException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RemittanceCreateValidationException.class)
    public ResponseEntity<ExceptionPayload> handleException(RemittanceCreateValidationException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CheckBalanceActionException.class)
    public ResponseEntity<ExceptionPayload> handleException(CheckBalanceActionException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(CheckLimitActionException.class)
    public ResponseEntity<ExceptionPayload> handleException(CheckLimitActionException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(GetExchangeRateActionException.class)
    public ResponseEntity<ExceptionPayload> handleException(GetExchangeRateActionException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.FAILED_DEPENDENCY);
    }
    @ExceptionHandler(ConvertRemittanceValueActionException.class)
    public ResponseEntity<ExceptionPayload> handleException(ConvertRemittanceValueActionException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(RemittanceCreateProcessException.class)
    public ResponseEntity<ExceptionPayload> handleException(RemittanceCreateProcessException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(RemittanceException.class)
    public ResponseEntity<ExceptionPayload> handleException(RemittanceException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(RemittanceQueryActionException.class)
    public ResponseEntity<ExceptionPayload> handleException(RemittanceQueryActionException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(RemittanceQueryProcessException.class)
    public ResponseEntity<ExceptionPayload> handleException(RemittanceQueryProcessException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(RemittanceEffectivationActionException.class)
    public ResponseEntity<ExceptionPayload> handleException(RemittanceEffectivationActionException ex) {
        return new ResponseEntity<ExceptionPayload>(
                exceptionService.generatePayload(ex),
                HttpStatus.BAD_REQUEST);
    }


}
