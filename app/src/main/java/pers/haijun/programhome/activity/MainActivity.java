package pers.haijun.programhome.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pers.haijun.programhome.R;
import pers.haijun.programhome.utils.ConstantUtil;

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
 *       Created by HaiJun on 2017/11/2 9:01
 */
public class MainActivity extends AppCompatActivity {
    private EventHandler eventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码
        //SMSSDK.setAskPermisionOnReadContact(true);

        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    String msg = throwable.getMessage();
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 处理你自己的逻辑
                    }
                }
            }
        };

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);

        findViewById(R.id.txtSendSms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取验证码
                //SMSSDK.getVerificationCode("86","13504255657");
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .connectTimeout(ConstantUtil.CONNECT_TIME_OUT, TimeUnit.MICROSECONDS)
                        .readTimeout(ConstantUtil.CONNECT_TIME_OUT, TimeUnit.MICROSECONDS)
                        .build();
                JSONObject json = new JSONObject();
                try {
                    json.put("userPhone", "13322222222");
                    json.put("password", "123456");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/111.png");
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);

                //RequestBody body = RequestBody.create(ConstantUtil.JSON, json.toString());
                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("userPhone", "13322277622")
                        .addFormDataPart("file", "111.png", fileBody)
                        .build();

                Request request = new Request.Builder()
                        .url(ConstantUtil.BASE_URL + "/user/test")
                        .post(body)
                        .build();

                client.newCall(request).enqueue(registerCallback);
            }
        });
    }

    private Callback registerCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("error", e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.d("successful", response.body().string());
        }
    };

    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
