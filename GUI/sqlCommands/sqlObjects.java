public class sqlObjects {
    public static class Inventory {
        int[] ingredientIDs;
        String[] names;
        float[] ppu;
        int[] count;

        Inventory(int[] ingredientIDs, String[] names, float[] ppu, int[] count) {
            this.ingredientIDs = ingredientIDs;
            this.names = names;
            this.ppu = ppu;
            this.count = count;
        }

        int length() {
            return ingredientIDs.length;
        }
    }

    public static class Menu {
        int[] menuItemIDs;
        String[] names;
        float[] price;

        Menu(int[] menuItemIDs, String[] names, float[] price) {
            this.menuItemIDs = menuItemIDs;
            this.names = names;
            this.price = price;
        }

        int length() {
            return names.length;
        }
    }

    public static class OrderList {
        String[] orderIDs;
        String[] customerNames;
        float[] taxPrices;
        float[] basePrices;
        String[] orderTimes;
        int[] employeeIDs;

        OrderList(String[] orderIDs, String[] customerNames, float[] taxPrices, float[] basePrices, String[] orderTimes,
                int[] employeeIDs) {
            this.orderIDs = orderIDs;
            this.customerNames = customerNames;
            this.taxPrices = taxPrices;
            this.basePrices = basePrices;
            this.orderTimes = orderTimes;
            this.employeeIDs = employeeIDs;
        }

        int length() {
            return orderIDs.length;
        }
    }
}
