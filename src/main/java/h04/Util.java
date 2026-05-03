package h04;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.KarelWorld;
import fopbot.Robot;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Util {

    public static FieldEntity[] getEntitiesInFrontOf(Robot robot) {
        try {
            Class<?> robotClass = robot.getClass();
            Field world = robotClass.getDeclaredField("world");
            world.setAccessible(true);
            KarelWorld karelWorld = (KarelWorld) world.get(robot);
            world.setAccessible(false);

            Direction direction = robot.getDirection();
            int x = robot.getX() + direction.getDx();
            int y = robot.getY() + direction.getDy();
            fopbot.Field field = karelWorld.getField(x, y);
            return field.getEntities().toArray(new FieldEntity[0]);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

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

    public static void removeRobot(Robot robot) {
        try {
            Class<?> robotClass = robot.getClass();
            Field world = robotClass.getDeclaredField("world");
            world.setAccessible(true);
            KarelWorld karelWorld = (KarelWorld) world.get(robot);
            world.setAccessible(false);

            removeEntity(robot, karelWorld);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
