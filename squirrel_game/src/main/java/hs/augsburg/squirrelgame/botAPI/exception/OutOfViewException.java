package hs.augsburg.squirrelgame.botAPI.exception;

public class OutOfViewException extends RuntimeException{

    public OutOfViewException(){
        super("The request is out of view!");
    }

}
