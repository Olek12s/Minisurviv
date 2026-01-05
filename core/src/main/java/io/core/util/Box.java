package io.core.util;

public class Box
{
    public static boolean overlaps(
            float ax0, float ay0, float ax1, float ay1,
            float bx0, float by0, float bx1, float by1
    ) {
        return ax1 > bx0 && ax0 < bx1 &&
                ay1 > by0 && ay0 < by1;
    }

}
