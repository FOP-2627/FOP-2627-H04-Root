package h04.swarms;

import h04.ProjectionMatrix;
import h04.Tetromino;

public class L extends Swarm{

    public L(int x, int y, boolean mirrored) {
        super(x, y);
        this.tetromino = mirrored ? Tetromino.J : Tetromino.L;
        this.projectionMatrix = new ProjectionMatrix(this.tetromino);
    }

}
