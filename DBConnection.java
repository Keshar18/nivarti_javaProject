import java.sql.*;

public class DBConnection {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/nivarti",
                "root",
                "root"   // password 
            );

            return con;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}