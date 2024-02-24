import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Database {

    public Database(){}
    
    public Connection connectToDatabase(){
        // Read credentials from login.txt
        String database_user = "";
        String database_password = "";
        try (BufferedReader br = new BufferedReader(new FileReader("login.txt"))) {
            database_user = br.readLine();
            database_password = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        //Building the connection
        Connection conn = null;
        String database_name = "csce315_902_01_db";
        String database_url = String.format("jdbc:postgresql://csce-315-db.engr.tamu.edu/%s", database_name);
        try {
            conn = DriverManager.getConnection(database_url, database_user, database_password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return conn; 
    }

    public ResultSet executeSQL(Connection conn, String sql){
            ResultSet result = null;

            try {
                Statement stmt = conn.createStatement();
                String sqlStatement = sql;
                result = stmt.executeQuery(sqlStatement);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error accessing Database.");
            }

            return result;
    }

    public void closeConnection(Connection conn){
        try {
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
        }
    }
        
}
