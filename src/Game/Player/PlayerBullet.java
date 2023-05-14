package Game.Player;

import Game.Movement.MovementComponent;

public abstract class PlayerBullet extends PlayerEntity {
    public PlayerBullet(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection) {
        setMovementComponent(new MovementComponent(xSpeed, ySpeed, xPosition, yPosition, xDirection, yDirection));
    }
}
