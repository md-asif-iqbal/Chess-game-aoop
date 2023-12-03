package g58137.chess.model.pieces;

import g58137.chess.model.*;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private static final String BLACK_BOLD = "\033[1;30m";
    private boolean enPassant;

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public List<Position> getPossibleMoves(Position position, Board board) {
        List<Position> moves = new ArrayList();
        boolean isBlack = this.color == Color.BLACK;
        if (isBlack) {
            canMove(board, position, Direction.S, moves);
            canCapture(board, position, Direction.SW, moves);
            canCapture(board, position, Direction.SE, moves);
        } else {
            canMove(board, position, Direction.N, moves);
            canCapture(board, position, Direction.NW, moves);
            canCapture(board, position, Direction.NE, moves);
        }
        return moves;
    }

    private void canCapture(Board board, Position position, Direction direction, List<Position> capture) {
        Position pos = position.next(direction);
        if (board.contains(pos)) {
            if (board.getPiece(pos) != null) {
                if (board.getPiece(position).color != board.getPiece(pos).getColor()) {
                    capture.add(pos);
                }
            } else {
                capture.add(pos);
            }
        }
    }

    private void canMove(Board board, Position position, Direction movement, List<Position> moves) {
        Position pos = position.next(movement);
        if (isEmptyPosition(board, pos)) {
            moves.add(pos);
            if (board.getInitialPawnRow(color).equals(position.getRow())) {
                pos = pos.next(movement);
                if (isEmptyPosition(board, pos)) {
                    moves.add(pos);
                }
            }
        }
        canEnPassant(board, position, moves, movement);
    }

    private void canEnPassant(Board board, Position position, List<Position> moves, Direction movement) {
        String opponentColor = getColor() == Color.BLACK ? "WHITE" : "BLACK";
        try {
            Pawn pawnPassedWest = (Pawn) board.getPiece(position.next(Direction.W));
            if (pawnPassedWest.getName().equals(opponentColor + "Pawn") && pawnPassedWest.enPassant
                    && board.isFree(position.next(Direction.W).next(movement))) {
                moves.add(position.next(Direction.W).next(movement));
            }
        } catch (Exception e) {
            // Handle exception appropriately
        }
        try {
            Pawn pawnPassedEast = (Pawn) board.getPiece(position.next(Direction.E));
            if (pawnPassedEast.getName().equals(opponentColor + "Pawn") && pawnPassedEast.enPassant
                    && board.isFree(position.next(Direction.E).next(movement))) {
                moves.add(position.next(Direction.E).next(movement));
            }
        } catch (Exception e) {
            // Handle exception appropriately
        }
    }

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }

    public String getName() {
        return color + "Pawn";
    }

    @Override
    public List<Position> getCapturePositions(Position position, Board board) {
        if (board.getPiece(position) != this) {
            throw new IllegalArgumentException("This position does not contain the piece");
        }
        List<Position> capture = new ArrayList();
        boolean isBlack = this.color == Color.BLACK;
        if (isBlack) {
            canCapture(board, position, Direction.SE, capture);
            canCapture(board, position, Direction.SW, capture);
        } else {
            canCapture(board, position, Direction.NE, capture);
            canCapture(board, position, Direction.NW, capture);
        }
        return capture;
    }
    @Override
    public String toString() {
        return color == Color.BLACK ? BLACK_BOLD + "♙" : "♙";
    }
}
