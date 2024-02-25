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

            String cmd = "SELECT * FROM Ingredients;";
            prep = db.con.prepareStatement(cmd, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            allIngredients = prep.executeQuery();

            // find the size of allIngredients
            allIngredients.last();
            size = allIngredients.getRow();

            int[] ingredientIDs = new int[size];
            String[] ingredientNames = new String[size];
            float[] ppu = new float[size];
            int[] count = new int[size];

            allIngredients.first();
            int counter = 0;

            while (allIngredients.next()) {
                ingredientIDs[counter] = allIngredients.getInt("IngredientID");
                ingredientNames[counter] = allIngredients.getString("IngredientName");
                ppu[counter] = allIngredients.getFloat("ppu");
                count[counter] = allIngredients.getInt("count");
                counter++;
            }
            sqlObjects.Inventory inventoryObj = new sqlObjects.Inventory(ingredientIDs, ingredientNames, ppu, count);
            return inventoryObj;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    // static sqlObjects.Menu getMenu(){
    // String[] names = ;//TODO get all menu names
    // float[] prices = ;//TODO get all menu item prices
    // return sqlObects.Menu(names, prices);
    // }

    // static sqlObjects.OrderList getOrders(){
    // String[] orderIDs = //TODO get all orderIDs
    // String[] customerNames = //TODO get all customerNames
    // float[] taxPrices = //TODO get all tax prices
    // String[] orderTimes = //TODO get all order placement times
    // int[] employeeIDs = //TODO get all employeeIDs
    // return sqlObjects.OrderList(orderIDs, customerNames, taxPrices, orderTimes,
    // employeeIDs);
    // }
}
