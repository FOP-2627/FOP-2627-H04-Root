package h04.swarms;

import h04.ProjectionMatrix;
import h04.Tetromino;

public class O extends Swarm{

    public O(int x, int y) {
        this.x = x;
        this.y = y;
        this.tetromino = Tetromino.O;
        this.projectionMatrix = new ProjectionMatrix(Tetromino.O);
    }

}
