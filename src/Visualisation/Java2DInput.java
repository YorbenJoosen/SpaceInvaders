package Visualisation;


import Game.Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Java2DInput extends Input {
    private LinkedList<InputTypes> keyInputs;
    public Java2DInput(GraphicsContext graphicsContext) {
        graphicsContext.getFrame().addKeyListener(new KeyInputAdapter());
        keyInputs = new LinkedList<>();
    }
    @Override
    public boolean inputAvailable() {
        return keyInputs.size() > 0;
    }
    @Override
    public InputTypes getInput() {return keyInputs.poll();}
    /*@Override
    public InputTypes getInput() {
        return keyInputs.get(0);
    }
    @Override
    public void removeInput() {
        keyInputs.removeFirst();
    }*/
    class KeyInputAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            switch (keycode) {
                case KeyEvent.VK_LEFT -> keyInputs.add(InputTypes.LEFT);
                case KeyEvent.VK_RIGHT -> keyInputs.add(InputTypes.RIGHT);
                case KeyEvent.VK_DOWN -> keyInputs.add(InputTypes.DOWN);
                case KeyEvent.VK_UP -> keyInputs.add(InputTypes.UP);
                case KeyEvent.VK_SPACE -> keyInputs.add(InputTypes.SPACE);
            }
        }
    }
}
