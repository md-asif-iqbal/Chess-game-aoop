// Import necessary classes
package g58137.chess.model;

// Define the Player class
public class Player {

    // Private member variable to store the color of the player
    private Color color;

    // Constructor to initialize a player with a specified color
    public Player(Color color) {
        this.color = color;
    }

    // Getter method to retrieve the color of the player
    public Color getColor() {
        return color;
    }

    // Hash code method to generate a hash code for the player
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    // Equals method to check if two players are equal based on their color
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (this.color != other.color) {
            return false;
        }
        return true;
    }

    // ToString method to represent the player as a string
    @Override
    public String toString() {
        // Return "white" if the player's color is BLACK, otherwise return "black"
        if (color == Color.BLACK) {
            return "white";
        }
        return "black";
    }
}
