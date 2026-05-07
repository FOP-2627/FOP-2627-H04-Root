package h04;

/* 
 * The fopbot world has bottom left origin, so does our memory representation, however this means that
 * visual in the editor the bottom row of the shape is the first row of the matrix, which is a bit counterintuitive.
 */

public enum Tetromino {

    I(new boolean[][] {
            { false, false, false, false },
            { false, false, false, false },
            { true, true, true, true },
            { false, false, false, false } }, 3, 3),
    O(new boolean[][] {
            { true, true },
            { true, true } }, 1, 1),
    T(new boolean[][] {
            { false, false, false },
            { true, true, true },
            { false, true, false } }, 2, 2),
    L(new boolean[][] {
            { false, true, true },
            { false, true, false },
            { false, true, false } }, 2, 2),
    J(new boolean[][] {
            { true, true, false },
            { false, true, false },
            { false, true, false } }, 2, 2),
    S(new boolean[][] {
            { false, false, false },
            { true, true, false },
            { false, true, true } }, 2, 2),
    Z(new boolean[][] {
            { false, false, false },
            { false, true, true },
            { true, true, false } }, 2, 2);

    public final boolean[][] shape;
    private final int pivotX2;
    private final int pivotY2;

    Tetromino(boolean[][] shape, int pivotX2, int pivotY2) {
        this.shape = shape;
        this.pivotX2 = pivotX2;
        this.pivotY2 = pivotY2;
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

    public Tetromino mirrored() {
        return this == L ? J : this == J ? L : this == S ? Z : this == Z ? S : this;
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
