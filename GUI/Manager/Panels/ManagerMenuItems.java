import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerMenuItems extends JPanel {
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private JButton createButton, deleteButton, cancelButton, submitButton;
    private JLabel nameLabel, priceLabel, ingredientsLabel;
    private JTextField nameTextField, priceTextField, ingredientsTextField;
    private JPanel rightPanel, leftPanel, buttonPanel;

    managerCmds manCmds;
    int currentItem;

    sqlObjects.Menu initialMenu;
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

        initialMenu = manCmds.getMenu();
        menu = formatMenuItems(initialMenu);
        createLeftPanel();
        createRightPanel();
        //Disable buttons until a row is selected
        setButtonState(false);
    }

    public void RefreshGUI(){
        setLayout(new GridBagLayout());
        //PLEASE ADD UPDATE LEFT AND RIGHT PANEL AND REPLACE THIS AWFUL MESS 
        menuTable = null;
        tableModel = null;
        createButton = null; deleteButton = null;  cancelButton = null; submitButton = null;
        nameLabel = null; priceLabel= null; ingredientsLabel = null;
        nameTextField = null; priceTextField = null; ingredientsTextField = null;
        rightPanel = null; leftPanel = null; buttonPanel = null;
        //PLEASE ADD UPDATE LEFT AND RIGHT PANEL AND REPLACE THIS AWFUL MESS ^^^^^^^
        initialMenu = manCmds.getMenu();
        menu = formatMenuItems(initialMenu);
        createLeftPanel();
        createRightPanel();
        //PLEASE ADD UPDATE LEFT AND RIGHT PANEL AND REPLACE THIS AWFUL MESS
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

        tableModel = new DefaultTableModel(menu, columns);
        
        menuTable = new JTable(tableModel);
        menuTable.setRowHeight(50);
        menuTable.setFont(new Font("Arial", Font.PLAIN, 15));

        leftPanel.add(new JScrollPane(menuTable), BorderLayout.CENTER);

        createButton = new JButton("Create");
        deleteButton = new JButton("Delete");
        buttonPanel.add(createButton);
        buttonPanel.add(deleteButton);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        //Make the left panel fill up 75% of the horizontal space
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.75;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(leftPanel, gbc);

        //action listeners are defined in classes at the bottom of the file
        ListSelectionModel selectionModel = menuTable.getSelectionModel();
        selectionModel.addListSelectionListener(e -> valueChanged(e));
        createButton.addActionListener(new CreateButtonListener());
        deleteButton.addActionListener(new DeleteButtonListener());
    }

    private void createRightPanel() {
        rightPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        nameLabel = new JLabel("Name: ");
        priceLabel = new JLabel("Price: ");
        ingredientsLabel = new JLabel("Ingredients: ");

        nameTextField = new JTextField();
        priceTextField = new JTextField();
        //TODO: change this into a table of ingredients instead of a text field
        ingredientsTextField = new JTextField();

        cancelButton = new JButton("Cancel");
        submitButton = new JButton("Submit");

        //Add the labels and text fields to the right panel
        rightPanel.add(nameLabel);
        rightPanel.add(nameTextField);
        rightPanel.add(priceLabel);
        rightPanel.add(priceTextField);
        rightPanel.add(ingredientsLabel);
        rightPanel.add(ingredientsTextField);
        rightPanel.add(cancelButton);
        rightPanel.add(submitButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(rightPanel, gbc);

        //adjust the values in the table when the submit button is clicked
        submitButton.addActionListener(new SubmitButtonListener());
        //clear the text fields when the cancel button is clicked
        cancelButton.addActionListener(new CancelButtonListener());

    }

    public void valueChanged(ListSelectionEvent event) {
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
        } else {
            //Clear the text fields if no row is selected
            nameTextField.setText("");
            priceTextField.setText("");
            ingredientsTextField.setText("");
        }
    }

    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //get the biggest ID and add 1 TODO add catagory functionlaity when that is implimeneted in the front end
            int newID = initialMenu.menuItemIDs[initialMenu.menuItemIDs.length - 1] + 1;
            System.out.println(newID);
            RefreshGUI();
            manCmds.addMenuItem(newID,"NewMenu Item" , 0.0f);
            RefreshGUI();
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = menuTable.getSelectedRow();
            System.out.println("Selected Row" + selectedRow);
            if (selectedRow >= 0) { 
                RefreshGUI();
                int toDeleteID = initialMenu.menuItemIDs[selectedRow];
                System.out.println("Deleting Menu Item " + toDeleteID);
                manCmds.deleteMenuItem(toDeleteID);
                RefreshGUI();
            }
        }
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            menuTable.clearSelection();
            nameTextField.setText("");
            priceTextField.setText("");
            ingredientsTextField.setText("");
        }
    }

    private class SubmitButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow >= 0) {
                tableModel.setValueAt(nameTextField.getText(), selectedRow, 1);
                tableModel.setValueAt(priceTextField.getText(), selectedRow, 2);
            }
        }
    }
    
}