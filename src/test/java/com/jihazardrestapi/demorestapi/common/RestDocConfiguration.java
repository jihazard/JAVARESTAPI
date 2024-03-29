package com.jihazardrestapi.demorestapi.common;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@TestConfiguration
public class RestDocConfiguration {
    @Bean
    public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer(){
        return configurer -> configurer.operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint());
    }
}
