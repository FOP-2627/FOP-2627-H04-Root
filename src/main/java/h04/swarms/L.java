package h04.swarms;

import fopbot.RobotFamily;
import h04.ProjectionMatrix;
import h04.Tetromino;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

@StudentCreationRequired
public class L extends Swarm{

    private final boolean mirrored;

    public L(int x, int y, boolean mirrored) {
        super(x, y);
        this.mirrored = mirrored;
        //this.tetromino = mirrored ? Tetromino.J : Tetromino.L;
        this.projectionMatrix = new ProjectionMatrix(mirrored ? Tetromino.J : Tetromino.L);
    }

    @Override
    public void spawn() {
        super.spawn();
        RobotFamily robotFamily = mirrored ? RobotFamily.SQUARE_BLUE : RobotFamily.SQUARE_ORANGE;
        for (int i = 0; i < tetrobots.length; i++) {
            tetrobots[i].setRobotFamily(robotFamily);
        }
    }

    @Override
    public String toString() {
        return mirrored ? "J" : "L";
    }

}
