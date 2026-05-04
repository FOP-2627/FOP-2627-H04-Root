package h04.swarms;

import fopbot.Direction;
import h04.*;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public abstract class Swarm {

    protected int x;

    protected int y;

    protected Tetrobot[] tetrobots;

    protected ProjectionMatrix projectionMatrix;

    protected Tetromino tetromino;

//    public void move() {
//        for (int i = 0; i < tetrobots.length; i++) {
//            tetrobots[i].move();
//        }
//    }

    public void move(Direction direction) {
        for (int i = 0; i < tetrobots.length; i++) {
            while (tetrobots[i].getDirection().equals(direction)) {
                tetrobots[i].turnLeft();
            }
        }
        for (int i = 0; i < tetrobots.length; i++) {
            tetrobots[i].move();
        }
    }

    public boolean isFrontClear() {
        for (int i = 0; i < tetrobots.length; i++) {
            if (!tetrobots[i].isFrontClear(tetrobots)) {
                return false;
            }
        }
        return true;
    }

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
