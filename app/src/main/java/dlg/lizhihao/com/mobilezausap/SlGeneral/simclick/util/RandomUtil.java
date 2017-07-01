package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util;

import java.util.Random;

/**
 * Created by sogood on 16/11/7.
 */

public class RandomUtil {

    private static Random r = new Random();

    /**
     * 按概率随机判断是否执行操作
     * @param rand
     * @return
     */
    public static boolean isHit(int rand, int total) {
        int p = r.nextInt(total);
        if (p > rand) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * [min, max)
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(int min, int max) {
        if (min == max) {
            return min;
        }
        int s = r.nextInt(max)%(max-min+1) + min;
        return s;
    }

}
