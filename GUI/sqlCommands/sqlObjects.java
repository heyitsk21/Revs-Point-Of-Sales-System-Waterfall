/**
 * The sqlObjects class contains several nested static classes that neatly package data to be recived from the database.
 * 
 * @author Team 21 Best Table Winners
 */
public class sqlObjects {

    /**
     * Represents the inventory of ingredients in the database.
     */
    public static class Inventory {
        int[] ingredientIDs;
        String[] names;
        float[] ppu;
        int[] count;
        int[] minamount;

        /**
         * Constructs an Inventory object.
         *
         * @param ingredientIDs The IDs of ingredients.
         * @param names         The names of ingredients.
         * @param ppu           The price per unit of ingredients.
         * @param count         The count of ingredients.
         * @param minamount     The minimum amount of ingredients.
         */
        Inventory(int[] ingredientIDs, String[] names, float[] ppu, int[] count, int[] minamount) {
            this.ingredientIDs = ingredientIDs;
            this.names = names;
            this.ppu = ppu;
            this.count = count;
            this.minamount = minamount;
        }

        /**
         * Gets the length of the inventory.
         *
         * @return The length of the inventory.
         */
        int length() {
            return ingredientIDs.length;
        }
    }

    /**
     * Represents the menu items in the database.
     */
    public static class Menu {
        int[] menuItemIDs;
        String[] names;
        float[] prices;

        /**
         * Constructs a Menu object.
         *
         * @param menuItemIDs The IDs of menu items.
         * @param names       The names of menu items.
         * @param prices      The prices of menu items.
         */
        Menu(int[] menuItemIDs, String[] names, float[] prices) {
            this.menuItemIDs = menuItemIDs;
            this.names = names;
            this.prices = prices;
        }

        /**
         * Gets the length of the menu.
         *
         * @return The length of the menu.
         */
        int length() {
            return names.length;
        }
    }

    /**
     * Represents the list of orders in the database.
     */
    public static class OrderList {
        String[] orderIDs;
        String[] customerNames;
        float[] taxPrices;
        float[] basePrices;
        String[] orderTimes;
        int[] employeeIDs;

        /**
         * Constructs an OrderList object.
         *
         * @param orderIDs      The IDs of orders.
         * @param customerNames The names of customers.
         * @param taxPrices     The tax prices of orders.
         * @param basePrices    The base prices of orders.
         * @param orderTimes    The times of orders.
         * @param employeeIDs   The IDs of employees.
         */
        OrderList(String[] orderIDs, String[] customerNames, float[] taxPrices, float[] basePrices, String[] orderTimes,
                  int[] employeeIDs) {
            this.orderIDs = orderIDs;
            this.customerNames = customerNames;
            this.taxPrices = taxPrices;
            this.basePrices = basePrices;
            this.orderTimes = orderTimes;
            this.employeeIDs = employeeIDs;
        }

        /**
         * Gets the length of the order list.
         *
         * @return The length of the order list.
         */
        int length() {
            return orderIDs.length;
        }
    }

    /**
     * Represents the ingredients of a menu item.
     */
    public static class MenuItemIngredients {
        int[] ingredientIDs;
        String[] names;

        /**
         * Constructs a MenuItemIngredients object.
         *
         * @param ingredientIDs The IDs of ingredients.
         * @param names         The names of ingredients.
         */
        MenuItemIngredients(int[] ingredientIDs, String[] names) {
            this.ingredientIDs = ingredientIDs;
            this.names = names;
        }

        /**
         * Gets the length of the menu item ingredients.
         *
         * @return The length of the menu item ingredients.
         */
        int length() {
            return ingredientIDs.length;
        }
    }

    /**
     * Represents the product usage chart in the database.
     */
    public static class ProductUsageChart {
        int[] ingredientIDs;
        float[] amountUsed;

        /**
         * Constructs a ProductUsageChart object.
         *
         * @param ingredientIDs The IDs of ingredients.
         * @param amountUsed    The amount of each ingredient used.
         */
        ProductUsageChart(int[] ingredientIDs, float[] amountUsed) {
            this.ingredientIDs = ingredientIDs;
            this.amountUsed = amountUsed;
        }

        /**
         * Gets the length of the product usage chart.
         *
         * @return The length of the product usage chart.
         */
        int length() {
            return ingredientIDs.length;
        }

    }

    /**
     * Represents the ordering trend report in the database.
     */
    public static class OrderingTrendReport {
        String[] menuID1;
        String[] menuID2;
        int[] count;

        /**
         * Constructs an OrderingTrendReport object.
         *
         * @param menuID1 The IDs of menu items in the first period.
         * @param menuID2 The IDs of menu items in the second period.
         * @param count   The count of orders for each menu item.
         */
        OrderingTrendReport(String[] menuID1, String[] menuID2, int[] count) {
            this.menuID1 = menuID1;
            this.menuID2 = menuID2;
            this.count = count;
        }

        /**
         * Gets the length of the ordering trend report.
         *
         * @return The length of the ordering trend report.
         */
        int length() {
            return count.length;
        }
    }
}
