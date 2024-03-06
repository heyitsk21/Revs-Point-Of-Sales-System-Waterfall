
/**
 * The `GUI` class contains the main method to launch the application.
 * It initializes the login interface (`LogInGUI`) when the application starts.
 * Uncomment and modify the code to launch other interfaces if needed.
 * 
 * @author Team 21 Best Table Winners
 */
public class GUI {
    public static void main(String[] args) {
        //Employee emp = new Employee();
        //emp.setVisible(true);

        // ManagerGUI manage = new ManagerGUI();
        // manage.setVisible(true);

        LogInGUI login = new LogInGUI();
        login.setVisible(true);
    }
}