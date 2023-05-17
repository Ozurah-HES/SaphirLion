package ch.hearc.SaphirLion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.hearc.SaphirLion.validator.BelongConnectedValidator;
import ch.hearc.SaphirLion.validator.BelongUserValidator;

@Configuration
public class ValidatorConfiguration {
    @Bean
    public BelongConnectedValidator belongValidator() {
        return new BelongConnectedValidator();
    }

    @Bean
    public BelongUserValidator belongUserValidator() {
        return new BelongUserValidator();
    }
}
