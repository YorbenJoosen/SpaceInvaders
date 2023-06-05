package Game.Enemy;


import Game.Movement.MovementComponent;

public abstract class EnemyBoss extends EnemyEntity {
    private final int sizeX;
    private final int sizeY;
    public EnemyBoss(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection, int sizeX, int sizeY) {
        setMovementComponent(new MovementComponent(xSpeed, ySpeed, xPosition, yPosition, xDirection, yDirection));
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
