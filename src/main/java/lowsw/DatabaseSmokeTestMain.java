package lowsw;

import lowsw.persistence.Db;
import lowsw.persistence.DbConfig;

import java.sql.Connection;

// With the use of AI
public class DatabaseSmokeTestMain {
    public static void main(String[] args) throws Exception {
        Db db = new Db(new DbConfig());
        try (Connection c = db.getConnection()) {
            System.out.println("DB connection OK: " + c.getMetaData().getURL());
        }
    }
}
