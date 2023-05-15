package Visualisation.Enemy;

import Game.Enemy.EnemyShip;
import Visualisation.GraphicsContext;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Java2DEnemyShip extends EnemyShip {
    private final BufferedImage enemy1;
    private final BufferedImage enemy2;
    private final BufferedImage enemy3;
    private final Graphics2D graphics2D;
    private final int size;
    public Java2DEnemyShip(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection, GraphicsContext graphicsContext) {
        super(xSpeed, ySpeed, xPosition, yPosition, xDirection, yDirection);
        this.graphics2D = graphicsContext.getG2d();
        this.size = graphicsContext.getSize();
        // Get character from the spritesheet
        this.enemy1 = graphicsContext.spriteSheet.getSubimage(1 * size, 1 * size, size, size);
        this.enemy2 = graphicsContext.spriteSheet.getSubimage(2 * size, 1 * size, size, size);
        this.enemy3 = graphicsContext.spriteSheet.getSubimage(3 * size, 1 * size, size, size);
    }
    @Override
    public void draw() {
        int xPosition = (int) Math.round(getMovementComponent().getxPosition());
        int yPosition = (int) Math.round(getMovementComponent().getyPosition());
        if (yPosition == 1) {
            graphics2D.drawImage(enemy1, xPosition*size, yPosition*size, null);
        }
        else if (yPosition == 2) {
            graphics2D.drawImage(enemy2, xPosition*size, yPosition*size, null);
        }
        else if (yPosition == 3) {
            graphics2D.drawImage(enemy3, xPosition*size, yPosition*size, null);
        }
    }
}
