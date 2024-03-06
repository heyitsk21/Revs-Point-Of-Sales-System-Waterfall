import javax.swing.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Font;

/**
 * The `LogInGUI` class represents the login interface for the POS system.
 * It provides fields for entering a username and password, and a login button to authenticate users.
 * The class interacts with the `Database` class to verify user credentials.
 * 
 * @author Team 21 Best Table Winners
 */
public class LogInGUI extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel passwordLabel, usernameLabel, posMessage;
    private JButton loginButton;
    private Database db = new Database();

    public LogInGUI() {

        // create frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");
        setSize(800, 600);

        setLocationRelativeTo(null);
        setLayout(null);

        // username and password labels and formatting
        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(200, 200, 100, 30);
        usernameLabel.setFont(new Font("Calibri", Font.BOLD, 20));

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(200, 250, 100, 30);
        passwordLabel.setFont(new Font("Calibri", Font.BOLD, 20));

        // Rev's POS System header
        posMessage = new JLabel("Rev's POS System Login Page");
        posMessage.setBounds(250, 130, 400, 30);
        posMessage.setFont(new Font("Calibri", Font.BOLD, 25));

        // username and password entry fields
        usernameField = new JTextField();
        usernameField.setBounds(300, 200, 300, 30);

        passwordField = new JPasswordField();
        passwordField.setBounds(300, 250, 300, 30);

        // login button
        loginButton = new JButton("Login");
        loginButton.setBounds(300, 300, 100, 30);

        loginButton.addActionListener(this);

        add(usernameField);
        add(usernameLabel);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(posMessage);

        this.getRootPane().setDefaultButton(loginButton);
    }

    /**
     * Authenticates the user by checking the entered credentials against the database.
     * 
     * @param username The entered username.
     * @param password The entered password.
     * @param isManager A flag indicating whether the user is a manager.
     * @return true if authentication is successful, false otherwise.
     */
    private boolean authenticate(String username, String password, boolean isManager) {
        String query = "SELECT employeename, password, ismanager FROM employee WHERE employeename = ? AND password = ?;";
        try {
            PreparedStatement prep = db.con.prepareStatement(query);
            prep.setString(1, username);
            prep.setString(2, password);
            ResultSet result = prep.executeQuery();
            if (result.next()) {
                return isManager == result.getBoolean("ismanager");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Handles the action when the login button is pressed.
     * Authenticates the user and switches to the appropriate screen based on the user's role.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Check if the entered credentials are valid
        if (authenticate(username, password, true)) {
            ManagerGUI manager = new ManagerGUI();
            manager.setVisible(true);
            setVisible(false);
            // When Manager Screen java is created, switch to that screen instead of this message
            //JOptionPane.showMessageDialog(this, "Switch to Manager Screen");
            // change to ManagerGUI.java

        } else if (authenticate(username, password, false)) {
            Employee employeeGUI = new Employee();
            employeeGUI.setVisible(true);
            setVisible(false);
            // When Employee Screen java is created, switch to that screen instead of this message
            //JOptionPane.showMessageDialog(this, "Switch to Employee Screen");
        }
        else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}