package Visualisation.Player;

import Game.Player.PlayerShip;
import Visualisation.GraphicsContext;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Java2DPlayerShip extends PlayerShip {
    private final BufferedImage playerShip;
    private final BufferedImage playerHealth;
    private final Graphics2D graphics2D;
    private final int size;
    public Java2DPlayerShip(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection, GraphicsContext graphicsContext) {
        super(xSpeed, ySpeed, xPosition, yPosition, xDirection, yDirection);
        this.graphics2D = graphicsContext.getG2d();
        this.size = graphicsContext.getSize();
        this.playerShip = graphicsContext.spriteSheet.getSubimage(4 * size, 2 * size, size, size);
        this.playerHealth = graphicsContext.heart;
    }
    @Override
    public void draw() {
        graphics2D.drawImage(playerShip, (int) Math.round(getMovementComponent().getxPosition())*size, (int) Math.round(getMovementComponent().getyPosition())*size, null);
    }
    @Override
    public void drawHealth(int xPosition, int yPosition) {
        graphics2D.drawImage(playerHealth, xPosition*size, yPosition*size, null);
    }
}
