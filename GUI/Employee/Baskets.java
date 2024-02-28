import java.sql.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import java.io.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;

public class Baskets extends JPanel {
    int numberOfItems;
    private List<Integer> menuItemIDs = new ArrayList<>();
    private List<Double> prices = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private List<Integer> selectedMenuIDs = new ArrayList<>();

    public Baskets(List<Integer> passedSelectedMenuIDs) {
        if (passedSelectedMenuIDs != null) {
            selectedMenuIDs = passedSelectedMenuIDs;
        }
        addMenuItems();

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
            String name = names.get(i);
            JButton button = new JButton(name);
            //LATER TODO: add prices as a small label inside the button next to the name of the item
            button.addActionListener(new ButtonClickListener(this, names.get(i)));
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
            // Add to selectedMenuIDs
            int index = names.indexOf(buttonName);
            selectedMenuIDs.add(menuItemIDs.get(index));
        }
    }

    private void addMenuItems() {
        //TODO: use sql commands to pull items from database
        numberOfItems = 5;

        names.add("Value Meal 1");
        names.add("Value Meal 2");
        names.add("Value Meal 3");
        names.add("Value Meal 4");
        names.add("Value Meal 5");

        menuItemIDs.add(1);
        menuItemIDs.add(2);
        menuItemIDs.add(3);
        menuItemIDs.add(4);
        menuItemIDs.add(5);

        prices.add(2.00);
        prices.add(3.00);
        prices.add(4.00);
        prices.add(5.50);
        prices.add(6.00);
    }
}

