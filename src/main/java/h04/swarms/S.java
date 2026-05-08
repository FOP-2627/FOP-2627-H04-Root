package h04.swarms;

import fopbot.RobotFamily;
import h04.ProjectionMatrix;
import h04.Tetrobot;
import h04.Tetromino;
import h04.Utils;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

/**
 * Swarm implementation for the S tetromino.
 */
@StudentCreationRequired
public class S extends Swarm {

    private final boolean mirrored;

    /**
     * Creates a new S or Z swarm.
     *
     * @param x the x-coordinate of the swarm
     * @param y the y-coordinate of the swarm
     * @param mirrored {@code true} to create the mirrored Z variant, otherwise {@code false}
     */
    public S(int x, int y, boolean mirrored) {
        super(x, y);
        this.mirrored = mirrored;
        //this.tetromino = mirrored ? Tetromino.Z : Tetromino.S;
        this.projectionMatrix = new ProjectionMatrix(mirrored ? Tetromino.Z : Tetromino.S);
    }

    /**
     * Spawns the swarm and assigns the appropriate robot family.
     */
    @Override
    public void spawn() {
        super.spawn();
        RobotFamily robotFamily = mirrored ? RobotFamily.SQUARE_RED : RobotFamily.SQUARE_GREEN;
        for (int i = 0; i < tetrobots.length; i++) {
            tetrobots[i].setRobotFamily(robotFamily);
        }
    }

    /**
     * Returns the tetromino name for this swarm.
     *
     * @return {@code "Z"} if mirrored, otherwise {@code "S"}
     */
    @Override
    public String toString() {
        return mirrored ? "Z" : "S";
    }

}
