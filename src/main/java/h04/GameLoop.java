package h04;

import fopbot.*;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class GameLoop {

    public void handleInput(KeyPressEvent event, Robot robot) {
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

    public void doGame() {
        World.setSize(10, 10);
        World.setDelay(100);
        World.setVisible(true);
        Tetrobot testbot = new Tetrobot(5, 5);
        List<KeyPressEvent> keyPressEvents = Collections.synchronizedList(new LinkedList<>());
        InputHandler inputHandler = InputHandler.getInputHandler();
        inputHandler.addKeyPressListener(keyPressEvents::add);
        while (true) {
            if (!keyPressEvents.isEmpty()) {
                handleInput(keyPressEvents.getFirst(), testbot);
                keyPressEvents.clear();
            }
        }
    }
}
