
import javax.swing.*;
import java.awt.*;

public class ManagerTrends extends JPanel {
    public ManagerTrends() {
        JLabel placeholderLabel = new JLabel("This is Manager Trends");
        placeholderLabel.setHorizontalAlignment(JLabel.CENTER);
        placeholderLabel.setVerticalAlignment(JLabel.CENTER);

        // Set some styles for better visibility
        placeholderLabel.setForeground(Color.GRAY);

        // Add the placeholder label to the panel
        add(placeholderLabel, BorderLayout.CENTER);

        //managerCmds manCmds = new managerCmds();
        //manCmds.addIngredient(64, "Alaskan Fresh Caught Salmon", 500, 20.0f, 200);
        //^^^ tested and working ^^^
    }
}