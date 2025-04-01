package hs.augsburg.squirrelgame.botAPI;

public class HighScore {

    private final String name;
    private final int round;
    private final int highScore;
    private int average;

    public HighScore(String name, int round, int highScore){
        this.name = name;
        this.round = round;
        this.highScore = highScore;
    }

    public String getName() {
        return name;
    }

    public int getRound() {
        return round;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }
}
