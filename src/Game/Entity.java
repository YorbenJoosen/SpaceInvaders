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

    public abstract void draw();

    @Override
    public String toString() {
        return "Entity{" +
                "component=" + component +
                '}';
    }
}
