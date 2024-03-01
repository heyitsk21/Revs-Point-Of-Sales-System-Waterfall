
import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.Calendar;

public class ManagerTrends extends JPanel {
    public ManagerTrends() {
        JLabel placeholderLabel = new JLabel("This is Manager Trends");
        placeholderLabel.setHorizontalAlignment(JLabel.CENTER);
        placeholderLabel.setVerticalAlignment(JLabel.CENTER);

        // Set some styles for better visibility
        placeholderLabel.setForeground(Color.GRAY);

        // Add the placeholder label to the panel
        add(placeholderLabel, BorderLayout.CENTER);

        managerCmds manCmds = new managerCmds();

        //SAMPLE CODE TO GET PRODUCT USAGE CHART WITH CERTAIN DATES, JAVA DATE TYPE IS DUMB :( 
        java.sql.Date todaysDate = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2024);
        cal.set(Calendar.MONTH, Calendar.FEBRUARY);
        cal.set(Calendar.DAY_OF_MONTH, 28);
        java.sql.Date beginningOfFeb = new Date(cal.getTimeInMillis());
        
 
        sqlObjects.ProductUsageChart chart= manCmds.ProductUsageChart(beginningOfFeb,todaysDate);
        for(int i = 0; i < chart.length();++i){
            System.out.println(chart.ingredientIDs[i] + " " + chart.amountUsed[i]);
        }
        
        //manCmds.addIngredient(64, "Alaskan Fresh Caught Salmon", 500, 20.0f, 200);
        //^^^ tested and working ^^^
    }
}