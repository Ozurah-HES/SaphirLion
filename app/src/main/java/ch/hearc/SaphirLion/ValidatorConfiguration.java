package ch.hearc.SaphirLion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.hearc.SaphirLion.validator.BelongValidator;

@Configuration
public class ValidatorConfiguration {
  @Bean
  public BelongValidator belongValidator() {
      return new BelongValidator();
  }
}
