package com.projectathena.movesmicroservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "fauna-db")
public class FaunaClientProperties {
    private String endpoint;
    @Value("${fauna-db.secret}")
    private String secret;
}
