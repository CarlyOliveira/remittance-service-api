package br.com.ctmait.remittanceserviceapi;

import br.com.ctmait.remittanceserviceapi.domain.models.account.Account;
import br.com.ctmait.remittanceserviceapi.domain.models.account.Balance;
import br.com.ctmait.remittanceserviceapi.domain.models.account.Currency;
import br.com.ctmait.remittanceserviceapi.domain.models.account.TransactionalLimit;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Payer;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Receiver;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.RemittanceStatus;
import br.com.ctmait.remittanceserviceapi.domain.models.user.Document;
import br.com.ctmait.remittanceserviceapi.domain.models.user.DocumentType;
import br.com.ctmait.remittanceserviceapi.domain.models.user.User;
import br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.entity.AccountEntity;
import br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.entity.RemittanceEntity;
import br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.entity.UserEntity;
import br.com.ctmait.remittanceserviceapi.tech.rest.payload.in.PersonPayloadIn;
import br.com.ctmait.remittanceserviceapi.tech.rest.payload.in.RemittancePayloadIn;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public class UtilTest {

    private static final BigDecimal LIMIT_PF_VALUE = BigDecimal.valueOf(10000.00);
    private static final BigDecimal LIMIT_PJ_VALUE = BigDecimal.valueOf(50000.00);
    private static final BigDecimal REMITTANCE_VALUE = BigDecimal.valueOf(100.00);
    private static final BigDecimal EXCHANGE_RATE = BigDecimal.valueOf(5.06190);




    public static Remittance generateRemittancePfRealToPjDolar(){
        var remittance = new Remittance();
        remittance.setId(UUID.randomUUID().toString());
        remittance.setValue(REMITTANCE_VALUE);
        remittance.setPayer(generatePayerPfAccountReal());
        remittance.setReceiver(generateReceiverPjAccountDolar());
        remittance.setRemittanceCreateDate(ZonedDateTime.now());
        remittance.setRemittanceStatus(RemittanceStatus.EFETIVADO);
        remittance.setConvertedValue(REMITTANCE_VALUE);
        remittance.setExchangeRate(EXCHANGE_RATE);
        remittance.setExchangeRateDate(LocalDate.now());
        return remittance;
    }
    public static Remittance generateRemittancePjDolarToPfReal(){
        var remittance = new Remittance();
        remittance.setId(UUID.randomUUID().toString());
        remittance.setValue(REMITTANCE_VALUE);
        remittance.setPayer(generatePayerPjAccountDolar());
        remittance.setReceiver(generateReceiverPfAccountReal());
        remittance.setRemittanceCreateDate(ZonedDateTime.now());
        remittance.setRemittanceStatus(RemittanceStatus.EFETIVADO);
        remittance.setConvertedValue(REMITTANCE_VALUE);
        remittance.setExchangeRate(EXCHANGE_RATE);
        remittance.setExchangeRateDate(LocalDate.now());
        return remittance;
    }
    public static Remittance generateRemittancePjDolarToPjReal(){
        var remittance = new Remittance();
        remittance.setId(UUID.randomUUID().toString());
        remittance.setValue(REMITTANCE_VALUE);
        remittance.setPayer(generatePayerPjAccountDolar());
        remittance.setReceiver(generateReceiverPjAccountReal());
        remittance.setRemittanceCreateDate(ZonedDateTime.now());
        remittance.setRemittanceStatus(RemittanceStatus.EFETIVADO);
        remittance.setConvertedValue(REMITTANCE_VALUE);
        remittance.setExchangeRate(EXCHANGE_RATE);
        remittance.setExchangeRateDate(LocalDate.now());
        return remittance;
    }

    public static Payer generatePayerPfAccountDolar(){
        var payer = new Payer();
        payer.setAccountId(generateAccountPfDolar().getId());
        payer.setDocument(generateUserPF().getDocument());
        payer.setUserName(generateUserPF().getName());
        payer.setBalance(generateAccountPfDolar().getBalance());
        return payer;
    }
    public static Payer generatePayerPjAccountDolar(){
        var payer = new Payer();
        payer.setAccountId(generateAccountPjDolar().getId());
        payer.setDocument(generateUserPJ().getDocument());
        payer.setUserName(generateUserPJ().getName());
        payer.setBalance(generateAccountPjDolar().getBalance());
        return payer;
    }
    public static Receiver generateReceiverPjAccountDolar(){
        var receiver = new Receiver();
        receiver.setAccountId(generateAccountPjDolar().getId());
        receiver.setDocument(generateUserPJ().getDocument());
        receiver.setUserName(generateUserPJ().getName());
        receiver.setAccountCurrency(Currency.DOLAR);
        return receiver;
    }
    public static Payer generatePayerPfAccountReal(){
        var payer = new Payer();
        payer.setAccountId(generateAccountPfReal().getId());
        payer.setDocument(generateUserPF().getDocument());
        payer.setUserName(generateUserPF().getName());
        payer.setBalance(generateAccountPfReal().getBalance());
        TransactionalLimit transactionalLimit = new TransactionalLimit();
        transactionalLimit.setValue(BigDecimal.valueOf(10000.00));
        payer.setLimit(transactionalLimit);
        return payer;
    }
    public static Receiver generateReceiverPfAccountReal(){
        var receiver = new Receiver();
        receiver.setAccountId(generateAccountPfReal().getId());
        receiver.setDocument(generateUserPF().getDocument());
        receiver.setUserName(generateUserPF().getName());
        receiver.setAccountCurrency(Currency.REAL);
        return receiver;
    }
    public static Payer generatePayerPjAccountReal(){
        var payer = new Payer();
        payer.setAccountId(generateAccountPjReal().getId());
        payer.setDocument(generateUserPJ().getDocument());
        payer.setUserName(generateUserPJ().getName());
        payer.setBalance(generateAccountPjReal().getBalance());
        return payer;
    }
    public static Receiver generateReceiverPjAccountReal(){
        var receiver = new Receiver();
        receiver.setAccountId(generateAccountPjReal().getId());
        receiver.setDocument(generateUserPJ().getDocument());
        receiver.setUserName(generateUserPJ().getName());
        receiver.setAccountCurrency(Currency.REAL);
        return receiver;
    }

    public static Account generateAccountPfReal(){
        var account = new Account();
        account.setId("eeaf7e4f-e218-490e-92e6-775f21c8ee23");
        account.setOwnerId(generateUserPF().getDocument().getValue());
        var balance = new Balance();
        balance.setValue(BigDecimal.valueOf(15000.00));
        balance.setCurrency(Currency.REAL);
        account.setBalance(balance);
        var transacionalLimitDaily = new TransactionalLimit();
        transacionalLimitDaily.setValue(LIMIT_PF_VALUE);
        account.setTransactionalLimitDaily(transacionalLimitDaily);
        return account;
    }
    public static Account generateAccountPfDolar(){
        var account = new Account();
        account.setId("eeaf7e4f-e218-490e-92e6-775f21c8ee22");
        account.setOwnerId(generateUserPF().getDocument().getValue());
        var balance = new Balance();
        balance.setValue(BigDecimal.valueOf(3000.00));
        balance.setCurrency(Currency.DOLAR);
        account.setBalance(balance);
        var transacionalLimitDaily = new TransactionalLimit();
        transacionalLimitDaily.setValue(LIMIT_PF_VALUE);
        account.setTransactionalLimitDaily(transacionalLimitDaily);
        return account;
    }

    public static Account generateAccountPjReal(){
        var account = new Account();
        account.setId("eeaf7e4f-e218-490e-92e6-775f21c8ee21");
        account.setOwnerId(generateUserPJ().getDocument().getValue());
        var balance = new Balance();
        balance.setValue(BigDecimal.valueOf(150000.00));
        balance.setCurrency(Currency.REAL);
        account.setBalance(balance);
        var transacionalLimitDaily = new TransactionalLimit();
        transacionalLimitDaily.setValue(LIMIT_PJ_VALUE);
        account.setTransactionalLimitDaily(transacionalLimitDaily);
        return account;
    }

    public static Account generateAccountPjDolar(){
        var account = new Account();
        account.setId("eeaf7e4f-e218-490e-92e6-775f21c8ee20");
        account.setOwnerId(generateUserPJ().getDocument().getValue());
        var balance = new Balance();
        balance.setValue(BigDecimal.valueOf(30000.00));
        balance.setCurrency(Currency.DOLAR);
        account.setBalance(balance);
        var transacionalLimitDaily = new TransactionalLimit();
        transacionalLimitDaily.setValue(LIMIT_PJ_VALUE);
        account.setTransactionalLimitDaily(transacionalLimitDaily);
        return account;
    }

    public static User generateUserPF(){
        var user = new User();
        user.setName("User Test Pessoa Física");
        user.setEmail("user.test.pf@test.com.br");
        var document = new Document();
        document.setValue("65394627002");
        document.setDocumentType(DocumentType.CPF);
        user.setDocument(document);
        return user;
    }

    public static UserEntity generateUserEntityPF(){
        var userEntity = new UserEntity();
        userEntity.setName("User Test Pessoa Física");
        userEntity.setEmail("user.test.pf@test.com.br");
        userEntity.setDocument("65394627002");
        userEntity.setDocumentType(DocumentType.CPF.getCode());
        return userEntity;
    }

    public static User generateUserPJ(){
        var user = new User();
        user.setName("User Test Pessoa Jurídica");
        user.setEmail("user.test.pj@test.com.br");
        var document = new Document();
        document.setValue("34751101000116");
        document.setDocumentType(DocumentType.CNPJ);
        user.setDocument(document);
        return user;
    }

    public static AccountEntity generateAccountEntityFromGenerateAccountPfReal(){
        var account = UtilTest.generateAccountPfReal();
        var accountEntity = new AccountEntity();
        accountEntity.setId(account.getId());
        accountEntity.setOwnerId(account.getOwnerId());
        accountEntity.setLimitValue(account.getTransactionalLimitDaily().getValue().toPlainString());
        accountEntity.setLimitCurrency(account.getTransactionalLimitDaily().getCurrency().getCode());
        accountEntity.setBalanceValue(account.getBalance().getValue().toPlainString());
        accountEntity.setBalanceCurrency(account.getBalance().getCurrency().getCode());
        return accountEntity;
    }

    public static RemittanceEntity generateRemittanceEntityFromRemittancePfRealToPjDolar(){
        var remittance = generateRemittancePfRealToPjDolar();
        var remittanceEntity = new RemittanceEntity();
        remittanceEntity.setId(remittance.getId());
        remittanceEntity.setValue(remittance.getValue().toPlainString());
        remittanceEntity.setValueCurrency(remittance.getPayer().getBalance().getCurrency().getCode());
        remittanceEntity.setConvertedValueCurrency(remittance.getConvertedValue().toPlainString());
        remittanceEntity.setConvertedValueCurrency(remittance.getReceiver().getAccountCurrency().getCode());
        remittanceEntity.setExchangeRate(remittance.getExchangeRate().toPlainString());
        remittanceEntity.setExchangeRateDate(remittance.getExchangeRateDate().toString());

        remittanceEntity.setPayerName(remittance.getPayer().getUserName());
        remittanceEntity.setPayerAccountId(remittance.getPayer().getAccountId());
        remittanceEntity.setPayerDocument(remittance.getPayer().getDocument().getValue());
        remittanceEntity.setPayerDocumentType(remittance.getPayer().getDocument().getDocumentType().getCode());

        remittanceEntity.setReceiverName(remittance.getReceiver().getUserName());
        remittanceEntity.setReceiverAccountId(remittance.getReceiver().getAccountId());
        remittanceEntity.setReceiverDocument(remittance.getReceiver().getDocument().getValue());
        remittanceEntity.setReceiverDocumentType(remittance.getReceiver().getDocument().getDocumentType().getCode());

        remittanceEntity.setRemittanceCreateDate(ZonedDateTime.now().toString());
        remittanceEntity.setRemittanceStatus(RemittanceStatus.EFETIVADO.getCode());

        remittanceEntity.setConvertedValue(remittance.getConvertedValue().toPlainString());
        remittanceEntity.setConvertedValueCurrency(remittance.getReceiver().getAccountCurrency().getCode());
        return remittanceEntity;

    }

    public static PersonPayloadIn generatePayerPayloadIn(){
        var payer = new PersonPayloadIn();
        payer.setAccountId("8f4e644c-c460-4dd5-a200-f1b2550d7700");
        var payerDocument = new Document();
        payerDocument.setValue("00011122233");
        payerDocument.setDocumentType(DocumentType.CPF);
        payer.setDocument(payerDocument);
        return payer;
    }

    public static PersonPayloadIn generateReceiverPayloadIn(){
        var receiver = new PersonPayloadIn();
        receiver.setAccountId("8f4e644c-c460-4dd5-a200-f1b2550d7711");
        var receiverDocument = new Document();
        receiverDocument.setValue("00011122233");
        receiverDocument.setDocumentType(DocumentType.CPF);
        receiver.setDocument(receiverDocument);
        return receiver;
    }

    public static RemittancePayloadIn generateRemittancePayloadIn(){
        var remittancePayloadIn = new RemittancePayloadIn();
        var payer = UtilTest.generatePayerPayloadIn();
        var receiver = UtilTest.generateReceiverPayloadIn();
        remittancePayloadIn.setValue(new BigDecimal(100.00));
        remittancePayloadIn.setPayer(payer);
        remittancePayloadIn.setReceiver(receiver);
        return remittancePayloadIn;
    }
}
