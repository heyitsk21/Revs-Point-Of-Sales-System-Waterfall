import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;

public class managerCmds {
    /*
    TODO should include the sequel object or whatever yall use to login here so its shared between all calls
    */

    static sqlObjects.Employee getEmployee(string username, string password){
        //TODO get employee from database
        sqlObjects.Employee employee = new sqlObjects.Employee();
        return employee;
    }

    static sqlObjects.Inventory getInventory(){
        string[] ingredientNames = //TODO get all ingredient names
        float[] ppu = //TODO get price per units
        int[] count = //TODO get number of each ingredient
        sqlObjects.Inventory inventory = new sqlObjects.Inventory(ingredientNames, ppu, count);
        return inventory;
    }
    static sqlObjects.Menu getMenu(){
        string[] names = //TODO get all menu names
        float[] prices = //TODO get all menu item prices
        return sqlObects.Menu(names, prices);
    }
    static sqlObjects.OrderList getOrders(){
        string[] orderIDs = //TODO get all orderIDs
        string[] customerNames = //TODO get all customerNames
        float[] taxPrices = //TODO get all tax prices
        string[] orderTimes = //TODO get all order placement times
        int[] employeeIDs = //TODO get all employeeIDs
        return sqlObjects.OrderList(orderIDs, customerNames, taxPrices, orderTimes, employeeIDs);
    }
}
