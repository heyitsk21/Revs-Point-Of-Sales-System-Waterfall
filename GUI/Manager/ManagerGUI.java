import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
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
        cardPanel.add(new ManagerInventory(), "Trends");
        cardPanel.add(new ManagerInventory(), "Inventory");
        cardPanel.add(new ManagerInventory(), "Menu Items");
        cardPanel.add(new ManagerInventory(), "Order History");

        // Create buttons
        JButton trendsBtn = createButton("Trends");
        JButton inventoryBtn = createButton("Inventory");
        JButton menuBtn = createButton("Menu Items");
        JButton orderBtn = createButton("Order History");

        trendsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the Trends tab
                cardLayout.show(cardPanel, "Trends");
            }
        });
        inventoryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the Inventory tab
                cardLayout.show(cardPanel, "Inventory");
            }
        });
        menuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the Menu items tab
                cardLayout.show(cardPanel, "Menu Items");
            }
        });
        orderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the Order History tab
                cardLayout.show(cardPanel, "Order History");
            }
        });

        // Add buttons to a panel at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(trendsBtn);
        buttonPanel.add(inventoryBtn);
        buttonPanel.add(menuBtn);
        buttonPanel.add(orderBtn);
        // Add the buttonPanel to the bottom of the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);
        // Set frame size
        frame.setSize(1280, 720);
        // Center the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton createButton(String panelName) {
        JButton button = new JButton(panelName);
        button.setPreferredSize(new Dimension(300, 50));
        button.addActionListener(new ButtonClickListener(panelName));
        return button;
    }

    private class ButtonClickListener implements ActionListener {
        private final String panelName;

        public ButtonClickListener(String panelName) {
            this.panelName = panelName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, panelName);
        }
    }
}