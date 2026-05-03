package h04;

import java.util.Map;

/**
 * Immutable tetromino template data including local shape and pivot position.
 *
 * Pivot coordinates are stored as doubled values (x2/y2) to support half-cell
 * pivot points such as 1.5 without using floating point arithmetic.
 */
public final class TemplateData {

//    public enum Tetromino {
//        I,
//        O,
//        T,
//        L,
//        J,
//        S,
//        Z
//    }
//
//    private static final boolean[][] I = {
//        {false, false, false, false},
//        {true, true, true, true},
//        {false, false, false, false},
//        {false, false, false, false}
//    };
//
//    private static final boolean[][] O = {
//        {false, true, true, false},
//        {false, true, true, false},
//        {false, false, false, false},
//        {false, false, false, false}
//    };
//
//    private static final boolean[][] T = {
//        {false, true, false},
//        {true, true, true},
//        {false, false, false}
//    };
//
//    private static final boolean[][] L = {
//        {false, true, false},
//        {false, true, false},
//        {false, true, true}
//    };
//
//    private static final boolean[][] S = {
//        {false, true, true},
//        {true, true, false},
//        {false, false, false}
//    };

    private static final TemplateData I_DATA = new TemplateData(Tetromino.I.shape, 3, 3);
    private static final TemplateData O_DATA = new TemplateData(Tetromino.O.shape, 3, 1);
    private static final TemplateData T_DATA = new TemplateData(Tetromino.T.shape, 2, 2);
    private static final TemplateData L_DATA = new TemplateData(Tetromino.L.shape, 2, 2);
    private static final TemplateData S_DATA = new TemplateData(Tetromino.S.shape, 2, 2);

    private static final Map<Tetromino, TemplateData> BASE_TEMPLATES = Map.of(
        Tetromino.I, I_DATA,
        Tetromino.O, O_DATA,
        Tetromino.T, T_DATA,
        Tetromino.L, L_DATA,
        Tetromino.J, L_DATA,
        Tetromino.S, S_DATA,
        Tetromino.Z, S_DATA
    );

    private final boolean[][] shape;
    private final int pivotX2;
    private final int pivotY2;

    public TemplateData(boolean[][] shape, int pivotX2, int pivotY2) {
        validateMatrix(shape);
        this.shape = deepCopy(shape);
        this.pivotX2 = pivotX2;
        this.pivotY2 = pivotY2;
    }

    public static TemplateData of(Tetromino tetromino) {
        TemplateData baseData = BASE_TEMPLATES.get(tetromino);
        return (tetromino == Tetromino.J || tetromino == Tetromino.Z)
            ? baseData.mirrored()
            : baseData;
    }

    public boolean[][] getShape() {
        return deepCopy(shape);
    }

    public int getPivotX2() {
        return pivotX2;
    }

    public int getPivotY2() {
        return pivotY2;
    }

    public TemplateData mirrored() {
        boolean[][] mirroredShape = deepCopy(shape);
        for (int y = 0; y < mirroredShape.length; y++) {
            for (int left = 0, right = mirroredShape[y].length - 1; left < right; left++, right--) {
                boolean tmp = mirroredShape[y][left];
                mirroredShape[y][left] = mirroredShape[y][right];
                mirroredShape[y][right] = tmp;
            }
        }
        int mirroredPivotX2 = 2 * (mirroredShape[0].length - 1) - pivotX2;
        return new TemplateData(mirroredShape, mirroredPivotX2, pivotY2);
    }

    static boolean[][] deepCopy(boolean[][] matrix) {
        boolean[][] copy = new boolean[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = matrix[i].clone();
        }
        return copy;
    }

    static void validateMatrix(boolean[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            throw new IllegalArgumentException("Shape matrix must not be empty");
        }

        int width = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i] == null || matrix[i].length != width) {
                throw new IllegalArgumentException("Shape matrix must be rectangular");
            }
        }
    }
}
