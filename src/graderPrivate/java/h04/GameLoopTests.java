package h04;

import fopbot.Direction;
import fopbot.Key;
import fopbot.KeyPressEvent;
import fopbot.World;
import h04.swarms.Swarm;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@TestForSubmission
public class GameLoopTests {

    @Tag("H4.3.1")
    @Test
    public void testHandleInputAAndD() {
        TestableGameLoop gameLoop = new TestableGameLoop();
        TetrisWorld tetrisWorld = mock(TetrisWorld.class);
        Swarm swarm = mock(Swarm.class);
        when(tetrisWorld.getActiveSwarm()).thenReturn(swarm);
        when(swarm.canMove(any())).thenReturn(true);
        setTetrisWorld(gameLoop, tetrisWorld);

        gameLoop.handleInput(event(Key.A));
        gameLoop.handleInput(event(Key.D));

        verify(swarm).move(Direction.LEFT);
        verify(swarm).move(Direction.RIGHT);
    }

    @Tag("H4.3.1")
    @Test
    public void testHandleInputS() {
        TestableGameLoop gameLoop = new TestableGameLoop();
        TetrisWorld tetrisWorld = mock(TetrisWorld.class);
        Swarm swarm = mock(Swarm.class);
        when(tetrisWorld.getActiveSwarm()).thenReturn(swarm);
        when(swarm.canMove(Direction.DOWN)).thenReturn(true);
        setTetrisWorld(gameLoop, tetrisWorld);

        gameLoop.handleInput(event(Key.S));

        verify(swarm).move(Direction.DOWN);
    }

    @Tag("H4.3.1")
    @Test
    public void testHandleInputArrowKeys() {
        TestableGameLoop gameLoop = configuredForRotation(true);

        gameLoop.handleInput(event(Key.LEFT));
        gameLoop.handleInput(event(Key.RIGHT));

        ArgumentCaptor<ProjectionMatrix> captor = ArgumentCaptor.forClass(ProjectionMatrix.class);
        verify(gameLoop.swarm, times(2)).setProjectionMatrix(captor.capture());
        verify(gameLoop.swarm, times(2)).commitSRSRotation();

        ProjectionMatrix expectedLeft = new ProjectionMatrix(Tetromino.T);
        expectedLeft.rotateLeft();
        TestUtils.assertBooleanMatrixEquals(expectedLeft.getShape(), captor.getAllValues().get(0).getShape(),
            "LEFT must rotate the active swarm counterclockwise");

        ProjectionMatrix expectedRight = new ProjectionMatrix(Tetromino.T);
        expectedRight.rotateRight();
        TestUtils.assertBooleanMatrixEquals(expectedRight.getShape(), captor.getAllValues().get(1).getShape(),
            "RIGHT must rotate the active swarm clockwise");
    }

    @Tag("H4.3.1")
    @Test
    public void testHandleInputSpace() {
        TestableGameLoop gameLoop = new TestableGameLoop();
        TetrisWorld tetrisWorld = mock(TetrisWorld.class);
        setTetrisWorld(gameLoop, tetrisWorld);

        gameLoop.handleInput(event(Key.SPACE));

        verify(tetrisWorld).drop();
    }

    @Tag("H4.3.1")
    @Test
    public void testHandleInputReturnValue() {
        TestableGameLoop gameLoop = configuredForRotation(true);
        when(gameLoop.swarm.canMove(any())).thenReturn(true);

        assertFalse(gameLoop.handleInput(event(Key.A)), "A must not reset gravity progression");
        assertFalse(gameLoop.handleInput(event(Key.D)), "D must not reset gravity progression");
        assertFalse(gameLoop.handleInput(event(Key.LEFT)), "LEFT must not reset gravity progression");
        assertFalse(gameLoop.handleInput(event(Key.RIGHT)), "RIGHT must not reset gravity progression");
        assertTrue(gameLoop.handleInput(event(Key.S)), "S must reset gravity progression");
        assertTrue(gameLoop.handleInput(event(Key.SPACE)), "SPACE must reset gravity progression");
        assertFalse(gameLoop.handleInput(event(Key.W)), "unhandled keys must not reset gravity progression");
    }

