package h04;

import fopbot.FieldEntity;
import fopbot.Robot;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

@StudentCreationRequired
public class Tetrobot extends Robot {

    /**
     * Creates a new Tetrobot at the specified coordinates.
     *
     * @param x the X coordinate of the Tetrobot
     * @param y the Y coordinate of the Tetrobot
     */
    public Tetrobot(int x, int y) {
        super(x, y);
    }

    /**
     * Checks whether the given entity is contained in the array of entities.
     *
     * @param entities the array of entities to search in
     * @param entity the entity to search for
     * @return {@code true} if the entity is found, otherwise {@code false}
     */
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

    /**
        * Checks whether the front of the Tetrobot is clear while respecting the world boundaries.
        *
        * @return {@code true} if the front is clear, otherwise {@code false}
     */
    @Override
    public boolean isFrontClear() {
        final int x = this.getX() + this.getDirection().getDx();
        final int y = this.getY() + this.getDirection().getDy();
        return Utils.isWithinBounds(x, y);
    }

    /**
     * Checks whether the front of the Tetrobot is clear while ignoring the specified entities.
     *
     * @param ignoredEntities the entities to ignore
     * @return {@code true} if the front is clear, otherwise {@code false}
     */
    public boolean isFrontClear(FieldEntity[] ignoredEntities) {
        FieldEntity[] entitiesInFront = Utils.getEntitiesInFrontOf(this);
        for (int i = 0; i < entitiesInFront.length; i++) {
            if (!(containsEntity(ignoredEntities, entitiesInFront[i]))) {
                return false;
            }
        }
        return isFrontClear();
    }

    /**
     * Removes the Tetrobot from the world.
     */
    public void despawn() {
        this.turnOff();
        Utils.removeRobot(this);
    }
}
