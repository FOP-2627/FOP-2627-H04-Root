package h04.swarms;

import fopbot.RobotFamily;
import h04.ProjectionMatrix;
import h04.Tetromino;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

@StudentCreationRequired
/**
 * Swarm implementation for the L tetromino.
 */
public class L extends Swarm{

    private final boolean mirrored;

    /**
     * Creates a new L or J swarm.
     *
     * @param x the x-coordinate of the swarm
     * @param y the y-coordinate of the swarm
     * @param mirrored {@code true} to create the mirrored J variant, otherwise {@code false}
     */
    public L(int x, int y, boolean mirrored) {
        super(x, y);
        this.mirrored = mirrored;
        //this.tetromino = mirrored ? Tetromino.J : Tetromino.L;
        this.projectionMatrix = new ProjectionMatrix(mirrored ? Tetromino.J : Tetromino.L);
    }

    /**
     * Spawns the swarm and assigns the appropriate robot family.
     */
    @Override
    public void spawn() {
        super.spawn();
        RobotFamily robotFamily = mirrored ? RobotFamily.SQUARE_BLUE : RobotFamily.SQUARE_ORANGE;
        for (int i = 0; i < tetrobots.length; i++) {
            tetrobots[i].setRobotFamily(robotFamily);
        }
    }

    /**
     * Returns the tetromino name for this swarm.
     *
     * @return {@code "J"} if mirrored, otherwise {@code "L"}
     */
    @Override
    public String toString() {
        return mirrored ? "J" : "L";
    }

}
