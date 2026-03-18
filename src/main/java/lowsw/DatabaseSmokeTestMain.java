package lowsw;

import lowsw.persistence.Db;
import lowsw.persistence.DbConfig;

public class DatabaseSmokeTestMain {
    public static void main(String[] args) {
        Db db = Db.getInstance();
        DbConfig config = new DbConfig();
        System.out.println("DB singleton ready for URL: " + config.url());
        System.out.println("DB instance: " + db.getClass().getSimpleName());
    }
}
