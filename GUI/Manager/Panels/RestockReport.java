import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates a restock report showing ingredients that are below their minimum amount.
 *
 * @author Team 21 Best Table Winners
 */
public class RestockReport extends JFrame {

    private Database database;

    private static final String REPORT_TITLE = "Restock Report";

    /**
     * Constructs a RestockReport object.
     *
     * @param database the database connection
     */
    public RestockReport(Database database) {
        super(REPORT_TITLE);
        this.database = database;

        JScrollPane scrollPane = createReport();
        setContentPane(scrollPane);

        setSize(800, 600);
    }

    /**
     * Creates a scrollable panel containing the restock report.
     *
     * @return a JScrollPane containing the restock report
     */
    private JScrollPane createReport() {
        JPanel reportPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawString(REPORT_TITLE, getWidth() / 2 - 50, 20);

                List<String> ingredientNames = new ArrayList<>();
                List<Integer> counts = new ArrayList<>();
                List<Integer> minimums = new ArrayList<>();
                fetchData(ingredientNames, counts, minimums);

                drawReport(g, ingredientNames, counts, minimums);
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
     * Fetches data from the database for the restock report.
     *
     * @param ingredientNames list to store ingredient names
     * @param counts          list to store ingredient counts
     * @param minimums        list to store ingredient minimum amounts
     */
    private void fetchData(List<String> ingredientNames, List<Integer> counts, List<Integer> minimums) {
        String query = "SELECT * FROM ingredients WHERE count < minamount";

        try {
            PreparedStatement pstmt = database.con.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String ingredientName = resultSet.getString("ingredientname");
                int count = resultSet.getInt("count");
                int min = resultSet.getInt("minamount");
                ingredientNames.add(ingredientName);
                counts.add(count);
                minimums.add(min);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while fetching data from database.");
        }
    }

    /**
     * Draws the restock report on the specified graphics context.
     *
     * @param g              the graphics context
     * @param ingredientNames list of ingredient names
     * @param counts         list of ingredient counts
     * @param minimums       list of ingredient minimum amounts
     */
    private void drawReport(Graphics g, List<String> ingredientNames, List<Integer> counts, List<Integer> minimums) {
        int x = 50;
        int startY = 50;
        int rowHeight = 30;
        int columnWidth = 200;

        g.drawString("Ingredient", x, startY);
        g.drawString("Current Amount", x + columnWidth, startY);
        g.drawString("Minimum Amount", x + 2 * columnWidth, startY);

        int y = startY + rowHeight;
        for (int i = 0; i < ingredientNames.size(); i++) {
            String ingredientName = ingredientNames.get(i);
            int count = counts.get(i);
            int min = minimums.get(i);

            g.drawString(ingredientName, x, y);
            g.drawString(String.valueOf(count), x + columnWidth, y);
            g.drawString(String.valueOf(min), x + 2 * columnWidth, y);

            y += rowHeight;
        }
    }

    /**
     * The main method to execute the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Database database = new Database(); // Initialize your Database instance
        SwingUtilities.invokeLater(() -> {
            RestockReport restockReport = new RestockReport(database);
            restockReport.setLocationRelativeTo(null);
            restockReport.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            restockReport.setVisible(true);
        });
    }
}
