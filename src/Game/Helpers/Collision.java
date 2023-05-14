package Game.Helpers;

import Game.Enemy.EnemyBullet;
import Game.Enemy.EnemyShip;
import Game.Movement.MovementComponent;
import Game.Player.PlayerBullet;
import Game.Player.PlayerShip;

import java.util.ArrayList;
import java.util.HashSet;

public class Collision {
    private int gameCellsX;
    private int gameCellsY;
    public Collision(int gameCellsX, int gameCellsY) {
        this.gameCellsX = gameCellsX;
        this.gameCellsY = gameCellsY;
    }
    public boolean rightBorderHit(MovementComponent component) {
        double xPosition = component.getxPosition();
        return (int) Math.round(xPosition) == gameCellsX - 1;
    }
    public boolean leftBorderHit(MovementComponent component) {
        double xPosition = component.getxPosition();
        return (int) Math.round(xPosition) == 0;
    }
    public void playerBullet(ArrayList<PlayerBullet> playerBullets, ArrayList<EnemyShip> enemyShips) {
        ArrayList<PlayerBullet> playerBulletsToRemove = new ArrayList<>();
        ArrayList<EnemyShip> enemyShipsToRemove = new ArrayList<>();

        for (PlayerBullet playerBullet: playerBullets) {
            int xPlayerBullet = (int) playerBullet.getMovementComponent().getxPosition();
            int yPlayerBullet = (int) playerBullet.getMovementComponent().getyPosition();
            // Check if bullet hits top of the screen
            if (yPlayerBullet == 0) {
                playerBulletsToRemove.add(playerBullet);
                continue; // Bullet can't hit top of the screen and hit a ship
            }

            for (EnemyShip enemyShip: enemyShips) {
                int xEnemyShip = (int) enemyShip.getMovementComponent().getxPosition();
                int yEnemyShip = (int) enemyShip.getMovementComponent().getyPosition();

                // Check if bullet hits enemy ship
                if (xPlayerBullet == xEnemyShip && yPlayerBullet == yEnemyShip) {
                    playerBulletsToRemove.add(playerBullet);
                    enemyShipsToRemove.add(enemyShip);
                }
            }
        }
        playerBullets.removeAll(playerBulletsToRemove);
        enemyShips.removeAll(enemyShipsToRemove);
    }
    public void enemyBullet(ArrayList<EnemyBullet> enemyBullets, ArrayList<PlayerBullet> playerBullets, PlayerShip playerShip) {
        HashSet<EnemyBullet> enemyBulletsToRemove = new HashSet<>();
        HashSet<PlayerBullet> playerBulletsToRemove = new HashSet<>();

        for (EnemyBullet enemyBullet: enemyBullets) {
            int xEnemyBullet = (int) enemyBullet.getMovementComponent().getxPosition();
            int yEnemyBullet = (int) enemyBullet.getMovementComponent().getyPosition();

            // Check if enemy bullet hits bottom
            if (yEnemyBullet == gameCellsY - 1) {
                enemyBulletsToRemove.add(enemyBullet);
                continue; // Enemy bullet can't hit bottom and hit player ship
            }

            // Check if enemy bullet hits player ship
            if (xEnemyBullet == (int) playerShip.getMovementComponent().getxPosition() && yEnemyBullet == (int) playerShip.getMovementComponent().getyPosition()) {
                enemyBulletsToRemove.add(enemyBullet);
                playerShip.updateHealth();
                if (playerShip.getHealth() == 0) {
                    //TODO death
                }
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
    playerBullets.removeAll(playerBulletsToRemove);
    }
}
