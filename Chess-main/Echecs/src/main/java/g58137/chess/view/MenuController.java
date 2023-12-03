// Import necessary JavaFX classes
package g58137.chess.view;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Define the controller class for the menu view
public class MenuController {
    // Reference to the root VBox defined in the associated FXML file
    @FXML
    private VBox root;

    // Method to handle the "play" action
    public void play() {
        // Get the reference to the current stage
        Stage stage = (Stage) root.getScene().getWindow();

        // Create a new instance of the BoardGrid (assuming it's a custom class)
        BoardGrid board = new BoardGrid(stage);

        // Create a new Scene with the BoardGrid and set it as the stage's scene
        Scene scene = new Scene(board);
        stage.setScene(scene);
    }

    // Method to handle the "getConsole" action
    public void getConsole() {
        // Get the reference to the current stage
        Stage stage = (Stage) root.getScene().getWindow();

        // Close the current stage
        stage.close();
    }

    // Method to handle the "logout" action
    public void logout() {
        // Get the reference to the current stage
        Stage stage = (Stage) root.getScene().getWindow();

        // Close the current stage
        stage.close();

        // Exit the Java Virtual Machine (JVM)
        System.exit(0);
    }
}
