package br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository;

import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;

public interface RemittanceRepository {
    Remittance getById(String remittanceId);
    void insert(Remittance remittance);
}
