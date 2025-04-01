package hs.augsburg.squirrelgame.util;

import hs.augsburg.squirrelgame.entity.Entity;

public class MathUtils {

    public static double getDistanceFromTwoPoints(int x1, int y1, int x2, int y2){
        return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
    }

    public static double getDistanceFromTwoPoints(XY pos1, XY pos2){
        int x1 = pos1.getX();
        int y1 = pos1.getY();
        int x2 = pos2.getX();
        int y2 = pos2.getY();
        return Math.sqrt(Math.pow((Math.abs(x2-x1)), 2) + Math.pow((Math.abs(y2-y1)), 2));
    }

    public static int getEnergyLoss(Entity affectedEntity, Entity implodeEntity, int impactRadius){
        double distance = getDistanceFromTwoPoints(affectedEntity.getPosition().getX(), affectedEntity.getPosition().getY(), implodeEntity.getPosition().getX(), implodeEntity.getPosition().getY());
        System.out.println("distance is " + distance + ", Entity is " + affectedEntity.getEntityType() + ", ID is " + affectedEntity.getId());
        int impactArea = (int) (Math.pow(impactRadius, 2) * Math.PI);
        System.out.println(200 * (affectedEntity.getEnergy()/impactArea) * (1 - (int)distance/impactRadius));
        return  Math.abs(200 * (affectedEntity.getEnergy()/impactArea) * (1 - (int)distance/impactRadius));
    }



}
