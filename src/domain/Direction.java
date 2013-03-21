package domain;

public enum Direction {

    LEFT,
    RIGHT,
    DOUBLE;

    public char getChar() {
        switch (this) {
            case LEFT:
                return '<';
            case RIGHT:
                return '>';
            case DOUBLE:
                return ' ';
            default:
                return '-';
        }
    }
}
