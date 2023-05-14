package Game.Player;

import Game.Input;
import Game.Movement.MovementComponent;

public abstract class PlayerShip extends PlayerEntity {
    private int lives;
    public PlayerShip(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection) {
        setMovementComponent(new MovementComponent(xSpeed, ySpeed, xPosition, yPosition, xDirection, yDirection));
        this.lives = 3;
    }
    public void update(Input.InputTypes input, int gameCellsX) {
        double xPosition = getMovementComponent().getxPosition();
        if (input == Input.InputTypes.LEFT && xPosition != 0) {
            getMovementComponent().setxPosition(xPosition - 1);
        }
        else if (input == Input.InputTypes.RIGHT && xPosition != gameCellsX - 1) {
            getMovementComponent().setxPosition(xPosition + 1);
        }
    }
    public int getHealth() {
        return lives;
    }
    public void updateHealth() {
        lives--;
    }
    public abstract void drawHealth(int xPosition, int yPosition);
}
