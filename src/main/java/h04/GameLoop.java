package h04;

import fopbot.*;
import h04.swarms.S;
import h04.swarms.Swarm;
import h04.swarms.TestSwarm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameLoop {

    private Swarm currentSwarm;

    public void handleInput(KeyPressEvent event) {
        switch (event.getKey()) {
            case W -> currentSwarm.move(Direction.UP);
            case A -> currentSwarm.move(Direction.LEFT);
            case S -> currentSwarm.move(Direction.DOWN);
            case D -> currentSwarm.move(Direction.RIGHT);
            case LEFT -> currentSwarm.turnLeft();
            case RIGHT -> currentSwarm.turnRight();
            default -> {}
        }
    }

    public void gameLoop() {
        World.setSize(10, 10);
        World.setDelay(100);
        World.setVisible(true);
        currentSwarm = new S(5, 5, false);
        currentSwarm.spawn();
        List<KeyPressEvent> keyPressEvents = Collections.synchronizedList(new LinkedList<>());
        InputHandler inputHandler = InputHandler.getInputHandler();
        inputHandler.addKeyPressListener(keyPressEvents::add);
        while (true) {
            if (!keyPressEvents.isEmpty()) {
                handleInput(keyPressEvents.getFirst());
                keyPressEvents.clear();
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
        Tetrobot testbot = new Tetrobot(5, 5);
        List<KeyPressEvent> keyPressEvents = Collections.synchronizedList(new LinkedList<>());
        InputHandler inputHandler = InputHandler.getInputHandler();
        inputHandler.addKeyPressListener(keyPressEvents::add);
        while (true) {
            if (!keyPressEvents.isEmpty()) {
                handleTestInput(keyPressEvents.getFirst(), testbot);
                keyPressEvents.clear();
            }
        }
    }
}
