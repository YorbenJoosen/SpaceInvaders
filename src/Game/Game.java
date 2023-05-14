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
import java.util.HashSet;
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
    /*private final HashSet<EnemyShip> enemyShips = new HashSet<>();
    private final HashSet<PlayerBullet> playerBullets = new HashSet<>();
    private final HashSet<EnemyBullet> enemyBullets = new HashSet<>();*/
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
                updateAll(timer.getDelta());
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

        /*
        // Create enemy ships
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                EnemyShip enemyShip = abstractFactory.createEnemyShip(j, i);
                enemyShips.add(enemyShip);
            }
        }
        // Create player ship
        playerShip = abstractFactory.createPlayerShip(gameCellsX / 2, gameCellsY - 1);
        */
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
        int enemySpeed = 5;
        int playerDirection = 1;

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


            /*ArrayList<PlayerBullet> bulletsToRemove = new ArrayList<>();
            ArrayList<EnemyShip> shipsToRemove = new ArrayList<>();

            for (PlayerBullet playerBullet : playerBullets) {
                int xBullet = (int) playerBullet.getMovementComponent().getxPosition();
                int yBullet = (int) playerBullet.getMovementComponent().getyPosition();

                for (EnemyShip enemyShip : enemyShips) {
                    int xShip = (int) enemyShip.getMovementComponent().getxPosition();
                    int yShip = (int) enemyShip.getMovementComponent().getyPosition();

                    // Check if bullet hits enemy ship
                    if (xBullet == xShip && yBullet == yShip) {
                        bulletsToRemove.add(playerBullet);
                        shipsToRemove.add(enemyShip);
                    }
                }
                // Check if bullet hits top of the screen
                if (yBullet == 0) {
                    bulletsToRemove.add(playerBullet);
                }
            }
            playerBullets.removeAll(bulletsToRemove);
            enemyShips.removeAll(shipsToRemove);*/

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
            /*HashSet<EnemyBullet> enemyBulletsToRemove = new HashSet<>();
            HashSet<PlayerBullet> playerBulletsToRemove = new HashSet<>();

            // Update enemy bullets
            for (EnemyBullet enemyBullet : enemyBullets) {
                int xEnemyBullet = (int) enemyBullet.getMovementComponent().getxPosition();
                int yEnemyBullet = (int) enemyBullet.getMovementComponent().getyPosition();
                // Check if enemy bullet hits player ship
                if (xEnemyBullet == (int) playerShip.getMovementComponent().getxPosition() && yEnemyBullet == (int) playerShip.getMovementComponent().getyPosition()) {
                    enemyBulletsToRemove.add(enemyBullet);
                    playerShip.updateHealth();
                    if (playerShip.getHealth() == 0) {
                        isPaused = true;
                    }
                }
                // Check if enemy bullet hits bottom
                if (yEnemyBullet == gameCellsY - 1) {
                    enemyBulletsToRemove.add(enemyBullet);
                }
                for (PlayerBullet playerBullet: playerBullets) {
                    int xPlayerBullet = (int) playerBullet.getMovementComponent().getxPosition();
                    int yPlayerBullet = (int) playerBullet.getMovementComponent().getyPosition();
                    // Check if player and enemy bullets hit each other
                    if (xEnemyBullet == xPlayerBullet && yEnemyBullet == yPlayerBullet) {
                        enemyBulletsToRemove.add(enemyBullet);
                        playerBulletsToRemove.add(playerBullet);
                    }
                }
            }
            enemyBullets.removeAll(enemyBulletsToRemove);
            playerBullets.removeAll(playerBulletsToRemove);*/


            for (EnemyBullet enemyBullet : enemyBullets) {
                enemyBullet.getMovementComponent().setTimeDelta(delta);
            }
            ArrayList<MovementComponent> components2 = enemyBullets.stream()
                    .map(EnemyBullet::getMovementComponent)
                    .collect(Collectors.toCollection(ArrayList::new));

            movementSystem.update(components2);
        }
        /*
        if (input.inputAvailable()) {
            Input.InputTypes currentInput = input.getInput();
            if (currentInput == Input.InputTypes.SPACE) {
                isPaused = !isPaused;
            }
            else if (currentInput == Input.InputTypes.UP && !isPaused) {
                playerBullets.add(abstractFactory.createPlayerBullet(playerShip.getxPosition(), playerShip.getyPosition()));
            }
            else if (!isPaused){
                playerShip.update(currentInput, gameCellsX);
            }
        }
        if (!isPaused) {
            // Update enemy ships
            double speed = 5;
            int xRightSide = gameCellsX - 1;

            for (EnemyShip enemyShip : enemyShips) {
                double xPosition = enemyShip.getxPosition();
                // Check if right side is hit
                if ((int) Math.round(xPosition) == xRightSide && dx == 1) {
                    dx = -1;
                    break;
                }
                // Check if left side is hit
                else if ((int) Math.round(xPosition) == 0 && dx == -1) {
                    dx = 1;
                    break;
                }
            }

            // Update positions, needs to be in separate loop otherwise dx gets updated in the middle of the array for example
            for (EnemyShip enemyShip: enemyShips) {
                double currentX = enemyShip.getxPosition() + dx * speed * delta;
                enemyShip.setxPosition(currentX);
            }

            // Collision checking
            double speed2 = 10;

            HashSet<PlayerBullet> bulletsToRemove = new HashSet<>();
            HashSet<EnemyShip> shipsToRemove = new HashSet<>();

            for (PlayerBullet playerBullet : playerBullets) {
                int xBullet = (int) playerBullet.getxPosition();
                int yBullet = (int) playerBullet.getyPosition();

                for (EnemyShip enemyShip : enemyShips) {
                    int xShip = (int) enemyShip.getxPosition();
                    int yShip = (int) enemyShip.getyPosition();

                    // Check if bullet hits enemy ship
                    if (xBullet == xShip && yBullet == yShip) {
                        bulletsToRemove.add(playerBullet);
                        shipsToRemove.add(enemyShip);
                    }
                }
                // Check if bullet hits top of the screen
                if (yBullet == 0) {
                    bulletsToRemove.add(playerBullet);
                }
            }
            playerBullets.removeAll(bulletsToRemove);
            enemyShips.removeAll(shipsToRemove);

            // Update player bullets
            for (PlayerBullet playerBullet : playerBullets) {
                double currentX = playerBullet.getxPosition();
                double currentY = playerBullet.getyPosition() - speed2 * delta;
                playerBullet.setxPosition(currentX);
                playerBullet.setyPosition(currentY);
            }

            // Get random enemy ship, this has a 33% chance to be a ship on the front row
            Random random = new Random();
            EnemyShip[] enemyShipsArray = enemyShips.toArray(new EnemyShip[enemyShips.size()]);
            EnemyShip randomEnemyShip = null;
            if (enemyShips.size() == 1) {
                 randomEnemyShip = enemyShipsArray[0];
            } else if (enemyShips.size() == 0) {
                isPaused = true;
                //TODO endgame
            } else {
                randomEnemyShip = enemyShipsArray[random.nextInt(enemyShipsArray.length)];
            }
            boolean frontRow = true;
            // Check if chosen ship is on the front row
            for (EnemyShip enemyShip: enemyShips) {
                if ((int) randomEnemyShip.getxPosition() == (int) enemyShip.getxPosition() && (int) randomEnemyShip.getyPosition() + 1 == (int) enemyShip.getyPosition()) {
                    frontRow = false;
                    break;
                }
            }
            // I want a 3.3% chance so 0.1
            if (frontRow && random.nextDouble() <= 0.05) {
                EnemyBullet enemyBullet =  abstractFactory.createEnemyBullet(randomEnemyShip.getxPosition(), randomEnemyShip.getyPosition() +1 );
                enemyBullets.add(enemyBullet);
            }

            HashSet<EnemyBullet> enemyBulletsToRemove = new HashSet<>();
            HashSet<PlayerBullet> playerBulletsToRemove = new HashSet<>();

            // Update enemy bullets
            for (EnemyBullet enemyBullet : enemyBullets) {
                int xEnemyBullet = (int) enemyBullet.getxPosition();
                int yEnemyBullet = (int) enemyBullet.getyPosition();
                // Check if enemy bullet hits player ship
                if (xEnemyBullet == (int) playerShip.getxPosition() && yEnemyBullet == (int) playerShip.getyPosition()) {
                    enemyBulletsToRemove.add(enemyBullet);
                    playerShip.updateHealth();
                    if (playerShip.getHealth() == 0) {
                        isPaused = true;
                    }
                }
                // Check if enemy bullet hits bottom
                if (yEnemyBullet == gameCellsY - 1) {
                    enemyBulletsToRemove.add(enemyBullet);
                }
                for (PlayerBullet playerBullet: playerBullets) {
                    int xPlayerBullet = (int) playerBullet.getxPosition();
                    int yPlayerBullet = (int) playerBullet.getyPosition();
                    // Check if player and enemy bullets hit each other
                    if (xEnemyBullet == xPlayerBullet && yEnemyBullet == yPlayerBullet) {
                        enemyBulletsToRemove.add(enemyBullet);
                        playerBulletsToRemove.add(playerBullet);
                    }
                }
            }
            enemyBullets.removeAll(enemyBulletsToRemove);
            playerBullets.removeAll(playerBulletsToRemove);

            // Update enemy bullets
            for (EnemyBullet enemyBullet : enemyBullets) {
                double currentX = enemyBullet.getxPosition();
                double currentY = enemyBullet.getyPosition() + speed2 * delta;
                enemyBullet.setxPosition(currentX);
                enemyBullet.setyPosition(currentY);
            }
        }*/
    }
}
