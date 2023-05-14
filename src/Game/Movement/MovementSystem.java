package Game.Movement;


import java.util.ArrayList;

public class MovementSystem {
    public void update(ArrayList<MovementComponent> components) {
        for (MovementComponent movementComponent: components) {
            float timeDelta = movementComponent.getTimeDelta();
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
