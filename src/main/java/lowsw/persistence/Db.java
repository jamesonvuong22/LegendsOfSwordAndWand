package lowsw.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private static final Db INSTANCE = new Db();
    private Db() { }
    public static Db getInstance() { return INSTANCE; }
    public Connection getConnection(DbConfig config) throws SQLException {
        return DriverManager.getConnection(config.url(), config.user(), config.password());
    }
}
