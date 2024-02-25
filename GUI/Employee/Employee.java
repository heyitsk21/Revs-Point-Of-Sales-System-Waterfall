import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;


public class Employee extends JFrame {
    // Switchable layouts
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public Employee() {
        // Calls createAndShowGUI
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Employee GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create cardPanel and cardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        //cardPanel.add(new EmployeeSubmitOrderPanel(), "SubmitOrder"); not sure if I'll need this

        // Create buttons
        JButton valMealBtn = createButton("ValMeals");
        valMealBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton burgerBtn = createButton("Burgers");
        burgerBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton sandwichBtn = createButton("Sandwiches");
        sandwichBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton basketBtn = createButton("Baskets");
        basketBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton sideBtn = createButton("Sides");
        sideBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton drinkBtn = createButton("Drinks");
        drinkBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton etcBtn = createButton("Etc");
        etcBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton limitedBtn = createButton("Limited Time");
        limitedBtn.setFont(new Font("Arial", Font.PLAIN, 25));

        // Add buttons to a panel at the bottom
        JPanel categoriesPanel = new JPanel();
        categoriesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        categoriesPanel.add(valMealBtn);
        categoriesPanel.add(burgerBtn);
        categoriesPanel.add(sandwichBtn);
        categoriesPanel.add(basketBtn);
        categoriesPanel.add(sideBtn);
        categoriesPanel.add(drinkBtn);
        categoriesPanel.add(etcBtn);
        categoriesPanel.add(limitedBtn);

        // Add the categoriesPanel to the bottom of the frame
        frame.add(categoriesPanel, BorderLayout.SOUTH);

        //TODO: make it so that the current selected category page shows up in a panel on the left
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        frame.add(itemsPanel, BorderLayout.WEST);
        //then swap out itemsPanel for one of the menuCategory panels? or maybe an itemsPanel isn't needed

        // Add panel on the right that shows final order
        JButton deleteBtn = createButton("DELETE");
        deleteBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton submitBtn = createButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.PLAIN, 25));

        JPanel currentOrderPanel = new JPanel();
        currentOrderPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        currentOrderPanel.add(deleteBtn);

        // Add the currentOrderPanel to the right of the frame
        frame.add(currentOrderPanel, BorderLayout.EAST);

        // Add the card layout to the frame
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
                //if submit was pressed, have the employee type in the order name and then either back or submit
                /* similar to manager inventory entries
                 * headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
                 * headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
                 * 
                 * userInputField.setHorizontalAlignment(SwingConstants.CENTER);
                 * userInputField.setFont(new Font("Arial", Font.PLAIN, 25));
                 * userInputField.setText("");
                 * 
                 * submitButton.setText("Submit");
                 * submitButton.setHorizontalAlignment(SwingConstants.CENTER);
                 * submitButton.setFont(new Font("Arial", Font.PLAIN, 25));
                 * submitButton.addActionListener(new SubmitButtonListener());
                 * 
                 * backButton.setText("Back");
                 * backButton.setHorizontalAlignment(SwingConstants.CENTER);
                 * backButton.setFont(new Font("Arial", Font.PLAIN, 25));
                 * backButton.addActionListener(new BackButtonListener());
                 * 
                 * submitPanel.add(headerLabel);
                 * submitPanel.add(userInputField);
                 * submitPanel.add(submitButton);
                 * submitPanel.add(backButton);
                 * 
                 * GridBagConstraints gbc = new GridBagConstraints();
                 * gbc.fill = GridBagConstraints.BOTH;  // Fill both horizontally and vertically
                 * gbc.weightx = 0.25;
                 * gbc.weighty = 1.0;
                 * gbc.gridx = 1;
                 * gbc.gridy = 0;
                 * add(rightPanel, gbc);
                 * 
                 * submit button listener would update the database by adding the order and decrementing inventory accordingly
                 * back button listener would close the keyboard
                 */
                //if delete was pressed and no "button" aka order item is currently "selected" then display error message
                //     if not then remove the "selected button" from the current order
                cardLayout.show(cardPanel, panelName); // Switch to the specified panel
            }
        });
        return button;
    }
}
