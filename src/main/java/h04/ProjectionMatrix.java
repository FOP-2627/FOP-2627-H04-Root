package h04;

/**
 * Matrix-based projection for tetrominoes using SRS-style pivot rotation.
 */
public class ProjectionMatrix implements Cloneable {

    private boolean[][] shape;
    private int pivotX2;
    private int pivotY2;

    public ProjectionMatrix(Tetromino tetromino) {
        this.shape = tetromino.shape;
        this.pivotX2 = tetromino.getPivotX2();
        this.pivotY2 = tetromino.getPivotY2();
    }

//    public ProjectionMatrix(TemplateData templateData) {
//        applyTemplateData(templateData);
//    }

    public ProjectionMatrix(boolean[][] shape) {
        this(shape, false);
    }

    public ProjectionMatrix(boolean[][] shape, boolean mirror) {
        Tetromino.validateMatrix(shape);
        this.shape = Tetromino.deepCopy(shape);
        this.pivotX2 = shape[0].length - 1;
        this.pivotY2 = shape.length - 1;
        if (mirror) {
            mirrorHorizontally();
        }
    }

    public void rotateRight() {
        shape = rotateAroundPivot(true);
    }

    public void rotateLeft() {
        shape = rotateAroundPivot(false);
    }

    public void mirrorHorizontally() {
        for (int y = 0; y < shape.length; y++) {
            for (int left = 0, right = shape[y].length - 1; left < right; left++, right--) {
                boolean tmp = shape[y][left];
                shape[y][left] = shape[y][right];
                shape[y][right] = tmp;
            }
        }
        pivotX2 = 2 * (shape[0].length - 1) - pivotX2;
    }

    public int[][] projectToWorld(int worldX, int worldY) {
        int count = 0;
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x]) {
                    count++;
                }
            }
        }

        int[][] projected = new int[count][2];
        int index = 0;
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x]) {
                    projected[index][0] = worldX + x;
                    projected[index][1] = worldY + y;
                    index++;
                }
            }
        }
        return projected;
    }

    public boolean[][] getShape() {
        return Tetromino.deepCopy(shape);
    }

    public int getPivotX2() {
        return pivotX2;
    }

    public int getPivotY2() {
        return pivotY2;
    }

//    private void applyTemplateData(TemplateData data) {
//        this.shape = data.getShape();
//        this.pivotX2 = data.getPivotX2();
//        this.pivotY2 = data.getPivotY2();
//    }

    private boolean[][] rotateAroundPivot(boolean clockwise) {
        boolean[][] rotated = new boolean[shape.length][shape[0].length];

        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (!shape[y][x]) {
                    continue;
                }

                int dx2 = 2 * x - pivotX2;
                int dy2 = 2 * y - pivotY2;

                int ndx2;
                int ndy2;
                if (!clockwise) {
                    ndx2 = -dy2;
                    ndy2 = dx2;
                } else {
                    ndx2 = dy2;
                    ndy2 = -dx2;
                }

                int rx2 = pivotX2 + ndx2;
                int ry2 = pivotY2 + ndy2;

                if ((rx2 & 1) != 0 || (ry2 & 1) != 0) {
                    throw new IllegalStateException("Invalid pivot/rotation state (half-cell mismatch)");
                }

                int rx = rx2 / 2;
                int ry = ry2 / 2;

                if (ry < 0 || ry >= rotated.length || rx < 0 || rx >= rotated[0].length) {
                    throw new IllegalStateException("Rotation moved block outside local template bounds");
                }

                rotated[ry][rx] = true;
            }
        }

        return rotated;
    }

    @Override
    public ProjectionMatrix clone() {
        try {
            ProjectionMatrix copy = (ProjectionMatrix) super.clone();
            copy.shape = Tetromino.deepCopy(this.shape);
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
