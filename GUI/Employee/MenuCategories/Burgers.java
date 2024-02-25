package MenuCategories;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.awt.*;


public class Burgers extends JPanel {
    //placeholder menu items
    int numberOfItems = 5; //= myInventory.length();
    int[] menuItemIDs = {1,2,3,4,5}; //= myInventory.ingredientIDs;
    String[] names = {"Burger 1", "Burger 2", "Burger 3", "Burger 4", "Burger 5"};
    double[] prices = {2.00, 3.00, 4.00, 5.50, 6.00};
    public Burgers() {
        setLayout(new BorderLayout());
        // Add components for editing orders
        // Example: JLabels, JTextFields, JButtons, etc.
        JLabel label = new JLabel("Burgers");
        add(label, BorderLayout.CENTER);
        JPanel menuItems = new JPanel();
        menuItems.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        //add all menu items as buttons in the edit order panel
        for (int i = 0; i < numberOfItems; i++) {
            JButton button = new JButton(names[i]);
            //TODO: add prices as a small label inside the button next to the name of the item
            button.addActionListener(new ButtonClickListener(this, String.valueOf(i)));
            button.setPreferredSize(new Dimension(300, 50));
            button.setFont(new Font("Arial", Font.PLAIN, 25));
            menuItems.add(button);
        }
    }

    //button click listener so things happen when buttons are clicked
    private class ButtonClickListener implements ActionListener {
        private Burgers burgers;
        private String buttonName;

        public ButtonClickListener(Burgers burgers, String buttonName) {
            this.burgers = burgers;
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Perform actions when the button is clicked
            System.out.println("Menu Item clicked: " + buttonName);
            JButton orderedBtn = new JButton(buttonName);
            orderedBtn.setFont(new Font("Arial", Font.PLAIN, 25));
            //TODO: add to submit order panel
            //currentOrderPanel.add(orderedBtn);
        }
    }
}
