package application.dtos;

import java.time.LocalDate;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import application.serializers.CustomLocalDateSerializer;
import application.serializers.CustomLocalDateDeserializer;

@Getter
@Setter
@ToString
public class CertificateDTO {

    private String id;
    private String subject;
    private String issuer;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate validFrom;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate validUntil;

    private String publicKey;
    private String signature;
    private String serialNumber;
}