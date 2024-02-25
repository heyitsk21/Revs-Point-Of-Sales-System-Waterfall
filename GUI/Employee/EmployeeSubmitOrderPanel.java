import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.awt.*;

// Panel for submitting orders
public class EmployeeSubmitOrderPanel extends JPanel {
    //placeholder menu items
    int numberOfItems = 5; //= myInventory.length();
    int[] menuItemIDs = {1,2,3,4,5}; //= myInventory.ingredientIDs;
    String[] names = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
    double[] prices = {4.00, 6.99, 5.49, 6.00, 7.25};

    public EmployeeSubmitOrderPanel() {
        setLayout(new BorderLayout());
        // Add components for submitting orders
        // Example: JLabels, JTextFields, JButtons, etc.
        JLabel label = new JLabel("Submit Order Panel");
        add(label, BorderLayout.CENTER);

        //submit order panel is pretty blank to start

        //whenever edit order panel taps a menu item it gets added to this panel
        //make a button out of it so it can be selected for deletion if needed
        /*
         * JLabel nameLabel = new JLabel();
         * nameLabel.setText(names[menuItem]);
         * nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
         * nameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
         * //add to the submit order panel
         * 
         * JLabel priceLabel = new JLabel();
         * priceLabel.setText(String.valueOf(prices[currIngredientIndex]));
         * priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
         * priceLabel.setFont(new Font("Arial", Font.PLAIN, 25));
         * add label to the submit order panel
         */

        //the total price + tax is listed under the list of items

        //when the order is submitted, inventory, order history, etc. is updated correctly 
    }
}