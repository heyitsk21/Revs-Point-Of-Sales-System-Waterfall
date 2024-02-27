import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EmployeeDelete extends JPanel {
    private JButton deleteButton;
    private JButton backButton;

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
                // Remove selected items from the submit order panel
                // Code to remove menu item with ID menuID from innerOrderPanel
                // selectedMenuItems.clear(); // Clear selected items
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