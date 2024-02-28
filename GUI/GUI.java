import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.awt.Font;

public class GUI {
    public static void main(String[] args) {
        // Employee emp = new Employee();
        // emp.setVisible(true);

        //ManagerGUI manage = new ManagerGUI();
        //manage.setVisible(true);

        
        LogInGUI login = new LogInGUI();
        login.setVisible(true);
    }
}
