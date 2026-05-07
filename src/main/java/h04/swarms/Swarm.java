package h04.swarms;

import fopbot.Direction;
import fopbot.World;
import h04.*;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

public abstract class Swarm {

    @DoNotTouch
    protected int x;

    @DoNotTouch
    protected int y;

    protected Tetrobot[] tetrobots;

    @DoNotTouch
    protected ProjectionMatrix projectionMatrix;

//    @DoNotTouch
//    protected Tetromino tetromino;

    @DoNotTouch
    public int getX() {
        return x;
    }

    @DoNotTouch
    public int getY() {
        return y;
    }

    @DoNotTouch
    public ProjectionMatrix getProjectionMatrix() {
        return projectionMatrix;
    }

    @DoNotTouch
    public void setProjectionMatrix(ProjectionMatrix projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

//    @DoNotTouch
//    public Tetromino getTetromino() {
//        return tetromino;
//    }


    /*
     * Moves the swarm in the specified direction.
     *
     * @param direction the direction to move
     * @throws IllegalArgumentException if the direction is not supported for swarm movement (e.g., UP)
     */
    @DoNotTouch
    public Swarm (int x, int y) {
        this.x = x;
        this.y = y;
    }

    @DoNotTouch
    public void move(Direction direction) throws IllegalArgumentException {
        if (direction == Direction.UP)
            throw new IllegalArgumentException("Direction UP is not supported for Swarm movement.");

        for (int i = 0; i < tetrobots.length; i++) {
            while (!tetrobots[i].getDirection().equals(direction)) {
                tetrobots[i].turnLeft();
            }
        }
        for (int i = 0; i < tetrobots.length; i++) {
            tetrobots[i].move();
        }
        x += direction.getDx();
        y += direction.getDy();
    }

    @DoNotTouch
    public boolean isFrontClear() {
        for (int i = 0; i < tetrobots.length; i++) {
            if (!tetrobots[i].isFrontClear(tetrobots)) {
                return false;
            }
        }
        return true;
    }

    @DoNotTouch
    public boolean canMove(Direction direction) {
        if (direction == Direction.UP)
            return false;

        for (int i = 0; i < tetrobots.length; i++) {
            while (!tetrobots[i].getDirection().equals(direction)) {
                tetrobots[i].turnLeft();
            }
            if (!tetrobots[i].isFrontClear(tetrobots)) {
                return false;
            }
        }

        return true;
    }

    @DoNotTouch
    public void spawn() {
        if (this.tetrobots != null) {
            for (int i = 0; i < tetrobots.length; i++) {
                Utils.removeRobot(tetrobots[i]);
            }
        }

        int[][] projected = projectionMatrix.projectToWorld(x, y);
        Tetrobot[] newTetrobots = new Tetrobot[projected.length];
        for (int i = 0; i < projected.length; i++) {
            newTetrobots[i] = new Tetrobot(projected[i][0], projected[i][1]);
        }
        this.tetrobots = newTetrobots;
    }

    @DoNotTouch
    public void commitSRSRotation() {
        int[][] projected = projectionMatrix.projectToWorld(x, y);
        for (int i = 0; i < tetrobots.length; i++) {
            int newX = projected[i][0];
            int newY = projected[i][1];
            tetrobots[i].setX(newX);
            tetrobots[i].setY(newY);
        }
    }

    @DoNotTouch
    public void turnRight() {
        projectionMatrix.rotateRight();
        commitSRSRotation();
    }

    @DoNotTouch
    public void turnLeft() {
        projectionMatrix.rotateLeft();
        commitSRSRotation();
    }
}
