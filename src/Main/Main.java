package Main;

import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is the main class that starts the application
 */
public class Main extends Application {

    /**
     * Starts the connection to the database and closes upon program exit
     *
     * @param args Not used, but arguments passed to the program if needed
     */
    public static void main(String[] args) {
        // Start connection
        DBConnection.startConnection();

        launch(args);

        // Close connection
        DBConnection.closeConnection();
    }

    /**
     * This method opens the login screen view and starts the program
     *
     * @param mainStage Stage that is passed to be shown to user
     * @throws Exception Exception that is caught in case of any that are detected
     */
    @Override
    public void start(Stage mainStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/Views/login_screen.fxml"));
        mainStage.setTitle("WGU Scheduling Application");
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }
}
