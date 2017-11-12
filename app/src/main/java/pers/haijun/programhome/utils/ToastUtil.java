package pers.haijun.programhome.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 *                     _ooOoo_
 *                    o8888888o
 *                    88" . "88
 *                    (| -_- |)
 *                    O\  =  /O
 *                 ____/`---'\____
 *               .'  \\|     |//  `.
 *              /  \\|||  :  |||//  \
 *             /  _||||| -:- |||||-  \
 *             |   | \\\  -  /// |   |
 *             | \_|  ''\---/''  |   |
 *             \  .-\__  `-`  ___/-. /
 *           ___`. .'  /--.--\  `. . __
 *        ."" '<  `.___\_<|>_/___.'  >'"".
 *       | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *       \  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 *                     `=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *              佛祖保佑       永无BUG
 * Created by HaiJun on 2017/11/12 0012 19:25
 */
public class ToastUtil {
    private ToastUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static Toast toast;
    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow) {
            toast = Toast.makeText(context, message, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow) {
            toast = Toast.makeText(context, message, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
