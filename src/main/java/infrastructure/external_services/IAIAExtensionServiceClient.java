package infrastructure.external_services;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public interface IAIAExtensionServiceClient {
    X509Certificate fetchParentCertificate(String aiaExtensionUrl) throws IOException, CertificateException;
}