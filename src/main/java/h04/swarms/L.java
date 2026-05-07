package h04.swarms;

import h04.ProjectionMatrix;
import h04.Tetromino;

public class L extends Swarm{

    private final boolean mirrored;

    public L(int x, int y, boolean mirrored) {
        super(x, y);
        this.mirrored = mirrored;
        this.tetromino = mirrored ? Tetromino.J : Tetromino.L;
        this.projectionMatrix = new ProjectionMatrix(this.tetromino);
    }

    @Override
    public String toString() {
        return mirrored ? "J" : "L";
    }

}
