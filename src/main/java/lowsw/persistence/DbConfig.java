package lowsw.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// With the use of AI
public class DbConfig {
    private final Properties props = new Properties();

    public DbConfig() {
        try (InputStream in = DbConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) throw new IllegalStateException("db.properties not found");
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String url() { return props.getProperty("db.url"); }
    public String user() { return props.getProperty("db.user"); }
    public String password() { return props.getProperty("db.password"); }
}
