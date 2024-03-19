package domain.services;

import domain.ports.outgoing.HttpClientPort;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.cert.X509CertificateHolder;
import application.exceptions.CertificateValidationException;
import application.exceptions.TrustChainRetrievalException;
import java.security.cert.CertificateException;
import java.util.List;

@RequiredArgsConstructor
public class CertificateValidationDomainService {

    private final CertificateValidator certificateValidator;
    private final TrustChainBuilder trustChainBuilder;

    public boolean validateCertificate(X509CertificateHolder certificateHolder) throws CertificateValidationException {
        try {
            return certificateValidator.validate(certificateHolder);
        } catch (Exception e) {
            throw new CertificateValidationException("Validation failed", e);
        }
    }

    public List<X509CertificateHolder> constructTrustChain(X509CertificateHolder certificateHolder) throws TrustChainRetrievalException {
        try {
            return trustChainBuilder.build(certificateHolder);
        } catch (Exception e) {
            throw new TrustChainRetrievalException("Unable to construct trust chain", e);
        }
    }
}
