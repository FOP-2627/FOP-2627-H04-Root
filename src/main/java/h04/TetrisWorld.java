package h04;

import fopbot.Direction;
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

	private final TetrisWorldState[][] cells;
	private Swarm activeSwarm;

	public TetrisWorld() {
		this.cells = new TetrisWorldState[HEIGHT][WIDTH];
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				cells[y][x] = TetrisWorldState.UNDEFINED;
			}
		}
	}

	public void clear() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				cells[y][x] = TetrisWorldState.UNDEFINED;
			}
		}
	}

	// public void captureWorldSnapshot() {
	// 	KarelWorld world = World.getGlobalWorld();
	// 	for (int y = 0; y < HEIGHT; y++) {
	// 		for (int x = 0; x < WIDTH; x++) {
	// 			fopbot.Field field = world.getField(x, y);
	// 			cells[y][x] = TetrisWorldState.UNDEFINED;
	// 			for (FieldEntity entity : field.getEntities()) {
	// 				if (entity instanceof Tetrobot) {
	// 					cells[y][x] = TetrisWorldState.FIXED_BOT;
	// 					break;
	// 				}
	// 			}
	// 		}
	// 	}
	// }

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
			if (cells[y][x] == TetrisWorldState.FIXED_BOT) {
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
			cells[point[1]][point[0]] = TetrisWorldState.FIXED_BOT;
		}
		clearActiveSwarm();
		return true;
	}

	public boolean getCell(int x, int y) {
		if (!isInside(x, y)) {
			throw new IllegalArgumentException("Coordinates out of bounds: (" + x + ", " + y + ")");
		}
		return cells[y][x] == TetrisWorldState.FIXED_BOT;
	}

	public void debugPrint(String label) {
		String title = (label == null || label.isBlank())
				? "TetrisWorld"
				: "TetrisWorld - " + label;

		System.out.println("=== " + title + " ===");
		for (int y = 0; y < HEIGHT; y++) {
			StringBuilder line = new StringBuilder("| ");
			for (int x = 0; x < WIDTH; x++) {
				TetrisWorldState value = cells[y][x];
				line.append(value == TetrisWorldState.FIXED_BOT ? "X " : ". ");
			}
			line.append("|");
			System.out.println(line);
		}
		System.out.println();
	}

	private static boolean isInside(int x, int y) {
		return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
	}

	public void drop() {
		while (activeSwarm != null && activeSwarm.canMove(Direction.DOWN)) {
			activeSwarm.move(Direction.DOWN);
		}
		fixActiveSwarm();
		clearActiveSwarm();
	}

	protected void copyDownEntitiesAtRowAndAbove(int y) {
		for (int copyY = y; copyY >= 0; copyY--) {
			for (int x = 0; x < WIDTH; x++) {
				cells[copyY + 1][x] = cells[copyY][x];
				cells[copyY][x] = TetrisWorldState.UNDEFINED;
			}
			for (int x = 0; x < WIDTH; x++) {
				for (FieldEntity entity : Util.getEntitiesAt(x, copyY)) {
					if (entity instanceof Tetrobot) {
						((Tetrobot) entity).setY(copyY + 1);
					}
				}
			}
		}
	}

	public void clearRows() {
		for (int y = HEIGHT - 1; y >= 0; y--) {
			boolean fullRow = true;
			for (int x = 0; x < WIDTH; x++) {
				if (cells[y][x] != TetrisWorldState.FIXED_BOT) {
					fullRow = false;
					break;
				}
			}
			if (fullRow) {
				for (int x = 0; x < WIDTH; x++) {
					cells[y][x] = TetrisWorldState.UNDEFINED;
					for (FieldEntity entity : Util.getEntitiesAt(x, y)) {
						if (entity instanceof Tetrobot) {
							Util.removeEntity(entity, World.getGlobalWorld());
						}
					}
				}

				copyDownEntitiesAtRowAndAbove(y - 1);
				y++; // Recheck the same row after shifting
			}
		}
	}

}
