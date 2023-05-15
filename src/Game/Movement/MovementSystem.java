package Game.Movement;


import Game.Entity;

import java.util.ArrayList;

public class MovementSystem {
    public void update(ArrayList<? extends Entity> entities, float timeDelta) {
        for (Entity entity: entities) {
            //TODO getSpeed function in Entity that gets speed from config file
            MovementComponent movementComponent = entity.getMovementComponent();
            double xPosition = movementComponent.getxPosition();
            double yPosition = movementComponent.getyPosition();
            double xDirection = movementComponent.getxDirection();
            double yDirection = movementComponent.getyDirection();
            double xSpeed = movementComponent.getxSpeed();
            double ySpeed = movementComponent.getySpeed();

            double xPositionNew = xPosition + timeDelta * xDirection * xSpeed;
            double yPositionNew = yPosition + timeDelta * yDirection * ySpeed;
            movementComponent.setxPosition(xPositionNew);
            movementComponent.setyPosition(yPositionNew);
        }
    }
}
