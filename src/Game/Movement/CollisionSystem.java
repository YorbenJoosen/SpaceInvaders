package Game.Movement;

import Game.Enemy.EnemyBoss;
import Game.Enemy.EnemyBullet;
import Game.Enemy.EnemyShip;
import Game.Entity;
import Game.Player.PlayerBullet;
import Game.Player.PlayerShip;

import java.util.ArrayList;

public class CollisionSystem {
    private int gameCellsX;
    private int gameCellsY;
    private int dx = 1;
    public CollisionSystem(int gameCellsX, int gameCellsY) {
        this.gameCellsX = gameCellsX;
        this.gameCellsY = gameCellsY;
    }

    public void collisionBetweenTwoEntities(ArrayList<? extends Entity> firstEntities, ArrayList<? extends Entity> secondEntities) {
        ArrayList<Entity> firstEntitiesToRemove = new ArrayList<>();
        ArrayList<Entity> secondEntitiestoRemove = new ArrayList<>();

        for (Entity firstEntity: firstEntities) {
            int xFirstEntity = (int) firstEntity.getMovementComponent().getxPosition();
            int yFirstEntity = (int) firstEntity.getMovementComponent().getyPosition();


            // Check if entity is EnemyBullet and hits bottom
            if (yFirstEntity == gameCellsY - 1 && firstEntity instanceof EnemyBullet) {
                firstEntitiesToRemove.add(firstEntity);
                continue;
            }
            // Check if entity is PlayerBullet and hits top
            else if (yFirstEntity == 0 && firstEntity instanceof PlayerBullet) {
                firstEntitiesToRemove.add(firstEntity);
                continue;
            }

            for (Entity secondEntity: secondEntities) {
                int xSecondEntity = (int) secondEntity.getMovementComponent().getxPosition();
                int ySecondEntity = (int) secondEntity.getMovementComponent().getyPosition();

                // Check if entities hit each other
                if (xFirstEntity == xSecondEntity && ySecondEntity == yFirstEntity) {
                    firstEntitiesToRemove.add(firstEntity);
                    secondEntitiestoRemove.add(secondEntity);
                }
            }
        }
        firstEntities.removeAll(firstEntitiesToRemove);
        secondEntities.removeAll(secondEntitiestoRemove);
    }
    public void playerShips(ArrayList<EnemyBullet> enemyBullets, ArrayList<PlayerShip> playerShips) {
        ArrayList<EnemyBullet> enemyBulletsToRemove = new ArrayList<>();

        for (EnemyBullet enemyBullet: enemyBullets) {
            int xEnemyBullet = (int) enemyBullet.getMovementComponent().getxPosition();
            int yEnemyBullet = (int) enemyBullet.getMovementComponent().getyPosition();

            for (PlayerShip playerShip: playerShips) {
                int xPlayerShip = (int) playerShip.getMovementComponent().getxPosition();
                int yPlayerShip = (int) playerShip.getMovementComponent().getyPosition();

                if (xEnemyBullet == xPlayerShip && yEnemyBullet == yPlayerShip) {
                    enemyBulletsToRemove.add(enemyBullet);
                    playerShip.updateHealth();
                }
            }
        }
        enemyBullets.removeAll(enemyBulletsToRemove);
    }
    public void enemyShips(ArrayList<EnemyShip> enemyShips) {
        boolean collision = false;
        for (EnemyShip enemyShip: enemyShips) {
            int xPosition = (int) enemyShip.getMovementComponent().getxPosition();
            if (xPosition == gameCellsX - 1) {
                collision = true;
                dx = -1;
                break;
            } else if (xPosition == 0) {
                collision = true;
                dx = 1;
                break;
            }
        } if (collision) {
            for (EnemyShip enemyShip: enemyShips) {
                enemyShip.getMovementComponent().setxDirection(dx);
            }
        }
    }
    public void enemyBosses(ArrayList<EnemyBoss> enemyBosses) {
        boolean collision = false;
        for (EnemyBoss enemyBoss: enemyBosses) {
            int xLeftPosition = (int) enemyBoss.getMovementComponent().getxPosition();
            int xRightPosition = xLeftPosition + enemyBoss.getSizeX() - 1;
            if (xRightPosition == gameCellsX - 1) {
                collision = true;
                dx = -1;
                break;
            } else if (xLeftPosition == 0) {
                collision = true;
                dx = 1;
                break;
            }
        } if (collision) {
            for (EnemyBoss enemyBoss: enemyBosses) {
                enemyBoss.getMovementComponent().setxDirection(dx);
            }
        }
    }
}
