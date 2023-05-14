package Game;

import Game.Enemy.EnemyBullet;
import Game.Enemy.EnemyShip;
import Game.Helpers.Collision;
import Game.Helpers.Timer;
import Game.Movement.MovementComponent;
import Game.Movement.MovementSystem;
import Game.Player.PlayerBullet;
import Game.Player.PlayerShip;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;


public class Game {
    private boolean isRunning;
    private boolean isPaused;
    private final AbstractFactory abstractFactory;
    private final int gameCellsX;
    private final int gameCellsY;
    private final Input input;
    private PlayerShip playerShip;
    private final ArrayList<EnemyShip> enemyShips = new ArrayList<>();
    private final ArrayList<PlayerBullet> playerBullets = new ArrayList<>();
    private final ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    private int dx = 1;
    private int enemyDirection = 1;
    private MovementSystem movementSystem;
    private Collision collision;

    public Game(AbstractFactory abstractFactory) {
        this.abstractFactory = abstractFactory;
        this.gameCellsY = 10;
        this.gameCellsX = gameCellsY * abstractFactory.getPixelRatio();
        abstractFactory.setGameDimensions(gameCellsX, gameCellsY);
        this.input = abstractFactory.createInput();
        this.movementSystem = new MovementSystem();
        this.collision = new Collision(gameCellsX, gameCellsY);
    }
    public void run() {
        isRunning = true;
        isPaused = false;
        Timer timer = new Timer(abstractFactory.getRefreshRate(), 25);
        init();
        while (isRunning) {
            timer.updateTimer();
            if (timer.updateGame(isPaused)) {
                updateAll(timer.getTimeDelta());
            }
            if (timer.updateRender()) {
                drawAll();
            }
            if (timer.calculationReady()) {
                abstractFactory.updateTitle(timer.getActualFPS(), timer.getActualUPS());
                timer.resetCalculation();
            }
        }
    }
    public void init() {
        // Create enemy ships
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                EnemyShip enemyShip = abstractFactory.createEnemyShip(5, 0, j, i, 1, 0);
                enemyShips.add(enemyShip);
            }
        }
        // Create player ship
        playerShip = abstractFactory.createPlayerShip(1, 0, gameCellsX/2, gameCellsY - 1, 0, 0);
    }
    public void drawAll() {
        // Draw player ship
        playerShip.draw();
        // Draw enemy ships
        for (EnemyShip enemyShip: enemyShips) {
            enemyShip.draw();
        }
        // Draw player bullets
        for (PlayerBullet playerBullet: playerBullets) {
            playerBullet.draw();
        }
        // Draw enemy bullets
        for (EnemyBullet enemyBullet: enemyBullets) {
            enemyBullet.draw();
        }
        for (int i = 0; i < playerShip.getHealth(); i++) {
            playerShip.drawHealth(i, 0);
        }
        abstractFactory.render();
    }
    public void updateAll(float delta) {
        int bulletSpeed = 10;

        if (input.inputAvailable()) {
            Input.InputTypes currentInput = input.getInput();
            if (currentInput == Input.InputTypes.SPACE) {
                isPaused = !isPaused;
            }
            else if (currentInput == Input.InputTypes.UP && !isPaused) {
                playerBullets.add(abstractFactory.createPlayerBullet(0, bulletSpeed, playerShip.getMovementComponent().getxPosition(), playerShip.getMovementComponent().getyPosition(), 0, -1));
            }
            else if (!isPaused){
                playerShip.update(currentInput, gameCellsX);
            }
        }
        if (!isPaused) {
            // Update enemy ships
            int xRightSide = gameCellsX - 1;

            for (EnemyShip enemyShip: enemyShips) {
                // Check if right side is hit
                if (collision.rightBorderHit(enemyShip.getMovementComponent())) {
                    enemyDirection = -1;
                    break;
                }
                // Check if left side is hit
                else if (collision.leftBorderHit(enemyShip.getMovementComponent())) {
                    enemyDirection = 1;
                    break;
                }
            }
            // Update time delta and direction
            for (EnemyShip enemyShip: enemyShips) {
                enemyShip.getMovementComponent().setTimeDelta(delta);
                enemyShip.getMovementComponent().setxDirection(enemyDirection);
            }
            // Get movementcomponents out of enemy ships
            ArrayList<MovementComponent> componentList = enemyShips.stream()
                    .map(EnemyShip::getMovementComponent)
                    .collect(Collectors.toCollection(ArrayList::new));

            // Update positions
            movementSystem.update(componentList);

            // Update player bullets
            collision.playerBullet(playerBullets, enemyShips);

            // Update player bullets
            for (PlayerBullet playerBullet : playerBullets) {
                playerBullet.getMovementComponent().setTimeDelta(delta);
            }
            ArrayList<MovementComponent> components = playerBullets.stream()
                    .map(PlayerBullet::getMovementComponent)
                    .collect(Collectors.toCollection(ArrayList::new));

            movementSystem.update(components);

            // Get random enemy ship, this has a 33% chance to be a ship on the front row
            Random random = new Random();
            EnemyShip randomEnemyShip = null;
            if (enemyShips.size() == 1) {
                randomEnemyShip = enemyShips.get(0);
            } else if (enemyShips.size() == 0) {
                isPaused = true;
                //TODO endgame
            } else {
                randomEnemyShip = enemyShips.get(random.nextInt(enemyShips.size()));
            }
            boolean frontRow = true;
            // Check if chosen ship is on the front row
            for (EnemyShip enemyShip: enemyShips) {
                if ((int) randomEnemyShip.getMovementComponent().getxPosition() == (int) enemyShip.getMovementComponent().getxPosition() && (int) randomEnemyShip.getMovementComponent().getyPosition() + 1 == (int) enemyShip.getMovementComponent().getyPosition()) {
                    frontRow = false;
                    break;
                }
            }
            // I want a 3.3% chance so 0.1
            if (frontRow && random.nextDouble() <= 0.05) {
                EnemyBullet enemyBullet =  abstractFactory.createEnemyBullet(0, bulletSpeed, randomEnemyShip.getMovementComponent().getxPosition(), randomEnemyShip.getMovementComponent().getyPosition() +1, 0, 1);
                enemyBullets.add(enemyBullet);
            }
            collision.enemyBullet(enemyBullets, playerBullets, playerShip);


            for (EnemyBullet enemyBullet : enemyBullets) {
                enemyBullet.getMovementComponent().setTimeDelta(delta);
            }
            ArrayList<MovementComponent> components2 = enemyBullets.stream()
                    .map(EnemyBullet::getMovementComponent)
                    .collect(Collectors.toCollection(ArrayList::new));

            movementSystem.update(components2);
        }
    }
}
