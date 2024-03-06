import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestockReport extends JFrame {

    private Database database;

    private static final String REPORT_TITLE = "Restock Report";

    public RestockReport(Database database) {
        super(REPORT_TITLE);
        this.database = database;

        JScrollPane scrollPane = createReport();
        setContentPane(scrollPane);

        setSize(800, 600);
    }

    private JScrollPane createReport() {
        JPanel reportPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawString(REPORT_TITLE, getWidth() / 2 - 50, 20);

                List<String> ingredientNames = new ArrayList<>();
                List<Integer> counts = new ArrayList<>();
                fetchData(ingredientNames, counts);

                drawReport(g, ingredientNames, counts);
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

    private void fetchData(List<String> ingredientNames, List<Integer> counts) {
        String query = "SELECT * FROM ingredients WHERE count < minamount";

        try {
            PreparedStatement pstmt = database.con.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String ingredientName = resultSet.getString("ingredientname");
                int count = resultSet.getInt("count");
                ingredientNames.add(ingredientName);
                counts.add(count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while fetching data from database.");
        }
    }

    private void drawReport(Graphics g, List<String> ingredientNames, List<Integer> counts) {
        int startX = 50;
        int startY = 50;
        int rowHeight = 30;

        int y = startY;
        for (int i = 0; i < ingredientNames.size(); i++) {
            String ingredientName = ingredientNames.get(i);
            int count = counts.get(i);

            g.drawString(ingredientName, startX, y);
            g.drawString(String.valueOf(count), startX + 200, y);

            y += rowHeight;
        }
    }

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