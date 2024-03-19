package application.use_cases;

import domain.entities.CertificateValidationResult;
import domain.services.CertificateValidationDomainService;
import application.dtos.CertificateDTO;
import application.exceptions.CertificateValidationException;
import application.exceptions.TrustChainRetrievalException;
import domain.ports.CertificateValidationPort;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.cert.X509CertificateHolder;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ValidateCertificateApplicationService {

    private final CertificateValidationDomainService certificateValidationDomainService;
    private final CertificateValidationPort certificateValidationPort;

    public CertificateValidationResult validateCertificate(CertificateDTO certificateDTO) throws CertificateValidationException, TrustChainRetrievalException {
        X509CertificateHolder certificateHolder = certificateDTO.toX509CertificateHolder();
        boolean validationResult = certificateValidationDomainService.validateCertificate(certificateHolder);

        if (!validationResult) {
            throw new CertificateValidationException("Certificate validation failed.");
        }

        List<X509CertificateHolder> trustChain = certificateValidationDomainService.constructTrustChain(certificateHolder);
        List<String> trustChainAsBase64 = trustChain.stream()
                .map(certificate -> Base64.getEncoder().encodeToString(certificate.getEncoded()))
                .collect(Collectors.toList());

        CertificateValidationResult result = new CertificateValidationResult();
        result.setValidationStatus(validationResult);
        result.setTrustChain(trustChainAsBase64);

        return result;
    }
}
