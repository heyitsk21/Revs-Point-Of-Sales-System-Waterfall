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


public class Salads extends JPanel {
    int numberOfItems = 0;
    List<Integer> menuItemIDs; 
    List<String> names;
    List<Float> prices;

    employeeCmds employeeCmds;

    public Salads() {
        addMenuItems();

        setLayout(new BorderLayout());
        // Add components for editing orders
        // Example: JLabels, JTextFields, JButtons, etc.
        JLabel label = new JLabel("Salads");
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
            button.addActionListener(new ButtonClickListener(this, name));
            button.setPreferredSize(new Dimension(300, 50));
            button.setFont(new Font("Arial", Font.PLAIN, 25));
            menuItems.add(button);
        }
    }

    //button click listener so things happen when buttons are clicked
    private class ButtonClickListener implements ActionListener {
        private Salads salads;
        private String buttonName;

        public ButtonClickListener(Salads salads, String buttonName) {
            this.salads = salads;
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
            System.out.println(names);
            System.out.println("The index of " + buttonName + " in names is " + index);
            int ID = menuItemIDs.get(index);
            Employee.selectedMenuIDs.add(ID);

            // Create a button & add it to current order panel to represent the item selected
            JButton button = new JButton(buttonName);
            button.setPreferredSize(new Dimension(100, 50));
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            Employee.innerOrderPanel.add(button);
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

    private void addMenuItems() {//300
        this.menuItemIDs = new ArrayList<>();
        this.names = new ArrayList<>();
        this.prices = new ArrayList<>();

        this.employeeCmds = new employeeCmds();
        sqlObjects.Menu menu = employeeCmds.getMenu();
        for (int i = 0; i < menu.menuItemIDs.length; i++) {
            if((menu.menuItemIDs[i] >= 300) && (menu.menuItemIDs[i] < 399)) {
                this.menuItemIDs.add(menu.menuItemIDs[i]);
                this.names.add(menu.names[i]);
                this.prices.add(menu.prices[i]);
                this.numberOfItems++;
            }
        }
    }
}

