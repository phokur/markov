
public class Piece {

    public enum PieceType {

        PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING, EMPTY
    }

    public enum ColorType {

        WHITE, BLACK, EMPTY
    }
    boolean doubleMoved;
    int moves;
    PieceType type; // Contains what type of piece it is
    ColorType color; // 0-white 1-black
    String id; // Instance of piece, used for identification

    public Piece(PieceType type, ColorType color, String id) {
        this.type = type;
        this.color = color;
        this.id = id;
    }
}
