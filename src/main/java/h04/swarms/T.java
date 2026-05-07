package h04.swarms;

import fopbot.RobotFamily;
import h04.ProjectionMatrix;
import h04.Tetromino;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class T extends Swarm{

    public T(int x, int y) {
        super(x, y);
        //this.tetromino = Tetromino.T;
        this.projectionMatrix = new ProjectionMatrix(Tetromino.T);
    }

    @Override
    public void spawn() {
        super.spawn();
        for (int i = 0; i < tetrobots.length; i++) {
            tetrobots[i].setRobotFamily(RobotFamily.SQUARE_PURPLE);
        }
    }

    @Override
    public String toString() {
        return "T";
    }

}
