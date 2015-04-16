package util;

import java.awt.Color;

/**
 * Une classe assez banale qui ne nous sert que pour obtenir des couleurs
 * differentes en continu.
 */
public class ColorSet {
    public static final int MAXCOLOR = 19;

    static final int colors[][] = 
    { { 128, 128, 128 }, { 160, 160, 160 }, { 195, 195, 195 }, { 240, 50, 27 }, 
      { 50, 200, 100 }, { 45, 100, 205 }, { 180, 90, 220 }, { 235, 225, 150 }, 
      { 210, 140, 80 }, { 150, 190, 215 }, { 155, 30, 40 }, { 30, 80, 50 }, 
      { 255, 0, 0 }, { 0, 255, 0 }, { 0, 0, 255 }, { 255, 255, 100 }, 
      { 100, 255, 255 }, { 255, 100, 255 }, { 255, 255, 255 } };

    public static Color getColor(int n) {
        return new Color(colors[n % ColorSet.MAXCOLOR][0], 
                         colors[n % ColorSet.MAXCOLOR][1], 
                         colors[n % ColorSet.MAXCOLOR][2]);
    }
}
