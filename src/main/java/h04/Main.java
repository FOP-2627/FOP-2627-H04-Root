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

        World.setSize(10, 20);
        World.setVisible(true);
        World.setDelay(0);

        Swarm s = new S(5, 5, false);
        s.spawn();
        Util.sleep(1000);
        s.turnRight();
        Util.sleep(1000);
        s.move(Direction.DOWN);
        Util.sleep(1000);

        TetrisWorld tetrisWorld = new TetrisWorld();


    }
}
