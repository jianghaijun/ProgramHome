package pers.haijun.programhome.mvp.register;

import android.content.Context;

import pers.haijun.programhome.listener.PromptListener;

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
 * Created by HaiJun on 2017/11/13 0013 22:46
 */

public interface IRegisteredBiz {
    /**
     * 注册
     * @param mContext
     * @param userPhone
     * @param password
     * @param queryPassword
     * @param msgCode
     * @param promptListener
     */
    void registered(Context mContext, String userPhone, String password, String queryPassword, String msgCode, PromptListener promptListener);

    void sendMsg(Context mContext, String userPhone, PromptListener promptListener);
}
