package br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.repository;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.entity.AccountEntity;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AccountRepositoryDynamodbTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @InjectMocks
    private AccountRepositoryDynamodb accountRepositoryDynamodb;

    @Test
    void getByIdSuccess() {
        var account = UtilTest.generateAccountPfReal();
        var accountEntity = UtilTest.generateAccountEntityFromGenerateAccountPfReal();
        when(dynamoDBMapper.load(AccountEntity.class, account.getId())).thenReturn(accountEntity);
        var accountReturned = accountRepositoryDynamodb.getById(account.getId());
        assertEquals(account.getId(), accountReturned.getId());
        assertEquals(account.getOwnerId(), accountReturned.getOwnerId());
        assertEquals(account.getBalance().getValue(), accountReturned.getBalance().getValue());
        assertEquals(account.getBalance().getCurrency(), accountReturned.getBalance().getCurrency());
        assertEquals(account.getTransactionalLimitDaily().getValue(), accountReturned.getTransactionalLimitDaily().getValue());
        assertEquals(account.getTransactionalLimitDaily().getCurrency(), accountReturned.getTransactionalLimitDaily().getCurrency());
    }

}
