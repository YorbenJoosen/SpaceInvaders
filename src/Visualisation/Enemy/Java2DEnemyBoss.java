package Visualisation.Enemy;

import Game.Enemy.EnemyBoss;
import Visualisation.GraphicsContext;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Java2DEnemyBoss extends EnemyBoss {
    private final BufferedImage image;
    private final Graphics2D graphics2D;
    private final int size;

    public Java2DEnemyBoss(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection, int sizeX, int sizeY, GraphicsContext graphicsContext) {
        super(xSpeed, ySpeed, xPosition, yPosition, xDirection, yDirection, sizeX, sizeY);
        this.graphics2D = graphicsContext.getG2d();
        this.size = graphicsContext.getSize();
        BufferedImage image = graphicsContext.spriteSheet.getSubimage(3*size, 2*size, size, size);
        this.image = graphicsContext.resizeImage(image, sizeX * size, sizeY * size);
    }


    @Override
    public void draw() {
        int xPosition = (int) Math.round(getMovementComponent().getxPosition());
        int yPosition = (int) Math.round(getMovementComponent().getyPosition());
        graphics2D.drawImage(image, xPosition * size, yPosition * size, null);
    }
}
