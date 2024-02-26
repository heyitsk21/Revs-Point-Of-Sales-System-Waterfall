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

    public sqlObjects.MenuItemIngredients getMenuItemIngredients(int menuItemID){
        try{
            int size = 0;
            PreparedStatement prep;
            ResultSet allMenuItemIngredients;

            String cmd = String.format("SELECT Ingredients.IngredientID, Ingredients.IngredientName " +
                "FROM menuitems JOIN menuitemingredients ON menuitems.MenuID = menuitemingredients.MenuID " +
                "JOIN Ingredients ON menuitemingredients.IngredientID = Ingredients.IngredientID " +
                "WHERE menuitems.MenuID = {};", menuItemID);
            prep = db.con.prepareStatement(cmd, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            allMenuItemIngredients = prep.executeQuery();

            // find the size of allMenuItemIngredients
            allMenuItemIngredients.last();
            size = allMenuItemIngredients.getRow();

            int[] ingredientIDs = new int[size];
            String[] names = new String[size];

            allMenuItemIngredients.first();
            int counter = 0;

            do {
                ingredientIDs[counter] = allMenuItemIngredients.getInt("Ingredients.IngredientID"); //TODO: check if this columnLabel works. unsure if this is necessary
                names[counter] = allMenuItemIngredients.getString("Ingredients.IngredientName");
                counter++;
            } while (allMenuItemIngredients.next()) ;

            sqlObjects.MenuItemIngredients menuItemIngredientObj = new sqlObjects.MenuItemIngredients(ingredientIDs, names);
            return menuItemIngredientObj;
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

            // find the size of allOrders
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

    public boolean updateIngredient(int ingredientID, int currentCount, String newName, float newPPU, int deltaCount, String logMessage) {
        if (newName != null && !newName.isEmpty()) {
            String updateNameCmd = "UPDATE Ingredients SET IngredientName = ? WHERE IngredientID = ?;";
            try {
                PreparedStatement prep = db.con.prepareStatement(updateNameCmd);
                prep.setString(1, newName);
                prep.setInt(2, ingredientID);
                prep.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return false;
            }
        }

        if (newPPU > 0) {
            String updatePPUCmd = String.format("UPDATE Ingredients SET PPU = %.2f WHERE IngredientID = %d;", newPPU, ingredientID);
            db.executeSQL(updatePPUCmd);
        }

        if (deltaCount != 0) {
            int newCount = currentCount + deltaCount;
            if (newCount < 0) {
                return false;
            }

            String updateCountCmd = String.format("UPDATE Ingredients SET Count = Count + %d WHERE IngredientID = %d;", deltaCount, ingredientID);
            db.executeSQL(updateCountCmd);

            String insertLogCmd = String.format(
                    "INSERT INTO InventoryLog (IngredientID, AmountChanged, LogMessage, LogDateTime) VALUES (%d, %d, '%s', NOW());",
                    ingredientID, deltaCount, logMessage);
            db.executeSQL(insertLogCmd);
        }

        return true; // Update successful
    }

    public boolean addIngredient(int newID,String ingredientName, int count, float PPU, int minamount){
        String addIngredientCmd = "INSERT INTO Ingredients (IngredientID, Ingredientname, Count, PPU, minamount) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement prep = db.con.prepareStatement(addIngredientCmd);
            prep.setInt(1,newID);
            prep.setString(2, ingredientName);
            prep.setInt(3, count);
            prep.setFloat(4, PPU);
            prep.setInt(5, minamount);
            prep.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

        String insertLogCmd = String.format( //TODO: parameterize this!
            "INSERT INTO InventoryLog (IngredientID, AmountChanged, LogMessage, LogDateTime) VALUES (%d, %d, '%s', NOW());",
            newID, count, "YOOO I CREATED A NEW INGREDIENT WITH NAME " + ingredientName);
        db.executeSQL(insertLogCmd);

        return true;
    }

    public boolean updateMenuItem(int menuItemID, String newName, float newPrice){
        // boolean shouldUpdateName = (newName != null && !newName.isEmpty());
        // boolean shouldUpdatePrice = (newPrice > 0);
        // if(shouldUpdateName && shouldUpdatePrice){
            
        // }
        if (newName != null && !newName.isEmpty()) {
            String updateNameCmd = "UPDATE menuItems SET itemname = ? WHERE menuid = ?;";
            try {
                PreparedStatement prep = db.con.prepareStatement(updateNameCmd);
                prep.setString(1, newName);
                prep.setInt(2, menuItemID);
                prep.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return false;
            }
        }

        if (newPrice > 0) {
            String updatePriceCmd = String.format("UPDATE menuitems SET price = %.2f WHERE menuid = %d;", newPrice, menuItemID);
            db.executeSQL(updatePriceCmd);
        }


        return true;
    }

    public boolean addMenuItem(int newID, String menuItemName, float price){
        String addMenuItemCmd = "INSERT INTO MenuItems (MenuID, ItemName, Price) VALUES (?,?,?);";
        try {
            PreparedStatement prep = db.con.prepareStatement(addMenuItemCmd);
            prep.setInt(1, newID);
            prep.setString(2, menuItemName);
            prep.setFloat(3, price);
            prep.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }


    public boolean deleteMenuItem(int menuItemID){
        String deleteCmd = String.format("DELETE FROM menuitemIngredients WHERE MenuID= %d;", menuItemID);
        db.executeSQL(deleteCmd);
        deleteCmd = String.format("DELETE FROM menuitems WHERE MenuID= %d;", menuItemID);
        db.executeSQL(deleteCmd);
        return true;
    }
    
    public boolean addMenuItemIngredient(int menuItemID,int ingredientID){
        String addMenuIngCmd = String.format("INSERT menuitemIngredients (MenuID, IngredientID) values (%d,%d);", menuItemID, ingredientID);
        db.executeSQL(addMenuIngCmd);
        return true;
    }

    public boolean deleteMenuItemIngredient(int menuItemID, int ingredientID){
        String deleteCmd = String.format("DELETE FROM menuitemIngredients WHERE MenuID= %d AND ingredientID= %d;", menuItemID, ingredientID);
        db.executeSQL(deleteCmd);
        return true;
    }

    public boolean updateOrder(int orderID,String customerName,float basePrice,int employeeID){
        if (customerName != null && !customerName.isEmpty()) {
            String updateNameCmd = "UPDATE orders SET CustomerName = ? WHERE menuid = ?;";
            try {
                PreparedStatement prep = db.con.prepareStatement(updateNameCmd);
                prep.setString(1, customerName);
                prep.setInt(2, orderID);
                prep.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return false;
            }
        }

        if (basePrice > 0) {
            String updatePriceCmd = String.format("UPDATE orders SET baseprice = %.2f WHERE menuid = %d;", basePrice, orderID);
            db.executeSQL(updatePriceCmd);
            updatePriceCmd = String.format("UPDATE orders SET taxprice = %.2f WHERE menuid = %d;", basePrice * 0.0825f, orderID);
            db.executeSQL(updatePriceCmd);
        }
        
        if(employeeID > 0){
            String updateEmpCmd = String.format("UPDATE orders SET employeeID = %d WHERE menuid = %d;", employeeID, orderID);
            db.executeSQL(updateEmpCmd);           
        }

        return true;

    }

    public boolean deleteOrder(int orderID){
        String deleteCmd = String.format("DELETE FROM Orders WHERE OrderID= %d;", orderID);
        db.executeSQL(deleteCmd);
        return true;
    }


}


