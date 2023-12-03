// Package declaration for the main class
package g58137.chess.main;

// Import statements for necessary classes from different packages
import g58137.chess.controller.Controller;
import g58137.chess.model.Game;
import g58137.chess.model.Model;
import g58137.chess.view.TextView;
import g58137.chess.view.FxView;

// Main class declaration
public class main {

    // The main method, the entry point of the program
    public static void main(String[] args) {

        // Launching the JavaFX view for the chess game
        FxView.main(args);

        // Creating an instance of the Game model
        Model game = new Game();

        // Creating an instance of the TextView, passing the game model to it
        TextView textView = new TextView(game);

        // Creating an instance of the Controller, passing the game model and TextView to it
        Controller controller = new Controller(game, textView);

        // Initiating the game by calling the play method on the controller
        controller.play();
    }
}
