package hs.augsburg.squirrelgame.ui;

import hs.augsburg.squirrelgame.board.Board;
import hs.augsburg.squirrelgame.entity.Entity;

public interface BoardView {

    Entity getEntity(int x, int y);

    Entity[][] getGameBoard();

    Board getBoard();

}
