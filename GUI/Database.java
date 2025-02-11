import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * The `Database` class handles the connection to a PostgreSQL database and provides methods
 * to execute SQL queries and commands.
 * 
 * @author Team 21 Best Table Winners
 */
public class Database {

    Connection con;

    /**
     * Constructs a new `Database` object and establishes a connection to the database.
     */
    public Database() {
        con = connectToDatabase();
    }

    /**
     * Closes the database connection when the object is garbage collected.
     */
    protected void finalize() {
        closeConnection();
    }

    /**
     * Establishes a connection to the PostgreSQL database using credentials from a "login.txt" file.
     * 
     * @return The database connection.
     */
    private Connection connectToDatabase() {
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

        // Building the connection
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

    /**
     * Executes an SQL query or command and returns the result set for SELECT queries.
     * 
     * @param sql The SQL query or command to execute.
     * @return The result set for SELECT queries; null for non-SELECT queries.
     */
    public ResultSet executeSQL(String sql) {
        ResultSet result = null;

        try {
            Statement stmt = con.createStatement();
            if(sql.startsWith("SELECT")){
                result = stmt.executeQuery(sql);
            }
            else{
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage() + " SQL COMMAND FAILED TO EXACUTE: " + sql);
        }

        return result;
    }

    /**
     * Closes the database connection.
     */
    public void closeConnection() {
        try {
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
        }
    }

}