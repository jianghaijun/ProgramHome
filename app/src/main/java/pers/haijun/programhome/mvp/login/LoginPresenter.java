package pers.haijun.programhome.mvp.login;

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
 * Created by HaiJun on 2017/11/12 0012 18:59
 */

public class LoginPresenter {
    private ILoginView loginView;
    private ILoginBiz loginBiz;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        this.loginBiz = new LoginBiz();
    }

    /**
     * 登录
     */
    public void login(){
        loginView.showLoading();
        loginBiz.Login(loginView.getContext(), loginView.getPhoneNum(), loginView.getPassWord(), new PromptListener() {
            @Override
            public void promptMessage(String msg) {
                if (msg.contains("成功")) {
                    loginView.requestSuccessful();
                } else {
                    loginView.requestError(msg);
                }
                loginView.hideLoading();
            }
        });
    }
}
