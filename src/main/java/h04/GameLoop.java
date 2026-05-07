package h04;

import fopbot.*;
import h04.swarms.*;

import java.sql.JDBCType;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameLoop {

    private TetrisWorld tetrisWorld;

    protected void moveDownChecked() {
        if (tetrisWorld.getActiveSwarm().canMove(Direction.DOWN)) {
            tetrisWorld.getActiveSwarm().move(Direction.DOWN);
        } else {
            tetrisWorld.drop();
        }
    }

    protected void moveLeftRightChecked(Direction direction) {
        if (tetrisWorld.getActiveSwarm().canMove(direction)) {
            tetrisWorld.getActiveSwarm().move(direction);
        }
    }

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

    public void gameLoop() {
        tetrisWorld = new TetrisWorld();
        World.setSize(10, 20);
        World.setDelay(0);
        World.setVisible(true);

        List<KeyPressEvent> keyPressEvents = Collections.synchronizedList(new LinkedList<>());
        InputHandler inputHandler = InputHandler.getInputHandler();
        inputHandler.addKeyPressListener(keyPressEvents::add);

        // time/clock object to track time since last automatic drop, to implement gravity
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

                // check that we can spawn the new swarm, if not, the game is over and we can just exit the loop
                if (!tetrisWorld.canProject(tetrisWorld.getActiveSwarm())) {
                    System.out.println("Game Over!");
                    break;
                }

                tetrisWorld.getActiveSwarm().spawn();
            }

            if (!keyPressEvents.isEmpty()) {
                var gravityProgression = handleInput(keyPressEvents.getFirst());
                keyPressEvents.clear();
                
                // reset the drop timer if the player has made a move that should reset gravity progression (e.g. moving down manually, or dropping)
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
                tetrisWorld.debugPrint2();
                tetrisWorld.clearRows();
                action = false;
            }
        }
    }

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

    public void inputTest() {
        World.setSize(10, 10);
        World.setDelay(100);
        World.setVisible(true);
        Tetrobot testBot = new Tetrobot(5, 5);
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
