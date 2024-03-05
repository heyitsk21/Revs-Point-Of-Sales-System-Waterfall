
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * ManagerOrderHistory class represents a JPanel for managing order history in a restaurant system.
 * It includes features for displaying order details, such as order IDs, customer names, prices,
 * and timestamps. Users can click on specific orders to view detailed information on the right panel.
 */
public class ManagerOrderHistory extends JPanel {
    String[] orderIDs;
    String[] customerNames;
    float[] taxPrices;
    float[] basePrices;
    String[] orderTimes;
    int[] employeeIDs;
    int numberOfItems;
    int currOrderIndex = 0;

    JPanel rightPanel = new JPanel();
    JPanel leftPanel = new JPanel();

    managerCmds manCmds;

    /**
     * Constructs a ManagerOrderHistory object.
     * Initializes the necessary components, retrieves order information from the database,
     * and sets up the layout with left and right panels.
     */
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
        if (numberOfItems > 100){
            numberOfItems = 100;
        }
        setLayout(new GridBagLayout());
        createLeft();
        createRight();
    }

    /**
     * Refreshes the GUI by updating both the left and right panels.
     */
    private void RefreshGUI(){
        updateRight();
        updateLeft();
    }

    JScrollPane scrollPane;
    Font buttonFont = new Font("Arial", Font.PLAIN, 17);

    /**
     * Creates the left panel, which contains a list of buttons representing each order.
     * Each button displays order information, and clicking on it updates the right panel.
     */
    void createLeft() {
        leftPanel.setLayout(new GridLayout(numberOfItems, 1)); // Vertical layout
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        scrollPane = new JScrollPane(leftPanel); // Instantiate scrollPane

        for (int i = 0; i < numberOfItems; i++) {
            JButton button = new JButton("Order " + orderIDs[i] + " Placed " + orderTimes[i]);
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
    JLabel totalPriceLabel = new JLabel();

    /**
     * Creates the right panel, which displays detailed information about the selected order.
     * Information includes order ID, customer name, subtotal, tax, total price, date/time, and employee ID.
     */
    void createRight() {
        rightPanel.setLayout(new GridLayout(7, 1));
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        orderIDLabel.setText("ID: " + orderIDs[currOrderIndex]);
        orderIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        orderIDLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(orderIDLabel);

        customerNameLabel.setText("Customer: " + customerNames[currOrderIndex]);
        customerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        customerNameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(customerNameLabel);

        basePriceLabel.setText("Subtotal: " + basePrices[currOrderIndex]);
        basePriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        basePriceLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(basePriceLabel);

        taxPriceLabel.setText("Tax: " + taxPrices[currOrderIndex]);
        taxPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        taxPriceLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(taxPriceLabel);

        float totalPrice = taxPrices[currOrderIndex] + basePrices[currOrderIndex];

        totalPriceLabel.setText("Total Price: " + String.format("%.2f", totalPrice));
        totalPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalPriceLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(totalPriceLabel);

        orderTimeLabel.setText("Date/Time: " + orderTimes[currOrderIndex]);
        orderTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        orderTimeLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(orderTimeLabel);

        employeeIDLabel.setText("Employee: " + employeeIDs[currOrderIndex]);
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

    /**
     * Updates the right panel with the information of the currently selected order.
     */
    void updateRight() {
        // Displays the name of the ingredient
        orderIDLabel.setText("ID: " + orderIDs[currOrderIndex]);
        customerNameLabel.setText("Customer: " + customerNames[currOrderIndex]);
        taxPriceLabel.setText("Tax: " + taxPrices[currOrderIndex]);
        basePriceLabel.setText("Subtotal: " + basePrices[currOrderIndex]);
        float totalPrice = taxPrices[currOrderIndex] + basePrices[currOrderIndex];
        totalPriceLabel.setText("Total Price: " + String.format("%.2f", totalPrice));
        orderTimeLabel.setText("Date/Time: " + orderTimes[currOrderIndex]);
        employeeIDLabel.setText("Employee: " + employeeIDs[currOrderIndex]);
    }

    /**
     * Updates the left panel (not implemented in the current code).
     * This method is kept empty since the left panel does not dynamically change its content.
     */
    void updateLeft() {
        return;
    }

    /**
     * ActionListener implementation for handling button clicks in the left panel.
     * When a button representing an order is clicked, the corresponding order details are displayed.
     */
    private class ButtonClickListener implements ActionListener {
        private String buttonName;

        public ButtonClickListener(String buttonName) {
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Perform actions when the button is clicked
            currOrderIndex = Integer.parseInt(buttonName);
            RefreshGUI();
        }
    }
}
