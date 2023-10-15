package br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.repository;

import br.com.ctmait.remittanceserviceapi.domain.models.account.Account;
import br.com.ctmait.remittanceserviceapi.domain.models.account.Balance;
import br.com.ctmait.remittanceserviceapi.domain.models.account.Currency;
import br.com.ctmait.remittanceserviceapi.domain.models.account.TransactionalLimit;
import br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.entity.AccountEntity;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.AccountRepository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Objects;


@Repository
@RequiredArgsConstructor
public class AccountRepositoryDynamodb implements AccountRepository {

    private static final Logger log = LoggerFactory.getLogger(AccountRepositoryDynamodb.class);

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public Account getById(String accountId){
        log.info("ARD-GBI-00 get account by id {}", accountId);
        try {
            Objects.requireNonNull(accountId, "accountId cannot null");
            var accountEntity = this.get(accountId);
            var account = this.convert(accountEntity);
            log.info("ARD-GBI-01 get return account {}", account);
            return account;
        }catch (Exception exception){
            log.error("ARD-GBI-02 error {} get account by id {} ", exception, accountId);
            throw exception;
        }
    }

    @Override
    public void updateBalance(String accountId, BigDecimal balanceValue){
        log.info("ARD-UB-00 update accountId {} balanceValue {}", accountId, balanceValue);
        try {
            Objects.requireNonNull(accountId, "accountId cannot null");
            Objects.requireNonNull(balanceValue, "balanceValue cannot null");
            var accountEntity = this.get(accountId);
            accountEntity.setBalanceValue(balanceValue.toPlainString());
            DynamoDBTableMapper<AccountEntity, String, ?> dynamoDBTableMapper = dynamoDBMapper.newTableMapper(AccountEntity.class);
            dynamoDBTableMapper.saveIfExists(accountEntity);
            log.info("ARD-UB-01 updated accountEntity {} ", accountEntity);
        }catch (Exception exception){
            log.error("ARD-UB-02 error {} updating accountId {} balanceValue {}", exception, accountId, balanceValue);
            throw exception;
        }
    }

    @Override
    public void updateLimit(String accountId, BigDecimal limitValue) {
        log.info("ARD-UL-00 update accountId {} limitValue {}", accountId, limitValue);
        try {
            Objects.requireNonNull(accountId, "accountId cannot null");
            Objects.requireNonNull(limitValue, "limitValue cannot null");
            var accountEntity = this.get(accountId);
            accountEntity.setLimitValue(limitValue.toPlainString());
            DynamoDBTableMapper<AccountEntity, String, ?> dynamoDBTableMapper = dynamoDBMapper.newTableMapper(AccountEntity.class);
            dynamoDBTableMapper.saveIfExists(accountEntity);
            log.info("ARD-UL-01 updated accountEntity {} ", accountEntity);
        }catch (Exception exception){
            log.error("ARD-UL-02 error {} updating accountId {} limitValue {}", exception, accountId, limitValue);
            throw exception;
        }
    }

    private AccountEntity get(String accountId){
        try {
            Objects.requireNonNull(accountId, "accountId cannot null");
            var accountEntity = dynamoDBMapper.load(AccountEntity.class, accountId);
            return accountEntity;
        }catch (Exception e){
            throw e;
        }
    }

    private Account convert (AccountEntity accountEntity){
        var account = new Account();
        account.setId(accountEntity.getId());
        account.setOwnerId(account.getOwnerId());
        var balance = new Balance();
        balance.setValue(new BigDecimal(accountEntity.getBalanceValue()));
        balance.setCurrency(Currency.getByCode(accountEntity.getBalanceCurrency()));
        account.setBalance(balance);
        var limit = new TransactionalLimit();
        limit.setValue(new BigDecimal(accountEntity.getLimitValue()));
        account.setTransactionalLimitDaily(limit);
        return account;
    }
}
