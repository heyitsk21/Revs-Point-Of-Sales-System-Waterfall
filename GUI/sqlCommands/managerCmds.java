import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;

public class managerCmds {
    /*
     * TODO should include the sequel object or whatever yall use to login here so
     * its shared between all calls
     */

    Database db;

    public managerCmds() {
        db = new Database();
    }

    public sqlObjects.Inventory getInventory() {
        try {
            int size = 0;
            PreparedStatement prep;
            ResultSet allIngredients;

            String cmd = "SELECT * FROM Ingredients ORDER BY IngredientID;";
            prep = db.con.prepareStatement(cmd, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            allIngredients = prep.executeQuery();

            // find the size of allIngredients
            allIngredients.last();
            size = allIngredients.getRow();

            int[] ingredientIDs = new int[size];
            String[] ingredientNames = new String[size];
            float[] ppu = new float[size];
            int[] count = new int[size];
            int[] minamount = new int[size];

            allIngredients.first();
            int counter = 0;

            do {
                ingredientIDs[counter] = allIngredients.getInt("IngredientID");
                ingredientNames[counter] = allIngredients.getString("IngredientName");
                ppu[counter] = allIngredients.getFloat("ppu");
                count[counter] = allIngredients.getInt("count");
                minamount[counter] = allIngredients.getInt("minamount");
                counter++;
            }while (allIngredients.next()) ;
            sqlObjects.Inventory inventoryObj = new sqlObjects.Inventory(ingredientIDs, ingredientNames, ppu, count,
                    minamount);
            return inventoryObj;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public sqlObjects.Menu getMenu() {
        try {
            int size = 0;
            PreparedStatement prep;
            ResultSet allMenuItems;

            String cmd = "SELECT ItemName, Price FROM MenuItems;";
            prep = db.con.prepareStatement(cmd, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            allMenuItems = prep.executeQuery();

            // find the size of allIngredients
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

    public sqlObjects.OrderList getOrders() {
        try {
            int size = 0;
            PreparedStatement prep;
            ResultSet allOrders;

            // OrderID,CustomerName,TaxPrice,BasePrice,OrderDateTime,EmployeeID
            String cmd = "SELECT OrderID, CustomerName, TaxPrice, BasePrice, OrderDateTime, EmployeeID FROM Orders ORDER BY OrderDateTime DESC;";
            prep = db.con.prepareStatement(cmd, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            allOrders = prep.executeQuery();

            // find the size of all orders
            allOrders.last();
            size = allOrders.getRow();

            String[] orderIDs = new String[size];
            String[] customerNames = new String[size];
            float[] taxPrices = new float[size];
            float[] basePrices = new float[size];
            String[] orderTimes = new String[size];
            int[] employeeIDs = new int[size];

            allOrders.first();
            int counter = 0;

            do{
                orderIDs[counter] = allOrders.getString("OrderID");
                customerNames[counter] = allOrders.getString("CustomerName");
                taxPrices[counter] = allOrders.getFloat("TaxPrice");
                basePrices[counter] = allOrders.getFloat("BasePrice");
                orderTimes[counter] = allOrders.getString("OrderDateTime");
                employeeIDs[counter] = allOrders.getInt("EmployeeID");
                counter++;
            }while (allOrders.next()) ;

            sqlObjects.OrderList orderList = new sqlObjects.OrderList(orderIDs, customerNames, taxPrices, basePrices,
                    orderTimes, employeeIDs);
            return orderList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /*
     * PARAMETERIZATION EXAMPLE TEMPLATE (POSSIBLY):
     * String updateNameCmd =
     * "UPDATE Ingredients SET IngredientName = ? WHERE IngredientID = ?";
     * try (PreparedStatement pstmt = db.con.prepareStatement(updateNameCmd)) {
     * pstmt.setString(1, newName);
     * pstmt.setInt(2, ingredientID);
     * pstmt.executeUpdate();
     * } catch (SQLException e) {
     * System.err.println("Error executing SQL query: " + e.getMessage());
     * }
     */

    public boolean updateIngredient(int ingredientID, int currentCount, String newName, float newPPU, int deltaCount,
            String logMessage) {
        if (newName != null && !newName.isEmpty()) {
            String updateNameCmd = "UPDATE Ingredients SET IngredientName = ? WHERE IngredientID = ?;";
            try {
                PreparedStatement prep = db.con.prepareStatement(updateNameCmd);
                prep.setString(1, newName);
                prep.setInt((2), ingredientID);
                prep.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        if (newPPU > 0) {
            String updatePPUCmd = String.format("UPDATE Ingredients SET PPU = %.2f WHERE IngredientID = %d;", newPPU,
                    ingredientID);
            db.executeSQL(updatePPUCmd);
        }

        if (deltaCount != 0) {
            int newCount = currentCount + deltaCount;
            if (newCount < 0) {
                return false;
            }

            String updateCountCmd = String.format("UPDATE Ingredients SET Count = Count + %d WHERE IngredientID = %d;",
                    deltaCount, ingredientID);
            db.executeSQL(updateCountCmd);

            String insertLogCmd = String.format(
                    "INSERT INTO InventoryLog (IngredientID, AmountChanged, LogMessage, LogDateTime) VALUES (%d, %d, '%s', NOW());",
                    ingredientID, deltaCount, logMessage);
            db.executeSQL(insertLogCmd);
        }

        return true; // Update successful
    }

}
