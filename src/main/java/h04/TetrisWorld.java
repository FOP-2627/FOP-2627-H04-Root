package h04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Logical Tetris board with standard dimensions (10x20).
 *
 * The board stores fixed tetromino cells and supports projecting a
 * {@link ProjectionMatrix} into world space.
 */
public class TetrisWorld {

	public static final class PlacedTetromino {
		private final ProjectionMatrix projectionMatrix;
		private final Tetromino tetromino;
		private final int worldX;
		private final int worldY;

		public PlacedTetromino(ProjectionMatrix projectionMatrix, Tetromino tetromino, int worldX, int worldY) {
			this.projectionMatrix = projectionMatrix;
			this.tetromino = tetromino;
			this.worldX = worldX;
			this.worldY = worldY;
		}

		public ProjectionMatrix getProjectionMatrix() {
			return projectionMatrix;
		}

		public Tetromino getTetromino() {
			return tetromino;
		}

		public int getWorldX() {
			return worldX;
		}

		public int getWorldY() {
			return worldY;
		}
	}

	public static final int WIDTH = 10;
	public static final int HEIGHT = 20;

	private final Tetromino[][] cells;
	private final List<PlacedTetromino> tetrominoes;

	public TetrisWorld() {
		this.cells = new Tetromino[HEIGHT][WIDTH];
		this.tetrominoes = new ArrayList<>();
	}

	public void clear() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				cells[y][x] = null;
			}
		}
	}

	public boolean canProject(ProjectionMatrix projectionMatrix, int worldX, int worldY) {
		int[][] points = projectionMatrix.projectToWorld(worldX, worldY);
		for (int[] point : points) {
			int x = point[0];
			int y = point[1];

			if (!isInside(x, y)) {
				return false;
			}
			if (cells[y][x] != null) {
				return false;
			}
		}
		return true;
	}

	public void addTetromino(ProjectionMatrix projectionMatrix, Tetromino tetromino, int worldX, int worldY) {
		tetrominoes.add(new PlacedTetromino(projectionMatrix, tetromino, worldX, worldY));
	}

	public List<PlacedTetromino> getTetrominoes() {
		return Collections.unmodifiableList(tetrominoes);
	}

	public void clearTetrominoes() {
		tetrominoes.clear();
		clear();
	}

	/**
	 * Projects all tetrominoes currently registered in this world.
	 *
	 * @return true if all tetrominoes could be projected without overlap and inside bounds
	 */
	public boolean project() {
		Tetromino[][] projected = new Tetromino[HEIGHT][WIDTH];

		for (PlacedTetromino placedTetromino : tetrominoes) {
			int[][] points = placedTetromino.getProjectionMatrix().projectToWorld(
				placedTetromino.getWorldX(),
				placedTetromino.getWorldY()
			);

			for (int[] point : points) {
				int x = point[0];
				int y = point[1];

				if (!isInside(x, y)) {
					return false;
				}
				if (projected[y][x] != null) {
					return false;
				}

				projected[y][x] = placedTetromino.getTetromino();
			}
		}

		for (int y = 0; y < HEIGHT; y++) {
			System.arraycopy(projected[y], 0, cells[y], 0, WIDTH);
		}

		return true;
	}

	public boolean project(ProjectionMatrix projectionMatrix, Tetromino tetromino, int worldX, int worldY) {
		addTetromino(projectionMatrix, tetromino, worldX, worldY);
		if (project()) {
			return true;
		}
		tetrominoes.remove(tetrominoes.size() - 1);
		project();
		return false;
	}

	public Tetromino getCell(int x, int y) {
		if (!isInside(x, y)) {
			throw new IllegalArgumentException("Coordinates out of bounds: (" + x + ", " + y + ")");
		}
		return cells[y][x];
	}

	public void debugPrint(String label) {
		String title = (label == null || label.isBlank())
			? "TetrisWorld"
			: "TetrisWorld - " + label;

		System.out.println("=== " + title + " ===");
		for (int y = 0; y < HEIGHT; y++) {
			StringBuilder line = new StringBuilder("| ");
			for (int x = 0; x < WIDTH; x++) {
				Tetromino value = cells[y][x];
				line.append(value == null ? ". " : abbreviationOf(value) + " ");
			}
			line.append("|");
			System.out.println(line);
		}
		System.out.println();
	}

	private static boolean isInside(int x, int y) {
		return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
	}

	private static char abbreviationOf(Tetromino tetromino) {
		return tetromino.name().charAt(0);
	}
}
