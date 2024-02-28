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


public class ValMeals extends JPanel {
    int numberOfItems;
    int[] menuItemIDs; 
    String[] names;
    float[] prices;

    //private List<Integer> selectedMenuIDs = new ArrayList<>();
    //private JPanel orderPanel = new JPanel();
    //private List<Integer> toBeDeleted = new ArrayList<>();

    employeeCmds employeeCmds;

    public ValMeals() {
        addMenuItems();

        setLayout(new BorderLayout());
        // Add components for editing orders
        // Example: JLabels, JTextFields, JButtons, etc.
        JLabel label = new JLabel("Value Meals");
        add(label, BorderLayout.NORTH);
        JPanel menuItems = new JPanel();
        add(menuItems, BorderLayout.CENTER);
        menuItems.setLayout(new GridLayout(0, 2));
        menuItems.setBorder(new EtchedBorder());

        //add all menu items as buttons in the edit order panel
        for (int i = 0; i < numberOfItems; i++) {
            if((menuItemIDs[i] >= 600) && (menuItemIDs[i] <= 699)) {
                String name = names[i];
                JButton button = new JButton(name);
                //LATER TODO: add prices as a small label inside the button next to the name of the item
                button.addActionListener(new ButtonClickListener(this, name));
                button.setPreferredSize(new Dimension(300, 50));
                button.setFont(new Font("Arial", Font.PLAIN, 25));
                menuItems.add(button);
            }
        }
    }

    //button click listener so things happen when buttons are clicked
    private class ButtonClickListener implements ActionListener {
        private ValMeals valMeals;
        private String buttonName;

        public ButtonClickListener(ValMeals valMeals, String buttonName) {
            this.valMeals = valMeals;
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Perform actions when the button is clicked
            System.out.println("Menu Item clicked: " + buttonName);
            JButton orderedBtn = new JButton(buttonName);
            orderedBtn.setFont(new Font("Arial", Font.PLAIN, 25));
            // Add to selectedMenuIDs
            int index = stringIndexOf(buttonName, names);
            Employee.selectedMenuIDs.add(intIndexOf(index, menuItemIDs));

            // Create a button & add it to current order panel to represent the item selected
            JButton button = new JButton(buttonName);
            button.setPreferredSize(new Dimension(100, 50));
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            Employee.innerOrderPanel.add(button);
            button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    button.setBackground(Color.RED);
                    Employee.toBeDeleted.add(intIndexOf(index, menuItemIDs));
                }
            });
        }
    }

    private void addMenuItems() {
        this.employeeCmds = new employeeCmds();
        sqlObjects.Menu menu = employeeCmds.getMenu();
        this.menuItemIDs = menu.menuItemIDs;
        this.names = menu.names;
        this.prices = menu.prices;
        this.numberOfItems = menuItemIDs.length;
    }

    private int stringIndexOf(String target, String[] array){
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                index = i; // Update index when target is found
                break;     // Exit the loop once the target is found
            }
        }

        return index;
    }

    private int intIndexOf(int target, int[] array){
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                index = i; // Update index when target is found
                break;     // Exit the loop once the target is found
            }
        }
        return index;
    }
}

