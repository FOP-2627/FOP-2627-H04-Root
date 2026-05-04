package h04.swarms;

import h04.ProjectionMatrix;
import h04.Tetromino;

public class T extends Swarm{

    public T(int x, int y) {
        this.x = x;
        this.y = y;
        this.tetromino = Tetromino.T;
        this.projectionMatrix = new ProjectionMatrix(Tetromino.T);
    }

}
