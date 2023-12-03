package g58137.chess.model.pieces;

import g58137.chess.model.*;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public static final String BLACK_BOLD = "\033[1;30m";

    public King(Color color) {
        super(color);
    }

    @Override
    public List<Position> getPossibleMoves(Position position, Board board) {
        List<Position> moves = new ArrayList();
        kingMove(board, position, Direction.E, moves);
        kingMove(board, position, Direction.N, moves);
        kingMove(board, position, Direction.NE, moves);
        kingMove(board, position, Direction.NW, moves);
        kingMove(board, position, Direction.S, moves);
        kingMove(board, position, Direction.SE, moves);
        kingMove(board, position, Direction.SW, moves);
        kingMove(board, position, Direction.W, moves);
        return moves;
    }

    public String getName(){
        return color + "King";
    }

    private void kingMove(Board board, Position position, Direction dir, List<Position> moves) {
        Position pos = position.next(dir);
        try {
            if (board.isFree(pos)) {
                moves.add(pos);
            } else if (this.color != board.getPiece(pos).color){
                moves.add(pos);
            }
            if(getInitialKingRow(color).equals(position.getRow()) &&
                    board.getPiece(pos) == null && board.getPiece(pos.next(dir)) == null){
                if(dir == Direction.E){
                    moves.add(pos.next(dir));
                } else if (dir == Direction.W && board.getPiece(pos.next(dir).next(dir)) == null) {
                    moves.add(pos.next(dir));
                }
            }
        } catch (Exception e) {}
    }

    public Integer getInitialKingRow(Color color) {
        return color == Color.BLACK ? 7 : 0;
    }

    @Override
    public String toString() {
        return color == Color.BLACK ? BLACK_BOLD+"♚" : "♚";
    }
}
