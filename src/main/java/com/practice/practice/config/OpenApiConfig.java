package com.practice.practice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customConfig() {
    return new OpenAPI()
        .info(new Info()
            .title("REST APIs For Testing")
            .version("1.0"));
  }
}
