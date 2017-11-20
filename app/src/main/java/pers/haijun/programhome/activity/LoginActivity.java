package pers.haijun.programhome.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import pers.haijun.programhome.R;
import pers.haijun.programhome.base.BaseActivity;
import pers.haijun.programhome.mvp.login.ILoginView;
import pers.haijun.programhome.mvp.login.LoginPresenter;
import pers.haijun.programhome.utils.ConstantUtil;
import pers.haijun.programhome.utils.LoadingUtils;
import pers.haijun.programhome.utils.SpUtil;
import pers.haijun.programhome.utils.ToastUtil;

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
 * Created by HaiJun on 2017/11/12 0012 17:10
 * 登陆界面
 */
public class LoginActivity extends BaseActivity implements ILoginView{
    @ViewInject(R.id.edtPhone)
    private EditText edtPhone;
    @ViewInject(R.id.edtPassWord)
    private EditText edtPassWord;

    private LoginPresenter loginPresenter = new LoginPresenter(this);

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        x.view().inject(this);
        mContext = this;
    }

    @Override
    public void showLoading() {
        LoadingUtils.showLoading(mContext);
    }

    @Override
    public void hideLoading() {
        LoadingUtils.hideLoading();
    }

    @Override
    public String getPhoneNum() {
        return edtPhone.getText().toString().trim();
    }

    @Override
    public String getPassWord() {
        return edtPassWord.getText().toString().trim();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void requestSuccessful() {
        SpUtil.put(this, ConstantUtil.isStart, true);
        startActivity(new Intent(mContext, MainActivity.class));
    }

    @Override
    public void requestError(String errMsg) {
        ToastUtil.showShort(mContext, errMsg);
    }

    @Event({R.id.btnSignIn, R.id.btnRegister, R.id.txtForgotPassword})
    private void onClick(View v) {
        switch (v.getId()) {
            // 登录
            case R.id.btnSignIn:
                loginPresenter.login();
                break;
            // 注册
            case R.id.btnRegister:
                startActivity(new Intent(mContext, RegisteredActivity.class));
                break;
            // 忘记密码
            case R.id.txtForgotPassword:
                break;
        }
    }
}
