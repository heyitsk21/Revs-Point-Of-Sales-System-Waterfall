import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdUsage extends JFrame {

    private Database database;

    private static final String CHART_TITLE = "Produce Usage (negative)";

    public ProdUsage(Database database, String startDate, String endDate) {
        super(CHART_TITLE);
        this.database = database;

        JScrollPane scrollPane = createChart(startDate, endDate);
        setContentPane(scrollPane);

        setSize(800, 600);
    }

    private JScrollPane createChart(String startDate, String endDate) {
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawString(CHART_TITLE, getWidth() / 2 - 50, 20);

                List<String> ingredientNames = new ArrayList<>();
                List<Integer> totalAmountChangedList = new ArrayList<>();
                fetchData(startDate, endDate, ingredientNames, totalAmountChangedList);

                drawChart(g, ingredientNames, totalAmountChangedList);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 1000);
            }
        };

        JScrollPane scrollPane = new JScrollPane(chartPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;
    }

    private void fetchData(String startDate, String endDate, List<String> ingredientNames, List<Integer> totalAmountChangedList) {
        String query = "SELECT IL.IngredientID, I.IngredientName, SUM(IL.AmountChanged) AS TotalAmountChanged " +
                "FROM InventoryLog IL JOIN Ingredients I ON IL.IngredientID = I.IngredientID " +
                "WHERE IL.AmountChanged < 0 AND DATE(IL.LogDateTime) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) " +
                "GROUP BY IL.IngredientID, I.IngredientName";

        try {
            PreparedStatement pstmt = database.con.prepareStatement(query);
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String ingredientName = resultSet.getString("IngredientName");
                int totalAmountChanged = resultSet.getInt("TotalAmountChanged");
                ingredientNames.add(ingredientName);
                totalAmountChangedList.add(totalAmountChanged);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while fetching data from database.");
        }
    }

    private void drawChart(Graphics g, List<String> ingredientNames, List<Integer> totalAmountChangedList) {
        int startX = 150;
        int startY = 50;
        int barHeight = 20;
        int maxBarWidth = 200;
        int yIncrement = 50;

        int sumTotalAmountChanged = totalAmountChangedList.stream().mapToInt(Integer::intValue).sum();

        int y = startY;
        for (int i = 0; i < ingredientNames.size(); i++) {
            String ingredientName = ingredientNames.get(i);
            int totalAmountChanged = totalAmountChangedList.get(i);
            int barWidth = (int) (((double) totalAmountChanged / sumTotalAmountChanged) * maxBarWidth);

            g.setColor(Color.BLUE);
            g.fillRect(startX, y, barWidth, barHeight);

            g.setColor(Color.BLACK);
            g.drawString(ingredientName, startX - 10, y + barHeight / 2 - 20);

            g.drawString(String.valueOf(totalAmountChanged), startX + barWidth + 5, y + barHeight / 2 + 5);

            y += yIncrement;
        }
    }

    public static void main(String[] args) {
        Database database = new Database();
        SwingUtilities.invokeLater(() -> {
            ProdUsage prodUsage = new ProdUsage(database, "", "");
            prodUsage.setLocationRelativeTo(null);
            prodUsage.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            prodUsage.setVisible(true);
        });
    }
}