// Package declaration for the FxView class
package g58137.chess.view;

// Import statements for necessary JavaFX classes
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

// FxView class declaration, extending the Application class
public class FxView extends Application {

    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        Application.launch(args);
    }

    // Overriding the start method from the Application class
    @Override
    public void start(Stage stage) throws Exception {

        // Setting the title of the application window
        stage.setTitle("Chess");

        // Making the application window not resizable
        stage.setResizable(true);

        // Getting the URL of the FXML file that defines the user interface (Menu.fxml)
        URL fxmlLocation = getClass().getResource("Menu.fxml");

        // Creating a FXMLLoader to load the FXML file
        FXMLLoader root = new FXMLLoader(fxmlLocation);

        // Creating a Scene and loading the FXML file into it
        Scene scene = new Scene(root.load());

        // Setting the scene for the application window
        stage.setScene(scene);

//        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("resources/g58137/chess/view/icon.png")));



        // Showing the application window
        stage.show();

        // Adding an event handler for the close request of the window to exit the application
        stage.setOnCloseRequest(event -> {
            System.exit(0);
        });
    }
}
