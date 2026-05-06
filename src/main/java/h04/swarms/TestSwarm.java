package h04.swarms;

import fopbot.Direction;
import fopbot.FieldEntity;
import h04.Tetrobot;
import org.junit.jupiter.api.Test;

public class TestSwarm extends Swarm{
    public TestSwarm (int x, int y) {
        super(x, y);
        this.tetrobots = new Tetrobot[]{new Tetrobot(x, y)};
    }

    @Override
    public void move(Direction direction) {
        while (tetrobots[0].getDirection() != direction) {
            tetrobots[0].turnLeft();
        }
        tetrobots[0].move();
        x += direction.getDx();
        y += direction.getDy();
    }

    @Override
    public void turnLeft() {
        tetrobots[0].turnLeft();
    }

    @Override
    public void turnRight() {
        tetrobots[0].turnLeft();
        tetrobots[0].turnLeft();
        tetrobots[0].turnLeft();
    }

    @Override
    public boolean isFrontClear() {
        return tetrobots[0].isFrontClear(new FieldEntity[0]);
    }

    @Override
    public void spawn() {

    }
}
