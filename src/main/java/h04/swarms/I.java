package h04.swarms;

import fopbot.RobotFamily;
import h04.ProjectionMatrix;
import h04.Tetromino;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class I extends Swarm{

    public I(int x, int y) {
        super(x, y);
        //this.tetromino = Tetromino.I;
        this.projectionMatrix = new ProjectionMatrix(Tetromino.I);
    }

    @Override
    public void spawn() {
        super.spawn();
        for (int i = 0; i < tetrobots.length; i++) {
            tetrobots[i].setRobotFamily(RobotFamily.SQUARE_AQUA);
        }
    }

    @Override
    public String toString() {
        return "I";
    }

}
