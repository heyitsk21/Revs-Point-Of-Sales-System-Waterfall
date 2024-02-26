import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;

public class employeeCmds {

    Database db;

    public managerCmds() 
    {
        db = new Database();
    }

    public sqlObjects.Menu getMenu() {
        try {
            int size = 0;
            PreparedStatement prep;
            ResultSet allMenuItems;

            String cmd = "SELECT MenuID, ItemName, Price FROM MenuItems;";
            prep = db.con.prepareStatement(cmd, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            allMenuItems = prep.executeQuery();

            // find the size of allMenuItems
            allMenuItems.last();
            size = allMenuItems.getRow();

            int[] menuItemIDs = new int[size];
            String[] names = new String[size];
            float[] prices = new float[size];

            allMenuItems.first();
            int counter = 0;

            do {
                menuItemIDs[counter] = allMenuItems.getInt("MenuID");
                names[counter] = allMenuItems.getString("ItemName");
                prices[counter] = allMenuItems.getFloat("Price");
                counter++;
            }while (allMenuItems.next()) ;

            sqlObjects.Menu menuItemObj = new sqlObjects.Menu(menuItemIDs, names, prices);
            return menuItemObj;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ResultSet addMenuItemToOrder(int menuItemID) {
        try {
            // Execute query to select menu item details
            String query = "SELECT MenuID, Name, Price FROM MenuItems WHERE MenuID = ?";
            PreparedStatement prep = db.con.prepareStatement(query);
            prep.setInt(1, menuItemID);
            ResultSet menuItemResult = prep.executeQuery();

            if (menuItemResult.next()) {
                int menuID = menuItemResult.getInt("MenuID");
                String name = menuItemResult.getString("Name");
                float price = menuItemResult.getFloat("Price");

                return menuItemResult;
            } else {
                // No menu item found with the provided ID
                System.out.println("No menu item found with ID: " + menuItemID);
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    // Method to submit order
    public boolean submitOrder(int selectedMenuID, String customerName, int employeeID) {
        try {
            // Query every single menu item ingredient count
            String ingredientQuery = "SELECT Ingredients.IngredientName, Ingredients.Count, Ingredients.IngredientID FROM menuitems JOIN menuitemingredients ON menuitems.MenuID = menuitemingredients.MenuID JOIN Ingredients ON menuitemingredients.IngredientID = Ingredients.IngredientID WHERE menuitems.MenuID = ?";
            PreparedStatement ingredientPrep = db.con.prepareStatement(ingredientQuery);
            ingredientPrep.setInt(1, selectedMenuID);
            ResultSet ingredientResult = ingredientPrep.executeQuery();
            
            // Add every single IngredientID to a map<ID,Count> 
            Map<Integer, Integer> ingredientCountMap = new HashMap<>();
            while (ingredientResult.next()) 
            {
                int ingredientID = ingredientResult.getInt("IngredientID");
                int count = ingredientResult.getInt("Count");
                ingredientCountMap.put(ingredientID, count);
            }

            // If any out of ingredient errors, break and return false
            for (Map.Entry<Integer, Integer> entry : ingredientCountMap.entrySet()) 
            {
                int ingredientID = entry.getKey();
                int requiredCount = entry.getValue();
                int availableCount = entry.getValue();
                if (availableCount < requiredCount) 
                {
                    System.out.println("Insufficient ingredients for the order");
                    return false;
                }
            }

            // Manage map of ingredients and update the count
            for (Map.Entry<Integer, Integer> entry : ingredientCountMap.entrySet()) 
            {
                // Update the count of each ingredient
                int ingredientID = entry.getKey();
                int requiredCount = entry.getValue();
                int newCount = requiredCount - entry.getValue();
                String updateQuery = "UPDATE Ingredients SET Count = ? WHERE IngredientID = ?";
                PreparedStatement updatePrep = db.con.prepareStatement(updateQuery);
                updatePrep.setInt(1, newCount);
                updatePrep.setInt(2, ingredientID);
                updatePrep.executeUpdate();

                // Generate inventory log entry for each ingredient changed
                String logMessage = "Order placed: " + selectedMenuID;
                String logQuery = "INSERT INTO InventoryLog (IngredientID, AmountChanged, LogMessage, LogDateTime) VALUES (?, ?, ?, NOW())";
                PreparedStatement logPrep = db.con.prepareStatement(logQuery);
                logPrep.setInt(1, ingredientID);
                logPrep.setInt(2, -requiredCount); // Negative value indicates deduction
                logPrep.setString(3, logMessage);
                logPrep.executeUpdate();
            }

            // Add order to order history (get highest OrderID and add 1)
            String orderIDQuery = "SELECT MAX(OrderID) AS MaxID FROM Orders";
            Statement orderIDStmt = db.con.createStatement();
            ResultSet orderIDResult = orderIDStmt.executeQuery(orderIDQuery);
            int newOrderID = 0;
            if (orderIDResult.next()) 
            {
                newOrderID = orderIDResult.getInt("MaxID") + 1;
            }

            // Calculate total price of the order
            float totalPrice = 0;
            String totalPriceQuery = "SELECT SUM(Price) AS TotalPrice FROM MenuItems WHERE MenuID = ?";
            PreparedStatement totalPricePrep = db.con.prepareStatement(totalPriceQuery);
            totalPricePrep.setInt(1, selectedMenuID);
            ResultSet totalPriceResult = totalPricePrep.executeQuery();
            if (totalPriceResult.next()) 
            {
                totalPrice = totalPriceResult.getFloat("TotalPrice");
            }

            String junctionQuery = "INSERT INTO OrderMenuItems (OrderID, MenuID) VALUES (?, ?)";
            PreparedStatement junctionPrep = db.con.prepareStatement(junctionQuery);
            junctionPrep.setInt(1, newOrderID);
            junctionPrep.setInt(2, selectedMenuID);
            junctionPrep.executeUpdate();

            // Insert the order into the Orders Table
            String orderQuery = "INSERT INTO Orders (OrderID, CustomerName, TaxPrice, BasePrice, OrderDateTime, EmployeeID) VALUES (?, ?, ?, ?, NOW(), ?)";
            PreparedStatement orderPrep = db.con.prepareStatement(orderQuery);
            orderPrep.setInt(1, newOrderID);
            orderPrep.setString(2, customerName); 
            orderPrep.setFloat(3, totalPrice * 0.0825f); 
            orderPrep.setFloat(4, totalPrice); 
            orderPrep.setInt(5, employeeID);
            orderPrep.executeUpdate();

            // If successful, return true
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

}