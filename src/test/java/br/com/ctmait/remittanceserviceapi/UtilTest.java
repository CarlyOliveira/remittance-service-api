package br.com.ctmait.remittanceserviceapi;

import br.com.ctmait.remittanceserviceapi.domain.models.account.Account;
import br.com.ctmait.remittanceserviceapi.domain.models.account.Balance;
import br.com.ctmait.remittanceserviceapi.domain.models.account.Currency;
import br.com.ctmait.remittanceserviceapi.domain.models.account.TransactionalLimit;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Payer;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Receiver;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.domain.models.user.Document;
import br.com.ctmait.remittanceserviceapi.domain.models.user.DocumentType;
import br.com.ctmait.remittanceserviceapi.domain.models.user.User;

import java.math.BigDecimal;
import java.util.UUID;

public class UtilTest {

    private static final BigDecimal LIMIT_PF_VALUE = BigDecimal.valueOf(10000.00);
    private static final BigDecimal LIMIT_PJ_VALUE = BigDecimal.valueOf(50000.00);
    private static final BigDecimal REMITTANCE_VALUE = BigDecimal.valueOf(100.00);



    public static Remittance generateRemittancePfRealToPjDolar(){
        var remittance = new Remittance();
        remittance.setId(UUID.randomUUID().toString());
        remittance.setValue(REMITTANCE_VALUE);
        remittance.setPayer(generatePayerPfAccountReal());
        remittance.setReceiver(generateReceiverPjAccountDolar());
        return remittance;
    }
    public static Remittance generateRemittancePjDolarToPfReal(){
        var remittance = new Remittance();
        remittance.setId(UUID.randomUUID().toString());
        remittance.setValue(REMITTANCE_VALUE);
        remittance.setPayer(generatePayerPjAccountDolar());
        remittance.setReceiver(generateReceiverPfAccountReal());
        return remittance;
    }
    public static Remittance generateRemittancePjDolarToPjReal(){
        var remittance = new Remittance();
        remittance.setId(UUID.randomUUID().toString());
        remittance.setValue(REMITTANCE_VALUE);
        remittance.setPayer(generatePayerPjAccountDolar());
        remittance.setReceiver(generateReceiverPjAccountReal());
        return remittance;
    }

    public static Payer generatePayerPfAccountDolar(){
        var payer = new Payer();
        payer.setAccountId(generateAccountPfDolar().getId());
        payer.setUserId(generateUserPF().getDocument().getValue());
        payer.setDocument(generateUserPF().getDocument());
        payer.setUserName(generateUserPF().getName());
        payer.setBalance(generateAccountPfDolar().getBalance());
        return payer;
    }
    public static Payer generatePayerPjAccountDolar(){
        var payer = new Payer();
        payer.setAccountId(generateAccountPjDolar().getId());
        payer.setUserId(generateUserPJ().getDocument().getValue());
        payer.setDocument(generateUserPJ().getDocument());
        payer.setUserName(generateUserPJ().getName());
        payer.setBalance(generateAccountPjDolar().getBalance());
        return payer;
    }
    public static Receiver generateReceiverPjAccountDolar(){
        var receiver = new Receiver();
        receiver.setAccountId(generateAccountPjDolar().getId());
        receiver.setUserId(generateUserPJ().getDocument().getValue());
        receiver.setDocument(generateUserPJ().getDocument());
        receiver.setUserName(generateUserPJ().getName());
        return receiver;
    }
    public static Payer generatePayerPfAccountReal(){
        var payer = new Payer();
        payer.setAccountId(generateAccountPfReal().getId());
        payer.setUserId(generateUserPF().getDocument().getValue());
        payer.setDocument(generateUserPF().getDocument());
        payer.setUserName(generateUserPF().getName());
        payer.setBalance(generateAccountPfReal().getBalance());
        return payer;
    }
    public static Receiver generateReceiverPfAccountReal(){
        var receiver = new Receiver();
        receiver.setAccountId(generateAccountPfReal().getId());
        receiver.setUserId(generateUserPF().getDocument().getValue());
        receiver.setDocument(generateUserPF().getDocument());
        receiver.setUserName(generateUserPF().getName());
        return receiver;
    }
    public static Payer generatePayerPjAccountReal(){
        var payer = new Payer();
        payer.setAccountId(generateAccountPjReal().getId());
        payer.setUserId(generateUserPJ().getDocument().getValue());
        payer.setDocument(generateUserPJ().getDocument());
        payer.setUserName(generateUserPJ().getName());
        payer.setBalance(generateAccountPjReal().getBalance());
        return payer;
    }
    public static Receiver generateReceiverPjAccountReal(){
        var payer = new Receiver();
        payer.setAccountId(generateAccountPjReal().getId());
        payer.setUserId(generateUserPJ().getDocument().getValue());
        payer.setDocument(generateUserPJ().getDocument());
        payer.setUserName(generateUserPJ().getName());
        return payer;
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
}
