package g58137.chess.model;

import g58137.chess.model.pieces.*;
import java.util.ArrayList;
import java.util.List;

public class Game implements Model {

    private Board board;
    private Player white;
    private Player black;
    private Player currentPlayer;
    private King whiteKing;
    private King blackKing;
    private GameState state;
    private Position pawnEnPassant;

    public Game() {
        white = new Player(Color.WHITE);
        black = new Player(Color.BLACK);
        board = new Board();
        whiteKing = new King(Color.WHITE);
        blackKing = new King(Color.BLACK);
    }

    //Constractor to initiate the Game

    @Override
    public void start() {
        for (int i = 0; i < board.getSquares().length; i++) {
            board.setPiece(new Pawn(Color.WHITE), new Position(1, i));
            board.setPiece(new Pawn(Color.BLACK), new Position(6, i));
            switch (i) {
                case 0 -> {
                    board.setPiece(new Rook(Color.WHITE), new Position(0, i));
                    board.setPiece(new Rook(Color.BLACK), new Position(7, i));
                }
                case 1 -> {
                    board.setPiece(new Knight(Color.WHITE), new Position(0, i));
                    board.setPiece(new Knight(Color.BLACK), new Position(7, i));
                }
                case 2 -> {
                    board.setPiece(new Bishop(Color.WHITE), new Position(0, i));
                    board.setPiece(new Bishop(Color.BLACK), new Position(7, i));
                }
                case 3 -> {
                    board.setPiece(new Queen(Color.WHITE), new Position(0, i));
                    board.setPiece(new Queen(Color.BLACK), new Position(7, i));
                }
                case 4 -> {
                    board.setPiece(whiteKing, new Position(0, i));
                    board.setPiece(blackKing, new Position(7, i));
                }
                case 5 -> {
                    board.setPiece(new Bishop(Color.WHITE), new Position(0, i));
                    board.setPiece(new Bishop(Color.BLACK), new Position(7, i));
                }
                case 6 -> {
                    board.setPiece(new Knight(Color.WHITE), new Position(0, i));
                    board.setPiece(new Knight(Color.BLACK), new Position(7, i));
                }
                case 7 -> {
                    board.setPiece(new Rook(Color.WHITE), new Position(0, i));
                    board.setPiece(new Rook(Color.BLACK), new Position(7, i));
                }
            }
        }
        currentPlayer = white; //set current player to white i.e. the first move player 1 starts
        state = GameState.PLAY;
        // Setting up pawns, rooks, knights, bishops, queens, and kings on the board.
        // Each piece is associated with its color (WHITE or BLACK).
        // The board and players are initialized for the start of the game.
        // The current player is set to white, and the game state is set to PLAY.
    }

    //getter for current and oposite player
    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public Player getOppositePlayer() {
        return currentPlayer.getColor() == Color.BLACK ? white : black;
    }

    @Override
    public List<Position> getPossibleMoves(Position position) {
        return board.getPiece(position).getPossibleMoves(position, board);
    }   // Get a list of possible moves for a given position.

    @Override
    public Piece getPiece(Position pos) {
        if (!board.contains(pos)) {
            throw new IllegalArgumentException("The given position is not on the board.");
        }
        return board.getPiece(pos);
    }
    // Get the piece at a given position.
    @Override
    public GameState getState() {
        return state;
    }

    // Get the current game state (PLAY, CHECK, CHECK_MATE, STALE_MATE).

    // Check if the given position belongs to the current player.
    @Override
    public boolean isCurrentPlayerPosition(Position pos) {
        if (!board.contains(pos)) {
            throw new IllegalArgumentException("The given position is not on the board.");
        }
        if (getPiece(pos) == null) {
            throw new IllegalArgumentException("There is no piece at this position.");
        }
        return getPiece(pos).getColor() == currentPlayer.getColor();
    }

