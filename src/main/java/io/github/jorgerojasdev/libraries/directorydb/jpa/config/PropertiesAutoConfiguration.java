package io.github.jorgerojasdev.libraries.directorydb.jpa.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@AutoConfigureBefore
@PropertySource("classpath:directorydb.properties")
public class PropertiesAutoConfiguration {
}
