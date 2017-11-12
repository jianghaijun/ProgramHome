package pers.haijun.programhome.model;

import android.text.TextUtils;

import pers.haijun.programhome.bean.LoginBean;

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
 * Created by HaiJun on 2017/11/12 0012 21:34
 */

public class LoginModel {
    private String code;
    private String message;
    private LoginBean result;

    public String getCode() {
        return TextUtils.isEmpty(code) ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return TextUtils.isEmpty(message) ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginBean getResult() {
        return result == null ? new LoginBean() : result;
    }

    public void setResult(LoginBean result) {
        this.result = result;
    }
}
