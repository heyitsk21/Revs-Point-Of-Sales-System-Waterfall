import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExcessReport extends JFrame {

    private Database database;

    private static final String REPORT_TITLE = "Excess Report";

    public ExcessReport(Database database, String startDate) {
        //super(REPORT_TITLE);
        this.database = database;

        JScrollPane scrollPane = createReport(startDate);
        setContentPane(scrollPane);

        setSize(800, 600);
    }

    private JScrollPane createReport(String startDate) {
        JPanel reportPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawString(REPORT_TITLE, getWidth() / 2 - 50, 20);

                List<Integer> ingredientIDs = new ArrayList<>();
                List<String> ingredientNames = new ArrayList<>();
                List<Integer> counts = new ArrayList<>();
                fetchData(startDate, ingredientIDs, ingredientNames, counts);

                drawReport(g, ingredientIDs, ingredientNames, counts);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 800);
            }
        };

        JScrollPane scrollPane = new JScrollPane(reportPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;
    }

    private void fetchData(String startDate, List<Integer> ingredientIDs, List<String> ingredientNames, List<Integer> counts) {
        String query = "SELECT i.ingredientid, i.ingredientname, i.count " +
            "FROM ingredients i " +
            "LEFT JOIN (" +
            "   SELECT ingredientid, SUM(amountchanged) AS total_sold " +
            "   FROM InventoryLog " +
            "   WHERE amountchanged < 1 " +
            "     AND logdatetime BETWEEN CAST(? AS TIMESTAMP) AND NOW() " +
            "   GROUP BY ingredientid " +
            ") il ON i.ingredientid = il.ingredientid " +
            "WHERE (il.total_sold IS NULL OR il.total_sold < 0.1 * i.count)";


        try {
            PreparedStatement pstmt = database.con.prepareStatement(query);
            pstmt.setString(1, startDate);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int ingredientID = resultSet.getInt("ingredientid");
                String ingredientName = resultSet.getString("ingredientname");
                int count = resultSet.getInt("count");

                ingredientIDs.add(ingredientID);
                ingredientNames.add(ingredientName);
                counts.add(count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while fetching data from database.");
        }
    }

    private void drawReport(Graphics g, List<Integer> ingredientIDs, List<String> ingredientNames, List<Integer> counts) {
        int x = 50;
        int startY = 50;
        int rowHeight = 30;
        int columnWidth = 200;

        g.drawString("Ingredient ID", x, startY);
        g.drawString("Ingredient Name", x + columnWidth, startY);
        g.drawString("Amount", x + 2 * columnWidth, startY);

        int y = startY + rowHeight;
        for (int i = 0; i < ingredientIDs.size(); i++) {
            int ingredientID = ingredientIDs.get(i);
            String ingredientName = ingredientNames.get(i);
            int count = counts.get(i);

            g.drawString(String.valueOf(ingredientID), x, y);
            g.drawString(ingredientName, x + columnWidth, y);
            g.drawString(String.valueOf(count), x + 2 * columnWidth, y);

            y += rowHeight;
        }
    }

}
