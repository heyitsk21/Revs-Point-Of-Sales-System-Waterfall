import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSubmit extends JPanel {
    private JTextField nameField;
    private JTextField IDField;
    private JButton submitButton;
    private JButton backButton;
    //private List<Integer> selectedMenuIDs;

    public EmployeeSubmit() {
        //selectedMenuIDs = passedSelectedMenuIDs;
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Enter a name for the order");
        add(label, BorderLayout.NORTH);

        JPanel submitScreen = new JPanel();
        submitScreen.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // NAME INPUT
        // Add label for name input
        JLabel nameLabel = new JLabel("Name:");
        submitScreen.add(nameLabel);
        // Add text field for name input
        nameField = new JTextField(20);
        submitScreen.add(nameField);

        // ID input
        // Add label for name input
        JLabel IDLabel = new JLabel("Employee ID:");
        submitScreen.add(IDLabel);
        // Add text field for ID input
        IDField = new JTextField(20);
        limitInputToNumeric(IDField);
        submitScreen.add(IDField);

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
                if (nameField.getText().isEmpty() || IDField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(employeeSubmit, "Please enter a name and employee ID for the order.", "Error", JOptionPane.ERROR_MESSAGE);
                } 
                else {
                    employeeCmds commands = new employeeCmds();
                    int employeeID = Integer.parseInt(IDField.getText());
                    System.out.println(employeeID);
                    boolean success = commands.submitOrder(Employee.selectedMenuIDs, nameField.getText(), employeeID);
                    if(success) {
                        System.out.println("Order submitted with name: " + nameField.getText());
                    }
                    else {
                        JOptionPane.showMessageDialog(employeeSubmit, "There was an error processing the order, please contact a manager", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    // Clear out selectedMenuIDs
                    Employee.selectedMenuIDs.clear();
                    // Close the frame
                    SwingUtilities.getWindowAncestor(EmployeeSubmit.this).dispose();
                    //removes buttons and repaints
                    Employee.innerOrderPanel.removeAll();
                    // reset price
                    Employee.currentPrice = 0.0f;
                }
            } else if (buttonName.equals("Back")) {
                    // Handle back action
                    System.out.println("Back clicked");
                    // Close the frame
                    SwingUtilities.getWindowAncestor(EmployeeSubmit.this).dispose();
            }
        }
    }

    private static void limitInputToNumeric(JTextField textField) {
        AbstractDocument document = (AbstractDocument) textField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("-?\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("-?\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
}