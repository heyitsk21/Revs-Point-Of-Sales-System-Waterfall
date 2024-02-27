import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import java.io.*;
import java.awt.*;

public class Baskets extends JPanel {
    //placeholder menu items, in the future we can go through the menu items list and pick out ones with certain types of IDs
    int numberOfItems = 5;
    int[] menuItemIDs = {1,2,3,4,5};
    String[] names = {"Basket 1", "Basket 2", "Basket 3", "Basket 4", "Basket 5"};
    double[] prices = {2.00, 3.00, 4.00, 5.50, 6.00};
    public Baskets() {
        setLayout(new BorderLayout());
        // Add components for editing orders
        // Example: JLabels, JTextFields, JButtons, etc.
        JLabel label = new JLabel("Baskets");
        add(label, BorderLayout.NORTH);
        JPanel menuItems = new JPanel();
        add(menuItems, BorderLayout.CENTER);
        menuItems.setLayout(new GridLayout(0, 2));
        menuItems.setBorder(new EtchedBorder());

        //add all menu items as buttons in the edit order panel
        for (int i = 0; i < numberOfItems; i++) {
            JButton button = new JButton(names[i]);
            //LATER TODO: add prices as a small label inside the button next to the name of the item
            button.addActionListener(new ButtonClickListener(this, names[i]));
            button.setPreferredSize(new Dimension(200, 50));
            button.setFont(new Font("Arial", Font.PLAIN, 25));
            menuItems.add(button);
        }
    }

    //button click listener so things happen when buttons are clicked
    private class ButtonClickListener implements ActionListener {
        private Baskets baskets;
        private String buttonName;

        public ButtonClickListener(Baskets baskets, String buttonName) {
            this.baskets = baskets;
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

