package domain.ports;

import domain.entities.CertificateEntity;
import domain.validation.ValidationResult;
import java.util.List;

/**
 * Defines the domain's expectations for external certificate validation,
 * serving as a contract for adapters to implement.
 */
public interface CertificateValidationPort {

    /**
     * Validates the provided certificate entity using cryptographic operations,
     * specifically leveraging the BouncyCastle library. Returns a ValidationResult
     * containing both the boolean result and the error message in case of failure.
     *
     * @param certificate the certificate entity to validate
     * @return ValidationResult containing the result and error message
     */
    ValidationResult validateCertificate(CertificateEntity certificate);

    /**
     * Retrieves the trust chain for the provided certificate entity, with each element
     * represented as a DER-formatted byte array. The trust chain elements are returned
     * in order from the root to the target certificate.
     *
     * @param certificate the certificate entity for which to retrieve the trust chain
     * @return List of byte arrays representing the trust chain in DER format
     */
    List<byte[]> retrieveTrustChain(CertificateEntity certificate);
}