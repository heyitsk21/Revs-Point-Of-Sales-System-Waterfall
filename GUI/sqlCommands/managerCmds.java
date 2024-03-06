import java.sql.*;


/**
 * Class to handle various operations related to managers, such as managing inventory, menu items, and orders.
 * @author best table winners
 */
public class managerCmds{
    Database db;
    /**
     * Constructor to initialize the database connection.
     */
    public managerCmds() {
        db = new Database();
    }


        /**
     * Retrieves the inventory from the database.
     * 
     * @return Inventory object containing the retrieved inventory items.
     */
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
    /**
     * Retrieves the menu items from the database.
     * 
     * @return Menu object containing the retrieved menu items.
     */
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
   /**
     * Retrieves the ingredients of a menu item from the database.
     * 
     * @param menuItemID ID of the menu item.
     * @return Ingredients object containing the retrieved ingredients from the given Menu Item.
     */
    public sqlObjects.MenuItemIngredients getMenuItemIngredients(int menuItemID){
        try{
    
            int size = 0;
            PreparedStatement prep;
            ResultSet allMenuItemIngredients;

            String cmd = String.format("SELECT Ingredients.IngredientID, Ingredients.IngredientName " +
                "FROM menuitems JOIN menuitemingredients ON menuitems.MenuID = menuitemingredients.MenuID " +
                "JOIN Ingredients ON menuitemingredients.IngredientID = Ingredients.IngredientID " +
                "WHERE menuitems.MenuID = %d;", menuItemID);
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
                ingredientIDs[counter] = allMenuItemIngredients.getInt("IngredientID"); //TODO: check if this columnLabel works. unsure if this is necessary
                names[counter] = allMenuItemIngredients.getString("IngredientName");
                counter++;
            } while (allMenuItemIngredients.next()) ;

            sqlObjects.MenuItemIngredients menuItemIngredientObj = new sqlObjects.MenuItemIngredients(ingredientIDs, names);
            return menuItemIngredientObj;
        } catch (SQLException e) {
            //System.err.println(e.getMessage()); catch the null case and then return
        }
        return new sqlObjects.MenuItemIngredients(new int[0],new String[0]);
    }

    /**
     * Retrieves the list of orders from the database.
     * 
     * @return OrderList object containing the retrieved orders.
     */
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


    /**
 * Updates the information of an ingredient in the database.
 * 
 * @param ingredientID   The ID of the ingredient to be updated.
 * @param currentCount   The current count of the ingredient.
 * @param newName        The new name of the ingredient (null or empty to skip).
 * @param newPPU         The new price per unit of the ingredient (0 or negative to skip).
 * @param deltaCount     The change in count of the ingredient.
 * @param newMinimum     The new minimum amount of the ingredient (0 to skip).
 * @param logMessage     The log message associated with the update.
 * @return True if the update was successful, false otherwise.
 */

    public boolean updateIngredient(int ingredientID, int currentCount, String newName, float newPPU, int deltaCount, int newMinimum, String logMessage) {
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

        if (newMinimum != 0){
            String updatePPUCmd = String.format("UPDATE Ingredients SET minamount = %d WHERE IngredientID = %d;", newMinimum, ingredientID);
            db.executeSQL(updatePPUCmd);
        }

        return true; // Update successful
    }


    /**
 * Adds a new ingredient to the database.
 * 
 * @param newID          The ID of the new ingredient.
 * @param ingredientName The name of the new ingredient.
 * @param count          The count of the new ingredient.
 * @param PPU            The price per unit of the new ingredient.
 * @param minamount      The minimum amount of the new ingredient.
 * @return True if the addition was successful, false otherwise.
 */
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


    /**
 * Deletes an ingredient from the database.
 * 
 * @param ingredientID    The ID of the ingredient to be deleted.
 * @param ingredientCount The count of the ingredient to be deleted.
 * @return True if the deletion was successful, false otherwise.
 */
    public boolean deleteIngredient(int ingredientID, int ingredientCount){

        String deleteIngredientFromJoinCmd = String.format("DELETE FROM MenuItemIngredients WHERE IngredientID = %d", ingredientID);
        db.executeSQL(deleteIngredientFromJoinCmd);

        String deleteIngredientCmd = String.format("DELETE FROM Ingredients WHERE IngredientID = %d", ingredientID);
        db.executeSQL(deleteIngredientCmd);


        int negateCount = ingredientCount * -1;
        String deleteLogCmd = String.format( 
            "INSERT INTO InventoryLog (IngredientID, AmountChanged, LogMessage, LogDateTime) VALUES (%d, %d, '%s', NOW());",
            ingredientID, negateCount, "INGREDIENT COUNT SET TO 0: DELETED INGREDIENT WITH ID ", ingredientID);
        db.executeSQL(deleteLogCmd);
        
        return true;
    }


    /**
 * Updates the information of a menu item in the database.
 * 
 * @param menuItemID The ID of the menu item to be updated.
 * @param newName    The new name of the menu item (null or empty to skip).
 * @param newPrice   The new price of the menu item (0 or negative to skip).
 * @return True if the update was successful, false otherwise.
 */
    public boolean updateMenuItem(int menuItemID, String newName, float newPrice){
    
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


    /**
 * Adds a new menu item to the database.
 * 
 * @param newID        The ID of the new menu item.
 * @param menuItemName The name of the new menu item.
 * @param price        The price of the new menu item.
 * @return True if the addition was successful, false otherwise.
 */
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

    /**
 * Deletes a menu item from the database.
 * 
 * @param menuItemID The ID of the menu item to be deleted.
 * @return True if the deletion was successful, false otherwise.
 */
    public boolean deleteMenuItem(int menuItemID){
        String deleteCmd = String.format("DELETE FROM menuitemIngredients WHERE MenuID= %d;", menuItemID);
        db.executeSQL(deleteCmd);
        deleteCmd = String.format("DELETE FROM menuitems WHERE MenuID= %d;", menuItemID);
        db.executeSQL(deleteCmd);
        return true;
    }
    
    /**
 * Adds an ingredient to a menu item in the database.
 * 
 * @param menuItemID   The ID of the menu item.
 * @param ingredientID The ID of the ingredient to be added to the menu item.
 * @return True if the addition was successful, false otherwise.
 */
    public boolean addMenuItemIngredient(int menuItemID,int ingredientID){
        String addMenuIngCmd = String.format("INSERT INTO menuitemIngredients (MenuID, IngredientID) values (%d,%d);", menuItemID, ingredientID);
        db.executeSQL(addMenuIngCmd);
        return true;
    }

    /**
 * Deletes an ingredient from a menu item in the database.
 * 
 * @param menuItemID   The ID of the menu item.
 * @param ingredientID The ID of the ingredient to be deleted from the menu item.
 * @return True if the deletion was successful, false otherwise.
 */
    public boolean deleteMenuItemIngredient(int menuItemID, int ingredientID){
        String deleteCmd = String.format("DELETE FROM menuitemIngredients WHERE MenuID= %d AND ingredientID= %d;", menuItemID, ingredientID);
        db.executeSQL(deleteCmd);
        return true;
    }

    /**
 * Updates the information of an order in the database.
 * 
 * @param orderID      The ID of the order to be updated.
 * @param customerName The new name of the customer (null or empty to skip).
 * @param basePrice    The new base price of the order (0 or negative to skip).
 * @param employeeID   The ID of the employee associated with the order (0 or negative to skip).
 * @return True if the update was successful, false otherwise.
 */
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

    /**
 * Deletes an order from the database.
 * 
 * @param orderID The ID of the order to be deleted.
 * @return True if the deletion was successful, false otherwise.
 */
    public boolean deleteOrder(int orderID){
        String deleteCmd = String.format("DELETE FROM Orders WHERE OrderID= %d;", orderID);
        db.executeSQL(deleteCmd);
        return true;
    }

    
/**
 * Generates a restock report of inventory items with counts below their minimum amounts.
 * 
 * @return Inventory object containing the inventory items that need restocking.
 */
    public sqlObjects.Inventory RestockReport(){
        try {
            int size = 0;
            PreparedStatement prep;
            ResultSet allIngredients;

            String cmd = "SELECT * FROM Ingredients WHERE COUNT < MINAMOUNT ORDER BY IngredientID;";
            prep = db.con.prepareStatement(cmd, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            allIngredients = prep.executeQuery();

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

/**
 * Generates a report on ordering trends within a specified date range.
 * 
 * @param lowerBound The lower bound of the date range (YYYY-MM-DD).
 * @param upperBound The upper bound of the date range (YYYY-MM-DD).
 * @return OrderingTrendReport object containing the ordering trends within the specified date range.
 */
    public sqlObjects.OrderingTrendReport OrderingTrendReport(String lowerBound, String upperBound){
        try {
            int size = 0;
            PreparedStatement prep;
            ResultSet rs;
            String cmd = "SELECT DISTINCT MID1, MID2, Count (*) AS count FROM (SELECT n1.itemname AS MID1, n2.itemname AS MID2, t1.OrderID FROM OrderMenuItems t1 JOIN OrderMenuItems t2 ON t1.OrderID = t2.OrderID AND t1.menuID <  t2.MenuID JOIN Orders ON Orders.OrderID = t1.OrderID Join MenuItems n1 ON t1.MenuID = n1.MenuID JOIN MenuItems n2 on n2.MenuID = t2.MenuID WHERE Orders.OrderDateTime BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)) AS doubleJoin  GROUP BY MID1, MID2 ORDER BY count DESC LIMIT 10;";
            prep = db.con.prepareStatement(cmd, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            prep.setString(1, lowerBound);
            prep.setString(2, upperBound);
            rs = prep.executeQuery();


            rs.last();
            size = rs.getRow();

            String[] menuID2 = new String[size];
            String[] menuID1 = new String[size];
            int[] count = new int[size];

            rs.first();
            int counter = 0;

            do {
                menuID1[counter] = rs.getString("mid1");
                menuID2[counter] = rs.getString("mid2");
                count[counter] = rs.getInt("count");
                counter++;
            }while (rs.next());
            sqlObjects.OrderingTrendReport chartObj = new sqlObjects.OrderingTrendReport(menuID1,menuID2,count);
            return chartObj;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;

    }

/**
 * Generates a chart of product usage within a specified date range.
 * 
 * @param lowerBound The lower bound of the date range.
 * @param upperBound The upper bound of the date range.
 * @return ProductUsageChart object containing the product usage within the specified date range.
 */
    public sqlObjects.ProductUsageChart ProductUsageChart(java.sql.Date lowerBound, java.sql.Date upperBound){
        try {
            int size = 0;
            PreparedStatement prep;
            ResultSet rs;

            String cmd = "SELECT IngredientID, SUM(AmountChanged) FROM InventoryLog  WHERE AmountChanged < 0 AND DATE(LogDateTime) BETWEEN '" +lowerBound.toString()+"' AND '"+ upperBound.toString() + "' GROUP BY IngredientID";
            prep = db.con.prepareStatement(cmd, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = prep.executeQuery();


            rs.last();
            size = rs.getRow();

            int[] ingredientIDs = new int[size];
            float[] amountUsed = new float[size];

            rs.first();
            int counter = 0;

            do {
                ingredientIDs[counter] = rs.getInt("IngredientID");
                amountUsed[counter] = rs.getInt("sum");
                counter++;
            }while (rs.next());
            sqlObjects.ProductUsageChart chartObj = new sqlObjects.ProductUsageChart(ingredientIDs, amountUsed);
            return chartObj;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;

    }
}


