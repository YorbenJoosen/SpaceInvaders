package Game;

public abstract class Input {
    public enum InputTypes {LEFT, RIGHT, UP, DOWN, SPACE};
    public abstract boolean inputAvailable();
    public abstract InputTypes getInput();
    //public abstract  void removeInput();
}
