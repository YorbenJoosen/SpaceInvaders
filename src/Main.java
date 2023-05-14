import Game.AbstractFactory;
import Game.Game;
import Visualisation.Java2DFactory;

public class Main {
    public static void main(String[] args) {
        AbstractFactory abstractFactory = new Java2DFactory();
        Game game = new Game(abstractFactory);
        game.run();
    }
}
