package Visualisation;

import Game.AbstractFactory;
import Game.Input;
import Game.Player.PlayerShip;
import Visualisation.Enemy.Java2DEnemyBullet;
import Visualisation.Enemy.Java2DEnemyShip;
import Visualisation.Player.Java2DPlayerBullet;
import Visualisation.Player.Java2DPlayerShip;

public class Java2DFactory extends AbstractFactory {
    static GraphicsContext graphicsContext = new GraphicsContext();
    /*@Override
    public PlayerShip createPlayerShip(double xPosition, double yPosition) {
        return new Java2DPlayerShip(xPosition, yPosition, graphicsContext);
    }
    @Override
    public Java2DEnemyShip createEnemyShip(double xPosition, double yPosition) {
        return new Java2DEnemyShip(xPosition, yPosition, graphicsContext);
    }
    @Override
    public Java2DPlayerBullet createPlayerBullet(double xPosition, double yPosition) {
        return new Java2DPlayerBullet(xPosition, yPosition, graphicsContext);
    }
    @Override
    public Java2DEnemyBullet createEnemyBullet(double xPosition, double yPosition) {
        return new Java2DEnemyBullet(xPosition, yPosition, graphicsContext);
    }*/
    @Override
    public PlayerShip createPlayerShip(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection) {
        return new Java2DPlayerShip(xSpeed, ySpeed, xPosition, yPosition, xDirection, yDirection, graphicsContext);
    }
    @Override
    public Java2DEnemyShip createEnemyShip(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection) {
        return new Java2DEnemyShip(xSpeed, ySpeed, xPosition, yPosition, xDirection, yDirection, graphicsContext);
    }
    @Override
    public Java2DPlayerBullet createPlayerBullet(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection) {
        return new Java2DPlayerBullet(xSpeed, ySpeed, xPosition, yPosition, xDirection, yDirection, graphicsContext);
    }
    @Override
    public Java2DEnemyBullet createEnemyBullet(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection) {
        return new Java2DEnemyBullet(xSpeed, ySpeed, xPosition, yPosition, xDirection, yDirection, graphicsContext);
    }
    @Override
    public void setGameDimensions(int gameCellsX, int gameCellsY) {
        graphicsContext.setGameDimensions(gameCellsX, gameCellsY);
    }
    @Override
    public int getRefreshRate() {
        return graphicsContext.getRefreshRate();
    }
    @Override
    public void render() {
        graphicsContext.render();
    }
    @Override
    public Input createInput() {
        return new Java2DInput(graphicsContext);
    }

    @Override
    public void updateTitle(int fps, int ups) {
        graphicsContext.setTitle(fps, ups);
    }
    @Override
    public int getPixelRatio() {
        return graphicsContext.getPixelRatio();
    }
}
