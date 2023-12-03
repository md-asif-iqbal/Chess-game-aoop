package g58137.chess.model;


public enum Color {
    WHITE, BLACK;
     

    public Color opposite(){
        return this == BLACK ? WHITE : BLACK;
    }
}
