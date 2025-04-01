package hs.augsburg.squirrelgame.botAPI.exception;

public class ImpactRadiusOutOfBoundsException extends RuntimeException{

    public ImpactRadiusOutOfBoundsException(String valid){
        super("The impact radius is out of bounds! Valid: " + valid);
    }

}
