package Game.Enemy;

import Game.Movement.MovementComponent;

public abstract class EnemyShip extends EnemyEntity {
    public EnemyShip(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection) {
        setMovementComponent(new MovementComponent(xSpeed, ySpeed, xPosition, yPosition, xDirection, yDirection));
    }
}
