package pers.haijun.programhome.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pers.haijun.programhome.R;
import pers.haijun.programhome.base.BaseActivity;

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
 * Created by HaiJun on 2017/11/12 0012 17:23
 * 启动页
 */
public class StartupPageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_page);

        new Handler().postAtTime(new Runnable() {
            @Override
            public void run() {
                startNextPage();
            }
        }, 2000);
    }

    /**
     * 启动下一页
     */
    private void startNextPage() {
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}
