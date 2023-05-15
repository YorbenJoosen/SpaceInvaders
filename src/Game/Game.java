package Game;

import Game.Enemy.EnemyBullet;
import Game.Enemy.EnemyShip;
import Game.Helpers.Timer;
import Game.Movement.CollisionSystem;
import Game.Movement.MovementSystem;
import Game.Player.PlayerBullet;
import Game.Player.PlayerShip;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Collection;


public class Game {
    private boolean isRunning;
    private boolean isPaused;
    private final AbstractFactory abstractFactory;
    private final int gameCellsX;
    private final int gameCellsY;
    private final Input input;
    private final ArrayList<PlayerShip> playerShips = new ArrayList<>();
    private final ArrayList<PlayerBullet> playerBullets = new ArrayList<>();
    private final ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    private final ArrayList<EnemyShip> enemyShips = new ArrayList<>();
    private final MovementSystem movementSystem;
    private final CollisionSystem collisionSystem;
    private Timer timer;
    private int entityWidth;
    private int entityHeight;

    public Game(AbstractFactory abstractFactory) {
        this.abstractFactory = abstractFactory;
        this.gameCellsY = 100;
        this.gameCellsX = 100;
        abstractFactory.setGameDimensions(gameCellsX, gameCellsY);
        this.input = abstractFactory.createInput();
        this.movementSystem = new MovementSystem();
        collisionSystem = new CollisionSystem(gameCellsX, gameCellsY);
        this.entityWidth = 4;
        this.entityHeight = entityWidth/abstractFactory.getPixelRatio();
    }
    public void run() {
        isRunning = true;
        isPaused = false;
        init();
        while (isRunning) {
            timer.updateTimer();
            if (timer.updateGame(isPaused)) {
                if (!isPaused) {
                    updateMovement();
                    checkCollisions();
                }
                updateAll();
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
        //TODO check gamemode
        PlayerShip playerShip = abstractFactory.createPlayerShip(1, 0, gameCellsX/2, gameCellsY - 1, 0, 0);
        playerShips.add(playerShip);
        timer = new Timer(abstractFactory.getRefreshRate(), 25);
    }
    public void drawAll() {
        // Put all the arrays into one array, so they can be drawn in one go
        ArrayList<Entity> allEntities = Stream.of(enemyShips, enemyBullets, playerBullets, playerShips)
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(ArrayList::new));
        
        // Draw all entities
        for (Entity entity: allEntities) {
            entity.draw();
        }

        PlayerShip playerShip = playerShips.get(0);
        for (int i = 0; i < playerShip.getHealth(); i++) {
            playerShip.drawHealth(i, 0);
        }
        abstractFactory.render();
    }
    public void updateMovement() {
        // Put all the arrays into one array, so they can get updated in one go
        ArrayList<Entity> allEntities = Stream.of(enemyShips, enemyBullets, playerBullets)
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(ArrayList::new));

        // Update the movement components
        movementSystem.update(allEntities, timer.getTimeDelta());
    }
    public void checkCollisions() {
        collisionSystem.enemyShips(enemyShips);
        // Needs to be first otherwise bullets will be removed before hitting the player ship
        collisionSystem.playerShips(enemyBullets, playerShips);
        collisionSystem.collisionBetweenTwoEntities(playerBullets, enemyShips);
        collisionSystem.collisionBetweenTwoEntities(enemyBullets, playerBullets);
    }
    public void updateAll() {
        int bulletSpeed = 10;
        PlayerShip playerShip = playerShips.get(0);

        if (input.inputAvailable()) {
            Input.InputTypes currentInput = input.getInput();
            if (currentInput == Input.InputTypes.SPACE) {
                isPaused = !isPaused;
            }
            else if (currentInput == Input.InputTypes.UP && !isPaused) {
                playerBullets.add(abstractFactory.createPlayerBullet(0, bulletSpeed, playerShip.getMovementComponent().getxPosition(), playerShip.getMovementComponent().getyPosition(), 0, -1));
            }
            else if (!isPaused){
                double xPosition = playerShip.getMovementComponent().getxPosition();
                if (currentInput == Input.InputTypes.LEFT && xPosition != 0) {
                    playerShip.getMovementComponent().setxDirection(-1);
                }
                else if (currentInput == Input.InputTypes.RIGHT && xPosition != gameCellsX - 1) {
                    playerShip.getMovementComponent().setxDirection(1);
                }
                playerShip.update(currentInput, gameCellsX);
            }
        }
        if (!isPaused) {
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
        }
    }
}
