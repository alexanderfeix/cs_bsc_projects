package hs.augsburg.squirrelgame.util;

import java.util.ArrayList;

public class WallPatterns {

    /**
     * This class is used to store several wall patterns
     */

    /**
     * This pattern is specifically for board-size 26x26
     */
    public static ArrayList<XY> DEFAULT = new ArrayList<>(){{
        add(new XY(2, 2));
        add(new XY(2+26, 2));
        add(new XY(2+26, 2+26));
        add(new XY(2, 2+26));
        add(new XY(14, 2));add(new XY(14, 3));add(new XY(15, 3));add(new XY(16, 3));add(new XY(17, 3));
        add(new XY(14+26, 2));add(new XY(14+26, 3));add(new XY(15+26, 3));add(new XY(16+26, 3));add(new XY(17+26, 3));
        add(new XY(14+26, 2+26));add(new XY(14+26, 3+26));add(new XY(15+26, 3+26));add(new XY(16+26, 3+26));add(new XY(17+26, 3+26));
        add(new XY(14, 2+26));add(new XY(14, 3+26));add(new XY(15, 3+26));add(new XY(16, 3+26));add(new XY(17, 3+26));
        add(new XY(4, 5));add(new XY(5, 5));add(new XY(6, 5));add(new XY(4, 6));add(new XY(4, 7));add(new XY(4, 8));add(new XY(4, 9));
        add(new XY(4, 5+26));add(new XY(5, 5+26));add(new XY(6, 5+26));add(new XY(4, 6+26));add(new XY(4, 7+26));add(new XY(4, 8+26));add(new XY(4, 9+26));
        add(new XY(4+26, 5+26));add(new XY(5+26, 5+26));add(new XY(6+26, 5+26));add(new XY(4+26, 6+26));add(new XY(4+26, 7+26));add(new XY(4+26, 8+26));add(new XY(4+26, 9+26));
        add(new XY(4+26, 5));add(new XY(5+26, 5));add(new XY(6+26, 5));add(new XY(4+26, 6));add(new XY(4+26, 7));add(new XY(4+26, 8));add(new XY(4+26, 9));
        add(new XY(13, 9));add(new XY(13, 10));add(new XY(13, 11));add(new XY(13, 12));add(new XY(13, 13));
        add(new XY(13, 9+26));add(new XY(13, 10+26));add(new XY(13, 11+26));add(new XY(13, 12+26));add(new XY(13, 13+26));
        add(new XY(13+26, 9));add(new XY(13+26, 10));add(new XY(13+26, 11));add(new XY(13+26, 12));add(new XY(13+26, 13));
        add(new XY(13+26, 9+26));add(new XY(13+26, 10+26));add(new XY(13+26, 11+26));add(new XY(13+26, 12+26));add(new XY(13+26, 13+26));

        add(new XY(21, 6));add(new XY(21, 7));add(new XY(21, 8));add(new XY(21, 9));add(new XY(21, 10));add(new XY(20, 10));add(new XY(19, 10));add(new XY(19, 11));add(new XY(19, 12));add(new XY(19, 13));add(new XY(18, 13));add(new XY(18, 14));
        add(new XY(21, 6+26));add(new XY(21, 7+26));add(new XY(21, 8+26));add(new XY(21, 9+26));add(new XY(21, 10+26));add(new XY(20, 10+26));add(new XY(19, 10+26));add(new XY(19, 11+26));add(new XY(19, 12+26));add(new XY(19, 13+26));add(new XY(18, 13+26));add(new XY(18, 14+26));
        add(new XY(21+26, 6));add(new XY(21+26, 7));add(new XY(21+26, 8));add(new XY(21+26, 9));add(new XY(21+26, 10));add(new XY(20+26, 10));add(new XY(19+26, 10));add(new XY(19+26, 11));add(new XY(19+26, 12));add(new XY(19+26, 13));add(new XY(18+26, 13));add(new XY(18, 14));
        add(new XY(21+26, 6+26));add(new XY(21+26, 7+26));add(new XY(21+26, 8+26));add(new XY(21+26, 9+26));add(new XY(21+26, 10+26));add(new XY(20+26, 10+26));add(new XY(19+26, 10+26));add(new XY(19+26, 11+26));add(new XY(19+26, 12+26));add(new XY(19+26, 13+26));add(new XY(18+26, 13+26));add(new XY(18+26, 14+26));
        add(new XY(4, 13));add(new XY(4, 14));add(new XY(5, 15));add(new XY(5, 16));add(new XY(5, 17));add(new XY(5, 18));
        add(new XY(4, 13+26));add(new XY(4, 14+26));add(new XY(5, 15+26));add(new XY(5, 16+26));add(new XY(5, 17+26));add(new XY(5, 18+26));
        add(new XY(4+26, 13));add(new XY(4+26, 14));add(new XY(5+26, 15));add(new XY(5+26, 16));add(new XY(5+26, 17));add(new XY(5+26, 18));
        add(new XY(4+26, 13+26));add(new XY(4+26, 14+26));add(new XY(5+26, 15+26));add(new XY(5+26, 16+26));add(new XY(5+26, 17+26));add(new XY(5+26, 18+26));
        add(new XY(13, 19));add(new XY(14, 19));add(new XY(13, 20));add(new XY(14, 20));
        add(new XY(13, 1+26));add(new XY(14, 19+26));add(new XY(13, 20+26));add(new XY(14, 20+26));
        add(new XY(13+26, 19));add(new XY(14+26, 19));add(new XY(13+26, 20));add(new XY(14+26, 20));
        add(new XY(13+26, 19+26));add(new XY(14+26, 19+26));add(new XY(13+26, 20+26));add(new XY(14+26, 20+26));
        add(new XY(2, 21));add(new XY(2, 22));add(new XY(2, 23));
        add(new XY(2, 21+26));add(new XY(2, 22+26));add(new XY(2, 23+26));
        add(new XY(2+26, 21));add(new XY(2+26, 22));add(new XY(2+26, 23));
        add(new XY(2+26, 21+26));add(new XY(2+26, 22+26));add(new XY(2+26, 23+26));
        add(new XY(6, 23));add(new XY(7, 23));
        add(new XY(6, 23+26));add(new XY(7, 23+26));
        add(new XY(6+26, 23));add(new XY(7+26, 23));
        add(new XY(6+26, 23));add(new XY(7+26, 23));
        add(new XY(22, 21));add(new XY(23, 21));add(new XY(22, 22));add(new XY(22, 23));add(new XY(22, 24));add(new XY(22, 25));add(new XY(23, 25));add(new XY(21, 25));add(new XY(20, 25));
    }};


}
