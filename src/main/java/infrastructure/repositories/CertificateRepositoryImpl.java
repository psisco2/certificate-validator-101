package infrastructure.repositories;

import domain.entities.CertificateEntity;
import domain.repositories.CertificateRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public CertificateRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public CertificateEntity save(CertificateEntity certificate) {
        try {
            entityManager.persist(certificate);
            return certificate;
        } catch (Exception e) {
            log.error("Could not persist certificate", e);
            throw new PersistenceException("Could not persist certificate", e);
        }
    }

    @Override
    public Optional<CertificateEntity> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(CertificateEntity.class, id));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        try {
            CertificateEntity certificate = entityManager.find(CertificateEntity.class, id);
            if (certificate != null) {
                entityManager.remove(certificate);
            }
        } catch (Exception e) {
            log.error("Could not delete certificate with id: {}", id, e);
            throw new PersistenceException("Could not delete certificate", e);
        }
    }
}
