package Sokoban.model;

public interface Movable {
    void move(int x, int y);
    void move(Direction direction);
}
