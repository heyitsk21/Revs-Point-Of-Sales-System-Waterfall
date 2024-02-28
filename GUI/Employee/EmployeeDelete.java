import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDelete extends JPanel {
    private JButton deleteButton;
    private JButton backButton;

    //private List<Integer> selectedMenuIDs;

    public EmployeeDelete() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Are you sure you want to delete?");
        add(label, BorderLayout.NORTH);

        JPanel deleteScreen = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(150, 70));
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 25));
        deleteScreen.add(deleteButton);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 70));
        backButton.setFont(new Font("Arial", Font.PLAIN, 25));
        deleteScreen.add(backButton);

        add(deleteScreen, BorderLayout.CENTER);

        // Add action listeners
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle delete action
                System.out.println("Delete clicked");
                // Clear selected items
                for (int i = 0; i < Employee.toBeDeleted.size(); i++) {
                    //find the index of the element from toBeDeleted in selectedMenuIDs
                    //then remove at that index from the panel
                    int index = Employee.selectedMenuIDs.indexOf(Employee.toBeDeleted.get(i));
                    if (index != -1) {
                        Employee.innerOrderPanel.remove(index);
                        System.out.println("Deleting: " + Employee.toBeDeleted.get(i));
                        Employee.selectedMenuIDs.remove(index);
                    }
                }
                Employee.toBeDeleted.clear();
                System.out.println(Employee.selectedMenuIDs);
                Employee.innerOrderPanel.repaint();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle back action
                System.out.println("Back clicked");
                // Close the frame
                SwingUtilities.getWindowAncestor(EmployeeDelete.this).dispose();
            }
        });
    }
}