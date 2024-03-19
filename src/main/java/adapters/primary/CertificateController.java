package adapters.primary;

import application.dtos.CertificateDTO;
import application.use_cases.ValidateCertificateApplicationService;
import domain.entities.CertificateValidationResult;
import application.exceptions.CertificateValidationException;
import application.exceptions.TrustChainRetrievalException;
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
    public ResponseEntity<CertificateValidationResult> validateCertificate(@RequestBody CertificateDTO certificateDTO) {
        try {
            CertificateValidationResult validationResult = validateCertificateApplicationService.validateCertificate(certificateDTO);
            return ResponseEntity.ok(validationResult);
        } catch (CertificateValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Certificate validation error", e);
        } catch (TrustChainRetrievalException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Trust chain retrieval error", e);
        }
    }
}
