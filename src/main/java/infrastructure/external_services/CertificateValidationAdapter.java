package infrastructure.external_services;

import domain.entities.CertificateEntity;
import domain.ports.CertificateValidationPort;
import domain.validation.ValidationResult;
import java.util.List;
import java.util.stream.Collectors;
import infrastructure.external_services.AIAExtensionServiceClient;
import application.exceptions.CertificateValidationException;
import application.exceptions.TrustChainRetrievalException;
import application.exceptions.TrustChainEncodingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificateValidationAdapter implements CertificateValidationPort {

    private final AIAExtensionServiceClient aiaExtensionServiceClient;

    @Override
    public ValidationResult validateCertificate(CertificateEntity certificateEntity) throws CertificateValidationException {
        if (certificateEntity == null) {
            throw new CertificateValidationException("Certificate entity cannot be null.");
        }
        try {
            boolean valid = certificateEntity.validate();
            String message = valid ? "Certificate is valid." : certificateEntity.getValidationErrorMessage();
            return new ValidationResult(valid, message);
        } catch (Exception e) {
            throw new CertificateValidationException("Validation failed", e);
        }
    }

    @Override
    public List<byte[]> retrieveTrustChain(CertificateEntity certificateEntity) throws TrustChainRetrievalException {
        if (certificateEntity == null) {
            throw new TrustChainRetrievalException("Certificate entity cannot be null.");
        }
        try {
            List<CertificateEntity> trustChainEntities = certificateEntity.getTrustChain();
            return trustChainEntities.stream()
                    .map(certificate -> {
                        try {
                            return certificate.toX509CertificateHolder().getEncoded();
                        } catch (Exception e) {
                            throw new TrustChainEncodingException("Failed to encode certificate.", e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (TrustChainEncodingException e) {
            throw e;
        } catch (Exception e) {
            throw new TrustChainRetrievalException("Retrieval of trust chain failed", e);
        }
    }

    // TODO: Implement logic for interacting with the AIAExtensionServiceClient to fetch parent certificates.
    // TODO: Implement error handling and retry logic for network-related issues when interacting with AIAExtensionServiceClient.
    // TODO: Add repository interface or domain service to handle persistence operations such as saving the updated CertificateEntity.
    // TODO: Define CertificateEntityRepository interface with necessary methods like save for persisting CertificateEntity changes.

    // TODO: Define TrustChainEncodingException class in the application.exceptions package.
}

// TODO: Extract the following class to its own file in the application.exceptions package.
class TrustChainEncodingException extends RuntimeException {
    public TrustChainEncodingException(String message, Throwable cause) {
        super(message, cause);
    }
}
