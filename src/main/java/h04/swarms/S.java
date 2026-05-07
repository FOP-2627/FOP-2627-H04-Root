package h04.swarms;

import h04.ProjectionMatrix;
import h04.Tetrobot;
import h04.Tetromino;
import h04.Util;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

@StudentCreationRequired
public class S extends Swarm {

    private final boolean mirrored;

    public S(int x, int y, boolean mirrored) {
        super(x, y);
        this.mirrored = mirrored;
        //this.tetromino = mirrored ? Tetromino.Z : Tetromino.S;
        this.projectionMatrix = new ProjectionMatrix(mirrored ? Tetromino.Z : Tetromino.S);
    }

    @Override
    public String toString() {
        return mirrored ? "Z" : "S";
    }

}
