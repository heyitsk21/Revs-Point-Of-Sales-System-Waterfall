import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates a sales report based on orders within a specified date range.
 *
 * @author Team 21 Best Table Winners
 */
public class SalesReport extends JFrame {

    Database database;

    private static final String REPORT_TITLE = "Sales Report";

    /**
     * Constructs a SalesReport object.
     *
     * @param database  the database connection
     * @param startDate the start date of the report
     * @param endDate   the end date of the report
     */
    public SalesReport(Database database, String startDate, String endDate) {
        super(REPORT_TITLE);
        this.database = database;

        JScrollPane scrollPane = createReport(startDate, endDate);
        setContentPane(scrollPane);

        setSize(800, 600);
    }

    /**
     * Creates a scrollable panel containing the sales report.
     *
     * @param startDate the start date of the report
     * @param endDate   the end date of the report
     * @return a JScrollPane containing the sales report
     */
    private JScrollPane createReport(String startDate, String endDate) {
        JPanel reportPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawString(REPORT_TITLE, getWidth() / 2 - 50, 20);

                List<Integer> menuIDs = new ArrayList<>();
                List<String> itemNames = new ArrayList<>();
                List<Double> totalSales = new ArrayList<>();
                fetchData(startDate, endDate, menuIDs, itemNames, totalSales);

                drawReport(g, menuIDs, itemNames, totalSales);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 1000);
            }
        };

        JScrollPane scrollPane = new JScrollPane(reportPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;
    }

    /**
     * Fetches data from the database for the sales report.
     *
     * @param startDate  the start date of the report
     * @param endDate    the end date of the report
     * @param menuIDs    list to store menu IDs
     * @param itemNames  list to store item names
     * @param totalSales list to store total sales
     */
    private void fetchData(String startDate, String endDate, List<Integer> menuIDs, List<String> itemNames, List<Double> totalSales) {
        String query = "SELECT menuitems.MenuID, menuitems.ItemName, SUM(menuitems.Price) AS TotalSales " +
                "FROM orders " +
                "JOIN ordermenuitems ON orders.OrderID = ordermenuitems.OrderID " +
                "JOIN menuitems ON ordermenuitems.MenuID = menuitems.MenuID " +
                "WHERE orders.OrderDateTime BETWEEN TO_TIMESTAMP(?, 'YYYY-MM-DD') AND TO_TIMESTAMP(?, 'YYYY-MM-DD') " +
                "GROUP BY menuitems.MenuID, menuitems.ItemName " +
                "ORDER BY TotalSales DESC";

        try {
            PreparedStatement pstmt = database.con.prepareStatement(query);
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int menuID = resultSet.getInt("MenuID");
                String itemName = resultSet.getString("ItemName");
                double totalSale = resultSet.getDouble("TotalSales");

                menuIDs.add(menuID);
                itemNames.add(itemName);
                totalSales.add(totalSale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while fetching data from database.");
        }
    }

    /**
     * Draws the sales report on the specified graphics context.
     *
     * @param g          the graphics context
     * @param menuIDs    list of menu IDs
     * @param itemNames  list of item names
     * @param totalSales list of total sales
     */
    private void drawReport(Graphics g, List<Integer> menuIDs, List<String> itemNames, List<Double> totalSales) {
        int startX = 50;
        int startY = 50;
        int rowHeight = 30;
        int columnWidth = 200;

        int y = startY;
        for (int i = 0; i < menuIDs.size(); i++) {
            int menuID = menuIDs.get(i);
            String itemName = itemNames.get(i);
            double totalSale = totalSales.get(i);

            g.drawString(String.valueOf(menuID), startX, y);
            g.drawString(itemName, startX + columnWidth, y);
            g.drawString(String.format("%.2f", totalSale), startX + 2 * columnWidth, y);

            y += rowHeight;
        }
    }

    /**
     * The main method to execute the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Database database = new Database();
        SwingUtilities.invokeLater(() -> {
            SalesReport salesReport = new SalesReport(database, "start_date", "end_date");
            salesReport.setLocationRelativeTo(null);
            salesReport.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            salesReport.setVisible(true);
        });
    }
}