    // Move a piece from oldPos to newPos on the board.
    @Override
    public void movePiecePosition(Position oldPos, Position newPos) {
        if (!board.contains(oldPos)) {
            throw new IllegalArgumentException("The first given position is not on the board.");
        } else if (!board.contains(newPos)) {
            throw new IllegalArgumentException("The second given position is not on the board.");
        } else if (board.isFree(oldPos)) {
            throw new IllegalArgumentException("There is no piece at this position.");
        } else if (!this.isCurrentPlayerPosition(oldPos)) {
            throw new IllegalArgumentException("This piece does not belong to you.");
        } else if (!board.getPiece(oldPos).getPossibleMoves(oldPos, board).contains(newPos)) {
            throw new IllegalArgumentException("You cannot make this move.");
        } else if (!isValidMove(oldPos, newPos)) {
            throw new IllegalArgumentException("This move is not valid.");
        }
        Piece piece = getPiece(oldPos);
        board.setPiece(piece, newPos);
        board.dropPiece(oldPos);

        enPassant(piece, oldPos, newPos);
        promotion(newPos);
        rook(piece, oldPos, newPos);

        state = GameState.PLAY;

        if (check()) {
            state = GameState.CHECK;
            if (checkmate()) {
                state = GameState.CHECK_MATE;
            }
        } else if (stalemate()) {
            state = GameState.STALE_MATE;
        }
        currentPlayer = getOppositePlayer();
        // Various checks to ensure the move is valid and update the board accordingly.
        // Handle special moves like en passant, promotion, and rook castling.
        // Update game state based on the result of the move (CHECK, CHECK_MATE, STALE_MATE).
        // Switch the current player to the opposite player.
    }

    // king castling
    private void rook(Piece piece, Position oldPos, Position newPos) {
        try {
            if (piece.getName().toLowerCase().contains("king")) {
                if (oldPos.next(Direction.E).next(Direction.E).equals(newPos)) {
                    Rook rook = (Rook) getPiece(newPos.next(Direction.E));
                    board.setPiece(rook, oldPos.next(Direction.E));
                    board.dropPiece(newPos.next(Direction.E));
                } else if (oldPos.next(Direction.W).next(Direction.W).equals(newPos)) {
                    Rook rook = (Rook) getPiece(newPos.next(Direction.W).next(Direction.W));
                    board.setPiece(rook, oldPos.next(Direction.W));
                    board.dropPiece(newPos.next(Direction.W).next(Direction.W));
                }
            }
        } catch (Exception e) {
        }
    }

    // Handle pawn promotion to queen when reaching the opponent's back rank.
    private void promotion(Position newPos) {
        Piece piece = getPiece(newPos);
        if ("♟".equals(piece.toString())) {
            if (newPos.getRow() == 0) {
                board.setPiece(new Queen(Color.BLACK), newPos);
            }
        } else if ("♙".equals(piece.toString())) {
            if (newPos.getRow() == 7) {
                board.setPiece(new Queen(Color.WHITE), newPos);
            }
        }
    }
    // Get positions that can be captured by pieces of a given player.
    private void enPassant(Piece piece, Position oldPos, Position newPos) {
        try {
            Pawn p = (Pawn) board.getPiece(pawnEnPassant);
            p.setEnPassant(false);
        } catch (Exception e) {
        }
        try {
            Pawn pawn = (Pawn) piece;
            if (pawn.getColor() == Color.WHITE && oldPos.next(Direction.N).next(Direction.N).equals(newPos)
                    || pawn.getColor() == Color.BLACK && oldPos.next(Direction.S).next(Direction.S).equals(newPos)) {
                pawn.setEnPassant(true);
                pawnEnPassant = newPos;
            }
            if (pawn.getColor() == Color.WHITE && oldPos.next(Direction.N).next(Direction.E).equals(newPos)
                    || oldPos.next(Direction.N).next(Direction.W).equals(newPos)) {
                board.dropPiece(newPos.next(Direction.S));
            } else if (pawn.getColor() == Color.BLACK && oldPos.next(Direction.S).next(Direction.E).equals(newPos)
                    || oldPos.next(Direction.S).next(Direction.W).equals(newPos)) {
                board.dropPiece(newPos.next(Direction.N));
            }
        } catch (Exception e) {
        }
    }

