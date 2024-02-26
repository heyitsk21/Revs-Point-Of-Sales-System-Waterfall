import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.awt.*;

public class EmployeeDelete extends JPanel {
    JPanel deleteScreen = new JPanel();
    public EmployeeDelete() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Are you sure you want to delete?");
        add(label, BorderLayout.SOUTH);
        deleteScreen.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Create delete and back buttons and add them to the deleteScreen panel
        JButton delete = new JButton("Delete");
        deleteScreen.add(delete);
        JButton back = new JButton("Back");
        deleteScreen.add(back);

        //add Action Listeners and formatting for delete and back
        delete.addActionListener(new ButtonClickListener(this, "Delete"));
        delete.setPreferredSize(new Dimension(300, 50));
        delete.setFont(new Font("Arial", Font.PLAIN, 25));

        back.addActionListener(new ButtonClickListener(this, "Back"));
        back.setPreferredSize(new Dimension(300, 50));
        back.setFont(new Font("Arial", Font.PLAIN, 25));
    }

    //button click listener so things happen when buttons are clicked
    private class ButtonClickListener implements ActionListener {
        private EmployeeDelete employeeDelete;
        private String buttonName;

        public ButtonClickListener(EmployeeDelete employeeDelete, String buttonName) {
            this.employeeDelete = employeeDelete;
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // SUBMIT
            if (buttonName == "Delete") {
                System.out.println("Submit clicked");
                //TODO: remove the selected items from the submit order panel
            }
            else if (buttonName == "Back") {
                System.out.println("Back clicked");
                //TODO: go back to screen without changing anything
            }
        }
    }
}
