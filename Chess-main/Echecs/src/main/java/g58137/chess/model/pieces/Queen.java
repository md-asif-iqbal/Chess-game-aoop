package g58137.chess.model.pieces;

import g58137.chess.model.*;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    public static final String BLACK_BOLD = "\033[1;30m";

    public Queen (Color color){
        super(color);
    }

    public String getName(){
        return color + "Queen";
    }

    @Override
    public List<Position> getPossibleMoves(Position position, Board board) {
        List<Position> moves = new ArrayList();
        line(board, position, Direction.E, moves);
        line(board, position, Direction.N, moves);
        line(board, position, Direction.S, moves);
        line(board, position, Direction.W, moves);
        diagonal(board, position, Direction.NW, moves);
        diagonal(board, position, Direction.NE, moves);
        diagonal(board, position, Direction.SW, moves);
        diagonal(board, position, Direction.SE, moves);

        return moves;
    }

    @Override
    public String toString() {
        return color == Color.BLACK ? BLACK_BOLD+"♛" : "♛";
    }
}
