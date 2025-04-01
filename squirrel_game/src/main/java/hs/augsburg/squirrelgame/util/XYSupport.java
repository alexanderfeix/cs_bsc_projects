package hs.augsburg.squirrelgame.util;

import hs.augsburg.squirrelgame.board.BoardConfig;

import java.util.Random;

public class XYSupport extends XY{


    public XYSupport(int posX, int posY) {
        super(posX, posY);
    }

    public XY escapeFromEntity(XY position) {
        if(position != null){
            if (position.getX() > getX() && position.getY() > getY()) {
                return this.plus(XY.LEFT_UP);
            } else if (position.getX() > getX() && position.getY() < getY()) {
                return this.plus(XY.LEFT_DOWN);
            } else if (position.getX() > getX() && position.getY() == getY()) {
                return this.plus(XY.LEFT);
            } else if (position.getX() < getX() && position.getY() > getY()) {
                return this.plus(XY.RIGHT_UP);
            } else if (position.getX() < getX() && position.getY() < getY()) {
                return this.plus(XY.RIGHT_DOWN);
            } else if (position.getX() < getX() && position.getY() == getY()) {
                return this.plus(XY.RIGHT);
            } else if (position.getX() == getX() && position.getY() > getY()) {
                return this.plus(XY.UP);
            } else if (position.getX() == getX() && position.getY() < getY()) {
                return this.plus(XY.DOWN);
            } else if (position.getX() == getX() && position.getY() == getY()) {
                return this;
            }
        }
        return this;
    }

    public XY chaseEntity(XY position) {
        if(position != null){
            if (position.getX() > getX() && position.getY() > getY()) {
                return this.plus(XY.RIGHT_DOWN);
            } else if (position.getX() > getX() && position.getY() < getY()) {
                return this.plus(XY.RIGHT_UP);
            } else if (position.getX() > getX() && position.getY() == getY()) {
                return this.plus(XY.RIGHT);
            } else if (position.getX() < getX() && position.getY() > getY()) {
                return this.plus(XY.LEFT_DOWN);
            } else if (position.getX() < getX() && position.getY() < getY()) {
                return this.plus(XY.LEFT_UP);
            } else if (position.getX() < getX() && position.getY() == getY()) {
                return this.plus(XY.LEFT);
            } else if (position.getX() == getX() && position.getY() > getY()) {
                return this.plus(XY.DOWN);
            } else if (position.getX() == getX() && position.getY() < getY()) {
                return this.plus(XY.UP);
            } else if (position.getX() == getX() && position.getY() == getY()) {
                return this;
            }
        }
        return this;
    }

    public XY getRandomNearbyPosition() {
        Random random = new Random();
        int directionInt = random.nextInt(8);
        XY newPosition = null;
        switch (directionInt) {
            case 0 -> //Move left-up
                    newPosition = this.plus(XY.LEFT_UP);
            case 1 -> //Move up
                    newPosition = this.plus(XY.UP);
            case 2 -> //Move right-up
                    newPosition = this.plus(XY.RIGHT_UP);
            case 3 -> //Move right
                    newPosition = this.plus(XY.RIGHT);
            case 4 -> //Move right-down
                    newPosition = this.plus(XY.RIGHT_DOWN);
            case 5 -> //Move down
                    newPosition = this.plus(XY.DOWN);
            case 6 -> //Move left-down
                    newPosition = this.plus(XY.LEFT_DOWN);
            case 7 -> //Move left
                    newPosition = this.plus(XY.LEFT);
            default -> throw new IllegalStateException("Unexpected value: " + directionInt);
        }
        if (newPosition.getX() >= BoardConfig.COLUMNS || newPosition.getX() < 0
                || newPosition.getY() >= BoardConfig.ROWS || newPosition.getY() < 0) {
            return getRandomNearbyPosition();
        }
        return newPosition;
    }

    public XY getRandomPosition() {
        Random random = new Random();
        int spawnX = random.nextInt(BoardConfig.COLUMNS - 2) + 1;
        int spawnY = random.nextInt(BoardConfig.ROWS - 2) + 1;
        return new XY(spawnX, spawnY);
    }

}
