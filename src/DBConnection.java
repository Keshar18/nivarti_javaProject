import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class DBConnection {
	

    public static Connection getConnection() {
    	System.out.println("🚀 Trying DB connection...");
    	
        Connection con = null;

        try {
            System.out.println("🔄 Trying DB connection...");

            Properties props = new Properties();

            InputStream input = DBConnection.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");

            if (input == null) {
                System.out.println("❌ config.properties NOT FOUND");
                return null;
            }

            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            // 🔥 DRIVER LOAD (important)
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, user, pass);

            if (con != null) {
                System.out.println("✅ DB CONNECTED SUCCESSFULLY");
            } else {
                System.out.println("❌ DB CONNECTION FAILED");
            }

        } catch (Exception e) {
            System.out.println("❌ DB ERROR:");
            e.printStackTrace();
        }

        return con;
    }
}