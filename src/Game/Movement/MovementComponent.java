package Game.Movement;

public class MovementComponent {
    // Velocity in X and Y direction
    private double xSpeed;
    private double ySpeed;
    // Position in X and Y direction
    private double xPosition;
    private double yPosition;
    // Time delta
    private float timeDelta;
    // Direction
    private int xDirection;
    private int yDirection;
    public MovementComponent(double xSpeed, double ySpeed, double xPosition, double yPosition, int xDirection, int yDirection) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.xPosition = xPosition;
        this.yPosition= yPosition;
        this.xDirection = xDirection;
        this.yDirection = yDirection;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public double getxPosition() {
        return xPosition;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public float getTimeDelta() {
        return timeDelta;
    }

    public void setTimeDelta(float timeDelta) {
        this.timeDelta = timeDelta;
    }

    public int getxDirection() {
        return xDirection;
    }

    public void setxDirection(int xDirection) {
        this.xDirection = xDirection;
    }

    public int getyDirection() {
        return yDirection;
    }

    public void setyDirection(int yDirection) {
        this.yDirection = yDirection;
    }

    @Override
    public String toString() {
        return "MovementComponent{" +
                "xSpeed=" + xSpeed +
                ", ySpeed=" + ySpeed +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                ", timeDelta=" + timeDelta +
                ", xDirection=" + xDirection +
                ", yDirection=" + yDirection +
                '}';
    }
}
