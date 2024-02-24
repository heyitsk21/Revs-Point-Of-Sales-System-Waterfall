import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.awt.*;

public class ManagerInventory extends JPanel {
    // Inventory myInventory = managerCmds.getInventory();
    int numberOfItems = 5; // = myInventory.length();
    int[] ingredientIDs = { 1, 2, 3, 4, 5 }; // = myInventory.ingredientIDs;
    String[] names = { "Item 1", "another ing", "cheese", "bread", "knucle sandwich" };
    int[] count = { 2, 5, 3, 99, 32, 45 };
    float[] ppu = { 1.234f, 1.234f, 1.234f, 1.234f, 1.234f };
    int currIngredientIndex = 0;

    JPanel rightPanel = new JPanel();
    JPanel leftPanel = new JPanel();

    public ManagerInventory() {
        setLayout(new GridBagLayout());
        createLeft();
        createRight();
        managerCmds manCmds = new managerCmds();
        sqlObjects.Inventory inventory = manCmds.getInventory();
        this.ingredientIDs = inventory.ingredientIDs;
        this.names = inventory.names;
        this.ppu = inventory.ppu;
        this.count = inventory.count;
    }

    void createLeft() {
        leftPanel.setLayout(new GridLayout(numberOfItems, 1)); // Vertical layout
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < numberOfItems; i++) {
            JButton button = new JButton(names[i] + ", Count: " + count[i]);
            button.addActionListener(new ButtonClickListener(this, String.valueOf(i)));
            button.setPreferredSize(new Dimension(300, 50));
            button.setFont(new Font("Arial", Font.PLAIN, 25));
            leftPanel.add(button);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        gbc.weightx = 0.75; // 75% of the horizontal space for the left component
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(leftPanel, gbc);
    }

    JLabel nameLabel = new JLabel();
    JLabel countLabel = new JLabel();
    JLabel ppuLabel = new JLabel();
    JLabel headerLabel = new JLabel("SET NEW AMOUNT");
    JTextField userInputField = new JTextField(10);
    JButton submitButton = new JButton();

    void createRight() {
        rightPanel.setLayout(new GridLayout(6, 1));
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Displays the name of the ingredient
        nameLabel.setText(names[currIngredientIndex]);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(nameLabel);

        // Displays the count of the ingredient remaining

        countLabel.setText(String.valueOf(count[currIngredientIndex]));
        countLabel.setHorizontalAlignment(SwingConstants.CENTER);
        countLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(countLabel);

        // Displays the price per ingredient

        ppuLabel.setText(String.valueOf(ppu[currIngredientIndex]));
        ppuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ppuLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        rightPanel.add(ppuLabel);

        // Creates a text box where the manager can set the new amount of item remaining

        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));

        limitInputToNumeric(userInputField);
        userInputField.setHorizontalAlignment(SwingConstants.CENTER);
        userInputField.setFont(new Font("Arial", Font.PLAIN, 25));
        userInputField.setText("0");

        submitButton.setText("Submit");
        submitButton.setHorizontalAlignment(SwingConstants.CENTER);
        submitButton.setFont(new Font("Arial", Font.PLAIN, 25));
        submitButton.addActionListener(new SubmitButtonListener());
        rightPanel.add(headerLabel);
        rightPanel.add(userInputField);
        rightPanel.add(submitButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(rightPanel, gbc);
    }

    void updateRight() {
        nameLabel.setText(names[currIngredientIndex]);
        countLabel.setText(String.valueOf(count[currIngredientIndex]));
        ppuLabel.setText(String.valueOf(ppu[currIngredientIndex]));
    }

    void updateLeft() {
        leftPanel.removeAll();
        for (int i = 0; i < numberOfItems; i++) {
            JButton button = new JButton(names[i] + ", Count: " + count[i]);
            button.addActionListener(new ButtonClickListener(this, String.valueOf(i)));
            button.setPreferredSize(new Dimension(300, 50));
            button.setFont(new Font("Arial", Font.PLAIN, 25));
            leftPanel.add(button);
        }
        leftPanel.revalidate();
        leftPanel.repaint();
    }

    private class ButtonClickListener implements ActionListener {
        private ManagerInventory managerInventory;
        private String buttonName;

        public ButtonClickListener(ManagerInventory managerInventory, String buttonName) {
            this.managerInventory = managerInventory;
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Perform actions when the button is clicked
            System.out.println("Ingredient clicked: " + buttonName);
            currIngredientIndex = Integer.parseInt(buttonName);
            updateRight();
        }
    }

    private class SubmitButtonListener implements ActionListener {
        int newAmount = 0;

        public SubmitButtonListener() {
            newAmount = Integer.parseInt(userInputField.getText());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Perform actions when the button is clicked
            if (userInputField.getText() != "") {
                newAmount = Integer.parseInt(userInputField.getText());
            }
            System.out.println("New amount: " + newAmount);

            // TODO send the IngredientID and new amount to sql and update the database and
            // screen with new amounts

            count[currIngredientIndex] = newAmount;
            updateRight();
            updateLeft();
        }
    }

    private static void limitInputToNumeric(JTextField textField) {
        AbstractDocument document = (AbstractDocument) textField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
}
