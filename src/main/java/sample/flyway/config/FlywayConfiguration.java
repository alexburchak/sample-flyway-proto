package sample.flyway.config;

import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Alexander Burchak
 */
@Configuration
public class FlywayConfiguration {
    @Bean
    @Primary
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return f -> {
        };
    }

    @Bean
    @Primary
    public FlywayConfigurationCustomizer flywayConfigurationCustomizer(ApplicationContext applicationContext) {
        return c -> c.resolvers(new SpringMigrationResolver(applicationContext, c));
    }
}
