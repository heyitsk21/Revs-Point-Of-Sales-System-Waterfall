import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;

public class ManagerOrderHistory extends JPanel {
    public ManagerOrderHistory() {
        JLabel placeholderLabel = new JLabel("This is order history");
        placeholderLabel.setHorizontalAlignment(JLabel.CENTER);
        placeholderLabel.setVerticalAlignment(JLabel.CENTER);

        // Set some styles for better visibility
        placeholderLabel.setForeground(Color.GRAY);

        // Add the placeholder label to the panel
        add(placeholderLabel, BorderLayout.CENTER);
    }
}
