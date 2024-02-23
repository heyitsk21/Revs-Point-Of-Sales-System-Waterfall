import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.awt.Font;



public class LogInScreen extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel passwordLabel, usernameLabel, posMessage;
    private JButton loginButton;
    
    

    public LogInScreen() {

        //create frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");
        setSize(800, 600);

        setLocationRelativeTo(null);
        setLayout(null);

        //username and password labels and formatting
        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(200, 200, 100, 30);
        usernameLabel.setFont(new Font("Calibri", Font.BOLD, 20));

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(200, 250, 100, 30);
        passwordLabel.setFont(new Font("Calibri", Font.BOLD, 20));

        //Rev's POS System header
        posMessage = new JLabel("Rev's POS System Login Page");
        posMessage.setBounds(250, 130, 300, 30);
        posMessage.setFont(new Font("Calibri", Font.BOLD, 25));

        //username and password entry fields
        usernameField = new JTextField();
        usernameField.setBounds(300, 200, 300, 30);

        passwordField = new JPasswordField();
        passwordField.setBounds(300, 250, 300, 30);

        //login button
        loginButton = new JButton("Login");
        loginButton.setBounds(300, 300, 100, 30);

        loginButton.addActionListener(this);

        add(usernameField);
        add(usernameLabel);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(posMessage);
    }

    public boolean authenticate(String username, String password) {
        return username.equals("placeholder") && password.equals("placeholder");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Check if the entered credentials are valid
        if (authenticate(username,password)) {
            JOptionPane.showMessageDialog(this, "Login successful");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*public static void main(String[] args) {
        //connectToDatabase();
        Database db = new Database();
        Connection conn = db.connectToDatabase();
        ResultSet result = db.executeSQL(conn, "SELECT * from employee;");

        //TODO:  
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }*/ 
}
