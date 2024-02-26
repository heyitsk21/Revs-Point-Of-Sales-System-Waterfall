import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.awt.*;

public class Drinks extends JPanel {
    //placeholder menu items, in the future we can go through the menu items list and pick out ones with certain types of IDs
    int numberOfItems = 5;
    int[] menuItemIDs = {1,2,3,4,5};
    String[] names = {"Drink 1", "Drink 2", "Drink 3", "Drink 4", "Drink 5"};
    double[] prices = {2.00, 3.00, 4.00, 5.50, 6.00};
    public Drinks() {
        setLayout(new BorderLayout());
        // Add components for editing orders
        // Example: JLabels, JTextFields, JButtons, etc.
        JLabel label = new JLabel("Drinks");
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
        private Drinks drinks;
        private String buttonName;

        public ButtonClickListener(Drinks drinks, String buttonName) {
            this.drinks = drinks;
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


