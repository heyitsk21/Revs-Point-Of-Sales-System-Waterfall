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

    //How to use this panel:
    //This panel is for the manager to view and edit the menu items
    //Click on a row in the table to enable the buttons
    //After clicking on a row, you can create new items and delete existing items
    //The attributes can be edited and submitted from the panel on the right hand side
    //Currently, nothing is being done with the ingredients
    //TODO: right now the ingredients is a textbox and it needs to be changed into a table

    public ManagerMenuItems() {
        setLayout(new GridBagLayout());
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
    private Object[][] formatMenuItems(sqlObjects.Menu menu){
        int size = menu.length();
        Object[][] menuItems = new Object[size][3];
        for (int i = 0; i < size; i++){
            menuItems[i][0] = menu.menuItemIDs[i];
            menuItems[i][1] = menu.names[i];
            menuItems[i][2] = menu.price[i];
        }

        return menuItems;
    }

    private void createLeftPanel() {
        Object[][] menuItems = {{"1","placeholder","x"},{"x","x","x"}};

        leftPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel();
        String[] columns = {"MenuID", "MenuItemName", "Price"};

        manCmds = new managerCmds();
        sqlObjects.Menu menu = manCmds.getMenu();

        //if nothing is returned from the database, formatMenuItems will throw an exception so adding a null check
        //if menu is null, we will just show the placeholder values
        if(menu != null){
            menuItems = this.formatMenuItems(menu);
        }

        tableModel = new DefaultTableModel(menuItems, columns);
        menuTable = new JTable(tableModel);
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
            nameTextField.setText((String) tableModel.getValueAt(selectedRow, 1));
            priceTextField.setText((String) tableModel.getValueAt(selectedRow, 2));
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
            // Placeholder for adding a new item
            tableModel.addRow(new Object[]{"NewID", "NewItemName", "NewPrice"});
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow >= 0) {
                tableModel.removeRow(selectedRow);
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