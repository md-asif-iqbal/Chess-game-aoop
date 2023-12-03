package g58137.chess.model.pieces;

import g58137.chess.model.*;
import java.util.ArrayList;
import java.util.List;


public class FlyingBishop extends Piece {

    public FlyingBishop(Color color) {
        super(color);
    }

    @Override
    public List<Position> getPossibleMoves(Position position, Board board) {
        List<Position> moves = new ArrayList();

        diagonalMove(board, position, Direction.NW, moves);
        diagonalMove(board, position, Direction.NE, moves);
        diagonalMove(board, position, Direction.SW, moves);
        diagonalMove(board, position, Direction.SE, moves);

        return moves;
    }

    private void diagonalMove(Board board, Position position, Direction dir, List<Position> moves) {
        Position pos = position;
        switch (dir) {
            case NE -> {
                while (true) {
                    pos = pos.next(Direction.NE);
                    if (board.contains(pos)) {
                        if (board.isFree(pos)) {
                            moves.add(pos);
                        }
                        else if (color != board.getPiece(pos).color){
                            moves.add(pos);
                        }
                    } else {
                        break;
                    }
                }
            }
            case NW -> {
                while (true) {
                    pos = pos.next(Direction.NW);
                    if (board.contains(pos)) {
                        if (board.isFree(pos)) {
                            moves.add(pos);
                        }
                        else if (color != board.getPiece(pos).color){
                            moves.add(pos);
                        }
                    } else {
                        break;
                    }
                }
            }
            case SE -> {
                while (true) {
                    pos = pos.next(Direction.SE);
                    if (board.contains(pos)) {
                        if (board.isFree(pos)) {
                            moves.add(pos);
                        }
                        else if (color != board.getPiece(pos).color){
                            moves.add(pos);
                        }
                    } else {
                        break;
                    }
                }
            }
            case SW -> {
                while (true) {
                    pos = pos.next(Direction.SW);
                    if (board.contains(pos)) {
                        if (board.isFree(pos)) {
                            moves.add(pos);
                        }
                        else if (color != board.getPiece(pos).color){
                            moves.add(pos);
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public String getName(){
        return color + "FlyingBishop";
    }
}