    @Tag("H4.3.2")
    @Test
    public void testRotateCheckedCommitsRotationOnActiveSwarm() {
        TestableGameLoop gameLoop = configuredForRotation(true);

        gameLoop.rotate(true);

        verify(gameLoop.swarm).commitSRSRotation();
    }

    @Tag("H4.3.2")
    @Test
    public void testRotateCheckedSetsProjectionMatrixOnlyWhenValid() {
        TestableGameLoop validLoop = configuredForRotation(true);
        validLoop.rotate(true);
        verify(validLoop.swarm).setProjectionMatrix(any(ProjectionMatrix.class));

        TestableGameLoop invalidLoop = configuredForRotation(false);
        invalidLoop.rotate(true);
        verify(invalidLoop.swarm, never()).setProjectionMatrix(any(ProjectionMatrix.class));
    }

    @Tag("H4.3.2")
    @Test
    public void testRotateCheckedDirection() {
        TestableGameLoop rightLoop = configuredForRotation(true);
        rightLoop.rotate(true);
        ArgumentCaptor<ProjectionMatrix> rightCaptor = ArgumentCaptor.forClass(ProjectionMatrix.class);
        verify(rightLoop.swarm).setProjectionMatrix(rightCaptor.capture());
        ProjectionMatrix expectedRight = new ProjectionMatrix(Tetromino.T);
        expectedRight.rotateRight();
        TestUtils.assertBooleanMatrixEquals(expectedRight.getShape(), rightCaptor.getValue().getShape(),
            "rotateChecked(true) must rotate clockwise");

        TestableGameLoop leftLoop = configuredForRotation(true);
        leftLoop.rotate(false);
        ArgumentCaptor<ProjectionMatrix> leftCaptor = ArgumentCaptor.forClass(ProjectionMatrix.class);
        verify(leftLoop.swarm).setProjectionMatrix(leftCaptor.capture());
        ProjectionMatrix expectedLeft = new ProjectionMatrix(Tetromino.T);
        expectedLeft.rotateLeft();
        TestUtils.assertBooleanMatrixEquals(expectedLeft.getShape(), leftCaptor.getValue().getShape(),
            "rotateChecked(false) must rotate counterclockwise");
    }

    private static TestableGameLoop configuredForRotation(boolean canProject) {
        TestableGameLoop gameLoop = new TestableGameLoop();
        TetrisWorld tetrisWorld = mock(TetrisWorld.class);
        Swarm swarm = mock(Swarm.class);
        when(tetrisWorld.getActiveSwarm()).thenReturn(swarm);
        when(swarm.getProjectionMatrix()).thenReturn(new ProjectionMatrix(Tetromino.T));
        when(swarm.getX()).thenReturn(3);
        when(swarm.getY()).thenReturn(17);
        when(tetrisWorld.canProject(any(ProjectionMatrix.class), eq(3), eq(17))).thenReturn(canProject);
        gameLoop.swarm = swarm;
        setTetrisWorld(gameLoop, tetrisWorld);
        return gameLoop;
    }

    private static KeyPressEvent event(Key key) {
        World.setSize(10, 20);
        World.setVisible(false);
        return new KeyPressEvent(World.getGlobalWorld(), key);
    }

    private static void setTetrisWorld(GameLoop gameLoop, TetrisWorld tetrisWorld) {
        try {
            Field field = GameLoop.class.getDeclaredField("tetrisWorld");
            field.setAccessible(true);
            field.set(gameLoop, tetrisWorld);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }

    private static class TestableGameLoop extends GameLoop {
        Swarm swarm;

        void rotate(boolean clockwise) {
            rotateChecked(clockwise);
        }
    }
}
