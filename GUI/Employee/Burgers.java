
import java.awt.event.*;
import javax.swing.*;

import javax.swing.border.EtchedBorder;
import java.awt.*;



public class Burgers extends JPanel {
    int numberOfItems = 0;
    int[] menuItemIDs; 
    String[] names;
    float[] prices;

    employeeCmds employeeCmds;

    public Burgers() {
        addMenuItems();

        setLayout(new BorderLayout());
        // Add components for editing orders
        // Example: JLabels, JTextFields, JButtons, etc.
        JLabel label = new JLabel("Burgers");
        add(label, BorderLayout.NORTH);
        JPanel menuItems = new JPanel();
        add(menuItems, BorderLayout.CENTER);
        menuItems.setLayout(new GridLayout(0, 2));
        menuItems.setBorder(new EtchedBorder());

        //add all menu items as buttons in the edit order panel
        for (int i = 0; i < numberOfItems; i++) {
            Integer id = menuItemIDs[i];
            String nameAndPrice = names[i] + ": $" + prices[i];
            JButton button = new JButton(nameAndPrice);
            button.addActionListener(new ButtonClickListener(this, id.toString()));
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

            int index = 0;
            for(index = 0; index < menuItemIDs.length; ++index){
                if(String.valueOf(menuItemIDs[index]) == buttonName){
                    break;
                }
            }
            
            float price = prices[index];
            String nameAndPrice = buttonName + " : $" + price;
            // Add to selectedMenuIDs
            int ID = menuItemIDs[index];
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

    private void addMenuItems() {

        this.employeeCmds = new employeeCmds();
        sqlObjects.Menu menu = employeeCmds.getMenu(100,199);
        this.menuItemIDs = menu.menuItemIDs;
        this.names = menu.names;
        this.prices = menu.prices;
    }
}

