import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class GUI extends JFrame implements ActionListener {
    static JFrame f;

    public static void main(String[] args) {
        ManagerGUI managerGUI = new ManagerGUI();
        managerGUI.setVisible(true);
    }

    // if button is pressed
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Close")) {
            f.dispose();
        }
    }
}
