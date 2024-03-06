import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates an order trend report based on menu IDs and counts within a specified date range.
 *
 * The OrderTrend class creates a graphical representation of the trend in orders between two dates,
 * showing pairs of menu items and their corresponding counts.
 *
 * @author Team 21 Best Table Winners
 */
public class OrderTrend extends JFrame {

    private String[] menuID1;
    private String[] menuID2;
    private int[] count;
    private managerCmds manCmds;
    private sqlObjects.OrderingTrendReport myReport;

    private static final String REPORT_TITLE = "Order Trend Report";

    /**
     * Constructs an OrderTrend object with the specified date range.
     *
     * @param date1 the start date of the report
     * @param date2 the end date of the report
     */
    public OrderTrend(String date1, String date2) {
        super(REPORT_TITLE);
        manCmds = new managerCmds();
        myReport = manCmds.OrderingTrendReport(date1, date2);

        this.menuID1 = myReport.menuID1;
        this.menuID2 = myReport.menuID2;
        this.count = myReport.count;

        JScrollPane scrollPane = createReport();
        setContentPane(scrollPane);

        setSize(800, 600);
    }

    /**
     * Creates a scrollable panel containing the order trend report.
     *
     * @return a JScrollPane containing the order trend report
     */
    private JScrollPane createReport() {
        JPanel reportPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawString(REPORT_TITLE, getWidth() / 2 - 50, 20);

                List<String> firstMenuID = new ArrayList<>();
                List<String> secondMenuID = new ArrayList<>();
                List<Integer> pairCount = new ArrayList<>();
                for (String element : menuID1){firstMenuID.add(element);}
                for (String element : menuID2){secondMenuID.add(element);}
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

    /**
     * Draws the order trend report on the specified graphics context.
     *
     * @param g           the graphics context
     * @param firstMenuID list of first menu IDs
     * @param secondMenuID list of second menu IDs
     * @param pairCount   list of pair counts
     */
    private void drawReport(Graphics g, List<String> firstMenuID, List<String> secondMenuID, List<Integer> pairCount) {
        int x = 50;
        int startY = 50;
        int rowHeight = 30;
        int columnWidth = 300;

        g.drawString("Menu Item 1", x, startY);
        g.drawString("Menu Item 2", x + columnWidth, startY);
        g.drawString("Pair Count", x + 2 * columnWidth, startY);

        int y = startY + rowHeight;
        for (int i = 0; i < firstMenuID.size() - 1; i++) {
            String firstname = firstMenuID.get(i);
            String secondname = secondMenuID.get(i);
            int aPairCount = pairCount.get(i);

            g.drawString(String.valueOf(firstname), x, y);
            g.drawString(String.valueOf(secondname), x + columnWidth, y);
            g.drawString(String.format("%d", aPairCount), x + 2 * columnWidth, y);

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
