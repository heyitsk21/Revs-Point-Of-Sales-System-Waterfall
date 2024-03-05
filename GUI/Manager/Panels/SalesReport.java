import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesReport extends JFrame {

    Database database;

    private static final String REPORT_TITLE = "Sales Report";

    public SalesReport(Database database, String startDate, String endDate) {
        super(REPORT_TITLE);
        this.database = database;

        JScrollPane scrollPane = createReport(startDate, endDate);
        setContentPane(scrollPane);

        setSize(800, 600);
    }

    private JScrollPane createReport(String startDate, String endDate) {
        JPanel reportPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawString(REPORT_TITLE, getWidth() / 2 - 50, 20);

                List<Integer> menuIDs = new ArrayList<>();
                List<String> itemNames = new ArrayList<>();
                List<Double> totalSales = new ArrayList<>();
                List<Integer> counts = new ArrayList<>();
                fetchData(startDate, endDate, menuIDs, itemNames, totalSales, counts);

                drawReport(g, menuIDs, itemNames, totalSales, counts);
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

    private void fetchData(String startDate, String endDate, List<Integer> menuIDs, List<String> itemNames, List<Double> totalSales, List<Integer> counts) {
        String query = "SELECT menuitems.MenuID, menuitems.ItemName, SUM(menuitems.Price) AS TotalSales, COUNT(*) AS OrderCount " + "FROM orders " + "JOIN ordermenuitems ON orders.OrderID = ordermenuitems.OrderID " + "JOIN menuitems ON ordermenuitems.MenuID = menuitems.MenuID " + "WHERE orders.OrderDateTime BETWEEN TO_TIMESTAMP(?, 'YYYY-MM-DD') AND TO_TIMESTAMP(?, 'YYYY-MM-DD') " + "GROUP BY menuitems.MenuID, menuitems.ItemName " + "ORDER BY TotalSales DESC";

        try {
            PreparedStatement pstmt = database.con.prepareStatement(query);
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int menuID = resultSet.getInt("MenuID");
                String itemName = resultSet.getString("ItemName");
                double totalSale = resultSet.getDouble("TotalSales");
                int count = resultSet.getInt("OrderCount");

                menuIDs.add(menuID);
                itemNames.add(itemName);
                totalSales.add(totalSale);
                counts.add(count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while fetching data from database.");
        }
    }

    private void drawReport(Graphics g, List<Integer> menuIDs, List<String> itemNames, List<Double> totalSales, List<Integer> counts) {
        int x = 50;
        int startY = 50;
        int rowHeight = 30;
        int columnWidth = 200;

        g.drawString("Menu ID", x, startY);
        g.drawString("Item Name", x + columnWidth, startY);
        g.drawString("Sales", x + 2 * columnWidth, startY);
        g.drawString("Amount Sold", x + 3*columnWidth, startY);

        int y = startY + rowHeight;
        for (int i = 0; i < menuIDs.size(); i++) {
            int menuID = menuIDs.get(i);
            String itemName = itemNames.get(i);
            double totalSale = totalSales.get(i);
            int count = counts.get(i);

            g.drawString(String.valueOf(menuID), x, y);
            g.drawString(itemName, x + columnWidth, y);
            g.drawString("$" + String.format("%.2f", totalSale), x + 2 * columnWidth, y);
            g.drawString(String.valueOf(count), x + 3 * columnWidth, y);

            y += rowHeight;
        }
    }

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
