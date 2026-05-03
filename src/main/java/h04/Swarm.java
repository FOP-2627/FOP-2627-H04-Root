package h04;

import fopbot.Direction;

public abstract class Swarm {

    private Tetrobot[] tetrobots;

//    public void move() {
//        for (int i = 0; i < tetrobots.length; i++) {
//            tetrobots[i].move();
//        }
//    }

    public void move(Direction direction) {
        for (int i = 0; i < tetrobots.length; i++) {
            while (tetrobots[i].getDirection().equals(direction)) {
                tetrobots[i].turnLeft();
            }
        }
        for (int i = 0; i < tetrobots.length; i++) {
            tetrobots[i].move();
        }
    }

    public boolean isFrontClear() {
        for (int i = 0; i < tetrobots.length; i++) {
            if (!tetrobots[i].isFrontClear(tetrobots)) {
                return false;
            }
        }
        return true;
    }

    
}
