package h04.swarms;

import h04.ProjectionMatrix;
import h04.Tetrobot;
import h04.Tetromino;
import h04.Util;

public class S extends Swarm {

    public S(int x, int y, boolean mirrored) {
        this.x = x;
        this.y = y;
        this.tetromino = mirrored ? Tetromino.Z : Tetromino.S;
        this.projectionMatrix = new ProjectionMatrix(this.tetromino);
    }

    @Override
    public void spawn() {
        if (this.tetrobots != null) {
            for (int i = 0; i < tetrobots.length; i++) {
                Util.removeRobot(tetrobots[i]);
            }
        }

        int[][] projected = projectionMatrix.projectToWorld(x, y);
        Tetrobot[] newTetrobots = new Tetrobot[projected.length];
        for (int i = 0; i < projected.length; i++) {
            newTetrobots[i] = new Tetrobot(projected[i][0], projected[i][1]);
        }
        this.tetrobots = newTetrobots;
    }
}
