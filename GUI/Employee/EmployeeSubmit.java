import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.awt.*;

public class EmployeeSubmit extends JPanel {
    public EmployeeSubmit() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Enter a name for the order");
        add(label, BorderLayout.CENTER);
        JPanel submitScreen = new JPanel();
        submitScreen.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        //TODO: Add a (label?) which asks: "Enter a name for the order"

        //TODO: Add a text submission box so employee can type in the name
        /* code from manager that creates a text box
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));

        userInputField.setHorizontalAlignment(SwingConstants.CENTER);
        userInputField.setFont(new Font("Arial", Font.PLAIN, 25));
        userInputField.setText("");

        submitButton.setText("Submit");
        submitButton.setHorizontalAlignment(SwingConstants.CENTER);

        backButton.setText("Back");
        backButton.setHorizontalAlignment(SwingConstants.CENTER);
        
        submitPanel.add(headerLabel);
        submitPanel.add(userInputField);
        submitPanel.add(submitButton);
        submitPanel.add(backButton);
        
        GridBagConstraints gbc = new GridBagConstraints(); 
        gbc.fill = GridBagConstraints.BOTH;  // Fill both horizontally and vertically
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.gridx = 1; 
        gbc.gridy = 0;
        add(rightPanel, gbc);
        */

        // Create submit and back buttons and add them to the submitScreen panel
        JButton submit = new JButton("Submit");
        submitScreen.add(submit);
        JButton back = new JButton("Back");
        submitScreen.add(submit);

        //add Action Listeners and formatting for submit and back
        submit.addActionListener(new ButtonClickListener(this, "Submit"));
        submit.setPreferredSize(new Dimension(300, 50));
        submit.setFont(new Font("Arial", Font.PLAIN, 25));

        back.addActionListener(new ButtonClickListener(this, "Back"));
        back.setPreferredSize(new Dimension(300, 50));
        back.setFont(new Font("Arial", Font.PLAIN, 25));
    }

    //button click listener so things happen when buttons are clicked
    private class ButtonClickListener implements ActionListener {
        private EmployeeSubmit employeeSubmit;
        private String buttonName;

        public ButtonClickListener(EmployeeSubmit employeeSubmit, String buttonName) {
            this.employeeSubmit = employeeSubmit;
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // SUBMIT
            if (buttonName == "Submit") {
                System.out.println("Submit clicked");
                

                if (false /*the text entry box is empty*/) {
                    //TODO: display error when no name is given
                }
                else {
                    //TODO: update the database with the order and reset the Employee GUI
                }
                
            }
            else if (buttonName == "Back") {
                System.out.println("Back clicked");
                //TODO: go back to screen without changing anything
            }
        }
    }
}
