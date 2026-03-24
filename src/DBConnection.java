import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {  
	
	

    public static Connection getConnection() {
    	
    	    try {
    	        Properties props = new Properties();

    	        InputStream input = DBConnection.class
    	                .getClassLoader()
    	                .getResourceAsStream("config.properties");

    	        if (input == null) {
    	            System.out.println("config.properties NOT FOUND ❌");
    	            return null;
    	        }

    	        props.load(input);

    	        String url = props.getProperty("db.url");
    	        String user = props.getProperty("db.user");
    	        String pass = props.getProperty("db.password");

    	        return DriverManager.getConnection(url, user, pass);

    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        return null;
    	    }
    	}