package pers.haijun.programhome.mvp.register;

import android.os.Handler;
import android.text.TextUtils;

import pers.haijun.programhome.R;
import pers.haijun.programhome.listener.PromptListener;
import pers.haijun.programhome.utils.CheckPhoneUtil;

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
 * Created by HaiJun on 2017/11/13 0013 23:01
 */

public class RegisteredPresenter {
    private IRegisteredView registeredView;
    private IRegisteredBiz registeredBiz;

    private Handler handler = new Handler();
    private int times = 90;

    public RegisteredPresenter(IRegisteredView registeredView){
        this.registeredView = registeredView;
        this.registeredBiz = new RegisteredBiz();
    }

    /**
     * 发送短信
     */
    public void sendMsg(){
        if (TextUtils.isEmpty(registeredView.getUserPhone())) {
            registeredView.requestError(registeredView.getContext().getString(R.string.phone_num_can_not_empty));
        } else if (!CheckPhoneUtil.isPhone(registeredView.getUserPhone())) {
            registeredView.requestError(registeredView.getContext().getString(R.string.phone_num_error));
        } else {
            registeredView.setTime(times + "s");

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    times--;
                    if (times == 0) {
                        times = 90;
                        registeredView.setTime(registeredView.getContext().getString(R.string.send_msg));
                        handler.removeCallbacks(this);
                    } else {
                        registeredView.setTime(times + "s");
                        handler.postDelayed(this, 1000);
                    }
                }
            };

            handler.postAtTime(runnable, 1000);

            registeredBiz.sendMsg(registeredView.getContext(), registeredView.getUserPhone(), new PromptListener() {
                @Override
                public void promptMessage(String msg) {
                    if (msg.contains("成功")) {
                        registeredView.sendMsgSuccessful(msg);
                    } else {
                        registeredView.requestError(msg);
                    }
                }
            });
        }
    }

    /**
     * 注册
     */
    public void registered(){
        registeredView.showLoading();
        registeredBiz.registered(registeredView.getContext(), registeredView.getUserPhone(), registeredView.getPassword(), registeredView.getQueryPassword(), registeredView.getMsgCode(), new PromptListener() {
            @Override
            public void promptMessage(String msg) {
                if (msg.contains("成功")) {
                    registeredView.requestSuccessful();
                } else {
                    registeredView.requestError(msg);
                }
                registeredView.hideLoading();
            }
        });
    }
}
