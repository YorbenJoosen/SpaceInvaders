package Visualisation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphicsContext {
    private final double ScreenWidth;
    private final double ScreenHeight;
    private final JFrame frame;
    private final JPanel panel;
    private BufferedImage g2dimage;     // used for drawing
    private Graphics2D g2d;             // always draw in this one
    private int size;                   // cell size
    private final int sizeX = 50; // Size of entities in pixels
    private final int sizeY = 50; // Size of entities in pixels
    public BufferedImage spriteSheet;
    public BufferedImage background;
    public BufferedImage heart;
    public BufferedImage playerBullet;
    public BufferedImage spriteSheet2;
    public Graphics2D getG2d() {
        return g2d;
    }
    public JFrame getFrame() {
        return frame;
    }
    public int getSize() {return size;}
    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight){
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    private void loadImages() {
        try {
            spriteSheet = ImageIO.read(new File("src/Resources/SpriteSheet.png"));
            background = ImageIO.read(new File("src/Resources/StarBackground.jpg"));
            heart = ImageIO.read(new File("src/resources/heart.png"));
            spriteSheet2 = ImageIO.read(new File("src/resources/SpriteSheet2.png"));
        } catch (IOException e) {
            System.out.println("Unable to load images!");
        }
    }

    public GraphicsContext() {
        // Set window to full size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ScreenWidth = screenSize.width;
        ScreenHeight = screenSize.height;
        frame = new JFrame();
        panel = new JPanel(true) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                doDrawing(g);
            }
        };
        frame.setFocusable(true);
        frame.add(panel);
        String titleString = String.format("Space invaders by Yorben Joosen, UPS: %d FPS: %d", 0, 0);
        frame.setTitle(titleString);
        // Set the window to be maximized
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void render() {
        panel.repaint();
    }

    private void doDrawing(Graphics g) {
        Graphics2D graph2d = (Graphics2D) g;
        Toolkit.getDefaultToolkit().sync();
        graph2d.drawImage(g2dimage, 0, 0, null);   // copy buffered image
        graph2d.dispose();
        if (g2d != null)
            g2d.drawImage(background, 0, 0, null);
    }

    public void setGameDimensions(int GameCellsX, int GameCellsY) {
        size = (int) Math.min(ScreenWidth/GameCellsX, ScreenHeight/GameCellsY);

        frame.setLocation(0,0);
        frame.setSize((int) ScreenWidth, (int) ScreenHeight);
        loadImages();
        try {
            background = resizeImage(background, (int) ScreenWidth, (int) ScreenHeight);
            spriteSheet = resizeImage(spriteSheet, 9 * size, 3 * size);
            spriteSheet2 = resizeImage(spriteSheet2, 9 * size, 3 * size);
            heart = resizeImage(heart, size/2, size/2);
            playerBullet = resizeImage(playerBullet, size/3, size/2);
            /*spriteSheet = resizeImage(spriteSheet, 9 * sizeX, 3 * sizeY);
            spriteSheet2 = resizeImage(spriteSheet2, 9 * sizeX, 3 * sizeY);
            heart = resizeImage(heart, sizeX, sizeY);
            playerBullet = resizeImage(playerBullet, sizeX, sizeY);*/
        } catch(Exception e) {
            e.getStackTrace();
        }
        g2dimage = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
        g2d = g2dimage.createGraphics();
        g2d.clearRect(0,0, frame.getWidth(), frame.getHeight());
    }
    public void setTitle(int fps, int ups) {
        String titleString = String.format("Space invaders by Yorben Joosen, UPS: %d FPS: %d", ups, fps);
        frame.setTitle(titleString);
    }
    public int getRefreshRate() {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] graphicsDevices = graphicsEnvironment.getScreenDevices();
        DisplayMode displayMode = graphicsDevices[0].getDisplayMode();
        return displayMode.getRefreshRate();
    }
    public int getPixelRatio() {
        return (int) Math.ceil(ScreenWidth/ScreenHeight);
    }
    public int getSizeX() {
        return sizeX;
    }
    public int getSizeY() {
        return sizeY;
    }
}


