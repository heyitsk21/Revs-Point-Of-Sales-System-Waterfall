import javax.swing.*;
import javax.swing.border.EtchedBorder;

import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;

import java.sql.*;
import java.io.*;


public class Employee extends JFrame {
    // Switchable layouts
    private JPanel menuPanel;
    private CardLayout menuCardLayout;
    private CardLayout orderCardLayout;
    private JPanel currentOrderPanel;
    private JPanel innerOrderPanel;
    //TODO: have a global list which keeps track of the menu item IDs that are "to-be-deleted" aka selected

    public Employee() {
        // Calls createAndShowGUI
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Employee GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create menuPanel and menuCardLayout
        menuCardLayout = new CardLayout();
        menuPanel = new JPanel(menuCardLayout);
        menuPanel.setBorder(new EtchedBorder());
        menuPanel.add(new ValMeals(), "ValMeals"); 
        menuPanel.add(new Burgers(), "Burgers"); 
        menuPanel.add(new Sandwiches(), "Sandwiches"); 
        menuPanel.add(new Baskets(), "Baskets"); 
        menuPanel.add(new Sides(), "Sides"); 
        menuPanel.add(new Drinks(), "Drinks"); 
        menuPanel.add(new Etc(), "Etc"); 
        menuPanel.add(new LimitedTime(), "Limited Time");
        menuPanel.add(new EmployeeSubmit(), "Employee Submit");
        menuPanel.add(new EmployeeDelete(), "Employee Delete");

        // MENU CATEGORIES

        // Create menu categories panel
        JPanel categoriesPanel = new JPanel();
        categoriesPanel.setBorder(new EtchedBorder());
        categoriesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // Create menu category buttons, also adds them to the panel
        JButton valMealBtn = createMenuCatButton("ValMeals", categoriesPanel);
        JButton burgerBtn = createMenuCatButton("Burgers", categoriesPanel);
        JButton sandwichBtn = createMenuCatButton("Sandwiches", categoriesPanel);
        JButton basketBtn = createMenuCatButton("Baskets", categoriesPanel);
        JButton sideBtn = createMenuCatButton("Sides", categoriesPanel);
        JButton drinkBtn = createMenuCatButton("Drinks", categoriesPanel);
        JButton etcBtn = createMenuCatButton("Etc", categoriesPanel);
        JButton limitedBtn = createMenuCatButton("Limited Time", categoriesPanel);
        // Add the categoriesPanel to the bottom of the frame
        frame.add(categoriesPanel, BorderLayout.SOUTH);

        // CURRENT ORDER

        // Create the panel to show what the order currently consists of
        orderCardLayout = new CardLayout();
        innerOrderPanel = new JPanel(orderCardLayout);
        currentOrderPanel = new JPanel();
        currentOrderPanel.setBorder(new EtchedBorder());
        currentOrderPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        innerOrderPanel.setBorder(new EtchedBorder());
        innerOrderPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // Create and add the delete and submit buttons to the currentOrderPanel
        JButton deleteBtn = createDeleteButton("DELETE", currentOrderPanel);
        JButton submitBtn = createSubmitButton("Submit", currentOrderPanel);
        innerOrderPanel.add(new EmployeeDelete(), "DELETE");
        innerOrderPanel.add(new EmployeeSubmit(), "Submit");
        // Add the currentOrderPanel to the right of the frame
        frame.add(currentOrderPanel, BorderLayout.EAST);
        currentOrderPanel.add(innerOrderPanel, BorderLayout.NORTH);

        // CARD LAYOUT

        // Add the card layout to the frame
        frame.add(menuPanel, BorderLayout.CENTER);
        // Set frame size
        frame.setSize(1280, 720);
        // Center the frame
        frame.setLocationRelativeTo(null);

        // DATE TIME USERNAME 

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

    private JButton createMenuCatButton(String panelName, JPanel panel) {
        JButton button = new JButton(panelName);
        button.setPreferredSize(new Dimension(180, 100));
        button.setFont(new Font("Arial", Font.PLAIN, 25));
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuCardLayout.show(menuPanel, panelName); // Switch to the specified panel
            }
        });
        return button;
    }

    private JButton createSubmitButton(String panelName, JPanel panel) {
        JButton button = new JButton(panelName);
        button.setPreferredSize(new Dimension(150, 70));
        button.setFont(new Font("Arial", Font.PLAIN, 25));
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderCardLayout.show(innerOrderPanel, panelName); // Switch to the specified panel
            }
        });
        return button;
    }

    private JButton createDeleteButton(String panelName, JPanel panel) {
        JButton button = new JButton(panelName);
        button.setPreferredSize(new Dimension(150, 70));
        button.setFont(new Font("Arial", Font.PLAIN, 25));
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderCardLayout.show(innerOrderPanel, panelName); // Switch to the specified panel
                //if the list of selected menu IDs is empty, then display error message
                //if not then remove the "selected button" from the current order
            }
        });
        return button;
    }
}
