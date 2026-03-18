package lowsw.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbConfig {
    private final Properties properties = new Properties();
    public DbConfig() {
        try (InputStream input = DbConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String url() { return properties.getProperty("db.url", "jdbc:mysql://localhost:3306/lowsw"); }
    public String user() { return properties.getProperty("db.user", "root"); }
    public String password() { return properties.getProperty("db.password", "CHANGE_ME"); }
}
