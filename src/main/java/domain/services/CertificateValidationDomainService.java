package domain.services;

import domain.ports.outgoing.HttpClientPort;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.cert.X509CertificateHolder;
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

    // Custom exceptions
    public static class CertificateValidationException extends CertificateException {
        public CertificateValidationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class TrustChainRetrievalException extends CertificateException {
        public TrustChainRetrievalException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

// Interfaces
package domain.services;

import org.bouncycastle.cert.X509CertificateHolder;
import java.util.List;

public interface CertificateValidator {
    boolean validate(X509CertificateHolder certificateHolder) throws CertificateValidationException;
}

package domain.services;

import org.bouncycastle.cert.X509CertificateHolder;
import java.util.List;

public interface TrustChainBuilder {
    List<X509CertificateHolder> build(X509CertificateHolder certificateHolder) throws TrustChainRetrievalException;
}

// TODO: Extract the following interface to its respective package and class name
// package domain.ports.outgoing;
// public interface HttpClientPort {
//     HttpResponse sendRequest(HttpUriRequest request) throws IOException;
// }
