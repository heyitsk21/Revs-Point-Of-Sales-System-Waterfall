import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.awt.*;

public class ManagerOrderHistory extends JPanel {
    String[] orderIDs;
    String[] customerNames;
    float[] taxPrices;
    float[] basePrices;
    String[] orderTimes;
    int[] employeeIDs;
    int numberOfItems; // = myInventory.length();
    int currOrderIndex = 0;

    JPanel rightPanel = new JPanel();
    JPanel leftPanel = new JPanel();

    managerCmds manCmds;

    public ManagerOrderHistory() {
        this.manCmds = new managerCmds();
        sqlObjects.OrderList orderList = manCmds.getOrders();
        this.orderIDs = orderList.orderIDs;
        this.customerNames = orderList.customerNames;
        this.taxPrices = orderList.taxPrices;
        this.basePrices = orderList.basePrices;
        this.orderTimes = orderList.orderTimes;
        this.employeeIDs = orderList.employeeIDs;
        this.numberOfItems = orderIDs.length;
        setLayout(new GridBagLayout());
        createLeft();
        createRight();
    }

    private void RefreshGUI(){
        sqlObjects.OrderList orderList = manCmds.getOrders();
        this.orderIDs = orderList.orderIDs;
        this.customerNames = orderList.customerNames;
        this.taxPrices = orderList.taxPrices;
        this.basePrices = orderList.basePrices;
        this.orderTimes = orderList.orderTimes;
        this.employeeIDs = orderList.employeeIDs;
        this.numberOfItems = orderIDs.length;
        updateRight();
        updateLeft();
    }

    JScrollPane scrollPane;
    Font buttonFont = new Font("Arial", Font.PLAIN, 17);

    void createLeft() {
        leftPanel.setLayout(new GridLayout(numberOfItems, 1)); // Vertical layout
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        scrollPane = new JScrollPane(leftPanel); // Instantiate scrollPane

        for (int i = 0; i < numberOfItems; i++) {
            JButton button = new JButton("Order " + orderIDs[currOrderIndex] + " Placed " + orderTimes[currOrderIndex]);
            button.addActionListener(new ButtonClickListener(String.valueOf(i)));
            button.setFont(buttonFont);
            leftPanel.add(button);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        gbc.weightx = 0.75; // 75% of the horizontal space for the left component
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(scrollPane, gbc); // Add scrollPane to the frame
    }

    JLabel orderIDLabel = new JLabel();
    JLabel customerNameLabel = new JLabel();
    JLabel taxPriceLabel = new JLabel();
    JLabel basePriceLabel = new JLabel();
    JLabel orderTimeLabel = new JLabel();
    JLabel employeeIDLabel = new JLabel();

    void createRight() {
        rightPanel.setLayout(new GridLayout(6, 1));
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Displays the name of the ingredient
        orderIDLabel.setText("Name: " + orderIDs[currOrderIndex]);
        orderIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        orderIDLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(orderIDLabel);

        // Displays the count of the ingredient remaining
        customerNameLabel.setText("Name: " + customerNames[currOrderIndex]);
        customerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        customerNameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(customerNameLabel);

        taxPriceLabel.setText("Name: " + taxPrices[currOrderIndex]);
        taxPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        taxPriceLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(taxPriceLabel);

        basePriceLabel.setText("Name: " + basePrices[currOrderIndex]);
        basePriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        basePriceLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(basePriceLabel);

        orderTimeLabel.setText("Name: " + orderTimes[currOrderIndex]);
        orderTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        orderTimeLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(orderTimeLabel);

        employeeIDLabel.setText("Name: " + employeeIDs[currOrderIndex]);
        employeeIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        employeeIDLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(employeeIDLabel);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(rightPanel, gbc);
    }

    void updateRight() {
        System.out.println("updateRight() called");
    }

    void updateLeft() {
        System.out.println("updateLeft() called");
    }

    private class ButtonClickListener implements ActionListener {
        private String buttonName;

        public ButtonClickListener(String buttonName) {
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Perform actions when the button is clicked
            System.out.println("Ingredient clicked: " + buttonName);
            currOrderIndex = Integer.parseInt(buttonName);
            RefreshGUI();
        }
    }
}
