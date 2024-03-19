package domain.entities;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateValidationResult {

    private boolean validationStatus;
    private List<String> trustChain;
}
