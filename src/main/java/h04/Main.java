package h04;

import org.tudalgo.algoutils.student.annotation.SolutionOnly;

/**
 * Main entry point in executing the program.
 */
public class Main {

    private static int debugPrintCount = 0;

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {

        GameLoop gameLoop = new GameLoop();
        gameLoop.gameLoop();

        // World.setSize(10, 10);
        // World.setVisible(true);
        //
        // Swarm s = new S(5, 5, false);
        // s.spawn();
        // s.turnRight();
        // s.move(Direction.DOWN);
        //
        // TetrisWorld tetrisWorld = new TetrisWorld();
        //
        // // 1) Create tetromino projection matrices.
        // ProjectionMatrix t = new ProjectionMatrix(Tetromino.T);
        // ProjectionMatrix l = new ProjectionMatrix(Tetromino.L);
        // ProjectionMatrix z = new ProjectionMatrix(Tetromino.Z);
        //
        // // Optional local-space rotation before projection into world space.
        // l.rotateRight();
        // z.rotateLeft();
        //
        // // 2) Add tetromino instances to the world list.
        // tetrisWorld.addTetromino(t, Tetromino.T, 4, 0);
        // tetrisWorld.addTetromino(l, Tetromino.L, 1, 4);
        // tetrisWorld.addTetromino(z, Tetromino.Z, 6, 8);
        //
        // // 3) Project ALL listed tetrominoes at once.
        // boolean ok = tetrisWorld.project();
        // System.out.println("project() success: " + ok);
        // tetrisWorld.debugPrint("after project() of all listed tetrominoes");
        //
        // // 4) Collision example: this one overlaps and should fail.
        // tetrisWorld.addTetromino(new ProjectionMatrix(Tetromino.O), Tetromino.O, 4,
        // 0);
        // boolean collisionOk = tetrisWorld.project();
        // System.out.println("project() after overlap success: " + collisionOk);
        // tetrisWorld.debugPrint("after attempted overlapping O");
    }
}
