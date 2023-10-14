package br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository;

import br.com.ctmait.remittanceserviceapi.domain.models.user.User;


public interface UserRepository {
    User getByDocument(String document);
}
