package h04;

import fopbot.*;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Utility methods for common operations in the Tetris game.
 */
@DoNotTouch
public class Utils {

    /**
     * Checks whether the given coordinates are within the bounds of the world.
     *
     * @param x the x-coordinate to check
     * @param y the y-coordinate to check
     * @return {@code true} if the coordinates are within bounds, otherwise
     *         {@code false}
     */
    public static boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < World.getWidth() && y >= 0 && y < World.getHeight();
    }

    /**
     * Returns the entities located in front of the given robot.
     *
     * @param robot the robot to check in front of
     * @return an array of entities located in front of the robot, or an empty array
     *         if the position is out of bounds
     */
    public static FieldEntity[] getEntitiesInFrontOf(Robot robot) {
        // try {
        // Class<?> robotClass = robot.getClass();
        // Field world = robotClass.getDeclaredField("world");
        // world.setAccessible(true);
        // KarelWorld karelWorld = (KarelWorld) world.get(robot);
        // world.setAccessible(false);
        //
        // Direction direction = robot.getDirection();
        // int x = robot.getX() + direction.getDx();
        // int y = robot.getY() + direction.getDy();
        // fopbot.Field field = karelWorld.getField(x, y);
        // return field.getEntities().toArray(new FieldEntity[0]);
        // } catch (NoSuchFieldException | IllegalAccessException e) {
        // throw new RuntimeException(e);
        // }
        KarelWorld world = World.getGlobalWorld();
        Direction direction = robot.getDirection();
        int x = robot.getX() + direction.getDx();
        int y = robot.getY() + direction.getDy();
        fopbot.Field field = isWithinBounds(x, y) ? world.getField(x, y) : null;
        return field != null ? field.getEntities().toArray(new FieldEntity[0]) : new FieldEntity[0];
    }

    /**
     * Returns the entities located at the specified coordinates.
     *
     * @param x the x-coordinate to check
     * @param y the y-coordinate to check
     * @return an array of entities located at the specified coordinates
     */
    public static FieldEntity[] getEntitiesAt(int x, int y) {
        KarelWorld world = World.getGlobalWorld();
        fopbot.Field field = world.getField(x, y);
        return field.getEntities().toArray(new FieldEntity[0]);
    }

    /**
     * Removes the specified entity from the world.
     *
     * @param entity     the entity to remove
     * @param karelWorld the world from which to remove the entity
     */
    public static void removeEntity(FieldEntity entity, KarelWorld karelWorld) {
        fopbot.Field field = karelWorld.getField(entity.getX(), entity.getY());
        List<FieldEntity> entities = field.getEntities();
        entities.remove(entity);

        try {
            Class<?> worldClass = karelWorld.getClass();
            Method updateMethod = worldClass.getDeclaredMethod("triggerUpdate");
            updateMethod.setAccessible(true);
            updateMethod.invoke(karelWorld);
            updateMethod.setAccessible(false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes the specified robot from the world.
     *
     * @param robot the robot to remove
     */
    public static void removeRobot(Robot robot) {
        removeEntity(robot, World.getGlobalWorld());
    }

    /**
     * Pauses the current thread for the specified number of milliseconds.
     *
     * @param millis the number of milliseconds to sleep
     */
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
