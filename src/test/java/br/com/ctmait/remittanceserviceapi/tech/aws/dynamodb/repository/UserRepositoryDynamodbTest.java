package br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.repository;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.entity.UserEntity;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserRepositoryDynamodbTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @InjectMocks
    private UserRepositoryDynamodb userRepositoryDynamodb;

    @Test
    void getByDocumentSuccess() {
        var user = UtilTest.generateUserPF();
        var userEntity = UtilTest.generateUserEntityPF();
        when(dynamoDBMapper.load(UserEntity.class, user.getDocument().getValue())).thenReturn(userEntity);
        var userReturned = userRepositoryDynamodb.getByDocument(user.getDocument().getValue());
        assertEquals(userReturned.getName(), user.getName());
        assertEquals(userReturned.getEmail(), user.getEmail());
        assertNotNull(userReturned.getDocument());
        assertEquals(userReturned.getDocument().getValue(), user.getDocument().getValue());
        assertEquals(userReturned.getDocument().getDocumentType(), user.getDocument().getDocumentType());
    }

}
