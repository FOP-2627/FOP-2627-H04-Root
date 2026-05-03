package h04;

public enum Tetromino {
    I(new boolean[][]{
        {false, false, false, false},
        {true, true, true, true},
        {false, false, false, false},
        {false, false, false, false}}),
    O(new boolean[][]{
        {false, true, true, false},
        {false, true, true, false},
        {false, false, false, false},
        {false, false, false, false}}),
    T(new boolean[][]{
        {false, true, false},
        {true, true, true},
        {false, false, false}}),
    L(new boolean[][]{
        {false, true, false},
        {false, true, false},
        {false, true, true}}),
    J(new boolean[][]{
        {false, true, false},
        {false, true, false},
        {false, true, true}}),
    S(new boolean[][]{
        {false, true, true},
        {true, true, false},
        {false, false, false}}),
    Z(new boolean[][]{
        {false, true, true},
        {true, true, false},
        {false, false, false}});

    Tetromino(boolean[][] template) {
        this.template = template;
    }

    public final boolean[][] template;
}
