import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;

/**
 * ManagerTrends class represents a JPanel for managing various trend reports in a restaurant system.
 * It includes functionality to generate trend reports such as ProdUsage, Sales Report, Excess Report,
 * Restock Report, and Order Trend Report based on specified start and end dates.
 */
public class ManagerTrends extends JPanel {
    private JTextField startDateField;
    private JTextField endDateField;

    /**
     * Constructs a ManagerTrends object.
     * Initializes text fields for start and end dates and buttons for generating different trend reports.
     */
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

        JButton orderTrend = new JButton("Generate Ordering Trend Report");
        orderTrend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                // Validate input and generate Excess Report
                if (isValidDate(startDate) && isValidDate(endDate)) {
                    generateOrderTrendReport(startDate, endDate);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter valid dates (YYYY-MM-DD).");
                }
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
        add(orderTrend);
    }

    /**
     * Validates a given date string against the format YYYY-MM-DD.
     *
     * @param date The date string to be validated.
     * @return True if the date is in the valid format, false otherwise.
     */
    private boolean isValidDate(String date) {
        // You can add your own validation logic here
        // For simplicity, we just check if the date matches the format YYYY-MM-DD
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    /**
     * Generates and displays a ProdUsage chart based on the specified start and end dates.
     *
     * @param startDate The start date for the ProdUsage chart.
     * @param endDate   The end date for the ProdUsage chart.
     */
    private void generateProdUsage(String startDate, String endDate) {
        // Create an instance of ProdUsage with appropriate parameters
        Database database = new Database(); // You may need to adjust this depending on your Database class constructor
        ProdUsage prodUsage = new ProdUsage(database, startDate, endDate);

        // Display ProdUsage in its own window
        prodUsage.setLocationRelativeTo(null);
        prodUsage.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        prodUsage.setVisible(true);
    }
    
    /**
     * Generates and displays a Sales Report based on the specified start and end dates.
     *
     * @param startDate The start date for the Sales Report.
     * @param endDate   The end date for the Sales Report.
     */
    private void generateSalesReport(String startDate, String endDate) {
        // Create an instance of SalesReport with appropriate parameters
        Database database = new Database(); // You may need to adjust this depending on your Database class constructor
        SalesReport salesReport = new SalesReport(database, startDate, endDate);

        // Display SalesReport in its own window
        salesReport.setLocationRelativeTo(null);
        salesReport.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        salesReport.setVisible(true);
    }
    
    /**
     * Generates and displays an Excess Report based on the specified start date.
     *
     * @param startDate The start date for the Excess Report.
     */
    private void generateExcessReport(String startDate) {
        // Create an instance of ExcessReport with appropriate parameters
        Database database = new Database(); // You may need to adjust this depending on your Database class constructor
        ExcessReport excessReport = new ExcessReport(database, startDate);

        // Display ExcessReport in its own window
        excessReport.setLocationRelativeTo(null);
        excessReport.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        excessReport.setVisible(true);
    }
    
    /**
     * Generates and displays a Restock Report.
     */
    private void generateRestockReport() {
        // Create an instance of RestockReport with appropriate parameters
        Database database = new Database(); // You may need to adjust this depending on your Database class constructor
        RestockReport restockReport = new RestockReport(database);

        // Display RestockReport in its own window
        restockReport.setLocationRelativeTo(null);
        restockReport.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        restockReport.setVisible(true);
    }

    /**
     * Generates and displays an Order Trend Report based on the specified start and end dates.
     *
     * @param startDate The start date for the Order Trend Report.
     * @param endDate   The end date for the Order Trend Report.
     */
    private void generateOrderTrendReport(String startDate, String endDate) {
        // Create an instance of OrderTrend with appropriate parameters
        OrderTrend orderTrend = new OrderTrend(startDate, endDate);

        orderTrend.setLocationRelativeTo(null);
        orderTrend.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        orderTrend.setVisible(true);
    }
}
