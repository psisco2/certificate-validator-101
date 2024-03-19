package domain.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidationResult {

    private final boolean valid;
    private final String errorMessage;

}