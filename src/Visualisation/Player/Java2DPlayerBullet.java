package Visualisation.Player;

import Game.Player.PlayerBullet;
import Visualisation.GraphicsContext;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Java2DPlayerBullet extends PlayerBullet {
    private final BufferedImage image;
    private final Graphics2D graphics2D;
    private final int size;
    public Java2DPlayerBullet(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection, GraphicsContext graphicsContext) {
        super(xSpeed, ySpeed, xPosition, yPosition, xDirection, yDirection);
        this.graphics2D = graphicsContext.getG2d();
        this.size = graphicsContext.getSize();
        this.image = graphicsContext.spriteSheet2.getSubimage(6 * size, 2 * size, size, size);
    }
    @Override
    public void draw() {
        graphics2D.drawImage(image, (int) Math.round(getMovementComponent().getxPosition())*size, (int) Math.round(getMovementComponent().getyPosition())*size, null);
    }
}
