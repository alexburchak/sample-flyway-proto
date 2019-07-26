package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Alexander Burchak
 */
public class V3__Java extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        Connection connection = context.getConnection();

        try (Statement select = connection.createStatement()) {
            try (ResultSet rows = select.executeQuery("SELECT id FROM Person ORDER BY id")) {
                while (rows.next()) {
                    int id = rows.getInt(1);

                    String anonymizedName = "Anonymous" + id;
                    try (Statement update = connection.createStatement()) {
                        update.execute("UPDATE Person SET first_name='" + anonymizedName + "' WHERE id=" + id);
                    }
                }
            }
        }
    }
}
