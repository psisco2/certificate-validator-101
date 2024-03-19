package domain.services;

import org.bouncycastle.cert.X509CertificateHolder;
import application.exceptions.TrustChainRetrievalException;
import java.util.List;

public interface TrustChainBuilder {
    List<X509CertificateHolder> build(X509CertificateHolder certificateHolder) throws TrustChainRetrievalException;
}