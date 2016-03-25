/**
 * Created by Calvin Wong & Jyoti Sharma
 *
 *
 * ===================
 * Puzzle Button Class
 * ===================
 *
 * Class Description: PuzzleButton (JButton) for Logic Puzzle
 *
 */
import javax.swing.*;

public class PuzzleButton extends JButton {

    String value;

    public PuzzleButton(String value) {
        this.setText(value); // Set Button Name
    }

    public void setValue(String value) {
        this.value = value;
        setText(value);
    }

    public String getValue() {
        return value;
    }

    public boolean equals(PuzzleButton object2) {
        if (this.value.equals(object2.getValue())) {
            return true;
        }
        else
            return false;
    }

    public String toString() {
        return "PuzzleButton{" +
                "value='" + value + '\'' +
                '}';
    }
} // end of class

