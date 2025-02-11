import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Class to handle various operations related to employees, such as retrieving menu items and submitting orders.
 * 
 * @author Team 21 Best Table Winners
 */
public class employeeCmds {

    Database db;

    /**
     * Constructor to initialize the database connection.
     */
    public employeeCmds() {
        db = new Database();
    }

    /**
     * Retrieves a range of menu items from the database.
     * 
     * @param lowerBound Lower bound of the menu item IDs to retrieve.
     * @param upperBound Upper bound of the menu item IDs to retrieve.
     * @return Menu object containing the retrieved menu items.
     */
    public sqlObjects.Menu getMenu(int lowerBound, int upperBound) {
        try {
            int size = 0;
            PreparedStatement prep;
            ResultSet allMenuItems;

            String cmd = String.format("SELECT MenuID, ItemName, Price FROM MenuItems WHERE MenuID BETWEEN %d AND %d;", lowerBound, upperBound);
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
            } while (allMenuItems.next());

            sqlObjects.Menu menuItemObj = new sqlObjects.Menu(menuItemIDs, names, prices);
            return menuItemObj;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Submits an order based on selected menu items, customer name, and employee ID.
     * 
     * @param selectedMenuIDs List of selected menu item IDs.
     * @param customerName Name of the customer placing the order.
     * @param employeeID ID of the employee processing the order.
     * @return True if the order submission is successful, false otherwise.
     */
    public boolean submitOrder(List<Integer> selectedMenuIDs, String customerName, int employeeID) {
        Client c = new Client(true);
        c.requestAndWaitForLock();
        float totalPrice = 0;
        try {
            // STARTS TRANSACTION MODE, REMOVED IN FINALLY LOOP

            db.con.setAutoCommit(false);

            // GET HIGHEST OrderID -- MOVED OUTSIDE OF LOOP
            int newOrderID = 0;
            String orderIDQuery = "SELECT OrderID AS MaxID FROM Orders ORDER BY OrderID DESC Limit 1";
            Statement orderIDStmt = db.con.createStatement();
            ResultSet orderIDResult = orderIDStmt.executeQuery(orderIDQuery);
            if (orderIDResult.next()) {
                newOrderID = orderIDResult.getInt("MaxID") + 1;
            }

            // CALCULATE TOTAL PRICE
            String list = selectedMenuIDs.toString();

            StringBuffer strbuf = new StringBuffer(list);
            strbuf.setCharAt(0, '(');
            strbuf.setCharAt(strbuf.length() - 1, ')');
            // STRING CONCAT OK SINCE THIS HAS TO COME FROM ARRAY LIST TYPE
            String totalPriceQuery = "SELECT SUM(Price) FROM MenuItems WHERE MenuID IN " + strbuf.toString();
            PreparedStatement totalPricePrep = db.con.prepareStatement(totalPriceQuery);
            ResultSet totalPriceResult = totalPricePrep.executeQuery();
            if (totalPriceResult.next()) {
                float price = totalPriceResult.getFloat("sum");
                totalPrice += price;
            }

            HashMap<Integer, Integer> ingredientToCountInOrder = new HashMap<>();
            // ITERATE OVER MENU ITEMS
            for (Integer selectedMenuID : selectedMenuIDs) {
                // QUERY EVERY INGREDIENT FOR MENU ITEM
                String ingredientQuery = "SELECT Ingredients.IngredientID, Ingredients.MinAmount, ingredients.Count FROM menuitems JOIN menuitemingredients ON menuitems.MenuID = menuitemingredients.MenuID JOIN Ingredients ON menuitemingredients.IngredientID = Ingredients.IngredientID WHERE menuitems.MenuID = ? GROUP BY Ingredients.IngredientID";
                PreparedStatement ingredientPrep = db.con.prepareStatement(ingredientQuery);
                ingredientPrep.setInt(1, selectedMenuID);
                ResultSet ingredientResult = ingredientPrep.executeQuery();

                // LOOP THROUGH EACH INGREDIENT
                while (ingredientResult.next()) {
                    int ingredientID = ingredientResult.getInt("IngredientID");
                    int requiredCount = ingredientResult.getInt("MinAmount");
                    int availableCount = ingredientResult.getInt("Count");

                    // COMPARE IF INGREDIENTS LESS THAN REQUIRED
                    Integer currCount = ingredientToCountInOrder.getOrDefault(ingredientID, 0);
                    if (availableCount - currCount < requiredCount) {
                        c.releaseLock();
                        c.quit();
                        db.con.rollback(); // Rollback transaction
                        return false;
                    }

                    // increment by one in hashmap
                    ingredientToCountInOrder.put(ingredientID, ingredientToCountInOrder.getOrDefault(ingredientID, 0) + 1);
                }

            }

            for (Map.Entry<Integer, Integer> set : ingredientToCountInOrder.entrySet()) {
                Integer ingredientID = set.getKey();
                Integer count = set.getValue();

                String updateQuery = "UPDATE Ingredients SET Count = Count - ? WHERE IngredientID = ?";
                PreparedStatement updatePrep = db.con.prepareStatement(updateQuery);
                updatePrep.setInt(1, count);
                updatePrep.setInt(2, ingredientID);
                updatePrep.executeUpdate();

                String logMessage = "Order placed: " + newOrderID;
                String logQuery = "INSERT INTO InventoryLog (IngredientID, AmountChanged, LogMessage, LogDateTime) VALUES (?, ?, ?, NOW())";
                PreparedStatement logPrep = db.con.prepareStatement(logQuery);
                logPrep.setInt(1, ingredientID);
                logPrep.setInt(2, -count); // Negative value indicates deduction
                logPrep.setString(3, logMessage);
                logPrep.executeUpdate();
            }

            // INSERT ORDER INTO TABLE
            String orderQuery = "INSERT INTO Orders (OrderID, CustomerName, TaxPrice, BasePrice, OrderDateTime, EmployeeID) VALUES (?, ?, ?, ?, NOW(), ?)";
            PreparedStatement orderPrep = db.con.prepareStatement(orderQuery);
            orderPrep.setInt(1, newOrderID);
            orderPrep.setString(2, customerName);
            orderPrep.setFloat(3, totalPrice * 0.0825f);
            orderPrep.setFloat(4, totalPrice);
            orderPrep.setInt(5, employeeID);
            orderPrep.executeUpdate();

            for (Integer selectedMenuID : selectedMenuIDs) {
                String junctionQuery = "INSERT INTO OrderMenuItems (OrderID, MenuID) VALUES (?, ?)";
                PreparedStatement junctionPrep = db.con.prepareStatement(junctionQuery);
                junctionPrep.setInt(1, newOrderID);
                junctionPrep.setInt(2, selectedMenuID);
                junctionPrep.executeUpdate();
            }

            // COMMIT TRANSACTION
            db.con.commit();
            c.releaseLock();
            c.quit();
            // IF SUCCESS, RETURN TRUE
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            try {
                // ROLLBACK IF EXCEPTION
                db.con.rollback();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
            c.releaseLock();
            c.quit();
            return false;
        } finally {
            try {
                // RESET AUTO COMMIT -- SQL COMMANDS RUN INDIVIDUALLY AGAIN
                db.con.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    /**
     * Calculates the total price of the order based on selected menu items.
     * 
     * @param selectedMenuIDs List of selected menu item IDs.
     * @return Total price of the order.
     */
    public float getOrderPrice(List<Integer> selectedMenuIDs) {
        float totalPrice = 0;
        try {
            for (Integer selectedMenuID : selectedMenuIDs) {
                // CALCULATE TOTAL PRICE
                String totalPriceQuery = "SELECT Price FROM MenuItems WHERE MenuID = ?";
                PreparedStatement totalPricePrep = db.con.prepareStatement(totalPriceQuery);
                totalPricePrep.setInt(1, selectedMenuID);
                ResultSet totalPriceResult = totalPricePrep.executeQuery();
                if (totalPriceResult.next()) {
                    float price = totalPriceResult.getFloat("Price");
                    totalPrice += price;
                }
            }
            return totalPrice;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return 0.0f;
        }
    }
}
