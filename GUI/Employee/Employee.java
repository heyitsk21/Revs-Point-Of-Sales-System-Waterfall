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
        cardPanel.setBorder(new EtchedBorder());
        cardPanel.add(new ValMeals(), "ValMeals"); 
        cardPanel.add(new Burgers(), "Burgers"); 
        cardPanel.add(new Sandwiches(), "Sandwiches"); 
        cardPanel.add(new Baskets(), "Baskets"); 
        cardPanel.add(new Sides(), "Sides"); 
        cardPanel.add(new Drinks(), "Drinks"); 
        cardPanel.add(new Etc(), "Etc"); 
        cardPanel.add(new LimitedTime(), "Limited Time"); 

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

        // Add panel on the right that shows final order
        JButton deleteBtn = createButton("DELETE");
        deleteBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton submitBtn = createButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.PLAIN, 25));

        JPanel currentOrderPanel = new JPanel();
        currentOrderPanel.setBorder(new EtchedBorder());
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

    private JButton createMenuCatButton(String panelName, JPanel panel) {
        JButton button = new JButton(panelName);
        button.setPreferredSize(new Dimension(180, 100));
        button.setFont(new Font("Arial", Font.PLAIN, 25));
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, panelName); // Switch to the specified panel
            }
        });
        return button;
    }

    private JButton createButton(String panelName) {
        JButton button = new JButton(panelName);
        button.setMinimumSize(new Dimension(150, 100));
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
