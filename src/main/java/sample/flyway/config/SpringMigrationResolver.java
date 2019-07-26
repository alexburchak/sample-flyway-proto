package sample.flyway.config;

import db.migration.SpringMigration;
import org.flywaydb.core.api.MigrationType;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.flywaydb.core.api.executor.Context;
import org.flywaydb.core.api.executor.MigrationExecutor;
import org.flywaydb.core.internal.resolver.AbstractJavaMigrationResolver;
import org.flywaydb.core.internal.scanner.Scanner;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import sample.flyway.service.MigrationService;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author Alexander Burchak
 */
class SpringMigrationResolver extends AbstractJavaMigrationResolver<SpringMigration, MigrationExecutor> {
    private AutowireCapableBeanFactory autowireCapableBeanFactory;
    private MigrationService migrationService;

    SpringMigrationResolver(ApplicationContext applicationContext, FluentConfiguration configuration) {
        super(
                new Scanner(Arrays.asList(configuration.getLocations()), configuration.getClassLoader(), configuration.getEncoding()),
                configuration
        );

        this.autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        this.migrationService = applicationContext.getBean(MigrationService.class);
    }

    @Override
    protected String getMigrationTypeStr() {
        return "JDBC";
    }

    @Override
    protected Class<SpringMigration> getImplementedInterface() {
        return SpringMigration.class;
    }

    @Override
    protected MigrationType getMigrationType() {
        return MigrationType.CUSTOM;
    }

    protected MigrationExecutor createExecutor(SpringMigration migration) {
        return new MigrationExecutor() {
            @Override
            public void execute(Context context) throws SQLException {
                try {
                    autowireCapableBeanFactory.autowireBean(migration);

                    migrationService.migrateTransactional(migration);
                } catch (Exception e) {
                    throw new SQLException("Migration failed !", e);
                }
            }

            @Override
            public boolean canExecuteInTransaction() {
                return true;
            }
        };
    }
}
