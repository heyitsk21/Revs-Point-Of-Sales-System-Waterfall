import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates an excess report based on database data.
 *
 * @author Team 21 Best Table Winners
 */
public class ExcessReport extends JFrame {

    private Database database;

    private static final String REPORT_TITLE = "Excess Report";

    /**
     * Constructs an ExcessReport object.
     *
     * @param database  the database connection
     * @param startDate the start date for the report
     */
    public ExcessReport(Database database, String startDate) {
        //super(REPORT_TITLE);
        this.database = database;

        JScrollPane scrollPane = createReport(startDate);
        setContentPane(scrollPane);

        setSize(800, 600);
    }

    /**
     * Creates a scrollable panel containing the excess report.
     *
     * @param startDate the start date for the report
     * @return a JScrollPane containing the excess report
     */
    private JScrollPane createReport(String startDate) {
        JPanel reportPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawString(REPORT_TITLE, getWidth() / 2 - 50, 20);

                List<Integer> ingredientIDs = new ArrayList<>();
                List<String> ingredientNames = new ArrayList<>();
                fetchData(startDate, ingredientIDs, ingredientNames);

                drawReport(g, ingredientIDs, ingredientNames);
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

    /**
     * Fetches data from the database for the report.
     *
     * @param startDate       the start date for the report
     * @param ingredientIDs   list to store ingredient IDs
     * @param ingredientNames list to store ingredient names
     */
    private void fetchData(String startDate, List<Integer> ingredientIDs, List<String> ingredientNames) {
        String query = "SELECT DISTINCT mi.MenuID, mi.ItemName " +
                       "FROM ingredients i " +
                       "JOIN menuitemingredients mii ON i.ingredientid = mii.ingredientid " +
                       "JOIN menuitems mi ON mii.menuid = mi.menuid " +
                       "LEFT JOIN ( " +
                       "    SELECT ingredientid, SUM(amountchanged) AS total_sold " +
                       "    FROM InventoryLog " +
                       "    WHERE amountchanged < 1 " +
                       "      AND logdatetime BETWEEN CAST(? AS TIMESTAMP) AND NOW() " +
                       "    GROUP BY ingredientid " +
                       ") il ON i.ingredientid = il.ingredientid " +
                       "WHERE (il.total_sold IS NULL OR -1*il.total_sold < 0.1 * i.count)";

        try {
            PreparedStatement pstmt = database.con.prepareStatement(query);
            pstmt.setString(1, startDate);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int menuID = resultSet.getInt("MenuID");
                String itemName = resultSet.getString("ItemName");

                // Add the menu item details to the lists
                ingredientIDs.add(menuID);
                ingredientNames.add(itemName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while fetching data from database.");
        }
    }

    /**
     * Draws the excess report on the specified graphics context.
     *
     * @param g               the graphics context
     * @param ingredientIDs   list of ingredient IDs
     * @param ingredientNames list of ingredient names
     */
    private void drawReport(Graphics g, List<Integer> ingredientIDs, List<String> ingredientNames) {
        int x = 50;
        int startY = 50;
        int rowHeight = 30;
        int columnWidth = 200;

        g.drawString("Ingredient ID", x, startY);
        g.drawString("Ingredient Name", x + columnWidth, startY);

        int y = startY + rowHeight;
        for (int i = 0; i < ingredientIDs.size(); i++) {
            int ingredientID = ingredientIDs.get(i);
            String ingredientName = ingredientNames.get(i);

            g.drawString(String.valueOf(ingredientID), x, y);
            g.drawString(ingredientName, x + columnWidth, y);

            y += rowHeight;
        }
    }

}