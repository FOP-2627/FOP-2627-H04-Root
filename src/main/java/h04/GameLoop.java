package h04;

import fopbot.*;
import h04.swarms.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Main controller for the Tetris game.
 * Handles user input, gravity, swarm spawning, and row clearing.
 */
public class GameLoop {

    /**
     * The world in which the Tetris game is played.
     */
    @DoNotTouch
    private TetrisWorld tetrisWorld;

    /**
     * Moves the active swarm down if possible, or drops it into place otherwise.
     */
    protected void moveDownChecked() {
        if (tetrisWorld.getActiveSwarm().canMove(Direction.DOWN)) {
            tetrisWorld.getActiveSwarm().move(Direction.DOWN);
        } else {
            tetrisWorld.drop();
        }
    }

    /**
     * Moves the active swarm left or right if the move is possible.
     *
     * @param direction the direction to move, must be {@link Direction#LEFT} or {@link Direction#RIGHT}
     */
    protected void moveLeftRightChecked(Direction direction) {
        if (tetrisWorld.getActiveSwarm().canMove(direction)) {
            tetrisWorld.getActiveSwarm().move(direction);
        }
    }

    /**
     * Rotates the active swarm if the rotated projection can be placed.
     *
     * @param clockwise {@code true} to rotate clockwise, {@code false} to rotate counterclockwise
     */
    protected void rotateChecked(boolean clockwise) {
        var projectionMatrix = tetrisWorld.getActiveSwarm().getProjectionMatrix().clone();

        if (clockwise) {
            projectionMatrix.rotateRight();
        } else {
            projectionMatrix.rotateLeft();
        }

        if (tetrisWorld.canProject(projectionMatrix, tetrisWorld.getActiveSwarm().getX(), tetrisWorld.getActiveSwarm().getY())) {
            tetrisWorld.getActiveSwarm().setProjectionMatrix(projectionMatrix);
        }

        tetrisWorld.getActiveSwarm().commitSRSRotation();
    }

    /**
     * Handles input for controlling the active swarm.
     *
     * @param event the key press event to handle
     * @return {@code true} if the input should reset gravity progression, otherwise {@code false}
     */
    public boolean handleInput(KeyPressEvent event) {
        switch (event.getKey()) {
            case A -> moveLeftRightChecked(Direction.LEFT);
            case S -> { moveDownChecked(); return true; }
            case D -> moveLeftRightChecked(Direction.RIGHT);
            case LEFT -> rotateChecked(false);
            case RIGHT -> rotateChecked(true);
            case SPACE -> {tetrisWorld.drop(); return true;}
            default -> {}
        }
        return false;
    }

    /**
     * Runs the main game loop.
     * The loop creates the playing field, spawns new swarms, processes input,
     * applies gravity, and clears rows until no new swarm can be spawned.
     */
    public void gameLoop() {
        tetrisWorld = new TetrisWorld();
        World.setSize(TetrisWorld.WIDTH, TetrisWorld.HEIGHT);
        World.setDelay(0);
        World.setVisible(true);

        List<KeyPressEvent> keyPressEvents = Collections.synchronizedList(new LinkedList<>());
        InputHandler inputHandler = InputHandler.getInputHandler();
        inputHandler.addKeyPressListener(keyPressEvents::add);

        // Track the time since the last automatic drop to implement gravity.
        var lastDropTime = System.currentTimeMillis();

        boolean action = false;
        while (true) {
            if (tetrisWorld.getActiveSwarm() == null) {
                var randomTetromino = new Random().nextInt(7);
                Swarm newSwarm = switch (randomTetromino) {
                    case 0 -> new I(3, 17);
                    case 1 -> new O(4, 18);
                    case 2 -> new T(3, 17);
                    case 3 -> new L(3, 17, false);
                    case 4 -> new L(3, 17, true);
                    case 5 -> new S(3, 17, false);
                    case 6 -> new S(3, 17, true);
                    default -> throw new IllegalStateException("Unexpected value: " + randomTetromino);
                };
                tetrisWorld.setActiveSwarm(newSwarm);

                // Stop when the next swarm cannot be placed.
                if (!tetrisWorld.canProject(tetrisWorld.getActiveSwarm())) {
                    System.out.println("Game Over!");
                    break;
                }

                tetrisWorld.getActiveSwarm().spawn();
            }

            if (!keyPressEvents.isEmpty()) {
                var gravityProgression = handleInput(keyPressEvents.getFirst());
                keyPressEvents.clear();
                
                // Reset the drop timer after player actions that should delay gravity.
                if (gravityProgression) {
                    lastDropTime = System.currentTimeMillis();
                }

                action = true;
            }

            var newTime = System.currentTimeMillis();
            if (newTime - lastDropTime >= 1000) {
                moveDownChecked();
                lastDropTime = newTime;
                action = true;
            }

            if (action) {
                // tetrisWorld.debugPrint2();
                tetrisWorld.clearRows();
                action = false;
            }
        }
    }

    /**
     * Handles test input for controlling a robot.
     *
     * @param event the key press event to handle
     * @param robot the robot to control
     */
    public void handleTestInput(KeyPressEvent event, Robot robot) {
        Direction direction = switch (event.getKey()) {
            case UP -> Direction.UP;
            case DOWN -> Direction.DOWN;
            case LEFT -> Direction.LEFT;
            case RIGHT -> Direction.RIGHT;
            default -> robot.getDirection();
        };
        while (robot.getDirection() != direction) {
            robot.turnLeft();
        }
        robot.move();
    }

    /**
     * Opens a simple input test for moving a single {@link Tetrobot} with the arrow keys.
     */
    public void inputTest() {
        World.setSize(TetrisWorld.WIDTH, TetrisWorld.HEIGHT);
        World.setDelay(100);
        World.setVisible(true);
        Tetrobot testBot = new Tetrobot(TetrisWorld.WIDTH / 2, TetrisWorld.HEIGHT / 2);
        List<KeyPressEvent> keyPressEvents = Collections.synchronizedList(new LinkedList<>());
        InputHandler inputHandler = InputHandler.getInputHandler();
        inputHandler.addKeyPressListener(keyPressEvents::add);
        while (true) {
            if (!keyPressEvents.isEmpty()) {
                handleTestInput(keyPressEvents.getFirst(), testBot);
                keyPressEvents.clear();
            }
        }
    }
}
