package application.exceptions;

import lombok.Getter;

@Getter
public class CertificateValidationException extends Exception {

    private final String errorMessage;

    public CertificateValidationException(String message) {
        super(message);
        this.errorMessage = message;
    }
}