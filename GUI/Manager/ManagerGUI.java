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
    String currentCardName;

    public ManagerGUI() {
        // Calls managerGUI
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Manager GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create cardPanel and cardLayout
        System.out.println("Panels starting");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(new ManagerTrends(), "Trends");
        cardPanel.add(new ManagerInventory(), "Inventory");
        cardPanel.add(new ManagerMenuItems(), "Menu Items");
        cardPanel.add(new ManagerOrderHistory(), "Order History");

        System.out.println("Panels made");

        // Create buttons
        JButton trendsBtn = createButton("Trends");
        trendsBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton inventoryBtn = createButton("Inventory");
        inventoryBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton menuBtn = createButton("Menu Items");
        menuBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton orderBtn = createButton("Order History");
        orderBtn.setFont(new Font("Arial", Font.PLAIN, 25));

        System.out.println("Building bottom panel");

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
        
        System.out.println("Building top panel");

        // Adds a panel at the top
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));

        JLabel dateTimeLabel = new JLabel();
        dateTimeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        updateDateTime(dateTimeLabel);

        JLabel usernameLabel = new JLabel("Username: YOUR_USERNAME");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 15));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Rebuild the current card in the card panel
                rebuildCurrentCard(cardPanel);
            }
        });
        topPanel.add(refreshButton);

        topPanel.add(dateTimeLabel);
        topPanel.add(usernameLabel);
        topPanel.setBorder(new EtchedBorder());

        frame.add(topPanel, BorderLayout.NORTH);

        // Use a Timer to update the dateTimeLabel every second
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDateTime(dateTimeLabel);
            }
        });
        timer.start();

        System.out.println("Making visible");
        frame.setVisible(true);
    }


    private JButton createButton(String panelName) {
        JButton button = new JButton(panelName);
        button.setPreferredSize(new Dimension(300, 50));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, panelName); // Switch to the specified panel
                currentCardName = panelName;
            }
        });
        return button;
    }

    private void updateDateTime(JLabel dateTimeLabel) {
        java.util.Date date = new java.util.Date();
        String dateTimeString = "Date/Time: " + date.toString();
        dateTimeLabel.setText(dateTimeString);
    }

    private void rebuildCurrentCard(Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            container.remove(component);
        }

        cardPanel.invalidate();
        cardPanel.add(new ManagerTrends(), "Trends");
        cardPanel.add(new ManagerInventory(), "Inventory");
        cardPanel.add(new ManagerMenuItems(), "Menu Items");
        cardPanel.add(new ManagerOrderHistory(), "Order History");
        cardPanel.validate();
        cardPanel.repaint();
        cardLayout.show(cardPanel, currentCardName);
    }
}