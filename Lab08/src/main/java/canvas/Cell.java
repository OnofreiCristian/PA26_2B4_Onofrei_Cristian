package canvas;

import lombok.Getter;
import lombok.Setter;

import javax.crypto.SealedObject;
import java.io.Serializable;

@Getter
@Setter
public class Cell implements Serializable {

    private int row;
    private int col;

    private boolean topWall = true;
    private boolean bottomWall = true;
    private boolean leftWall = true;
    private boolean rightWall = true;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public boolean hasTopWall() {
        return topWall;
    }
    public boolean hasBottomWall() {
        return bottomWall;
    }
    public boolean hasLeftWall() {
        return leftWall;
    }
    public boolean hasRightWall() {
        return rightWall;
    }
}
