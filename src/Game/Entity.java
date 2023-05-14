package Game;

import Game.Movement.MovementComponent;

public abstract class Entity {
    private MovementComponent component;
    public MovementComponent getMovementComponent() {
        return this.component;
    }
    public void setMovementComponent(MovementComponent component) {
        this.component = component;
    }
    /*private double xPosition;
    private double yPosition;

    public double getxPosition() {
        return xPosition;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }*/
    public abstract void draw();

    /*@Override
    public String toString() {
        return "Entity{" +
                "xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                '}';
    }*/
    /*public boolean collides(Entity otherEntity) {
        boolean xCollision = this.xPosition && otherEntity.xPosition < this.xPosition + 1;
        boolean yCollision = otherEntity
        return (this.xPosition)
    }*/

    @Override
    public String toString() {
        return "Entity{" +
                "component=" + component +
                '}';
    }
}
