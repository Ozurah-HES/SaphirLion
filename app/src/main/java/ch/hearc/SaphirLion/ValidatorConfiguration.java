package ch.hearc.SaphirLion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.hearc.SaphirLion.validator.BelongConnectedValidator;

@Configuration
public class ValidatorConfiguration {
    @Bean
    public BelongConnectedValidator belongValidator() {
        return new BelongConnectedValidator();
    }
}
