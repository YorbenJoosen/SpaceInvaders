package Game.Enemy;

import Game.Movement.MovementComponent;

public abstract class EnemyBullet extends EnemyEntity {
    public EnemyBullet(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection) {
        setMovementComponent(new MovementComponent(xSpeed, ySpeed, xPosition, yPosition, xDirection, yDirection));
    }
    /*public EnemyBullet(double xPosition, double yPosition) {
        setxPosition(xPosition);
        setyPosition(yPosition);
    }*/
}
