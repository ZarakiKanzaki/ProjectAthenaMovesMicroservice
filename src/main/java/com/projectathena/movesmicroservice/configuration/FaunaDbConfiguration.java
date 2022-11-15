package com.projectathena.movesmicroservice.configuration;

import com.faunadb.client.FaunaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.MalformedURLException;

@Configuration
public class FaunaDbConfiguration {
    @Autowired
    private FaunaClientProperties faunaProperties;

    @Bean
    @Scope("request")
    public FaunaClient faunaClient() throws  MalformedURLException {

        return FaunaClient.builder()
                .withEndpoint(faunaProperties.getEndpoint())
                .withSecret(faunaProperties.getSecret())
                .build();
    }
}
