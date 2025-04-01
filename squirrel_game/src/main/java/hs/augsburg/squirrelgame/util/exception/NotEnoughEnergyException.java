package hs.augsburg.squirrelgame.util.exception;

public class NotEnoughEnergyException extends RuntimeException{

    public NotEnoughEnergyException(){
        super("Error! Energy of mini squirrel can't be bigger than energy of master squirrel!");
    }

}
