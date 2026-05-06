package h04;

import fopbot.FieldEntity;
import fopbot.Robot;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

@StudentCreationRequired
public class Tetrobot extends Robot {

//    //Alternative 1: ignored entities are kept as an attribute
//    FieldEntity[] ignoredEntities;

    public Tetrobot(int x, int y) {
        super(x, y);
    }

//    public Tetrobot(int x, int y, FieldEntity[] ignoredEntities) {
//        super(x, y);
//        this.ignoredEntities = ignoredEntities;
//    }

//    //Alternative 1: ignored entities are kept as an attribute
//    public void ignoreEntity(FieldEntity entity) {
//        int ignoredAmount = this.ignoredEntities.length;
//        FieldEntity[] ignoredEntities = new FieldEntity[ignoredAmount + 1];
//        for (int i = 0; i < ignoredAmount; i++) {
//            ignoredEntities[i] = this.ignoredEntities[i];
//        }
//        ignoredEntities[ignoredAmount + 1] = entity;
//        this.ignoredEntities = ignoredEntities;
//    }

//    //Alternative 1: ignored entities are kept as an attribute
//    public void unignoreEntity(FieldEntity entity) {
//        throw new UnsupportedOperationException();
//    }

    private boolean containsEntity(FieldEntity[] entities, FieldEntity entity) {
        if (entities == null) {
            return false;
        }
        for (int i = 0; i < entities.length; i++) {
            if (entities[i].equals(entity)) {
                return true;
            }
        }
        return false;
    }

//    //Alternative 1: ignored entities are kept as an attribute
//    public boolean isFrontClear() {
//        FieldEntity[] entitiesInFront = Util.getEntitiesInFrontOf(this);
//        for (int i = 0; i < entitiesInFront.length; i++) {
//            if (!(containsEntity(this.ignoredEntities, entitiesInFront[i]))) {
//                return false;
//            }
//        }
//        return true;
//    }

    @Override
    public boolean isFrontClear() {
        final int x = this.getX() + this.getDirection().getDx();
        final int y = this.getY() + this.getDirection().getDy();
        return Util.isWithinBounds(x, y);
    }

    //Alternative 2: ignored entities are passed as an argument
    public boolean isFrontClear(FieldEntity[] ignoredEntities) {
        FieldEntity[] entitiesInFront = Util.getEntitiesInFrontOf(this);
        for (int i = 0; i < entitiesInFront.length; i++) {
            if (!(containsEntity(ignoredEntities, entitiesInFront[i]))) {
                return false;
            }
        }
        return isFrontClear();
    }

    public void despawn() {
        this.turnOff();
        Util.removeRobot(this);
    }
}
