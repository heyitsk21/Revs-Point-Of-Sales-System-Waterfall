public class sqlObjects {
    public class Inventory {
        string[] names;
        float[] ppu;
        int[] count;
        Inventory(string[] names, float[] ppu, int[] count){
            this.names = names;
            this.ppu = ppu;
            this.count = count;
        }
    }
    public class Menu {
        string[] names;
        float[] price;
        Menu(string[] names, float[] price){
            this.names = names;
            this.price = price;
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
    }
    public class Employee{
        string employeeNames;
        string password;
        boolean isManager;
        
        Employee(string employeeNames, string password, boolean isManager){
            this.employeeNames = employeeNames;
            this.password = password;
            this.isManager = isManager;
        }
    }
}
