package h04;

import fopbot.Block;
import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission
public class TetrobotTests {

    private static final String TETROBOT = "h04.Tetrobot";

    @BeforeEach
    public void setUpWorld() {
        World.reset();
        World.setSize(5, 5);
        World.setDelay(0);
        World.setVisible(false);
    }

    @Tag("H4.1.1")
    @Test
    public void testTetrobotClassDeclaration() {
        TestUtils.assertPublicClassExtends(TETROBOT, Robot.class);
    }

    @Tag("H4.1.1")
    @Test
    public void testTetrobotConstructor() {
        Object tetrobot = newTetrobot(2, 3);

        assertEquals(2, ((Robot) tetrobot).getX(), "The constructor must pass x to the Robot constructor");
        assertEquals(3, ((Robot) tetrobot).getY(), "The constructor must pass y to the Robot constructor");
    }

    @Tag("H4.1.2")
    @Test
    public void testIsFrontClearIsOverridden() {
        Class<?> clazz = TestUtils.requireClass(TETROBOT);
        Method method = TestUtils.requireDeclaredMethod(clazz, "isFrontClear");
        Method superMethod = TestUtils.requireMethod(Robot.class, "isFrontClear");

        assertTrue(Modifier.isPublic(method.getModifiers()), "isFrontClear() must be public");
        assertEquals(boolean.class, method.getReturnType(), "isFrontClear() must return boolean");
        assertEquals(0, method.getParameterCount(), "The overridden isFrontClear method must not declare parameters");
        assertArrayEquals(superMethod.getParameterTypes(), method.getParameterTypes(),
            "The overridden isFrontClear method must use the same parameter list as Robot.isFrontClear()");
        assertEquals(superMethod.getReturnType(), method.getReturnType(),
            "The overridden isFrontClear method must use the same return type as Robot.isFrontClear()");
    }

    @Tag("H4.1.2")
    @Test
    public void testIsFrontClearResult() {
        Robot tetrobot = (Robot) newTetrobot(0, 0);
        Method isFrontClear = TestUtils.requireMethod(tetrobot.getClass(), "isFrontClear");

        turnTo(tetrobot, Direction.DOWN);
        assertEquals(false, TestUtils.invoke(isFrontClear, tetrobot),
            "isFrontClear() must return false when the field in front is outside the world");

        turnTo(tetrobot, Direction.RIGHT);
        assertEquals(true, TestUtils.invoke(isFrontClear, tetrobot),
            "isFrontClear() must return true when the field in front is inside the world");

        Block block = new Block(1, 0);
        World.getGlobalWorld().placeFieldEntity(block);
        assertEquals(true, TestUtils.invoke(isFrontClear, tetrobot),
            "isFrontClear() must only check world bounds and must not reject obstacles in front");
    }

    @Tag("H4.1.3")
    @Test
    public void testIgnoredFieldEntitiesAreIgnored() {
        Robot tetrobot = (Robot) newTetrobot(1, 1);
        turnTo(tetrobot, Direction.RIGHT);
        Block block = new Block(2, 1);
        World.getGlobalWorld().placeFieldEntity(block);
        Method isFrontClear = TestUtils.requireDeclaredMethod(tetrobot.getClass(), "isFrontClear", FieldEntity[].class);

        assertEquals(true, TestUtils.invoke(isFrontClear, tetrobot, (Object) new FieldEntity[] { block }),
            "isFrontClear(FieldEntity[]) must ignore entities passed through ignoredEntities");
        assertEquals(false, TestUtils.invoke(isFrontClear, tetrobot, (Object) new FieldEntity[0]),
            "isFrontClear(FieldEntity[]) must reject entities in front that are not passed through ignoredEntities");
    }

    @Tag("H4.1.3")
    @Test
    public void testOverloadedIsFrontClearChecksBounds() {
        Robot tetrobot = (Robot) newTetrobot(0, 0);
        turnTo(tetrobot, Direction.DOWN);
        Method isFrontClear = TestUtils.requireDeclaredMethod(tetrobot.getClass(), "isFrontClear", FieldEntity[].class);

        assertEquals(false, TestUtils.invoke(isFrontClear, tetrobot, (Object) new FieldEntity[0]),
            "isFrontClear(FieldEntity[]) must return false when the field in front is outside the world");
    }

    @Tag("H4.1.3")
    @Test
    public void testOverloadedIsFrontClearResult() {
        Robot tetrobot = (Robot) newTetrobot(1, 1);
        turnTo(tetrobot, Direction.RIGHT);
        Block block = new Block(2, 1);
        World.getGlobalWorld().placeFieldEntity(block);
        Method isFrontClear = TestUtils.requireDeclaredMethod(tetrobot.getClass(), "isFrontClear", FieldEntity[].class);

        assertEquals(false, TestUtils.invoke(isFrontClear, tetrobot, (Object) new FieldEntity[0]),
            "isFrontClear(FieldEntity[]) must reject non-ignored entities in front");
        assertEquals(true, TestUtils.invoke(isFrontClear, tetrobot, (Object) new FieldEntity[] { block }),
            "isFrontClear(FieldEntity[]) must accept ignored entities in front");
    }

    @Tag("H4.1.4")
    @Test
    public void testDespawnTurnsTetrobotOff() {
        Robot tetrobot = (Robot) newTetrobot(2, 2);
        Method despawn = TestUtils.requireMethod(tetrobot.getClass(), "despawn");

        TestUtils.invoke(despawn, tetrobot);

        assertTrue(tetrobot.isTurnedOff(), "despawn() must turn off the tetrobot");
    }

    @Tag("H4.1.4")
    @Test
    public void testDespawnRemovesTetrobotFromWorld() {
        Robot tetrobot = (Robot) newTetrobot(2, 2);
        Method despawn = TestUtils.requireMethod(tetrobot.getClass(), "despawn");

        TestUtils.invoke(despawn, tetrobot);

        assertTrue(World.getGlobalWorld().getField(2, 2).getEntities().stream()
                .noneMatch(entity -> entity.getClass().getName().equals(TETROBOT)),
            "despawn() must remove the tetrobot from the world");
    }

    private static Object newTetrobot(int x, int y) {
        Class<?> clazz = TestUtils.requireClass(TETROBOT);
        Constructor<?> constructor = TestUtils.requireConstructor(clazz, int.class, int.class);
        return TestUtils.newInstance(constructor, x, y);
    }

    private static void turnTo(Robot robot, Direction direction) {
        while (robot.getDirection() != direction) {
            robot.turnLeft();
        }
    }
}
