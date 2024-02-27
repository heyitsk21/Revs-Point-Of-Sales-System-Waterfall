import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private JButton createButton, deleteButton, cancelButton, submitButton;
    private JLabel nameLabel, priceLabel, ingredientsLabel;
    private JTextField nameTextField, priceTextField;
    private JPanel rightPanel, leftPanel, buttonPanel;

    managerCmds manCmds;
    int currentItem;

    sqlObjects.Menu initialMenu;
    sqlObjects.Inventory myInventory;
    Object[][] menu;

    //How to use this panel:
    //This panel is for the manager to view and edit the menu items
    //Click on a row in the table to enable the buttons
    //After clicking on a row, you can create new items and delete existing items
    //The attributes can be edited and submitted from the panel on the right hand side
    //Currently, nothing is being done with the ingredients
    //TODO: right now the ingredients is a textbox and it needs to be changed into a table

    public ManagerMenuItems() {
        setLayout(new GridBagLayout());
        manCmds = new managerCmds();
        setLayout(new GridLayout(1, 2));

        initialMenu = manCmds.getMenu();
        menu = formatMenuItems(initialMenu);
        createLeftPanel();
        createRightPanel();
        //Disable buttons until a row is selected
        setButtonState(false);
    }

    //Enable or disable buttons
    private void setButtonState(boolean enabled) {
        createButton.setEnabled(enabled);
        deleteButton.setEnabled(enabled);
        cancelButton.setEnabled(enabled);
        submitButton.setEnabled(enabled);
    }

    //menu contains the values from the menuItems table in the database
    //when this is read from the database, the format doesn't match with what we need to show in the UI
    //helper method formatMenuItems formats the values into a 2D array
    private Object[][] formatMenuItems(sqlObjects.Menu myMenu){
        int size = myMenu.menuItemIDs.length;
        Object[][] menuItems = new Object[size][3];
        for (int i = 0; i < size; i++){
            menuItems[i][0] = myMenu.menuItemIDs[i];
            menuItems[i][1] = myMenu.names[i];
            menuItems[i][2] = myMenu.price[i];
        }

        return menuItems;
    }

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

        //Make the left panel fill up 75% of the horizontal space
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(leftPanel);

        //action listeners are defined in classes at the bottom of the file
        ListSelectionModel selectionModel = menuTable.getSelectionModel();
        selectionModel.addListSelectionListener(e -> rowClicked(e));
        createButton.addActionListener(new CreateButtonListener());
        deleteButton.addActionListener(new DeleteButtonListener());
    }

    JLabel checkedItemsLabel;

    private void createRightPanel() {
        rightPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        nameLabel = new JLabel("Name: ");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        priceLabel = new JLabel("Price: ");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        ingredientsLabel = new JLabel("Ingredients: ");
        ingredientsLabel.setFont(new Font("Arial", Font.PLAIN, 20));

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
        rightPanel.add(checkedItemsLabel);
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
        
        checkedItemsLabel = new JLabel("Checked Items: ");
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

    private void updateCheckedItemsLabel() {
        StringBuilder labelText = new StringBuilder("Checked Items: ");
        //System.out.println("Selected Item ID: " + ingredientNametoID.get(selectedItem)); 
        for (String item : checkedItems) {
            labelText.append(item).append(", ");
        }
        // Set the label text directly
        checkedItemsLabel.setText(labelText.toString());
    }

    public void rowClicked(ListSelectionEvent event) {
        int selectedRow = menuTable.getSelectedRow();
        //System.out.println(selectedRow);
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
    
            //System.out.println(newID);
            manCmds.addMenuItem(newID, "NewMenu Item", 0.0f);
            refreshGUI();
        }
    }
    

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            int selectedRow = menuTable.getSelectedRow();
            //System.out.println("Selected Row: " + selectedRow);
            if (selectedRow >= 0 && selectedRow < menu.length) {
                int toDeleteID = (int) initialMenu.menuItemIDs[selectedRow];
                System.out.println("Deleting Menu Item " + toDeleteID);
                manCmds.deleteMenuItem(toDeleteID);
            }
            refreshGUI();
            // TODO sql here
        }
    }
    
    
    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            menuTable.clearSelection();
            nameTextField.setText("");
            priceTextField.setText("");
        }
    }

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
            //TODO sql here
        }
    }
    
}