package pers.haijun.programhome.mvp.register;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pers.haijun.programhome.R;
import pers.haijun.programhome.base.BaseMode;
import pers.haijun.programhome.listener.PromptListener;
import pers.haijun.programhome.utils.CheckPhoneUtil;
import pers.haijun.programhome.utils.ConstantUtil;
import pers.haijun.programhome.utils.JsonUtils;

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

public class RegisteredBiz implements IRegisteredBiz{
    private String sPwsd;
    private String sQpwsd;
    private String sPhone;
    private String sMsgCode;
    private Activity mActivity;
    private PromptListener listener;

    /**
     * 注册
     * @param mContext
     * @param userPhone
     * @param password
     * @param queryPassword
     * @param msgCode
     * @param promptListener
     */
    @Override
    public void registered(Context mContext, String userPhone, String password, String queryPassword, String msgCode, PromptListener promptListener) {
        this.mActivity = (Activity) mContext;
        this.sPhone = userPhone;
        this.sPwsd = password;
        this.sMsgCode = msgCode;
        this.sQpwsd = queryPassword;
        this.listener = promptListener;

        if (TextUtils.isEmpty(sPhone)) {
            listener.promptMessage(mActivity.getString(R.string.phone_num_can_not_empty));
        } else if (!CheckPhoneUtil.isPhone(sPhone)) {
            listener.promptMessage(mActivity.getString(R.string.phone_num_error));
        } else if (TextUtils.isEmpty(sMsgCode)) {
            listener.promptMessage(mActivity.getString(R.string.verification_code_can_not_empty));
        } else {
            checkMsgCode();
        }
    }

    /**
     * 发送验证码
     * @param mContext
     * @param userPhone
     * @param promptListener
     */
    @Override
    public void sendMsg(Context mContext, String userPhone, PromptListener promptListener) {
        this.mActivity = (Activity) mContext;
        this.sPhone = userPhone;
        this.listener = promptListener;

        if (TextUtils.isEmpty(sPhone)) {
            listener.promptMessage(mActivity.getString(R.string.phone_num_can_not_empty));
        } else if (!CheckPhoneUtil.isPhone(sPhone)) {
            listener.promptMessage(mActivity.getString(R.string.phone_num_error));
        } else {
            // 创建EventHandler对象
            EventHandler eventHandler = new EventHandler() {
                public void afterEvent(int event, int result, Object data) {
                    if (data instanceof Throwable) {
                        Throwable throwable = (Throwable)data;
                        final String detali = throwable.getMessage();

                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String msg = null;
                                try {
                                    JSONObject obj = new JSONObject(detali);
                                    msg = obj.getString("detail");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                listener.promptMessage(msg);
                            }
                        });
                    } else {
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            // 处理你自己的逻辑
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.promptMessage(mActivity.getString(R.string.send_msg_successful));
                                }
                            });
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (TextUtils.isEmpty(sPwsd)) {
                                listener.promptMessage(mActivity.getString(R.string.password_can_not_empty));
                            } else if (!sPwsd.equals(sQpwsd)) {
                                listener.promptMessage(mActivity.getString(R.string.password_inconsistency));
                            } else {
                                registeredUser();
                            }
                        }
                    }
                }
            };

            // 注册监听器
            SMSSDK.registerEventHandler(eventHandler);
            // 发送手机验证码
            SMSSDK.getVerificationCode("86",sPhone);
        }
    }

    /**
     * 验证验证码
     */
    private void checkMsgCode() {
        SMSSDK.submitVerificationCode("86", sPhone, sMsgCode);
    }

    /**
     * 注册用户
     */
    private void registeredUser() {
        OkHttpClient client = new OkHttpClient();
        JSONObject obj = new JSONObject();
        try {
            obj.put("userPhone", sPhone);
            obj.put("password", sPwsd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(ConstantUtil.JSON, obj.toString());
        Request request = new Request.Builder()
                .url(ConstantUtil.BASE_URL + ConstantUtil.REGISTERED)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.promptMessage(mActivity.getString(R.string.server_error));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String data = response.body().string();
                Log.d("Msg", data);
                if (!JsonUtils.isGoodJson(data)) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.promptMessage(mActivity.getString(R.string.json_error));
                        }
                    });
                } else {
                    final BaseMode baseModel = gson.fromJson(data, BaseMode.class);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.promptMessage(baseModel.getMessage());
                        }
                    });
                }
            }
        });
    }
}
