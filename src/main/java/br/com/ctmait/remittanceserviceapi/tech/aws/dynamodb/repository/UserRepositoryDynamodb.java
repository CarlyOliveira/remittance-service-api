package br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.repository;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceNotFoundException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.UserNotFoundException;
import br.com.ctmait.remittanceserviceapi.domain.models.user.Document;
import br.com.ctmait.remittanceserviceapi.domain.models.user.DocumentType;
import br.com.ctmait.remittanceserviceapi.domain.models.user.User;
import br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.entity.UserEntity;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.UserRepository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserRepositoryDynamodb implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryDynamodb.class);

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public User getByDocument(String document){
        log.info("URD-GBD-00 get user by document {}", document);
        try {
            Objects.requireNonNull(document, "document cannot null");
            var userEntity = this.get(document);
            var user = this.convert(userEntity);
            log.info("URD-GBD-01 get return user {}", user);
            return user;
        }catch (Exception exception){
            log.error("URD-GBD-02 error {} get user by document {} ", exception, document);
            throw exception;
        }
    }

    private UserEntity get(String document){
        try {
            Objects.requireNonNull(document, "document cannot null");
            var userEntity = dynamoDBMapper.load(UserEntity.class, document);
            return Optional.ofNullable(userEntity)
                    .orElseThrow(() -> new UserNotFoundException("document value " + document + " not found"));
        }catch (Exception e){
            throw e;
        }
    }

    private User convert (UserEntity userEntity){
        var user = new User();
        user.setName(userEntity.getName());
        user.setEmail(userEntity.getEmail());
        var document = new Document();
        document.setValue(userEntity.getDocument());
        document.setDocumentType(DocumentType.getByCode(userEntity.getDocumentType()));
        user.setDocument(document);
        return user;
    }
}
