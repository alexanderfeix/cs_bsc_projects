package hs.augsburg.squirrelgame.botAPI.exception;

public class SpawnException extends RuntimeException{

    public SpawnException(){
        super("Entity cant be spawned!");
    }
}
