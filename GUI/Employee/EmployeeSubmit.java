import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EmployeeSubmit extends JPanel {
    private JTextField nameField;
    private JButton submitButton;
    private JButton backButton;

    public EmployeeSubmit() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Enter a name for the order");
        add(label, BorderLayout.NORTH);

        JPanel submitScreen = new JPanel();
        submitScreen.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add label for name input
        JLabel nameLabel = new JLabel("Name:");
        submitScreen.add(nameLabel);

        // Add text field for name input
        nameField = new JTextField(20);
        submitScreen.add(nameField);

        // Create submit and back buttons and add them to the submitScreen panel
        submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(150, 70));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 25));
        submitScreen.add(submitButton);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 70));
        backButton.setFont(new Font("Arial", Font.PLAIN, 25));
        submitScreen.add(backButton);

        add(submitScreen, BorderLayout.CENTER);

        // Add action listeners
        submitButton.addActionListener(new ButtonClickListener(this, "Submit"));
        backButton.addActionListener(new ButtonClickListener(this, "Back"));
    }

    // Button click listener for submit and back buttons
    private class ButtonClickListener implements ActionListener {
        private EmployeeSubmit employeeSubmit;
        private String buttonName;

        public ButtonClickListener(EmployeeSubmit employeeSubmit, String buttonName) {
            this.employeeSubmit = employeeSubmit;
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttonName.equals("Submit")) {
                // Check if name field is empty
                if (nameField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(employeeSubmit, "Please enter a name for the order.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // TODO: Update the database with the order
                    System.out.println("Order submitted with name: " + nameField.getText());
                    // Reset the Employee GUI (navigate back to previous panel)
                    CardLayout cardLayout = (CardLayout) getParent().getLayout();
                    cardLayout.show(getParent(), "previousPanel"); // TODO: Replace "previousPanel" with the actual name of the panel to navigate back to
                }
            } else if (buttonName.equals("Back")) {
                    // Handle back action
                    System.out.println("Back clicked");
                    // Close the frame
                    SwingUtilities.getWindowAncestor(EmployeeSubmit.this).dispose();
            }
        }
    }
}