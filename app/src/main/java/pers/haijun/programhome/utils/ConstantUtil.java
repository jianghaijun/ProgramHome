package pers.haijun.programhome.utils;

import okhttp3.MediaType;

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
 * 			   佛祖保佑       永无BUG
 *       Created by HaiJun on 2017/11/3 9:31
 */

public class ConstantUtil {
    /*基础路径*/
    public static final String BASE_URL = "http://45.77.107.130:8080";

    /*请求参数格式*/
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    /*请求超时时间*/
    public static final int CONNECT_TIME_OUT = 20000;
    /*是否已启动*/
    public static String isStart = "IsStart";

    /*登录*/
    public static final String SIGN_IN = "/ProgramHome/user/login";
    /*注册*/
    public static final String REGISTERED = "/ProgramHome/user/registeredUser";
}
