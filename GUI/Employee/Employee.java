import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Employee extends JFrame {
    public static List<Integer> selectedMenuIDs;
    public static List<Integer> toBeDeleted;
    public static JPanel innerOrderPanel;
    public static JPanel pricePanel;
    public static Float currentPrice;
    public static employeeCmds empCmds;

    private JPanel menuPanel;
    private CardLayout cardLayout;
    private JPanel orderPanel;
    private JPanel submitAndDeletePanel;
    //private List<Integer> toBeDeleted;
    //private List<Integer> selectedMenuIDs; // list to keep track of selected menu items



    public Employee() {
        empCmds = new employeeCmds();
        createAndShowGUI();
        selectedMenuIDs = new ArrayList<>();
        toBeDeleted = new ArrayList<>();
        currentPrice = 0.0f;

    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Employee GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        orderPanel = new JPanel();
        innerOrderPanel = new JPanel();
        submitAndDeletePanel = new JPanel();
        pricePanel = new JPanel();

        // Create menuPanel and cardLayout
        cardLayout = new CardLayout();
        menuPanel = new JPanel(cardLayout);
        menuPanel.setBorder(new EtchedBorder());
        menuPanel.add(new MenuSection("ValMeals", 600, 699), "ValMeals");
        menuPanel.add(new MenuSection("Burgers", 100, 199), "Burgers");
        menuPanel.add(new MenuSection("Sandwiches", 200, 299), "Sandwiches");
        menuPanel.add(new MenuSection("DrinksAndFries", 500, 599), "DrinksAndFries");
        menuPanel.add(new MenuSection("Salads", 300, 399), "Salads");
        menuPanel.add(new MenuSection("IceCream", 400, 499), "IceCream");
        menuPanel.add(new MenuSection("LimitedTime", 700, 799), "LimitedTime");

        // MENU CATEGORIES

        // Create menu categories panel
        JPanel categoriesPanel = new JPanel();
        categoriesPanel.setBorder(new EtchedBorder());
        //categoriesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        categoriesPanel.setLayout(new GridLayout(1, 0));
        submitAndDeletePanel.setMaximumSize(new Dimension(100, 80));
        // Create menu category buttons, also adds them to the panel
        createMenuCatButton("ValMeals", categoriesPanel);
        createMenuCatButton("Burgers", categoriesPanel);
        createMenuCatButton("Sandwiches", categoriesPanel);
        createMenuCatButton("DrinksAndFries", categoriesPanel);
        createMenuCatButton("Salads", categoriesPanel);
        createMenuCatButton("IceCream", categoriesPanel);
        createMenuCatButton("LimitedTime", categoriesPanel);
        // Add the categoriesPanel to the bottom of the frame
        frame.add(categoriesPanel, BorderLayout.SOUTH);

        // CURRENT ORDER

        // Create the panel to show what the order currently consists of
        orderPanel.setBorder(new EtchedBorder());
        orderPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        innerOrderPanel.setBorder(new EtchedBorder());
        innerOrderPanel.setLayout(new GridLayout(0, 1));
        submitAndDeletePanel.setBorder(new EtchedBorder());
        submitAndDeletePanel.setLayout(new GridLayout(0, 2));
        // Create and add the delete and submit buttons to the orderPanel
        createDeleteButton("DELETE", submitAndDeletePanel);
        createSubmitButton("Submit", submitAndDeletePanel);
        // Add the orderPanel to the right of the frame
        frame.add(orderPanel, BorderLayout.EAST);
        // Add innerOrderPanel, submitAndDelete, and pricePanel to orderPanel
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        orderPanel.add(innerOrderPanel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        orderPanel.add(pricePanel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        orderPanel.add(submitAndDeletePanel, gbc);
        //add Total Price Label
        JLabel totalPrice = new JLabel("Total Price");
        pricePanel.add(totalPrice);
        pricePanel.setMaximumSize(new Dimension(300, 30));

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
                cardLayout.show(menuPanel, panelName); // Switch to the specified panel
            }
        });
        return button;
    }

    private JButton createSubmitButton(String panelName, JPanel panel) {
        JButton button = new JButton(panelName);
        button.setMinimumSize(new Dimension(200, 70));
        button.setFont(new Font("Arial", Font.PLAIN, 25));
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new frame for EmployeeSubmit panel
                JFrame submitFrame = new JFrame("Employee Submit");
                submitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                submitFrame.getContentPane().add(new EmployeeSubmit());
                submitFrame.pack();
                submitFrame.setLocationRelativeTo(null); // Center the frame
                submitFrame.setVisible(true);
            }
        });
        return button;
    }
    
    private JButton createDeleteButton(String panelName, JPanel panel) {
        JButton button = new JButton(panelName);
        button.setMinimumSize(new Dimension(200, 70));
        button.setFont(new Font("Arial", Font.PLAIN, 25));
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new frame for EmployeeDelete panel
                JFrame deleteFrame = new JFrame("Employee Delete");
                deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                deleteFrame.getContentPane().add(new EmployeeDelete());
                deleteFrame.pack();
                deleteFrame.setLocationRelativeTo(null); // Center the frame
                deleteFrame.setVisible(true);
            }
        });
        return button;
    }

    public static void update() {
        //change total price
        pricePanel.removeAll();
        currentPrice = empCmds.getOrderPrice(selectedMenuIDs);
        String truncatedPrice = String.format("%.2f", currentPrice);
        JLabel totalPrice = new JLabel("Total Price: $" + truncatedPrice);
        pricePanel.add(totalPrice);
        innerOrderPanel.repaint();
        pricePanel.repaint();
    }
    
}