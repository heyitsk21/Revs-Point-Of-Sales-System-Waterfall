import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderTrend extends JFrame {
    private int[] menuID1;
    private int[] menuID2;
    private int[] count;
    String startDate;
    String endDate;
    managerCmds manCmds;
    sqlObjects.OrderingTrendReport myReport;

    private static final String REPORT_TITLE = "Order Trend Report";

    public OrderTrend(String date1, String date2) {
        super(REPORT_TITLE);
        manCmds = new managerCmds();
        myReport = manCmds.OrderingTrendReport(date1, date2);

        this.menuID1 = myReport.menuID1;
        this.menuID2 = myReport.menuID2;
        this.count = myReport.count;
        startDate = date1;
        endDate = date2;

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

                List<Integer> firstMenuID = new ArrayList<>();
                List<Integer> secondMenuID = new ArrayList<>();
                List<Integer> pairCount = new ArrayList<>();
                for (int element : menuID1){firstMenuID.add(element);}
                for (int element : menuID2){secondMenuID.add(element);}
                for (int element : count){pairCount.add(element);}

                drawReport(g, firstMenuID, secondMenuID, pairCount);
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

    private void drawReport(Graphics g, List<Integer> firstMenuID, List<Integer> secondMenuID, List<Integer> pairCount) {
        int startX = 50;
        int startY = 50;
        int rowHeight = 30;
        int columnWidth = 200;

        int y = startY;
        for (int i = 0; i < firstMenuID.size(); i++) {
            int firstID = firstMenuID.get(i);
            int secondID = secondMenuID.get(i);
            int aPairCount = pairCount.get(i);

            g.drawString("MenuID 1 " + String.valueOf(firstID), startX, y);
            g.drawString("MenuID 2 " + String.valueOf(secondID), startX + columnWidth, y);
            g.drawString(String.format("%d", aPairCount), startX + 2 * columnWidth, y);

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