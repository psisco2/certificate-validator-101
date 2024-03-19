package infrastructure.external_services;

import lombok.AllArgsConstructor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@AllArgsConstructor
public class AIAExtensionServiceClient implements IAIAExtensionServiceClient {

    private final HttpClient httpClient;

    @Override
    public X509Certificate fetchParentCertificate(String aiaExtensionUrl) throws IOException, CertificateException {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(aiaExtensionUrl))
                    .GET()
                    .build();
        } catch (Exception e) {
            throw new IOException("Invalid URL format", e);
        }

        HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        return (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(response.body()));
    }
}

// IAIAExtensionServiceClient.java
interface IAIAExtensionServiceClient {
    X509Certificate fetchParentCertificate(String aiaExtensionUrl) throws IOException, CertificateException;
}
