package pers.haijun.programhome.mvp.login;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pers.haijun.programhome.R;
import pers.haijun.programhome.listener.PromptListener;
import pers.haijun.programhome.model.LoginModel;
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
 * Created by HaiJun on 2017/11/12 0012 19:11
 */

public class LoginBiz implements ILoginBiz{

    @Override
    public void Login(Context mContext, String phoneNum, String password, PromptListener promptListener) {
        String loginFlag = "";
        if (TextUtils.isEmpty(phoneNum)) {
            loginFlag = mContext.getString(R.string.phone_num_can_not_empty);
            promptListener.promptMessage(loginFlag);
        } else if (!CheckPhoneUtil.isPhone(phoneNum)) {
            loginFlag = mContext.getString(R.string.phone_num_error);
            promptListener.promptMessage(loginFlag);
        } else if (TextUtils.isEmpty(password)) {
            loginFlag = mContext.getString(R.string.password_can_not_empty);
            promptListener.promptMessage(loginFlag);
        } else {
            signIn((Activity) mContext, phoneNum, password, promptListener);
        }
    }

    /**
     * 登录
     * @param phoneNum
     * @param password
     * @return
     */
    private void signIn(final Activity mContext, String phoneNum, String password, final PromptListener promptListener){
        OkHttpClient client = new OkHttpClient();
        JSONObject obj = new JSONObject();
        try {
            obj.put("userPhone", phoneNum);
            obj.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(ConstantUtil.JSON, obj.toString());

        Request request = new Request.Builder()
                .url(ConstantUtil.BASE_URL + ConstantUtil.SIGN_IN)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        promptListener.promptMessage(mContext.getString(R.string.server_error));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String data = response.body().string();
                if (!JsonUtils.isGoodJson(data)) {
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            promptListener.promptMessage(mContext.getString(R.string.json_error));
                        }
                    });
                } else {
                    final LoginModel loginModel = gson.fromJson(data, LoginModel.class);

                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            promptListener.promptMessage(loginModel.getMessage());
                        }
                    });
                }
            }
        });
    }

}
