package br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository;

import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;

public interface RemittanceRepository {
    void load(Remittance remittance);
    void insert(Remittance remittance);
}
