package Main;

import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        // Start connection
        DBConnection.startConnection();

        launch(args);

        // Close connection
        DBConnection.closeConnection();
    }

    @Override
    public void start(Stage mainStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/Views/login_screen.fxml"));
        mainStage.setTitle("WGU Scheduling Application");
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }
}
