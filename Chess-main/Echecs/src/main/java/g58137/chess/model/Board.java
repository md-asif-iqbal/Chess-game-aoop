// Import necessary classes
package g58137.chess.model;

import g58137.chess.model.pieces.Piece;
import java.util.ArrayList;
import java.util.List;

// Define the Board class
public class Board {

    // Declare a 2D array to represent the squares on the chessboard
    private Square[][] squares;

    // Constructor to initialize the chessboard with empty squares
    public Board() {
        squares = new Square[8][8];
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                // Initialize each square with a null piece
                squares[i][j] = new Square(null);
            }
        }
    }

    // Getter method to retrieve the squares array
    public Square[][] getSquares() {
        return squares;
    }

    // Check if a given position is within the bounds of the board
    public boolean contains(Position pos) {
        Integer row = pos.getRow();
        Integer column = pos.getColumn();

        return !(row < 0 || row > squares.length-1 || column < 0 || column > squares.length-1);
    }

    // Set a piece at a specified position on the board
    public void setPiece(Piece piece, Position position) {
        if (!contains(position)) {
            throw new IllegalArgumentException("The given position is not on the board.");
        }
        Integer row = position.getRow();
        Integer column = position.getColumn();

        // Set the specified piece on the square at the given position
        squares[column][row].setPiece(piece);
    }

    // Get the piece at a specified position on the board
    public Piece getPiece(Position pos) {
        if (!contains(pos)) {
            throw new IllegalArgumentException("The given position is not on the board.");
        }
        Integer row = pos.getRow();
        Integer column = pos.getColumn();

        // Retrieve the piece from the square at the given position
        return squares[column][row].getPiece();
    }

    // Get the initial row for a pawn based on its color
    public Integer getInitialPawnRow(Color color) {
        return color == Color.BLACK ? 6 : 1;
    }

    // Remove a piece from a specified position on the board
    public void dropPiece(Position pos) {
        if (!contains(pos)) {
            throw new IllegalArgumentException("The given position is not on the board.");
        }
        // Set the piece at the given position to null, effectively removing it
        setPiece(null, pos);
    }

    // Check if a specified position on the board is free (contains no piece)
    public boolean isFree(Position pos) {
        if (!contains(pos)) {
            throw new IllegalArgumentException("The given position is not on the board.");
        }
        Integer row = pos.getRow();
        Integer column = pos.getColumn();

        // Check if the square at the given position is free
        return squares[column][row].isFree();
    }

    // Check if a specified position on the board contains a piece of the opposite color
    public boolean containsOppositeColor(Position pos, Color col) {
        if (!contains(pos)) {
            throw new IllegalArgumentException("The given position is not on the board.");
        }
        if (isFree(pos)) {
            return false;
        }
        // Check if the piece at the given position has a color different from the specified color
        return this.getPiece(pos).getColor() != col;
    }

    // Get a list of positions occupied by pieces of a specific player
    public List<Position> getPositionOccupiedBy(Player player) {
        List<Position> occupiedPositions = new ArrayList();

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                Position pos = new Position(j, i);
                // Check if the square at the current position is not free and has a piece of the player's color
                if (!isFree(pos)) {
                    if (player.getColor() == getPiece(pos).getColor()) {
                        // Add the position to the list of occupied positions
                        occupiedPositions.add(new Position(j, i));
                    }
                }
            }
        }
        return occupiedPositions; //moving is possible player-wise?
    }

    // Get the position of a specified piece on the board
    public Position getPiecePosition(Piece piece){
        for(int i = 0; i<squares.length; i++){
            for(int j = 0;j<squares[i].length;j++){
                Position pos = new Position(j,i);
                // Check if the piece at the current position matches the specified piece
                if(getPiece(pos) == piece){
                    return pos;
                }
            }
        }
        // Return null if the piece is not found on the board
        return null;
    }
}
