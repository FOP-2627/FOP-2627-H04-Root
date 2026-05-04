package h04.swarms;

import fopbot.Direction;
import h04.ProjectionMatrix;
import h04.TetrisWorld;
import h04.Tetrobot;
import h04.Tetromino;

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

    public abstract void spawn();

}
