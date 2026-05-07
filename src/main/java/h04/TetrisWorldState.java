package h04;

/**
 * Represents the state of a cell in the Tetris world.
 * Used to track whether a cell is empty, occupied by a fixed bot, or occupied by the active swarm.
 * ACTIVE_SWARM is currently unused
 */
public enum TetrisWorldState {
    UNDEFINED,
    FIXED_BOT,
    ACTIVE_SWARM,
}
