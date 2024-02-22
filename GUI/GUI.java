import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class GUI extends JFrame implements ActionListener {
    static JFrame f;

    public static void main(String[] args) {
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
        JOptionPane.showMessageDialog(null, "Opened database successfully");

        String name = "";
        String price = "";
        try {
            //create a statement object
            Statement stmt = conn.createStatement();
            //create a SQL statement
            String sqlStatement = "SELECT itemname, price FROM menuitems;";
            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                // TODO you probably need to change the column name tat you are retrieving
                //      this command gets the data from the "name" attribute
                name += result.getString("itemName") + " ";
                name += result.getString("price") + "\n";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error accessing Database.");
        }
        // create a new frame
        f = new JFrame("DB GUI");

        // create a object
        GUI s = new GUI();

        // create a panel
        JPanel p = new JPanel();

        JButton b = new JButton("Close");

        // add actionlistener to button
        b.addActionListener(s);

        // Create a JTextArea object using the queried data
        JTextArea textArea = new JTextArea(name + "\n" + price);

        // Add the new object to the JPanel p
        p.add(textArea);

        // add button to panel
        p.add(b);

        // add panel to frame
        f.add(p);

        // set the size of frame
        f.setSize(400, 400);

        f.setVisible(true);

        //closing the connection
        try {
            conn.close();
            JOptionPane.showMessageDialog(null, "Connection Closed.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
        }
    }

    // if button is pressed
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Close")) {
            f.dispose();
        }
    }
}
