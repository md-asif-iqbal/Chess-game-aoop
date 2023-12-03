package g58137.chess.controller;

import g58137.chess.model.*;
import g58137.chess.view.*;

public class Controller {

    private View view;
    private Model model;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void play() {
        Model game = new Game();
        View view = new TextView(game);

        // A boolean to check if the game is over.
        boolean gameIsOver = false;

        // Display a title and welcome message to the players.
        view.displayTitle();

        // Prepare the game board.
        game.start();

        // A boolean to check if the position is valid.
        boolean goodPosition = false;

        Position start = null;
        Position end;

        while (!gameIsOver) { // Loop until the game is over.
            view.displayPlayer();
            view.displayState(game.getState());
            view.displayBoard();
            // (Re)check if the game is over.
            while (!goodPosition) {
                start = view.askPosition();
                try {
                    // Is it my piece?
                    if (game.isCurrentPlayerPosition(start)) { // Yes, it's my piece.
                        // Can my piece move?
                        if (game.getPossibleMoves(start).isEmpty()) { // No, it can't move.
                            view.displayError("This piece cannot move.");
                        } else { // Yes, it can move.
                            goodPosition = true;
                        }
                    } else { // No, it's not my piece.
                        view.displayError("This is not your piece.");
                    }
                } catch (Exception e) { // No, an exception is thrown because there is no piece at this position.
                    view.displayError(e.getMessage());
                }
            }
            goodPosition = false;
            while (!goodPosition) {
                view.displayMessage("Where do you want to go?");
                end = view.askPosition();
                try {
                    // Is it a valid move?
                    game.movePiecePosition(start, end);
                    // Yes.
                    goodPosition = true;
                } catch (Exception e) { // No, an exception is thrown.
                    view.displayError(e.getMessage());
                    goodPosition = true;
                }
            }
            goodPosition = false;
            gameIsOver = game.getState() == GameState.CHECK_MATE || game.getState() == GameState.STALE_MATE;
        }
        // Display the winner.
        view.displayWinner();
    }
}
