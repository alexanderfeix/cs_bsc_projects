package hs.augsburg.squirrelgame.ui;

import hs.augsburg.squirrelgame.command.Command;
import hs.augsburg.squirrelgame.command.CommandScanner;
import hs.augsburg.squirrelgame.command.GameCommandType;
import hs.augsburg.squirrelgame.entity.Entity;
import hs.augsburg.squirrelgame.game.Game;
import hs.augsburg.squirrelgame.util.Direction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleUI implements UI {

    private static Direction nextDirection;

    private final BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
    private final CommandScanner commandScanner = new CommandScanner(GameCommandType.values(), inputReader);

    @Override
    public void render(BoardView view) {
        view.getBoard().getEntityInformation();
        printBoard(view.getGameBoard());
        setNextDirection(null);
    }

    @Override
    public Direction getNextDirection() {
        return nextDirection;
    }

    @Override
    public void setNextDirection(Direction direction) {
        nextDirection = direction;
    }

    @Override
    public Command getCommand() {
        if(Game.FPS_MODE){
            try {
                if(inputReader.ready()){
                    return commandScanner.next();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else{
            return commandScanner.next();
        }
    }

    @Override
    public void handleSquirrelDead() {

    }

    /**
     * Prints the gameBoard to the console.
     * This method should get deleted once the gui works fine.
     */
    private void printBoard(Entity[][] gameBoard) {
        for (int row = 0; row < gameBoard[0].length; row++) {
            for (int col = 0; col < gameBoard.length; col++) {
                if (gameBoard[col][row] == null) {
                    System.out.print("   ");
                    continue;
                }
                if (!gameBoard[col][row].getEntity().isAlive()) {
                    System.out.print("   ");
                    continue;
                }
                switch (gameBoard[col][row].getEntityType()) {
                    case WALL -> System.out.print("W  ");
                    case MASTER_SQUIRREL -> System.out.print("MA ");
                    case BAD_BEAST -> System.out.print("BB ");
                    case GOOD_BEAST -> System.out.print("GB ");
                    case GOOD_PLANT -> System.out.print("GP ");
                    case BAD_PLANT -> System.out.print("BP ");
                    case MINI_SQUIRREL -> System.out.print("MS ");
                    default -> System.out.print("   ");
                }
            }
            System.out.println();
        }
    }

}
