package lowsw.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// With the use of AI
public class Db {
    private final DbConfig cfg;

    public Db(DbConfig cfg) { this.cfg = cfg; }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(cfg.url(), cfg.user(), cfg.password());
    }
}
