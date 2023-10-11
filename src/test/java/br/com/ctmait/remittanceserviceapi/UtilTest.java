package br.com.ctmait.remittanceserviceapi;

import br.com.ctmait.remittanceserviceapi.domain.models.account.Account;
import br.com.ctmait.remittanceserviceapi.domain.models.account.Balance;
import br.com.ctmait.remittanceserviceapi.domain.models.account.Currency;
import br.com.ctmait.remittanceserviceapi.domain.models.account.TransactionalLimit;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Person;
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
        remittance.setPayer(generatePersonPfAccountReal());
        remittance.setReceiver(generatePersonPjAccountDolar());
        return remittance;
    }
    public static Remittance generateRemittancePjDolarToPfReal(){
        var remittance = new Remittance();
        remittance.setId(UUID.randomUUID().toString());
        remittance.setValue(REMITTANCE_VALUE);
        remittance.setPayer(generatePersonPjAccountDolar());
        remittance.setReceiver(generatePersonPfAccountReal());
        return remittance;
    }
    public static Remittance generateRemittancePjDolarToPjReal(){
        var remittance = new Remittance();
        remittance.setId(UUID.randomUUID().toString());
        remittance.setValue(REMITTANCE_VALUE);
        remittance.setPayer(generatePersonPjAccountDolar());
        remittance.setReceiver(generatePersonPjAccountReal());
        return remittance;
    }

    public static Person generatePersonPfAccountDolar(){
        var person = new Person();
        person.setAccountId(generateAccountPfDolar().getId());
        person.setUserId(generateUserPF().getId());
        person.setDocument(generateUserPF().getDocument());
        person.setUserName(generateUserPF().getName());
        person.setBalance(generateAccountPfDolar().getBalance());
        return person;
    }
    public static Person generatePersonPjAccountDolar(){
        var person = new Person();
        person.setAccountId(generateAccountPjDolar().getId());
        person.setUserId(generateUserPJ().getId());
        person.setDocument(generateUserPJ().getDocument());
        person.setUserName(generateUserPJ().getName());
        person.setBalance(generateAccountPjDolar().getBalance());
        return person;
    }
    public static Person generatePersonPfAccountReal(){
        var person = new Person();
        person.setAccountId(generateAccountPfReal().getId());
        person.setUserId(generateUserPF().getId());
        person.setDocument(generateUserPF().getDocument());
        person.setUserName(generateUserPF().getName());
        person.setBalance(generateAccountPfReal().getBalance());
        return person;
    }
    public static Person generatePersonPjAccountReal(){
        var person = new Person();
        person.setAccountId(generateAccountPjReal().getId());
        person.setUserId(generateUserPJ().getId());
        person.setDocument(generateUserPJ().getDocument());
        person.setUserName(generateUserPJ().getName());
        person.setBalance(generateAccountPjReal().getBalance());
        return person;
    }

    public static Account generateAccountPfReal(){
        var account = new Account();
        account.setId("eeaf7e4f-e218-490e-92e6-775f21c8ee23");
        account.setOwnerId(generateUserPF().getId());
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
        account.setOwnerId(generateUserPF().getId());
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
        account.setOwnerId(generateUserPJ().getId());
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
        account.setOwnerId(generateUserPJ().getId());
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
        user.setId("eeaf7e4f-e218-490e-92e6-775f21c8ee18");
        user.setName("User Test Pessoa Física");
        user.setEmail("user.test.pf@test.com.br");
        user.setPassword("123456");
        var document = new Document();
        document.setValue("65394627002");
        document.setDocumentType(DocumentType.CPF);
        user.setDocument(document);
        return user;
    }

    public static User generateUserPJ(){
        var user = new User();
        user.setId("eeaf7e4f-e218-490e-92e6-775f21c8ee17");
        user.setName("User Test Pessoa Jurídica");
        user.setEmail("user.test.pj@test.com.br");
        user.setPassword("654321");
        var document = new Document();
        document.setValue("34751101000116");
        document.setDocumentType(DocumentType.CNPJ);
        user.setDocument(document);
        return user;
    }
}
