package h04;

import fopbot.FieldEntity;
import fopbot.KarelWorld;
import fopbot.RobotFamily;
import fopbot.World;
import h04.swarms.Swarm;

/**
 * Logical Tetris board with standard dimensions (10x20).
 *
 * The board stores fixed tetromino cells and keeps at most one active swarm.
 */
public class TetrisWorld {

	public static final int WIDTH = 10;
	public static final int HEIGHT = 20;

	private final boolean[][] cells;
	private Swarm activeSwarm;

	public TetrisWorld() {
		this.cells = new boolean[HEIGHT][WIDTH];
	}

	public void clear() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				cells[y][x] = false;
			}
		}
	}

	public void captureWorldSnapshot() {
		KarelWorld world = World.getGlobalWorld();
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				fopbot.Field field = world.getField(x, y);
				cells[y][x] = false;
				for (FieldEntity entity : field.getEntities()) {
					if (entity instanceof Tetrobot) {
						cells[y][x] = true;
						break;
					}
				}
			}
		}
	}

	public void setActiveSwarm(Swarm swarm) {
		this.activeSwarm = swarm;
	}

	public Swarm getActiveSwarm() {
		return activeSwarm;
	}

	public void clearActiveSwarm() {
		this.activeSwarm = null;
	}

	public boolean canProject(ProjectionMatrix projectionMatrix, int worldX, int worldY) {
		int[][] points = projectionMatrix.projectToWorld(worldX, worldY);
		for (int[] point : points) {
			int x = point[0];
			int y = point[1];

			if (!isInside(x, y)) {
				return false;
			}
			if (cells[y][x]) {
				return false;
			}
		}
		return true;
	}

	public boolean canProject(Swarm swarm) {
		return swarm != null && canProject(swarm.getProjectionMatrix(), swarm.getX(), swarm.getY());
	}

	public boolean project() {
		return fixActiveSwarm();
	}

	public boolean project(Swarm swarm) {
		setActiveSwarm(swarm);
		return fixActiveSwarm();
	}

	public boolean fixActiveSwarm() {
		if (activeSwarm == null || !canProject(activeSwarm)) {
			return false;
		}

		int[][] points = activeSwarm.getProjectionMatrix().projectToWorld(activeSwarm.getX(), activeSwarm.getY());
		for (int[] point : points) {
			cells[point[1]][point[0]] = true;
		}
		clearActiveSwarm();
		return true;
	}

	public boolean getCell(int x, int y) {
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
				boolean value = cells[y][x];
				line.append(value ? "X " : ". ");
			}
			line.append("|");
			System.out.println(line);
		}
		System.out.println();
	}

	private static boolean isInside(int x, int y) {
		return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
	}
}
