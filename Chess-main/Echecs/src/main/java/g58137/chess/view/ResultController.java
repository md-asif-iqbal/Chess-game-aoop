// Import necessary JavaFX and Java standard library classes
package g58137.chess.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

// Define the controller class for the result view
public class ResultController {
    // Reference to the root VBox defined in the associated FXML file
    @FXML
    private VBox root;

    // Reference to the victoryLabel defined in the associated FXML file
    @FXML
    private Label victoryLabel;

    // Reference to the primary stage
    private Stage primaryStage;

    // Method to set the primary stage and display the winner's name
    public void getPrimaryStage(Stage stage, String winnerName) {
        // Set the primary stage reference
        this.primaryStage = stage;

        // Set the text of the victoryLabel to display the winner's name
        victoryLabel.setText("The " + winnerName + " have won!");
    }

    // Method to handle the "rematch" action
    public void rematch() {
        // Create a new instance of the BoardGrid (assuming it's a custom class)
        BoardGrid board = new BoardGrid(primaryStage);

        // Create a new Scene with the BoardGrid and set it as the primary stage's scene
        Scene scene = new Scene(board);
        primaryStage.setScene(scene);

        // Close the current stage
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    // Method to handle the "back" action
    public void back() {
        // Get the reference to the current stage
        Stage stage = (Stage) root.getScene().getWindow();

        // Load the Menu.fxml file using FXMLLoader
        URL FxmlLocation = getClass().getResource("Menu.fxml");
        FXMLLoader loader = new FXMLLoader(FxmlLocation);

        try {
            // Create a new Scene with the loaded content and set it as the primary stage's scene
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);

            // Close the current stage
            stage.close();
        } catch (Exception e) {
            // Print the exception message if an error occurs during loading
            System.out.println(e.getMessage());
        }
    }
}
