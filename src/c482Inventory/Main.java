package c482Inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main window of the application.
 */
public class Main extends Application {

    /**
     * Opens the Main window.
     *
     * @param primaryStage the stage for the main window
     * @throws Exception IOException when the FXMLLoader cannot load the specified file
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("mainPage.fxml")));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Javadoc Location:
    // C482Project/doc
    /**
     * Main method.
     * <br><br>
     * Javadoc Location:
     * <br>
     * C482Project/doc
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
