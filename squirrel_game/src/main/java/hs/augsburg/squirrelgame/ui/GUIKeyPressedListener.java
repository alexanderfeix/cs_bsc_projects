package hs.augsburg.squirrelgame.ui;

import hs.augsburg.squirrelgame.util.Direction;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class GUIKeyPressedListener extends FxUI implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent keyEvent) {
        String keyTyped = keyEvent.getText().toUpperCase();
        switch (keyTyped) {
            case "UP", "W" -> setNextDirection(Direction.UP);
            case "LEFT", "A" -> setNextDirection(Direction.LEFT);
            case "DOWN", "S" -> setNextDirection(Direction.DOWN);
            case "RIGHT", "D" -> setNextDirection(Direction.RIGHT);
        }

    }
}
