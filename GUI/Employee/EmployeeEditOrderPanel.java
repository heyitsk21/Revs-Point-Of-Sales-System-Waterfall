import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.awt.*;


// Panel for editing orders
public class EmployeeEditOrderPanel extends JPanel {
    //placeholder menu items
    int numberOfItems = 5; //= myInventory.length();
    int[] menuItemIDs = {1,2,3,4,5}; //= myInventory.ingredientIDs;
    String[] names = {"Item 1", "another ing", "cheese", "bread", "knucle sandwich"};
    public EmployeeEditOrderPanel() {
        setLayout(new BorderLayout());
        // Add components for editing orders
        // Example: JLabels, JTextFields, JButtons, etc.
        JLabel label = new JLabel("Edit Order Panel");
        add(label, BorderLayout.CENTER);
        JPanel menuItems = new JPanel();
        menuItems.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        //add all menu items as buttons in the edit order panel
        for (int i = 0; i < numberOfItems; i++) {
            JButton button = new JButton(names[i]);
            button.addActionListener(new ButtonClickListener(this, String.valueOf(i)));
            button.setPreferredSize(new Dimension(300, 50));
            button.setFont(new Font("Arial", Font.PLAIN, 25));
            menuItems.add(button);
        }
    }

    //button click listener so things happen when buttons are clicked
    private class ButtonClickListener implements ActionListener {
        private EmployeeEditOrderPanel editOrder;
        private String buttonName;

        public ButtonClickListener(EmployeeEditOrderPanel editOrder, String buttonName) {
            this.editOrder = editOrder;
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Perform actions when the button is clicked
            System.out.println("Menu Item clicked: " + buttonName);
            //add to submit order panel
        }
    }
}