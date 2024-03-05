import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;




public class ManagerTrends extends JPanel {
    private JTextField startDateField;
    private JTextField endDateField;

    public ManagerTrends() {
        // Create text fields for start date and end date
        startDateField = new JTextField(10);
        endDateField = new JTextField(10);

        // Create button to generate ProdUsage chart
        JButton prodUsageButton = new JButton("Generate ProdUsage");
        prodUsageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                // Validate input and generate ProdUsage chart
                if (isValidDate(startDate) && isValidDate(endDate)) {
                    generateProdUsage(startDate, endDate);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter valid dates (YYYY-MM-DD).");
                }
            }
        });

        // Create button to generate Sales Report
        JButton salesReportButton = new JButton("Generate Sales Report");
        salesReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                // Validate input and generate Sales Report
                if (isValidDate(startDate) && isValidDate(endDate)) {
                    generateSalesReport(startDate, endDate);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter valid dates (YYYY-MM-DD).");
                }
            }
        });
        
        // Create button to generate Excess Report
        JButton excessReportButton = new JButton("Generate Excess Report");
        excessReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startDate = startDateField.getText();
                // Validate input and generate Excess Report
                if (isValidDate(startDate)) {
                    generateExcessReport(startDate);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid start date (YYYY-MM-DD).");
                }
            }
        });
        
        // Create button to generate Restock Report
        JButton restockReportButton = new JButton("Generate Restock Report");
        restockReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRestockReport();
            }
        });

        // Add components to the panel
        setLayout(new FlowLayout());
        add(new JLabel("Start Date:"));
        add(startDateField);
        add(new JLabel("End Date:"));
        add(endDateField);
        add(prodUsageButton);
        add(salesReportButton);
        add(excessReportButton);
        add(restockReportButton); // Add the Restock Report button
    }

    private boolean isValidDate(String date) {
        // You can add your own validation logic here
        // For simplicity, we just check if the date matches the format YYYY-MM-DD
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private void generateProdUsage(String startDate, String endDate) {
        // Create an instance of ProdUsage with appropriate parameters
        Database database = new Database(); // You may need to adjust this depending on your Database class constructor
        ProdUsage prodUsage = new ProdUsage(database, startDate, endDate);

        // Display ProdUsage in its own window
        prodUsage.setLocationRelativeTo(null);
        prodUsage.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        prodUsage.setVisible(true);
    }
    
    private void generateSalesReport(String startDate, String endDate) {
        // Create an instance of SalesReport with appropriate parameters
        Database database = new Database(); // You may need to adjust this depending on your Database class constructor
        SalesReport salesReport = new SalesReport(database, startDate, endDate);

        // Display SalesReport in its own window
        salesReport.setLocationRelativeTo(null);
        salesReport.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        salesReport.setVisible(true);
    }
    
    private void generateExcessReport(String startDate) {
        // Create an instance of ExcessReport with appropriate parameters
        Database database = new Database(); // You may need to adjust this depending on your Database class constructor
        ExcessReport excessReport = new ExcessReport(database, startDate);

        // Display ExcessReport in its own window
        excessReport.setLocationRelativeTo(null);
        excessReport.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        excessReport.setVisible(true);
    }
    
    private void generateRestockReport() {
        // Create an instance of RestockReport with appropriate parameters
        Database database = new Database(); // You may need to adjust this depending on your Database class constructor
        RestockReport restockReport = new RestockReport(database);

        // Display RestockReport in its own window
        restockReport.setLocationRelativeTo(null);
        restockReport.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        restockReport.setVisible(true);
    }
}
