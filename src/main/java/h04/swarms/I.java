package h04.swarms;

import h04.ProjectionMatrix;
import h04.Tetromino;

public class I extends Swarm{

    public I(int x, int y) {
        super(x, y);
        this.tetromino = Tetromino.I;
        this.projectionMatrix = new ProjectionMatrix(Tetromino.I);
    }

    @Override
    public String toString() {
        return "I";
    }

}
