package Sokoban.model;

public abstract class CollisionObject extends GameObject {
    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction) {
        int newX = this.getX();
        int newY = this.getY();
        if (direction.equals(Direction.DOWN)) {
            newX = this.getX();
            newY = this.getY() + Model.FIELD_CELL_SIZE;
        }
        if (direction.equals(Direction.UP)) {
            newX = this.getX();
            newY = this.getY() - Model.FIELD_CELL_SIZE;
        }
        if (direction.equals(Direction.LEFT)) {
            newX = this.getX() - Model.FIELD_CELL_SIZE;
            newY = this.getY();
        }
        if (direction.equals(Direction.RIGHT)) {
            newX = this.getX() + Model.FIELD_CELL_SIZE;
            newY = this.getY();
        }
        if(newX == gameObject.getX() && newY == gameObject.getY()) return true;
        return false;
    }

}
