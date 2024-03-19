package domain.entities;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateValidationResult {

    private boolean validationStatus;
    private List<String> trustChain;

    public boolean getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(boolean validationStatus) {
        this.validationStatus = validationStatus;
    }

    public List<String> getTrustChain() {
        return trustChain;
    }

    public void setTrustChain(List<String> trustChain) {
        this.trustChain = trustChain;
    }
}
