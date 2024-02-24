public class sqlObjects {
    public class Inventory {
        int[] ingredientIDs;
        string[] names;
        float[] ppu;
        int[] count;
        Inventory(int[] ingredientIDs, string[] names, float[] ppu, int[] count){
            this.ingredientIDs = ingredientIDs;
            this.names = names;
            this.ppu = ppu;
            this.count = count;
        }
        int length(){
            return size(ingredientIDs);
        }
    }
    public class Menu {
        string[] names;
        float[] price;
        Menu(string[] names, float[] price){
            this.names = names;
            this.price = price;
        }
        int length(){
            return size(names);
        }
    }
    public class OrderList {
        string[] orderIDs;
        string[] customerNames;
        float[] taxPrices;
        string[] orderTimes;
        int[] employeeIDs;
        OrderList(string[] orderIDs, string[] customerNames, float[] taxPrices, string[] orderTimes, int[] employeeIDs){
            this.orderIDs = orderIDs;
            this.customerNames = customerNames;
            this.taxPrices = taxPrices;
            this.orderTimes = orderTimes;
            this.employeeIDs = employeeIDs;
        }
        int length(){
            return size(orderIDs);
        }
    }
}
