import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.io.*;
import java.awt.*;

public class ManagerGUI extends JFrame {
    // Switchable layouts
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public ManagerGUI() {
        // Calls managerGUI
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Manager GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create cardPanel and cardLayout
        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(new ManagerTrends(), "Trends");
        cardPanel.add(new ManagerInventory(), "Inventory");
        cardPanel.add(new ManagerMenuItems(), "Menu Items");
        cardPanel.add(new ManagerOrderHistory(), "Order History");

        // Create buttons
        JButton trendsBtn = createButton("Trends");
        trendsBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton inventoryBtn = createButton("Inventory");
        inventoryBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton menuBtn = createButton("Menu Items");
        menuBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton orderBtn = createButton("Order History");
        orderBtn.setFont(new Font("Arial", Font.PLAIN, 25));

        // Add buttons to a panel at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(trendsBtn);
        buttonPanel.add(inventoryBtn);
        buttonPanel.add(menuBtn);
        buttonPanel.add(orderBtn);
        // Add the buttonPanel to the bottom of the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);
        //Add the card layout to the frame
        frame.add(cardPanel, BorderLayout.CENTER);
        // Set frame size
        frame.setSize(1280, 720);
        // Center the frame
        frame.setLocationRelativeTo(null);
        

        // Adds a panel at the top
        JPanel topPanel = new JPanel();

        JLabel dateTimeLabel = new JLabel();
        java.util.Date date = new java.util.Date();
        String dateTimeString = "Date/Time: " + date.toString();
        dateTimeLabel.setText(dateTimeString);


        JLabel usernameLabel = new JLabel("Username: YOUR_USERNAME");

        topPanel.add(dateTimeLabel);
        topPanel.add(usernameLabel);
        topPanel.setBorder(new EtchedBorder());

        frame.add(topPanel, BorderLayout.NORTH);

        frame.setVisible(true);
    }


    private JButton createButton(String panelName) {
        JButton button = new JButton(panelName);
        button.setPreferredSize(new Dimension(300, 50));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, panelName); // Switch to the specified panel
            }
        });
        return button;
    }
}