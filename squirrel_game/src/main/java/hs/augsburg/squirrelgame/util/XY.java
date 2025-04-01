package hs.augsburg.squirrelgame.util;


public class XY {

    public static final XY ZERO_ZERO = new XY(0, 0);
    public static final XY RIGHT = new XY(1, 0);
    public static final XY LEFT = new XY(-1, 0);
    public static final XY UP = new XY(0, -1);
    public static final XY DOWN = new XY(0, 1);
    public static final XY RIGHT_UP = new XY(1, -1);
    public static final XY RIGHT_DOWN = new XY(1, 1);
    public static final XY LEFT_UP = new XY(-1, -1);
    public static final XY LEFT_DOWN = new XY(-1, 1);

    private final int x;
    private final int y;

    public XY(int posX, int posY) {
        this.x = posX;
        this.y = posY;
    }

    public XY plus(XY xy){
        int newX = getX() + xy.getX();
        int newY = getY() + xy.getY();
        return new XY(newX, newY);
    }

    public XY minus(XY xy){
        int newX = getX() - xy.getX();
        int newY = getY() - xy.getY();
        return new XY(newX, newY);
    }

    public XY times(int factor){
        int newX = getX() * factor;
        int newY = getY() * factor;
        return new XY(newX, newY);
    }

    public double length(){
        return 0.;
    }

    public double distanceFrom(XY xy){
        int xDiff = Math.abs(xy.getX() - getX());
        int yDiff = Math.abs(xy.getY() - getY());
        return Math.pow(xDiff, 2) + Math.pow(yDiff, 2);
    }

    public XYSupport getUtils(){
        return new XYSupport(getX(), getY());
    }

    @Override
    public String toString() {
        return getX() + ", " + getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
