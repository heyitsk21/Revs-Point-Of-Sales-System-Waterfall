/**
 * ManagerMenuItems class represents a JPanel for the manager to view and edit menu items.
 * It includes functionalities such as creating new items, deleting existing items, and editing attributes.
 * The panel consists of two main sections: a table on the left displaying menu items and a panel on the right
 * for editing and submitting changes.
 *
 * Usage:
 * - Click on a row in the table to enable the buttons.
 * - After clicking on a row, create new items and delete existing items.
 * - Edit attributes in the panel on the right-hand side.
 *
 * Note: Currently, the ingredients are displayed as a textbox and need to be changed into a table.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManagerMenuItems extends JPanel {
    // Member variables for UI components
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private JButton createButton, deleteButton, cancelButton, submitButton;
    private JLabel nameLabel, priceLabel, ingredientsLabel;
    private JTextField nameTextField, priceTextField;
    private JPanel rightPanel, leftPanel, buttonPanel;

    // Other member variables
    managerCmds manCmds;
    int currentItem;
    sqlObjects.Menu initialMenu;
    sqlObjects.Inventory myInventory;
    Object[][] menu;

    /**
     * Constructor for ManagerMenuItems class.
     * Initializes the UI components and sets up the layout.
     * Disables buttons until a row is selected.
     */
    public ManagerMenuItems() {
        manCmds = new managerCmds();
        setLayout(new GridLayout(1, 2));

        initialMenu = manCmds.getMenu();
        menu = formatMenuItems(initialMenu);
        createLeftPanel();
        createRightPanel();
        //Disable buttons until a row is selected
        setButtonState(false);
    }

    /**
     * Helper method to enable or disable buttons based on the provided boolean value.
     *
     * @param enabled True to enable buttons, false to disable.
     */
    private void setButtonState(boolean enabled) {
        createButton.setEnabled(true);
        deleteButton.setEnabled(enabled);
        cancelButton.setEnabled(enabled);
        submitButton.setEnabled(enabled);
    }

    /**
     * Helper method to format menu items retrieved from the database into a 2D array for UI display.
     *
     * @param myMenu The Menu object containing menu item details.
     * @return A 2D array representing menu items for display in the UI.
     */
    private Object[][] formatMenuItems(sqlObjects.Menu myMenu){
        int size = myMenu.menuItemIDs.length;
        Object[][] menuItems = new Object[size][3];
        for (int i = 0; i < size; i++){
            menuItems[i][0] = myMenu.menuItemIDs[i];
            menuItems[i][1] = myMenu.names[i];
            menuItems[i][2] = myMenu.prices[i];
        }

        return menuItems;
    }

    /**
     * Creates the left panel containing a table displaying menu items and buttons for interaction.
     */
    private void createLeftPanel() {
        leftPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel();
        String[] columns = {"MenuID", "Menu Item Name", "Price"};

        

        //if nothing is returned from the database, formatMenuItems will throw an exception so adding a null check
        //if menu is null, we will just show the placeholder values

        tableModel = new DefaultTableModel(menu, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        menuTable = new JTable(tableModel);
        menuTable.setRowHeight(50);
        menuTable.setFont(new Font("Arial", Font.PLAIN, 15));

        leftPanel.add(new JScrollPane(menuTable), BorderLayout.CENTER);

        createButton = new JButton("Create");
        deleteButton = new JButton("Delete");
        createButton.setFont(new Font("Arial", Font.PLAIN, 20));
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 20));
        buttonPanel.add(createButton);
        buttonPanel.add(deleteButton);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(leftPanel);

        //action listeners are defined in classes at the bottom of the file
        ListSelectionModel selectionModel = menuTable.getSelectionModel();
        selectionModel.addListSelectionListener(e -> rowClicked(e));
        createButton.addActionListener(new CreateButtonListener());
        deleteButton.addActionListener(new DeleteButtonListener());
    }

    JLabel checkedItemsLabel = new JLabel("");
    JPanel ingredientsPanel = new JPanel();

    /**
     * Creates the right panel containing text fields and buttons for editing and submitting changes.
     */
    private void createRightPanel() {
        rightPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        nameLabel = new JLabel("Name: ");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        priceLabel = new JLabel("Price: ");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        ingredientsLabel = new JLabel("Ingredients: ");
        ingredientsLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        ingredientsPanel.setLayout(new GridLayout(2, 1));
        ingredientsPanel.add(ingredientsLabel);
        ingredientsPanel.add(checkedItemsLabel);

        nameTextField = new JTextField();
        priceTextField = new JTextField();
        nameTextField.setFont(new Font("Arial", Font.PLAIN, 15));
        priceTextField.setFont(new Font("Arial", Font.PLAIN, 15));

        JPanel ingredientDrop = createIngredientBox();

        cancelButton = new JButton("Cancel");
        submitButton = new JButton("Submit");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 25));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 25));

        //Add the labels and text fields to the right panel
        rightPanel.add(nameLabel);
        rightPanel.add(nameTextField);
        rightPanel.add(priceLabel);
        rightPanel.add(priceTextField);
        rightPanel.add(ingredientsPanel);
        rightPanel.add(ingredientDrop);
        rightPanel.add(cancelButton);
        rightPanel.add(submitButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(rightPanel);

        //adjust the values in the table when the submit button is clicked
        submitButton.addActionListener(new SubmitButtonListener());
        //clear the text fields when the cancel button is clicked
        cancelButton.addActionListener(new CancelButtonListener());

    }

    String selectedItem;
    JComboBox<String> comboBox;
    List<String> checkedItems;
    Map<String, Integer> ingredientNametoID = new HashMap<>();
    Map<Integer, String> ingredientIDtoName = new HashMap<>();

    /**
     * Creates a panel with a dropdown for selecting ingredients.
     *
     * @return JPanel containing ingredient selection components.
     */
    public JPanel createIngredientBox(){
        myInventory = manCmds.getInventory();
        comboBox = new JComboBox<>();
        checkedItems = new ArrayList<>();
        

        String[] ingredientNames = myInventory.names;
        int[] ingredientIDs = myInventory.ingredientIDs;
        for (int i = 0; i < ingredientIDs.length; ++i){
            ingredientNametoID.put(ingredientNames[i], ingredientIDs[i]);
            ingredientIDtoName.put(ingredientIDs[i], ingredientNames[i]);
        }

        selectedItem = ingredientNames[0];
        updateCheckedItemsLabel(); // Call the method with the label

        // Populate the JComboBox with items
        comboBox.setModel(new DefaultComboBoxModel<>(ingredientNames));

        // For when an item is selected in combobox
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When an item is selected, check/uncheck it and update the list
                selectedItem = (String) comboBox.getSelectedItem();
            }
        });

        // Create buttons for adding and removing ingredients
        JButton addButton = new JButton("Add Ingredient");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkedItems.contains(selectedItem)) {
                    checkedItems.add(selectedItem);
                    updateCheckedItemsLabel();
                    //manCmds.addMenuItemIngredient((int)menu[currentItem][0], ingredientNametoID.get(selectedItem));
                }
            }
        });

        JButton removeButton = new JButton("Remove Ingredient");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkedItems.contains(selectedItem)) {
                    checkedItems.remove(selectedItem);
                    updateCheckedItemsLabel();
                    //manCmds.deleteMenuItemIngredient((int)menu[currentItem][0], ingredientNametoID.get(selectedItem));
                }
            }
        });

        // Create a panel and add components to it
        JPanel panel = new JPanel();
        panel.add(new JLabel("Select items: "));
        panel.add(comboBox);
        panel.add(new JLabel("New Ingredient: "));
        panel.add(addButton);
        panel.add(removeButton);
        return panel;
    }

    /**
     * Updates the label displaying checked items based on the current list of selected items.
     */
    private void updateCheckedItemsLabel() {
        StringBuilder labelText = new StringBuilder("");
        for (String item : checkedItems) {
            labelText.append(item).append(", ");
        }
        // Set the label text directly
        checkedItemsLabel.setText(labelText.toString());
    }

    /**
     * Event listener for row selection in the menu table.
     *
     * @param event ListSelectionEvent triggered on row selection.
     */
    public void rowClicked(ListSelectionEvent event) {
        int selectedRow = menuTable.getSelectedRow();
        boolean rowSelected = false;
        if(selectedRow >=0){
            rowSelected = true;
        }
        //Buttons enabled if a row is selected
        setButtonState(rowSelected);

        if (rowSelected) {
            //Set the text fields with the values from the selected row
            nameTextField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 1)));
            priceTextField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
            sqlObjects.MenuItemIngredients menuIng = manCmds.getMenuItemIngredients((int)tableModel.getValueAt(selectedRow, 0));
            //sqlObjects.MenuItemIngredients menuIng = manCmds.getMenuItemIngredients(101);
            if (menuIng != null){
                checkedItems.clear();
                for (int i = 0; i < menuIng.length(); ++i){
                    checkedItems.add(ingredientIDtoName.get(menuIng.ingredientIDs[i]));
                }
                updateCheckedItemsLabel();
            }else{
                checkedItems.clear();
                updateCheckedItemsLabel();
            }
        } else {
            //Clear the text fields if no row is selected
            nameTextField.setText("");
            priceTextField.setText("");
        }
    }

    /**
     * Refreshes the GUI by updating the menu items and recreating the UI components.
     */
    public void refreshGUI(){
        removeAll();
        initialMenu = manCmds.getMenu();
        menu = formatMenuItems(initialMenu);
        createLeftPanel();
        createRightPanel();
        //Disable buttons until a row is selected
        setButtonState(false);
        repaint();
    }

    /**
     * Event listener for the "Create" button.
     * Adds a new menu item with a unique ID and refreshes the GUI.
     */
    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //get the biggest ID and add 1 TODO add category functionality when that is implemented in the front end
            int newID = 700;
            boolean isTaken;
    
            do {
                isTaken = false;
                for (Object[] menuItem : menu) {
                    if (newID == (int) menuItem[0]) {
                        isTaken = true;
                        newID++;
                        break;
                    }
                }
            } while (isTaken);
    
            manCmds.addMenuItem(newID, "NewMenu Item", 0.0f);
            refreshGUI();
        }
    }
    
    /**
     * Event listener for the "Delete" button.
     * Deletes the selected menu item and refreshes the GUI.
     */
    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < menu.length) {
                int toDeleteID = (int) initialMenu.menuItemIDs[selectedRow];
                manCmds.deleteMenuItem(toDeleteID);
            }
            refreshGUI();
            // TODO sql here
        }
    }
    
    /**
     * Event listener for the "Cancel" button.
     * Clears the text fields and deselects rows in the menu table.
     */
    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            menuTable.clearSelection();
            nameTextField.setText("");
            priceTextField.setText("");
        }
    }

    /**
     * Event listener for the "Submit" button.
     * Updates the selected menu item with edited attributes and ingredients, then refreshes the GUI.
     */
    private class SubmitButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = menuTable.getSelectedRow();
            manCmds.updateMenuItem((int)tableModel.getValueAt(selectedRow, 0), nameTextField.getText(), Float.parseFloat(priceTextField.getText()));
            sqlObjects.MenuItemIngredients menuIng = manCmds.getMenuItemIngredients((int)tableModel.getValueAt(selectedRow, 0));
            int[] tempIngredientIDs = menuIng.ingredientIDs;
            List<Integer> ingredientIDs = Arrays.stream(tempIngredientIDs)
                                            .boxed()
                                            .collect(Collectors.toList());
            for (String item : checkedItems) {
                if (!ingredientIDs.contains(ingredientNametoID.get(item))){
                    manCmds.addMenuItemIngredient((int)tableModel.getValueAt(selectedRow, 0), ingredientNametoID.get(item));
                }
            }
            for (Integer item : ingredientIDs) {
                if (!checkedItems.contains(ingredientIDtoName.get(item))){
                    manCmds.deleteMenuItemIngredient((int)tableModel.getValueAt(selectedRow, 0), item);
                }
            }
            refreshGUI();
        }
    }
    
}