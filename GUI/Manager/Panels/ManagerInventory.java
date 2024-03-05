
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class ManagerInventory extends JPanel {
    // Inventory myInventory = managerCmds.getInventory()
    int[] ingredientIDs; // = myInventory.ingredientIDs;
    String[] names;
    int[] count;
    int [] minamount;
    float[] ppu;
    int inventorySize;
    int currIngredientIndex = 0;

    managerCmds manCmds;
    Object[][] inventory;
    sqlObjects.Inventory myInventory;

    public ManagerInventory() {
        this.manCmds = new managerCmds();
        formatInventory();
        setLayout(new GridLayout(1, 2));
        createLeft();
        createRight();
    }

    private void formatInventory(){
        myInventory = manCmds.getInventory();
        this.ingredientIDs = myInventory.ingredientIDs;
        this.names = myInventory.names;
        this.ppu = myInventory.ppu;
        this.count = myInventory.count;
        this.minamount = myInventory.minamount;
        inventorySize = myInventory.ingredientIDs.length;
        inventory = new Object[inventorySize][3];
        for (int i = 0; i < inventorySize; i++){
            inventory[i][0] = myInventory.names[i];
            inventory[i][1] = myInventory.ppu[i];
            inventory[i][2] = myInventory.count[i];
        }
    }

    private void RefreshGUI(){
        formatInventory();
        updateLeft();
        updateRight();
    }

    JPanel leftPanel;
    JPanel buttonPanel;
    Font buttonFont = new Font("Arial", Font.PLAIN, 17);
    private JTable inventoryTable;
    private DefaultTableModel inventoryTableModel;
    JButton createButton, deleteButton;
    String[] columns;

    void createLeft() {
        leftPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel();
        columns = new String[]{"Ingredient Name", "Price Per Unit", "Count"};

        inventoryTableModel = new DefaultTableModel(inventory, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        inventoryTable = new JTable(inventoryTableModel);
        inventoryTable.setRowHeight(50);
        inventoryTable.setFont(new Font("Arial", Font.PLAIN, 15));
        leftPanel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);

        createButton = new JButton("Create");
        deleteButton = new JButton("Delete");
        createButton.addActionListener(e -> {
            int newID = 700;

            boolean isTaken;
            do {
                isTaken = false;
                for (int i = 0; i < ingredientIDs.length; ++i) {
                    if (newID == ingredientIDs[i]) {
                        isTaken = true;
                        newID++;
                        break;
                    }
                }
            } while (isTaken);
            manCmds.addIngredient(newID, "Unnamed Item", 0, 0.0f, 0);
            RefreshGUI();
        });
        deleteButton.addActionListener(e -> {
            if (currIngredientIndex >= 0 && currIngredientIndex < inventorySize) {
                int toDeleteID = ingredientIDs[currIngredientIndex];
                manCmds.deleteIngredient(toDeleteID, count[toDeleteID]);
            }
            RefreshGUI();
        });
        createButton.setFont(new Font("Arial", Font.PLAIN, 20));
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 20));
        buttonPanel.add(createButton);
        buttonPanel.add(deleteButton);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(leftPanel); // Add scrollPane to the frame

        ListSelectionModel selectionModel = inventoryTable.getSelectionModel();
        selectionModel.addListSelectionListener(e -> rowClicked(e));
    }
    void updateLeft() {
        inventoryTableModel.setDataVector(inventory, columns);
        inventoryTableModel.setRowCount(ingredientIDs.length);
    }

    JPanel rightPanel = new JPanel();
    JLabel nameLabel = new JLabel();
    JTextField nameInput = new JTextField(10);
    JLabel countLabel = new JLabel();
    JTextField countInput = new JTextField(10);
    JLabel ppuLabel = new JLabel();
    JTextField ppuInput = new JTextField(10);
    JLabel minAmntLabel = new JLabel();
    JTextField minAmnInput = new JTextField(10);
    JButton cancelButton = new JButton();
    JButton submitButton = new JButton();

    void createRight() {
        rightPanel.setLayout(new GridLayout(5, 2));
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        Font labelFont = new Font("Arial", Font.PLAIN, 25);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 15);

        // Displays the name of the ingredient
        nameLabel.setText("Name:");
        nameLabel.setFont(labelFont);
        rightPanel.add(nameLabel);
        nameInput.setFont(textFieldFont);
        rightPanel.add(nameInput);

        minAmntLabel.setText("Minimum amount:");
        minAmntLabel.setFont(labelFont);
        rightPanel.add(minAmntLabel);
        minAmnInput.setFont(textFieldFont);
        rightPanel.add(minAmnInput);

        countLabel.setText("Update count:");
        countLabel.setFont(labelFont);
        rightPanel.add(countLabel);
        countInput.setFont(textFieldFont);
        rightPanel.add(countInput);

        ppuLabel.setText("Price per unit:");
        ppuLabel.setFont(labelFont);
        rightPanel.add(ppuLabel);
        ppuInput.setFont(textFieldFont);
        rightPanel.add(ppuInput);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(e -> {
        });
        rightPanel.add(cancelButton);

        submitButton.setText("Submit");
        submitButton.addActionListener(e -> {
            int deltaCount = Integer.parseInt(countInput.getText()) - count[currIngredientIndex];
            int newMinimum = Integer.parseInt(minAmnInput.getText());
            manCmds.updateIngredient(ingredientIDs[currIngredientIndex], count[currIngredientIndex], nameInput.getText(), Float.parseFloat(ppuInput.getText()), deltaCount, newMinimum, "Updating ingredient");
            RefreshGUI();
        });
        rightPanel.add(submitButton);

        limitInputToNumeric(countInput);

        add(rightPanel);
    }

    void updateRight() {
        if (currIngredientIndex < 0){
            nameInput.setText("");
            countInput.setText("");
            ppuInput.setText("");
            minAmnInput.setText("");
        }else{
            nameInput.setText(names[currIngredientIndex]);
            countInput.setText(String.valueOf(count[currIngredientIndex]));
            ppuInput.setText(String.valueOf(ppu[currIngredientIndex]));
            minAmnInput.setText(String.valueOf(minamount[currIngredientIndex]));
        }
    }

    public void rowClicked(ListSelectionEvent event) {
        currIngredientIndex = inventoryTable.getSelectedRow();
        boolean rowSelected = false;
        if(currIngredientIndex >=0){
            rowSelected = true;
        }
        //Buttons enabled if a row is selected
        //setButtonState(rowSelected);

        if (rowSelected) {
            //Set the text fields with the values from the selected row
            updateRight();
        } else {
            //Clear the text fields if no row is selected
        }
    }

    //Enable or disable buttons
    private void setButtonState(boolean enabled) {
        createButton.setEnabled(true);
        deleteButton.setEnabled(enabled);
        cancelButton.setEnabled(enabled);
        submitButton.setEnabled(enabled);
    }

    private static void limitInputToNumeric(JTextField textField) {
        AbstractDocument document = (AbstractDocument) textField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("-?\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("-?\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
}
