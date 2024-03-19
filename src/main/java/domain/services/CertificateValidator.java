package domain.services;

import org.bouncycastle.cert.X509CertificateHolder;
import application.exceptions.CertificateValidationException;

public interface CertificateValidator {
    boolean validate(X509CertificateHolder certificateHolder) throws CertificateValidationException;
}