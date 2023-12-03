package g58137.chess.model;



import g58137.chess.model.pieces.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class BoardTest {
    
    private Board board;
    
    @BeforeEach
    public void setUp() {
        board = new Board();
    }


    @Test
    public void testContainsOnCorner() {

        Position pos = new Position(0,0);
        assertTrue(board.contains(pos));
    }
    
    @Test
    public void testContainsMiddleBoard() {

        Position pos = new Position(4,4);
        assertTrue(board.contains(pos));
    }
    
    @Test
    public void testContainsRowOutOfRange() {

        Position pos = new Position(-1,3);
        assertFalse(board.contains(pos));
    }
    
    @Test
    public void testContainsColOutOfRange() {

        Position pos = new Position(1,8);
        assertFalse(board.contains(pos));
    }
    
    @Test
    public void testContainsColAndRowOutOfRange() {

        Position pos = new Position(10,10);
        assertFalse(board.contains(pos));
    }
    

    
    @Test
    public void testIsFreePositionNotFree() {

        Position pos = new Position(1,1);
        board.setPiece(new Pawn(Color.BLACK), pos);
        assertFalse(board.isFree(pos));
    }
    
    @Test
    public void testIsFreePositionIsFree() {

        Position pos = new Position(3,2);
        
        assertTrue(board.isFree(pos));
    }

    @Test
    public void testContainsOppositeColorNoPiece() {

        Position pos = new Position(4,4);
        assertFalse(board.containsOppositeColor(pos, Color.BLACK));
    }
    
    @Test
    public void testContainsOppositeColorSameColorBlack() {

        Position pos = new Position(2,5);
        board.setPiece(new Pawn(Color.BLACK), pos);
        assertFalse(board.containsOppositeColor(pos, Color.BLACK));
    }
    
    @Test
    public void testContainsOppositeColorSameColorWhite() {

        Position pos = new Position(7,7);
        board.setPiece(new Pawn(Color.WHITE), pos);
        assertFalse(board.containsOppositeColor(pos, Color.WHITE));
    }
    
    @Test
    public void testContainsOppositeColorOppositeColorWB() {

        Position pos = new Position(7,7);
        board.setPiece(new Pawn(Color.WHITE), pos);
        assertTrue(board.containsOppositeColor(pos, Color.BLACK));
    }
    
    @Test
    public void testContainsOppositeColorOppositionColorBW() {

        Position pos = new Position(7,7);
        board.setPiece(new Pawn(Color.BLACK), pos);
        assertTrue(board.containsOppositeColor(pos, Color.WHITE));
    }

    
    @Test
    public void testGetPositionOccupiedByWhiteInitialPosition() {
    
        Color color = Color.WHITE;
        Player player = new Player(color);
        List<Position> positions; 
        List<Position> expected = new ArrayList<>();
        
        for (int i = 0; i < 8; i++){
            board.setPiece(new Pawn(color), new Position(1,i));
            expected.add(new Position(1,i));
        }
        
        positions = board.getPositionOccupiedBy(player);
        
        assertEqualsIgnoringOrder(positions,expected);   
    } 
    
    @Test
    public void testGetPositionOccupiedByWhiteRandomPositions() {
    
        Color color = Color.WHITE;
        Player player = new Player(color);
        List<Position> positions; 
        List<Position> expected = new ArrayList<>();
        
      
        board.setPiece(new Pawn(color), new Position(2,0));
        expected.add(new Position(2,0));
        
        board.setPiece(new Pawn(color), new Position(3,2));
        expected.add(new Position(3,2));
        
        board.setPiece(new Pawn(color), new Position(5,5));
        expected.add(new Position(5,5));
        
        positions = board.getPositionOccupiedBy(player);

        assertEqualsIgnoringOrder(positions,expected);   
    } 
    
    @Test
    public void testGetPositionOccupiedByBlackInitialPositions() {
    
        Color color = Color.BLACK;
        Player player = new Player(color);
        List<Position> positions; 
        List<Position> expected = new ArrayList<>();
        
        for (int i = 0; i < 8; i++){
            board.setPiece(new Pawn(color), new Position(6,i));
            expected.add(new Position(6,i));
        }
        
        positions = board.getPositionOccupiedBy(player);
        

        assertEqualsIgnoringOrder(positions,expected);
    } 
    
    @Test
    public void testGetPositionOccupiedByBlackRandomPositions() {
    
        Color color = Color.BLACK;
        Player player = new Player(color);
        List<Position> positions; 
        List<Position> expected = new ArrayList<>();
        
        board.setPiece(new Pawn(color), new Position(0,0));
        expected.add(new Position(0,0));
        
        board.setPiece(new Pawn(color), new Position(1,1));
        expected.add(new Position(1,1));
        
        board.setPiece(new Pawn(color), new Position(6,7));
        expected.add(new Position(6,7));
        
        board.setPiece(new Pawn(color), new Position(1,7));
        expected.add(new Position(1,7));
        
        positions = board.getPositionOccupiedBy(player);
        
        assertEqualsIgnoringOrder(positions,expected);
    }
    
    @Test
    public void testGetPositionOccupiedByBlackNoPositions() {
    
        Color color = Color.BLACK;
        Player player = new Player(color);
        List<Position> positions; 
        
        positions = board.getPositionOccupiedBy(player);
           
        assertTrue(positions.isEmpty());
    }

    private void assertEqualsIgnoringOrder(List<Position> expected, List<Position> actual) {
        assertEquals(expected.size(), actual.size());
        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }
}
