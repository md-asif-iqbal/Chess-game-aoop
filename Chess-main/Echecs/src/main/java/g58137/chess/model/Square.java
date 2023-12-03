package g58137.chess.model;

import g58137.chess.model.pieces.Piece;

public class Square {

    private Piece piece;

    public Square(Piece piece){
        this.piece = piece;
    } //constractor

    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isFree(){
        return piece == null;
    }
}
