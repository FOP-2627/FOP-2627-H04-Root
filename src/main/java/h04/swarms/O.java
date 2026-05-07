package h04.swarms;

import fopbot.RobotFamily;
import h04.ProjectionMatrix;
import h04.Tetromino;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

@StudentCreationRequired
public class O extends Swarm{

    public O(int x, int y) {
        super(x, y);
        //this.tetromino = Tetromino.O;
        this.projectionMatrix = new ProjectionMatrix(Tetromino.O);
    }

    @Override
    public void spawn() {
        super.spawn();
        for (int i = 0; i < tetrobots.length; i++) {
            tetrobots[i].setRobotFamily(RobotFamily.SQUARE_YELLOW);
        }
    }

    @Override
    public String toString() {
        return "O";
    }

}
