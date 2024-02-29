import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import java.io.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;


public class LimitedTime extends JPanel {
    int numberOfItems = 0;
    List<Integer> menuItemIDs; 
    List<String> names;
    List<Float> prices;

    employeeCmds employeeCmds;

    public LimitedTime() {
        addMenuItems();

        setLayout(new BorderLayout());
        // Add components for editing orders
        // Example: JLabels, JTextFields, JButtons, etc.
        JLabel label = new JLabel("LimitedTime");
        add(label, BorderLayout.NORTH);
        JPanel menuItems = new JPanel();
        add(menuItems, BorderLayout.CENTER);
        menuItems.setLayout(new GridLayout(0, 2));
        menuItems.setBorder(new EtchedBorder());

        //add all menu items as buttons in the edit order panel
        for (int i = 0; i < numberOfItems; i++) {
            String name = names.get(i);
            String nameAndPrice = name + ": $" + prices.get(i);
            JButton button = new JButton(nameAndPrice);
            button.addActionListener(new ButtonClickListener(this, name));
            button.setPreferredSize(new Dimension(300, 50));
            button.setFont(new Font("Arial", Font.PLAIN, 25));
            menuItems.add(button);
        }
    }

    //button click listener so things happen when buttons are clicked
    private class ButtonClickListener implements ActionListener {
        private LimitedTime limitedTime;
        private String buttonName;

        public ButtonClickListener(LimitedTime limitedTime, String buttonName) {
            this.limitedTime = limitedTime;
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Menu Item clicked: " + buttonName);
            int index = names.indexOf(buttonName);
            System.out.println(names);
            System.out.println("The index of " + buttonName + " in names is " + index);
            float price = prices.get(index);
            String nameAndPrice = buttonName + " : $" + price;
            // Add to selectedMenuIDs
            int ID = menuItemIDs.get(index);
            Employee.selectedMenuIDs.add(ID);

            // Create a button & add it to current order panel to represent the item selected
            JButton button = new JButton(nameAndPrice);
            button.setPreferredSize(new Dimension(100, 50));
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            Employee.innerOrderPanel.add(button);
            Employee.update();
            button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if(button.getBackground() != Color.RED) {
                        button.setBackground(Color.RED);
                        //add the ID to the to-be deleted list
                        Employee.toBeDeleted.add(ID);
                    }
                    else {
                        button.setBackground(Color.LIGHT_GRAY);
                        //remove the ID from the to-be deleted list
                        Employee.toBeDeleted.remove(Employee.toBeDeleted.indexOf(ID));
                    }
                }
            });
        }
    }

    private void addMenuItems() {//700
        this.menuItemIDs = new ArrayList<>();
        this.names = new ArrayList<>();
        this.prices = new ArrayList<>();

        this.employeeCmds = new employeeCmds();
        sqlObjects.Menu menu = employeeCmds.getMenu();
        for (int i = 0; i < menu.menuItemIDs.length; i++) {
            if((menu.menuItemIDs[i] >= 700) && (menu.menuItemIDs[i] < 799)) {
                this.menuItemIDs.add(menu.menuItemIDs[i]);
                this.names.add(menu.names[i]);
                this.prices.add(menu.prices[i]);
                this.numberOfItems++;
            }
        }
    }
}

