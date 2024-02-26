import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;


public class Employee extends JFrame {
    // Switchable layouts
    private JPanel cardPanel;
    private CardLayout cardLayout;
    //TODO: have a global list which keeps track of the menu item IDs that are "to-be-deleted" aka selected

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
        /*
        cardPanel.add(new ValMeals(), "ValMeals"); 
        cardPanel.add(new Burgers(), "Burgers"); 
        cardPanel.add(new Sandwiches(), "Sandwiches"); 
        cardPanel.add(new Baskets(), "Baskets"); 
        cardPanel.add(new Sides(), "Sides"); 
        cardPanel.add(new Drinks(), "Drinks"); 
        cardPanel.add(new Etc(), "Etc"); 
        cardPanel.add(new LimitedTime(), "Limited Time"); 
        */
        cardPanel.add(new EmployeeSubmit(), "Employee Submit");
        cardPanel.add(new EmployeeDelete(), "Employee Delete");

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
        JPanel currentOrderPanel = new JPanel();
        currentOrderPanel.setBorder(new EtchedBorder());
        currentOrderPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // Create and add the delete and submit buttons to the currentOrderPanel
        JButton deleteBtn = createDeleteButton("DELETE");
        JButton submitBtn = createSubmitButton("Submit");
        currentOrderPanel.add(deleteBtn);
        currentOrderPanel.add(submitBtn);
        // Add the currentOrderPanel to the right of the frame
        frame.add(currentOrderPanel, BorderLayout.EAST);

        // CARD LAYOUT

        // Add the card layout to the frame
        frame.add(cardPanel, BorderLayout.CENTER);
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
                cardLayout.show(cardPanel, panelName); // Switch to the specified panel
            }
        });
        return button;
    }

    private JButton createSubmitButton(String panelName) {
        JButton button = new JButton(panelName);
        button.setPreferredSize(new Dimension(150, 70));
        button.setFont(new Font("Arial", Font.PLAIN, 25));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, panelName); // Switch to the specified panel
            }
        });
        return button;
    }

    private JButton createDeleteButton(String panelName) {
        JButton button = new JButton(panelName);
        button.setPreferredSize(new Dimension(150, 70));
        button.setFont(new Font("Arial", Font.PLAIN, 25));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, panelName); // Switch to the specified panel
                //if the list of selected menu IDs is empty, then display error message
                //if not then remove the "selected button" from the current order
            }
        });
        return button;
    }
}
