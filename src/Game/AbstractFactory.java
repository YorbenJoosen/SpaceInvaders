package Game;

import Game.Enemy.EnemyBullet;
import Game.Enemy.EnemyShip;
import Game.Player.PlayerBullet;
import Game.Player.PlayerShip;

public abstract class AbstractFactory {
    public abstract PlayerShip createPlayerShip(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection);
    public abstract EnemyShip createEnemyShip(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection);
    public abstract PlayerBullet createPlayerBullet(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection);
    public abstract EnemyBullet createEnemyBullet(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection);
    public abstract void setGameDimensions(int gameCellsX, int gameCellsY);
    public abstract void render();
    public abstract Input createInput();
    public abstract int getRefreshRate();
    public abstract void updateTitle(int fps, int ups);
    public abstract int getPixelRatio();
}
