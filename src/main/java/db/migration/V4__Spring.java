package db.migration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Alexander Burchak
 */
public class V4__Spring implements SpringMigration {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void migrate() {
        entityManager.createQuery("UPDATE Person SET first_name=replace(first_name, 'Anonymous', 'Nobody') WHERE first_name like 'Anonymous%'").executeUpdate();
    }
}
