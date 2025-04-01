package hs.augsburg.squirrelgame.game;

import hs.augsburg.squirrelgame.board.Board;
import hs.augsburg.squirrelgame.board.FlattenedBoard;

public class State {

    private Board board;
    private int highScore = 0;

    public State(Board board) {
        setBoard(board);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public FlattenedBoard getFlattenedBoard() {
        return getBoard().flatten();
    }
}
