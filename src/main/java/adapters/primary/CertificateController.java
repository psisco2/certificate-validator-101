package adapters.primary;

import application.dtos.CertificateDTO;
import application.use_cases.ValidateCertificateApplicationService;
import domain.entities.CertificateValidationResult;
import domain.exceptions.CertificateValidationException;
import domain.exceptions.TrustChainRetrievalException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CertificateController {

    private final ValidateCertificateApplicationService validateCertificateApplicationService;

    @PostMapping("/validate-certificate")
    @ApiOperation(value = "Validate a certificate", notes = "Validates the provided certificate and returns the validation results.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Certificate validated successfully"),
            @ApiResponse(code = 400, message = "Certificate validation error"),
            @ApiResponse(code = 500, message = "Trust chain retrieval error")
    })
    public ResponseEntity<CertificateDTO> validateCertificate(@RequestBody CertificateDTO certificateDTO) {
        try {
            CertificateValidationResult validationResult = validateCertificateApplicationService.validateCertificate(certificateDTO);
            CertificateDTO resultDTO = validateCertificateApplicationService.convertToDTO(validationResult);
            return ResponseEntity.ok(resultDTO);
        } catch (CertificateValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Certificate validation error", e);
        } catch (TrustChainRetrievalException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Trust chain retrieval error", e);
        }
    }
}

// TODO: Extract the following exceptions to their respective files in the package 'domain.exceptions'

package domain.exceptions;

public class CertificateValidationException extends Exception {
    public CertificateValidationException(String message) {
        super(message);
    }
}

package domain.exceptions;

public class TrustChainRetrievalException extends Exception {
    public TrustChainRetrievalException(String message) {
        super(message);
    }
}

// TODO: Implement the method 'convertToDTO' in ValidateCertificateApplicationService that converts CertificateValidationResult to CertificateDTO
