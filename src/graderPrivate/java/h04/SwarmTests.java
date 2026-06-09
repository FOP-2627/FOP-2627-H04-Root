package h04;

import fopbot.RobotFamily;
import fopbot.World;
import h04.swarms.Swarm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission
public class SwarmTests {

    @BeforeEach
    public void setUpWorld() {
        World.reset();
        World.setSize(10, 20);
        World.setDelay(0);
        World.setVisible(false);
    }

    @Tag("H4.2.1")
    @Test
    public void testLClassDeclaration() {
        assertSwarmClassDeclaration("h04.swarms.L");
    }

    @Tag("H4.2.1")
    @Test
    public void testLConstructor() {
        assertSwarmConstructor("h04.swarms.L", Tetromino.L, Tetromino.J);
    }

    @Tag("H4.2.1")
    @Test
    public void testLSpawnCallsSuperSpawn() {
        TestUtils.assertCallsSuperSpawn(TestUtils.requireClass("h04.swarms.L"));
    }

    @Tag("H4.2.1")
    @Test
    public void testLSpawn() {
        assertSpawnFamilies("h04.swarms.L", RobotFamily.SQUARE_ORANGE, RobotFamily.SQUARE_BLUE);
    }

    @Tag("H4.2.1")
    @Test
    public void testLToString() {
        assertToString("h04.swarms.L", "L", "J");
    }

    @Tag("H4.2.2")
    @Test
    public void testSClassDeclaration() {
        assertSwarmClassDeclaration("h04.swarms.S");
    }

    @Tag("H4.2.2")
    @Test
    public void testSConstructor() {
        assertSwarmConstructor("h04.swarms.S", Tetromino.S, Tetromino.Z);
    }

    @Tag("H4.2.2")
    @Test
    public void testSSpawnCallsSuperSpawn() {
        TestUtils.assertCallsSuperSpawn(TestUtils.requireClass("h04.swarms.S"));
    }

    @Tag("H4.2.2")
    @Test
    public void testSSpawn() {
        assertSpawnFamilies("h04.swarms.S", RobotFamily.SQUARE_GREEN, RobotFamily.SQUARE_RED);
    }

    @Tag("H4.2.2")
    @Test
    public void testSToString() {
        assertToString("h04.swarms.S", "S", "Z");
    }

    private static void assertSwarmClassDeclaration(String className) {
        Class<?> clazz = TestUtils.requireClass(className);
        assertTrue(Modifier.isPublic(clazz.getModifiers()), className + " must be public");
        assertEquals(Swarm.class, clazz.getSuperclass(), className + " must directly extend Swarm");
        try {
            Field mirrored = clazz.getDeclaredField("mirrored");
            assertEquals(boolean.class, mirrored.getType(), "mirrored must have type boolean");
            assertTrue(Modifier.isPrivate(mirrored.getModifiers()), "mirrored must be private");
        } catch (NoSuchFieldException e) {
            fail(className + " must declare a private boolean field named mirrored");
        }
    }

    private static void assertSwarmConstructor(String className, Tetromino normal, Tetromino mirrored) {
        Object normalSwarm = newSwarm(className, 3, 17, false);
        Object mirroredSwarm = newSwarm(className, 4, 16, true);

        assertEquals(3, ((Swarm) normalSwarm).getX(), "constructor must pass x to Swarm");
        assertEquals(17, ((Swarm) normalSwarm).getY(), "constructor must pass y to Swarm");
        TestUtils.assertBooleanMatrixEquals(normal.getShape(),
            ((Swarm) normalSwarm).getProjectionMatrix().getShape(),
            "constructor must use the non-mirrored tetromino when mirrored is false");
        TestUtils.assertBooleanMatrixEquals(mirrored.getShape(),
            ((Swarm) mirroredSwarm).getProjectionMatrix().getShape(),
            "constructor must use the mirrored tetromino when mirrored is true");
    }

    private static void assertSpawnFamilies(String className, RobotFamily normalFamily, RobotFamily mirroredFamily) {
        assertSpawnFamily(newSwarm(className, 0, 0, false), normalFamily);
        World.reset();
        World.setSize(10, 20);
        assertSpawnFamily(newSwarm(className, 0, 0, true), mirroredFamily);
    }

    private static void assertSpawnFamily(Object swarmObject, RobotFamily expectedFamily) {
        Swarm swarm = (Swarm) swarmObject;
        TestUtils.invoke(TestUtils.requireMethod(swarmObject.getClass(), "spawn"), swarmObject);
        int[][] projected = swarm.getProjectionMatrix().projectToWorld(swarm.getX(), swarm.getY());

        for (int[] point : projected) {
            var tetrobots = World.getGlobalWorld().getField(point[0], point[1]).getEntities().stream()
                .filter(entity -> entity.getClass().getName().equals("h04.Tetrobot"))
                .map(entity -> (fopbot.Robot) entity)
                .toList();
            assertEquals(1, tetrobots.size(), "spawn() must create one tetrobot on each projected cell");
            assertSame(expectedFamily, tetrobots.getFirst().getRobotFamily(), "spawn() must set the correct robot family");
        }
    }

    private static void assertToString(String className, String normalName, String mirroredName) {
        Class<?> clazz = TestUtils.requireClass(className);
        assertEquals(normalName, newSwarm(className, 0, 0, false).toString(),
            "toString() must return the non-mirrored tetromino name");
        assertEquals(mirroredName, newSwarm(className, 0, 0, true).toString(),
            "toString() must return the mirrored tetromino name");
        TestUtils.assertSingleConditionalReturn(clazz, "toString");
    }

    private static Object newSwarm(String className, int x, int y, boolean mirrored) {
        Class<?> clazz = TestUtils.requireClass(className);
        Constructor<?> constructor = TestUtils.requireConstructor(clazz, int.class, int.class, boolean.class);
        return TestUtils.newInstance(constructor, x, y, mirrored);
    }
}
