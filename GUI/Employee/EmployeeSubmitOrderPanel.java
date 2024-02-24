import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.awt.*;

// Panel for submitting orders
public class EmployeeSubmitOrderPanel extends JPanel {
    public EmployeeSubmitOrderPanel() {
        setLayout(new BorderLayout());
        // Add components for submitting orders
        // Example: JLabels, JTextFields, JButtons, etc.
        JLabel label = new JLabel("Submit Order Panel");
        add(label, BorderLayout.CENTER);
    }
}