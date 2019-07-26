package sample.flyway.service;

import db.migration.SpringMigration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Alexander Burchak
 */
@Component
public class MigrationService {
    @Transactional
    public void migrateTransactional(SpringMigration migration) {
        migration.migrate();
    }
}
