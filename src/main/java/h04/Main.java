package h04;

import fopbot.Direction;
import fopbot.World;
import h04.swarms.S;
import h04.swarms.Swarm;

/**
 * Main entry point in executing the program.
 */
public class Main {

    private static int debugPrintCount = 0;

    static void printMatrix(ProjectionMatrix projectionMatrix, String label) {
        boolean[][] matrix = projectionMatrix.getShape();
        int snapshot = ++debugPrintCount;

        String title = (label == null || label.isBlank())
            ? "Snapshot " + snapshot
            : "Snapshot " + snapshot + " - " + label;

        System.out.println("=== " + title + " ===");
        for (boolean[] row : matrix) {
            StringBuilder line = new StringBuilder("| ");
            for (boolean cell : row) {
                line.append(cell ? "X " : ". ");
            }
            line.append("|");
            System.out.println(line);
        }
        System.out.println("pivot(x2,y2)= (" + projectionMatrix.getPivotX2() + ", " + projectionMatrix.getPivotY2() + ")");
        System.out.println();
    }

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        GameLoop gameLoop = new GameLoop();
        gameLoop.doGame();

//        World.setSize(10, 10);
//        World.setVisible(true);
//
//        Swarm s = new S(5, 5, false);
//        s.spawn();
//        s.turnRight();
//        s.move(Direction.DOWN);
//
//        TetrisWorld tetrisWorld = new TetrisWorld();
//
//        // 1) Create tetromino projection matrices.
//        ProjectionMatrix t = new ProjectionMatrix(Tetromino.T);
//        ProjectionMatrix l = new ProjectionMatrix(Tetromino.L);
//        ProjectionMatrix z = new ProjectionMatrix(Tetromino.Z);
//
//        // Optional local-space rotation before projection into world space.
//        l.rotateRight();
//        z.rotateLeft();
//
//        // 2) Add tetromino instances to the world list.
//        tetrisWorld.addTetromino(t, Tetromino.T, 4, 0);
//        tetrisWorld.addTetromino(l, Tetromino.L, 1, 4);
//        tetrisWorld.addTetromino(z, Tetromino.Z, 6, 8);
//
//        // 3) Project ALL listed tetrominoes at once.
//        boolean ok = tetrisWorld.project();
//        System.out.println("project() success: " + ok);
//        tetrisWorld.debugPrint("after project() of all listed tetrominoes");
//
//        // 4) Collision example: this one overlaps and should fail.
//        tetrisWorld.addTetromino(new ProjectionMatrix(Tetromino.O), Tetromino.O, 4, 0);
//        boolean collisionOk = tetrisWorld.project();
//        System.out.println("project() after overlap success: " + collisionOk);
//        tetrisWorld.debugPrint("after attempted overlapping O");
    }
}
