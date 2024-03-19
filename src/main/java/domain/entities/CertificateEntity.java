package domain.entities;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import application.exceptions.CertificateValidationException;
import application.exceptions.TrustChainRetrievalException;
import domain.services.CertificateValidationDomainService;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import java.io.StringReader;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

@Getter
@Setter
public class CertificateEntity {

    private UUID id;
    private String certificateData;
    private String authorityKeyIdentifier;
    private String issuer;
    private String subject;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private PublicKey publicKey;
    private byte[] signature;
    private boolean isRevoked;
    private LocalDateTime created;
    private String serialNumber;
    private long version;
    private LocalDateTime lastUpdated;

    private final CertificateValidationDomainService certificateValidationService;

    public CertificateEntity(CertificateValidationDomainService certificateValidationService) {
        this.certificateValidationService = certificateValidationService;
    }

    public boolean validate() throws CertificateValidationException {
        return certificateValidationService.validateCertificate(this.toX509CertificateHolder());
    }

    public List<CertificateEntity> getTrustChain() throws TrustChainRetrievalException {
        return certificateValidationService.constructTrustChain(this.toX509CertificateHolder());
    }

    public void revoke() {
        this.isRevoked = true;
        // TODO: Implement logic to persist the revocation status change if necessary
    }

    public X509CertificateHolder toX509CertificateHolder() throws CertificateEncodingException {
        try (PEMParser pemParser = new PEMParser(new StringReader(this.certificateData))) {
            Object parsedObj = pemParser.readObject();
            if (parsedObj instanceof X509Certificate) {
                X509Certificate certificate = (X509Certificate) parsedObj;
                return new X509CertificateHolder(certificate.getEncoded());
            } else if (parsedObj instanceof SubjectPublicKeyInfo) {
                return new X509CertificateHolder(new JcaPEMKeyConverter().getPublicKey((SubjectPublicKeyInfo) parsedObj).getEncoded());
            }
            throw new IllegalArgumentException("Unable to parse certificate data.");
        } catch (Exception e) {
            throw new CertificateEncodingException("Failed to convert CertificateEntity to X509CertificateHolder", e);
        }
    }

    // TODO: Add repository interface or domain service to handle persistence operations such as saving the updated CertificateEntity.

    // TODO: Define CertificateEntityRepository interface with necessary methods like save for persisting CertificateEntity changes.
}