    protected Board getBoard() {
        return board;
    }

    protected void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public boolean isValidMove(Position oldPos, Position newPos) {
        if (board.isFree(oldPos)) {
            throw new IllegalArgumentException("The given position does not contain a piece.");
        } else if (!board.getPiece(oldPos).getPossibleMoves(oldPos, board).contains(newPos)) {
            throw new IllegalArgumentException("The move is not possible.");
        }
        Piece piece = board.getPiece(oldPos);
        Color myColor = piece.getColor();
        Piece piece2 = board.getPiece(newPos);
        Color color = piece.getColor().opposite();

        board.setPiece(piece, newPos);
        board.dropPiece(oldPos);

        Position kingPosition = board.getPiecePosition(getKing(new Player(myColor)));

        // if the attacking player is in check after their move
        boolean valid = !getCapturePositions(new Player(color)).contains(kingPosition);

        board.setPiece(piece, oldPos);
        board.dropPiece(newPos);
        board.setPiece(piece2, newPos);

        return valid;

        // Check if the move is possible for the piece at oldPos.
        // Simulate the move on the board and check if it puts the current player's king in check.
        // Revert the board to its original state after the simulation.
        // Return true if the move is valid, false otherwise.
    }

    private List<Position> getCapturePositions(Player player) {
        List<Position> possibleCaptures = new ArrayList();

        List<Position> pieces = board.getPositionOccupiedBy(player);

        for (int i = 0; i < pieces.size(); i++) {
            Position position = pieces.get(i);
            List<Position> capturesPiece = board.getPiece(position).getCapturePositions(position, board);
            possibleCaptures.addAll(capturesPiece);
        }
        return possibleCaptures;
    }

    private King getKing(Player player) {
        return player.getColor() == Color.WHITE ? whiteKing : blackKing;
    }

    private boolean check() {
        return getCapturePositions(currentPlayer).contains(board.getPiecePosition(getKing(getOppositePlayer())));
    }

    // Check if the current player is in check.
    private boolean checkmate() {
        List<Position> pieces = board.getPositionOccupiedBy(getOppositePlayer());
        boolean mate = false;
        for (int i = 0; i < pieces.size(); i++) {
            List<Position> moves = getPossibleMoves(pieces.get(i));
            for (int j = 0; j < moves.size(); j++) {
                Position oldPos = pieces.get(i);
                Position newPos = moves.get(j);

                if (isValidMove(oldPos, newPos)) {
                    Piece piece2 = getPiece(newPos);
                    board.setPiece(getPiece(oldPos), newPos);
                    board.dropPiece(oldPos);
                    if (!check()) {
                        board.setPiece(getPiece(newPos), oldPos);
                        board.dropPiece(newPos);
                        board.setPiece(piece2, newPos);
                        return mate;
                    }
                    board.setPiece(getPiece(newPos), oldPos);
                    board.dropPiece(newPos);
                    board.setPiece(piece2, newPos);
                }
            }
        }
        mate = true;
        return mate;
    }

    // Check if the current player is in check.

    private boolean stalemate() {
        List<Position> pieces = board.getPositionOccupiedBy(getOppositePlayer());
        boolean stalemate = false;
        for (int i = 0; i < pieces.size(); i++) {
            List<Position> moves = getPossibleMoves(pieces.get(i));
            Position oldPos = pieces.get(i);

            for (int j = 0; j < moves.size(); j++) {
                Position newPos = moves.get(j);
                if (isValidMove(oldPos, newPos)) {
                    return stalemate;
                }
            }
        }
        stalemate = true;
        return stalemate;
    }
}
