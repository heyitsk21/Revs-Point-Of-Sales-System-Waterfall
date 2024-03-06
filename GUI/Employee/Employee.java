import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Employee class represents the graphical user interface for employees.
 * This class extends JFrame to create the main window of the application.
 */
public class Employee extends JFrame {
    /** The list of selected menu item IDs. */
    public static List<Integer> selectedMenuIDs;
    /** The list of menu item IDs to be deleted. */
    public static List<Integer> toBeDeleted;
    /** The panel for displaying current order details. */
    public static JPanel upperOrderPanel;
    /** The panel for displaying the total price. */
    public static JPanel pricePanel;
    /** The current total price. */
    public static Float currentPrice;
    /** The instance of employeeCmds for managing employee commands. */
    public static employeeCmds empCmds;

    private JPanel menuPanel;
    private CardLayout cardLayout;
    private JPanel orderPanel;
    private JPanel submitAndDeletePanel;

    /**
     * Constructs a new Employee object. Initializes public lists and the public float currentPrice.
     */
    public Employee() {
        empCmds = new employeeCmds();
        createAndShowGUI();
        selectedMenuIDs = new ArrayList<>();
        toBeDeleted = new ArrayList<>();
        currentPrice = 0.0f;
    }

    /**
     * Creates and displays the graphical user interface.
     * This method sets up the layout and components of the main window.
     */
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Employee GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        orderPanel = new JPanel();
        upperOrderPanel = new JPanel();
        submitAndDeletePanel = new JPanel();
        pricePanel = new JPanel();

        // Create menuPanel and cardLayout
        cardLayout = new CardLayout();
        menuPanel = new JPanel(cardLayout);
        menuPanel.setBorder(new EtchedBorder());
        // Add different menu sections to the menuPanel
        menuPanel.add(new MenuSection("ValMeals", 600, 699), "ValMeals");
        menuPanel.add(new MenuSection("Burgers", 100, 199), "Burgers");
        menuPanel.add(new MenuSection("Sandwiches", 200, 299), "Sandwiches");
        menuPanel.add(new MenuSection("DrinksAndFries", 500, 599), "DrinksAndFries");
        menuPanel.add(new MenuSection("Salads", 300, 399), "Salads");
        menuPanel.add(new MenuSection("IceCream", 400, 499), "IceCream");
        menuPanel.add(new MenuSection("LimitedTime", 700, 799), "LimitedTime");

        // Create menu categories panel
        JPanel categoriesPanel = new JPanel();
        categoriesPanel.setBorder(new EtchedBorder());
        categoriesPanel.setLayout(new GridLayout(1, 0));
        submitAndDeletePanel.setMaximumSize(new Dimension(100, 80));

        // Create menu category buttons and add them to the panel
        createMenuCatButton("ValMeals", categoriesPanel);
        createMenuCatButton("Burgers", categoriesPanel);
        createMenuCatButton("Sandwiches", categoriesPanel);
        createMenuCatButton("DrinksAndFries", categoriesPanel);
        createMenuCatButton("Salads", categoriesPanel);
        createMenuCatButton("IceCream", categoriesPanel);
        createMenuCatButton("LimitedTime", categoriesPanel);

        // Add the categoriesPanel to the bottom of the frame
        frame.add(categoriesPanel, BorderLayout.SOUTH);

        // Create the panel to show the current order details
        JPanel lowerOrderPanel = new JPanel();
        JScrollPane scroller = new JScrollPane(upperOrderPanel);
        scroller.setAlignmentX(RIGHT_ALIGNMENT);

        orderPanel.setBorder(new EtchedBorder());
        orderPanel.setLayout(new BorderLayout());
        orderPanel.add(scroller, BorderLayout.CENTER);
        orderPanel.add(lowerOrderPanel, BorderLayout.SOUTH);

        upperOrderPanel.setBorder(new EtchedBorder());
        upperOrderPanel.setLayout(new GridLayout(0, 1));

        lowerOrderPanel.add(pricePanel);
        lowerOrderPanel.add(submitAndDeletePanel);
        lowerOrderPanel.setLayout(new BoxLayout(lowerOrderPanel, BoxLayout.Y_AXIS));
        lowerOrderPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        submitAndDeletePanel.setBorder(new EtchedBorder());
        submitAndDeletePanel.setLayout(new GridLayout(0, 2));
        submitAndDeletePanel.setMaximumSize(new Dimension(300, 300));
        createDeleteButton("DELETE", submitAndDeletePanel);
        createSubmitButton("Submit", submitAndDeletePanel);

        JLabel totalPrice = new JLabel("Total Price");
        pricePanel.add(totalPrice);
        pricePanel.setLayout(new GridLayout(0, 1));
        pricePanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Add panels to the frame
        frame.add(menuPanel, BorderLayout.CENTER);
        frame.add(orderPanel, BorderLayout.EAST);

        // Set frame size and center it
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);

        // Add top panel with date/time and username
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

    /**
     * Creates a menu category button and adds it to the specified panel.
     * @param panelName The name of the panel.
     * @param panel The panel to which the button is added.
     * @return The created button.
     */
    private JButton createMenuCatButton(String panelName, JPanel panel) {
        JButton button = new JButton(panelName);
        button.setPreferredSize(new Dimension(180, 100));
        button.setFont(new Font("Arial", Font.PLAIN, 25));
        panel.add(button);
        // ActionListener to switch to the specified panel
        button.addActionListener(new ActionListener() {
            /**
             * Invoked when the menu category button is clicked.
             * This method switches the view to the specified menu category.
             * @param e The action event generated by clicking the menu category button.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(menuPanel, panelName); // Switch to the specified panel
            }
        });
        return button;
    }

    /**
     * Creates a submit button and adds it to the specified panel.
     * @param panelName The name of the panel.
     * @param panel The panel to which the button is added.
     * @return The created button.
     */
    private JButton createSubmitButton(String panelName, JPanel panel) {
        JButton button = new JButton(panelName);
        button.setMinimumSize(new Dimension(200, 100));
        button.setFont(new Font("Arial", Font.PLAIN, 25));
        panel.add(button);
        // ActionListener to open a new frame for submission
        button.addActionListener(new ActionListener() {
            /**
             * Invoked when the submit button is clicked.
             * This method opens a new frame for employee submission.
             * @param e The action event generated by clicking the submit button.
             */
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
    
    /**
     * Creates a delete button and adds it to the specified panel.
     * @param panelName The name of the panel.
     * @param panel The panel to which the button is added.
     * @return The created button.
     */
    private JButton createDeleteButton(String panelName, JPanel panel) {
        JButton button = new JButton(panelName);
        button.setMinimumSize(new Dimension(200, 100));
        button.setFont(new Font("Arial", Font.PLAIN, 25));
        panel.add(button);
        // ActionListener to open a new frame for deletion
        button.addActionListener(new ActionListener() {
            /**
             * Invoked when the delete button is clicked.
             * This method opens a new frame for employee deletion.
             * @param e The action event generated by clicking the delete button.
             */
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

    /**
     * Updates the total price displayed on the interface.
     */
    public static void update() {
        // Update total price
        pricePanel.removeAll();
        currentPrice = empCmds.getOrderPrice(selectedMenuIDs);
        String truncatedPrice = String.format("%.2f", currentPrice);
        JLabel totalPrice = new JLabel("Total Price: $" + truncatedPrice);
        pricePanel.add(totalPrice);
        upperOrderPanel.repaint();
        pricePanel.repaint();
    }  
}